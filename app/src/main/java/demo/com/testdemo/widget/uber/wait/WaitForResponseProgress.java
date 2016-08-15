package demo.com.testdemo.widget.uber.wait;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on 16/8/10.
 */
public class WaitForResponseProgress extends View {

    private boolean isOneShot;
    private int[] mProgressColorSchema = new int[]{
            getResources().getColor(android.R.color.darker_gray),
            getResources().getColor(android.R.color.holo_red_light),
            getResources().getColor(android.R.color.holo_blue_light),
            getResources().getColor(android.R.color.black),
            getResources().getColor(android.R.color.holo_green_light)};
    private int mScreenWidth;
    private final int ANIM_DURATION = 500;

    private Paint mLastPaint;
    private Paint mPaint;
    private Path mLinePath = new Path();
    private final float mPathMoveX;
    private final float mPathMoveY;
    private RectF mLeftRect = new RectF();
    private RectF mRightRect = new RectF();
    // move distance x to left
    private float mPathLeftMoveX;
    // move distance x to right
    private float mPathRightMoveX;

    private int mCurrentPaintColor;
    private int mLastPaintColor = -1;
    // title bar height
    private final float mTitleBarHeight;
    // progress height
    private final float mProgressHeight;

    private Animator mAnimator;

    public WaitForResponseProgress(Context context) {
        this(context, null);
    }

    public WaitForResponseProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitForResponseProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaitForResponseProgress);
        isOneShot = a.getBoolean(R.styleable.WaitForResponseProgress_progress_oneshot, false);
        mProgressHeight = a.getDimension(R.styleable.WaitForResponseProgress_progress_height, 10);
        mTitleBarHeight = a.getDimension(R.styleable.WaitForResponseProgress_progress_top, 90);
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mProgressHeight);

        mLastPaint = new Paint();
        mLastPaint.setAntiAlias(true);
        mLastPaint.setStyle(Paint.Style.FILL);
        mLastPaint.setStrokeWidth(mProgressHeight);

        DisplayMetrics outMetrics = getResources().getDisplayMetrics();
        mScreenWidth = outMetrics.widthPixels;

        mPathMoveX = mScreenWidth / 2;
        mPathMoveY = mTitleBarHeight + mProgressHeight;
        resetRect();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLastPaintColor != -1) {
            RectF rect = new RectF();
            rect.set(0, mTitleBarHeight, mScreenWidth, mPathMoveY);
            canvas.drawRect(rect, mLastPaint);
        }

        mLeftRect.left = mPathLeftMoveX;
        mRightRect.right = mPathRightMoveX;
        mLinePath.addRect(mLeftRect, Path.Direction.CCW);
        mLinePath.addRect(mRightRect, Path.Direction.CCW);
        canvas.drawPath(mLinePath, mPaint);
    }

    /**
     * width anim
     */
    private Animator widthAnim() {
        final ValueAnimator widthAnim = ValueAnimator.ofInt(0, mScreenWidth / 2);
        widthAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnim.setDuration(ANIM_DURATION);
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animValue = (Integer) animation.getAnimatedValue();
                mPathLeftMoveX = mPathMoveX - animValue;
                mPathRightMoveX = mPathMoveX + animValue;
                postInvalidate();
            }
        });
        widthAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (mCurrentPaintColor > mProgressColorSchema.length - 1) {
                    mCurrentPaintColor = 0;
                }
                mPaint.setColor(mProgressColorSchema[mCurrentPaintColor]);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLastPaintColor = mCurrentPaintColor;
                mLastPaint.setColor(mProgressColorSchema[mLastPaintColor]);
                mCurrentPaintColor++;
                if (!isOneShot) {
                    resetRect();
                    playAnim();
                }
            }
        });
        return widthAnim;
    }

    /**
     * reset path and RectF
     */
    private void resetRect() {
        mLinePath.reset();
        mLeftRect.setEmpty();
        mRightRect.setEmpty();
        mLeftRect.set(mPathMoveX, mTitleBarHeight, mPathMoveX, mPathMoveY);
        mRightRect.set(mPathMoveX, mTitleBarHeight, mPathMoveX, mPathMoveY);
        mPathLeftMoveX = 0f;
        mPathRightMoveX = 0f;
    }

    /**
     * play loading animation
     */
    public void playAnim() {
        release();
        mAnimator = widthAnim();
        mAnimator.start();
    }

    /**
     * release obj
     */
    public void release() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator.removeAllListeners();
            mAnimator = null;
        }
    }
}
