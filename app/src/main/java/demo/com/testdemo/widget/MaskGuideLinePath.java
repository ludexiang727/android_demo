package demo.com.testdemo.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view13.
 * 蒙版对应划线及圆
 */
public class MaskGuideLinePath extends View {
    public static final int LINE_HEIGHT = 380;
    private float mStartSweep = 90f;
    private float mSweepAngle = 0f;
    private int mCircleDiameter = 100;

    private Paint mPaint;
    private float mStartX;
    private int mLastLineY = 0;
    private float mContainerHeight;

    private RectF mCircleRect = new RectF();
    private ICallback iListener;
    private boolean isDrawArc = false;
    private boolean isOnlyExecute = false;

    public MaskGuideLinePath(Context context) {
        this(context, null);
    }

    public MaskGuideLinePath(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskGuideLinePath(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MaskGuideStyle);
//        mCircleDiameter = array.getDimensionPixelOffset(R.styleable.map_view_delegate, 100);
//        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

//        // 抠图
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setAntiAlias(true);
//        paint.setColor(Color.parseColor("#434343"));
//        //dst
//        RectF rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
//        int layer = canvas.saveLayer(rect, null, Canvas.MATRIX_SAVE_FLAG
//                | Canvas.CLIP_SAVE_FLAG
//                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
//                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
//                | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
//        canvas.drawRect(rect, paint);
//        //src
//        canvas.drawColor(Color.TRANSPARENT);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        canvas.drawCircle(mStartX, mContainerHeight - LINE_HEIGHT - mCircleDiameter + mCircleDiameter / sliding_view2, mCircleDiameter / sliding_view2, paint);
//        paint.setXfermode(null);
//        canvas.restoreToCount(layer);


        // draw line
        Path mLinePath = new Path();
        mLinePath.moveTo(mStartX, mContainerHeight);
        mLinePath.lineTo(mStartX, mContainerHeight - mLastLineY);
        canvas.drawPath(mLinePath, mPaint);

        if (isDrawArc) {
            Path arcPath = new Path();
            arcPath.addArc(mCircleRect, mStartSweep, mSweepAngle);
            canvas.drawPath(arcPath, mPaint);
        }
    }

    /**
     * @param startX          线的起始点
     * @param containerHeight 整个view的高度
     */
    public void setStartXY(float startX, float containerHeight) {
        mStartX = startX;
        mContainerHeight = containerHeight;
        mCircleRect.set(mStartX - mCircleDiameter / 2, containerHeight - LINE_HEIGHT - mCircleDiameter,
                mStartX + mCircleDiameter / 2, containerHeight - LINE_HEIGHT);
//        drawLineByPath();
        playAnim(0, LINE_HEIGHT, 800, 500);
    }

    /**
     * sub thread draw line
     */
    private void drawLineByPath() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mLastLineY <= LINE_HEIGHT) {
                    postInvalidate();

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mLastLineY += 10;
                }
            }
        }).start();
    }


    public void playAnim(int from, int to, long linDuration, long arcDuration) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator lineAnim = drawLineByAnim(from, to, linDuration);
        ValueAnimator arcAnim = drawArc(from > to, arcDuration);

        if (from > to) {
            // exit
            isOnlyExecute = false;
            set.playTogether(lineAnim, arcAnim);
        } else {
            set.playSequentially(lineAnim, arcAnim);
        }

        set.start();
    }

    /**
     * through anim draw line
     *
     * @param from
     * @param to
     * @param duration
     */
    private ValueAnimator drawLineByAnim(final int from, final int to, long duration) {
        ValueAnimator lineAnim = obtainValueAnim(from, to);
        lineAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animValue = (Integer) animation.getAnimatedValue();
                mLastLineY = animValue;
                postInvalidate();
            }
        });
        lineAnim.setDuration(duration);
        return lineAnim;
    }

    /**
     * @param ccw 是否是逆时针(隐藏)
     */
    private ValueAnimator drawArc(final boolean ccw, long duration) {
        isDrawArc = true;
        final ValueAnimator arcAnim = obtainValueAnim(ccw ? -360 : 0, ccw ? 0 : 360);
        if (ccw) {
            mStartSweep = 360f;
        } else {
            mStartSweep = 90f;
        }
        arcAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int sweepAngle = (Integer) animation.getAnimatedValue();
                mSweepAngle = sweepAngle;
                if (mSweepAngle >= 0 || mSweepAngle <= 0) {
                    if (iListener != null && !isOnlyExecute) {
                        isOnlyExecute = true;
                        iListener.startFrameAnim(mCircleRect, ccw);
                    }
                }
                postInvalidate();
            }
        });
        arcAnim.setDuration(duration);
        return arcAnim;
    }

    private ValueAnimator obtainValueAnim(int from, int to) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        return valueAnimator;
    }

    public void setListener(ICallback listener) {
        iListener = listener;
    }

    public interface ICallback {
        void startFrameAnim(RectF rectF, boolean isExitAnim);
    }
}
