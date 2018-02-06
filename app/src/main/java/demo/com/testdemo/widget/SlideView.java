package demo.com.testdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import demo.com.testdemo.R;
import demo.com.testdemo.widget.interf.ISlideView.IViewListener;

/**
 * Created by ludexiang on 2018/sliding_view1/31.
 */

public class SlideView extends RelativeLayout {

  private final int FILL = 0;
  private final int STROKE = 1;
  private boolean isSlide = false;
  private int fillColor;
  private float radius;
  private int style; // 默认是填充
  private int strokeWidth;

  private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private RectF rectF = new RectF();
  private int mDownX;
  private int mDownY;
  private int mLastMoveX;

  private boolean isMove = false;

  private VelocityTracker mTracker;
  private float minSlop;
  private float minVelocity;
  private float maxVelocity;

  //  private AnimationDrawable mArrowDrawable;
  private ImageView arrow1, arrow2;
  private Resources mRes;
  private IViewListener mListener;

  public SlideView(Context context) {
    this(context, null);
  }

  public SlideView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideView);
    isSlide = array.getBoolean(R.styleable.SlideView_isSlide, false);
    fillColor = array.getColor(R.styleable.SlideView_fillColor, 0);
    radius = array.getDimensionPixelOffset(R.styleable.SlideView_radius, 0);
    style = array.getInt(R.styleable.SlideView_style, 0);
    strokeWidth = array.getDimensionPixelOffset(R.styleable.SlideView_stroke_width, 0);
    array.recycle();

    ViewConfiguration configuration = ViewConfiguration.get(context);
    minSlop = configuration.getScaledTouchSlop();
    minVelocity = configuration.getScaledMinimumFlingVelocity();
    maxVelocity = configuration.getScaledMaximumFlingVelocity();
    mRes = getResources();
    setWillNotDraw(false);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (isSlide) {
      if (getChildCount() > 0) {
        arrow1 = (ImageView) findViewById(R.id.slide_arrow1);
        arrow2 = (ImageView) findViewById(R.id.slide_arrow2);
//      mArrowDrawable = (AnimationDrawable) (arrow.getBackground());
//      if (mArrowDrawable != null && !mArrowDrawable.isRunning()) {
//        mArrowDrawable.start();
//      }
      }
    }
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    mPaint.setColor(fillColor);
    mPaint.setStyle(style == FILL ? Style.FILL : Style.STROKE);
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    if (style == STROKE) {
      mPaint.setStrokeWidth(strokeWidth);
    }
    rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
    canvas.drawRoundRect(rectF, radius, radius, mPaint);
    super.dispatchDraw(canvas);
  }

  @Override
  public void onWindowFocusChanged(boolean hasWindowFocus) {
    super.onWindowFocusChanged(hasWindowFocus);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (isSlide) {
      addVelocityTracker(event);
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
          mDownX = (int) (event.getX() + 0.5);
          mDownY = (int) (event.getRawY() + 0.5);
          return true;
        }
        case MotionEvent.ACTION_MOVE: {
          int x = (int) (event.getX() + 0.5f);
          int y = (int) (event.getRawY() + 0.5f);
          if (x - mDownX >= minSlop) {
            isMove = true;
          }
          if (isMove) {
            int offset = (x - mDownX) / 2;
            handleMove(offset);
          }
          break;
        }
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL: {
          int x = (int) (event.getX() + 0.5f);
          int y = (int) (event.getRawY() + 0.5f);

          mTracker.computeCurrentVelocity(1000, maxVelocity);
          boolean slideMoreHalf = getTranslationX() > getMeasuredWidth() / 2;
          boolean isFling = mTracker.getXVelocity() < minVelocity ? false : true;
          handleUp(slideMoreHalf, isFling);
          recycleVelocityTracker();
          isMove = false;
          return true;
        }
      }
    }
    return super.onTouchEvent(event);
  }

  private void handleMove(int moveX) {
    if (mLastMoveX != moveX) {
      mLastMoveX = moveX;
      int newMoveX = (int) (getTranslationX() + moveX + 0.5);
      newMoveX = newMoveX < 0 ? 0 : newMoveX;
      newMoveX = newMoveX > getMeasuredWidth() ? getMeasuredWidth() : newMoveX;
      Log.e("slide", "newMoveX " + newMoveX);
      setTranslationX(newMoveX);
      if (mListener != null) {
        mListener.move(newMoveX * 1f / getMeasuredWidth());
      }
    }
  }

  private void handleUp(boolean slideMoreHalf, boolean isFling) {
    int translate = (int) (getTranslationX() + 0.5);
    float scale = ((float) translate) / getMeasuredWidth();
    int end = isFling ? (slideMoreHalf ? getMeasuredWidth() : 0)
        : (scale > 0.5f ? getMeasuredWidth() : 0);
    int duration = Math.abs(300 * (end - translate) / getMeasuredWidth());
    goonMove(translate, end, duration);
  }

  private void addVelocityTracker(MotionEvent ev) {
    if (mTracker == null) {
      mTracker = VelocityTracker.obtain();
    }
    mTracker.addMovement(ev);
  }

  private void recycleVelocityTracker() {
    if (mTracker != null) {
      mTracker.recycle();
      mTracker = null;
    }
  }

  private void goonMove(final int from, final int to, int duration) {
    ValueAnimator animator = ValueAnimator.ofInt(from, to);
    animator.setDuration(duration);
    animator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int marginLeft = (Integer) animation.getAnimatedValue();
        setTranslationX(marginLeft);
      }
    });
    animator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        if (mListener != null && to > from) {
          mListener.moveEnd();
//          if (mArrowDrawable != null && mArrowDrawable.isRunning()) {
//            mArrowDrawable.stop();
//          }
        }
      }
    });
    animator.start();
  }

  public void setListener(IViewListener listener) {
    mListener = listener;
  }

  public void refresh() {
    setTranslationX(0);
    if (mListener != null) {
      mListener.move(0f);
    }
//    if (mArrowDrawable != null && !mArrowDrawable.isRunning()) {
//      mArrowDrawable.start();
//    }
  }
}
