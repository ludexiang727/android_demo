package demo.com.testdemo.lockscreen.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2017/2/21.
 */

public class BoldTextView extends android.support.v7.widget.AppCompatTextView {

    private String mBoldStr;
    private String mNormalStr;

    private Paint mStrPaint;

    private int mBColor;
    private int mBSize;
    private int mNColor;
    private int mNSize;
    private int mMarginLeft;
    private int mMarginTop;
    private boolean isHorCenter, isBBold, isNBold;
    private int mScreenWidth;
    private int mPaddingTop;
    private float mBXScale, mNXScale;
    private String mLastBold;
    /** 上个String向上移动的距离*/
    private int mLastMoveTop = 0;
    /** 当前String向上移动的距离*/
    private int mCurMoveTop = 0;
    /** 粗体字符串的高度*/
    private float mBoldHeight;

    private RectF mRectF;
    private Paint mRectPaint;

    public BoldTextView(Context context) {
        this(context, null);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        setWillNotDraw(false);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BoldTextView);
        mBColor = array.getColor(R.styleable.BoldTextView_b_color, Color.BLACK);
        mBSize = array.getDimensionPixelSize(R.styleable.BoldTextView_b_size, 15);
        mNColor = array.getColor(R.styleable.BoldTextView_n_color, Color.LTGRAY);
        mNSize = array.getDimensionPixelSize(R.styleable.BoldTextView_n_size, 12);
        isBBold = array.getBoolean(R.styleable.BoldTextView_b_bold, false);
        isNBold = array.getBoolean(R.styleable.BoldTextView_n_bold, false);
        mBXScale = array.getFloat(R.styleable.BoldTextView_b_x_scale, 1);
        mNXScale = array.getFloat(R.styleable.BoldTextView_n_x_scale, 1);
        isHorCenter = array.getBoolean(R.styleable.BoldTextView_hor_center, false);
        mMarginLeft = array.getDimensionPixelOffset(R.styleable.BoldTextView_b_bold_x, 0);
        mMarginTop = array.getDimensionPixelOffset(R.styleable.BoldTextView_b_bold_y, 0);
        mPaddingTop = array.getDimensionPixelOffset(R.styleable.BoldTextView_n_padding_top, 0);
        array.recycle();

        mStrPaint = new Paint();
        mStrPaint.setAntiAlias(true);
        mStrPaint.setStyle(Paint.Style.FILL);
        mStrPaint.setTextAlign(Paint.Align.CENTER);

        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setColor(Color.WHITE);
        mRectPaint.setAntiAlias(true);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
    }

    /**
     * 设置要画的文案
     * @param bold
     * @param normal
     */
    public void setTextStr(@NonNull String bold, String normal) {
        mBoldStr = bold;
        mNormalStr = normal;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBoldStr == null || "".equals(mBoldStr)) {
            return;
        }
        Rect rect = new Rect();
        mStrPaint.setColor(mBColor);
        mStrPaint.setTextSize(mBSize);
        mStrPaint.setFakeBoldText(isBBold);
        mStrPaint.setTextScaleX(mBXScale);
        mStrPaint.getTextBounds(mBoldStr, 0, mBoldStr.length(), rect);
        float bWidth = rect.width() ;
        Paint.FontMetrics metrics = mStrPaint.getFontMetrics();
        mBoldHeight = rect.height();

        mRectF = new RectF(0, mBoldHeight, getWidth(), mMarginTop + mBoldHeight);
        drawBold(canvas);
        drawRect(canvas);
        drawNormal(canvas, bWidth, metrics);
    }

    /**
     * 画粗字体
     * @param canvas
     */
    private void drawBold(Canvas canvas) {
        // drawText 中的x , y 坐标是设置文案在什么位置显示的中心点而不是从(x,y)开始画
        int count = canvas.save();
        if (!mBoldStr.equals(mLastBold)) {
            upAnim();
            if (isHorCenter) {
                canvas.drawText(mBoldStr, mScreenWidth / 2, mMarginTop, mStrPaint);
            } else {
                if (mLastBold != null) {
                    canvas.drawText(mLastBold, mMarginLeft, mLastMoveTop, mStrPaint);
                }
                canvas.drawText(mBoldStr, mMarginLeft, mCurMoveTop, mStrPaint);
            }
        } else {
            if (isHorCenter) {
                canvas.drawText(mBoldStr, mScreenWidth / 2, mMarginTop, mStrPaint);
            } else {
                canvas.drawText(mBoldStr, mMarginLeft, mMarginTop, mStrPaint);
            }
        }
        canvas.restoreToCount(count);
    }

    /**
     * 画白色背景
     **/
    private void drawRect(Canvas canvas) {
        canvas.drawRect(mRectF, mRectPaint);
    }

    /**
     * 画普通字体
     * @param canvas
     * @param bWidth
     * @param metrics
     */
    private void drawNormal(Canvas canvas, float bWidth, Paint.FontMetrics metrics) {
        if (mNormalStr != null && !"".equals(mNormalStr)) {
            mStrPaint.setColor(mNColor);
            mStrPaint.setTextSize(mNSize);
            mStrPaint.setFakeBoldText(isNBold);
            mStrPaint.setTextAlign(Paint.Align.CENTER);
            mStrPaint.setTextScaleX(mNXScale);
            float nWidth = mStrPaint.measureText(mNormalStr);
            if (isHorCenter) {
                canvas.drawText(mNormalStr, mScreenWidth / 2, metrics.bottom * 5 + mMarginTop + mPaddingTop, mStrPaint);
            } else {
                canvas.drawText(mNormalStr, mMarginLeft + Math.abs((bWidth - nWidth)) / 10,
                        metrics.bottom * 1.5f + mMarginTop + mPaddingTop, mStrPaint);
            }
        }
    }

    private boolean isStarting = false;
//    AnimatorSet up;
    private void upAnim() {
//        if (up == null) {
        if (!isStarting) {
            isStarting = true;
            AnimatorSet up = new AnimatorSet();
            ValueAnimator curMoveAnim = curMoveAnim(mMarginTop + mBoldHeight, mMarginTop);
            ValueAnimator lastMoveAnim = lastMoveAnim(mMarginTop, 0);

            up.setDuration(500);
            up.setInterpolator(new AccelerateInterpolator());
            up.playTogether(curMoveAnim, lastMoveAnim);
            up.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mLastBold = mBoldStr;
//                    up = null;
                    isStarting = false;
                }
            });
            up.start();
//        }
        }
    }

    private ValueAnimator curMoveAnim(float from, float to) {
        ValueAnimator move = ValueAnimator.ofFloat(from, to);
        move.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mCurMoveTop = (int) value.floatValue();
                postInvalidate();
            }
        });
        return move;
    }

    private ValueAnimator lastMoveAnim(float from, float to) {
        ValueAnimator move = ValueAnimator.ofFloat(from, to);
        move.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mLastMoveTop = (int) value.floatValue();
                postInvalidate();
            }
        });
        return move;
    }
}
