package demo.com.testdemo.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2018/1/31.
 */

public class SlideView extends View {

  private boolean isSlide = false;
  private int fillColor;
  private float radius;

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
    array.recycle();

    ViewConfiguration configuration = ViewConfiguration.get(context);
    minSlop = configuration.getScaledTouchSlop();
    minVelocity = configuration.getScaledMinimumFlingVelocity();
    maxVelocity = configuration.getScaledMaximumFlingVelocity();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setColor(fillColor);
    mPaint.setStyle(Style.FILL);
    rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
    canvas.drawRoundRect(rectF, radius, radius, mPaint);
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
      setTranslationX(newMoveX);
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

  private void goonMove(int from, int to, int duration) {
    ValueAnimator animator = ValueAnimator.ofInt(from, to);
    animator.setDuration(duration);
    animator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int marginLeft = (Integer) animation.getAnimatedValue();
        setTranslationX(marginLeft);
      }
    });
    animator.start();
  }
}
