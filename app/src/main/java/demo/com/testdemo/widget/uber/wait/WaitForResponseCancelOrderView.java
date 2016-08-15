package demo.com.testdemo.widget.uber.wait;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by ludexiang on 16/8/11.
 */
public class WaitForResponseCancelOrderView extends View {

    private final int mScreenWidth;
    private final int mScreenHeight;
    private ICancelOrderLocation mCancelLocation;
    private int mTopMoveOffset;
    private int mCancelOpX;
    private int mCancelOpY;
    private boolean isShowPathView = false;
    private final int FOREGROUND = 10;
    private final int BACKGROUND = 8;


    public WaitForResponseCancelOrderView(Context context) {
        this(context, null);
    }

    public WaitForResponseCancelOrderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitForResponseCancelOrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final DisplayMetrics outMetrics = getResources().getDisplayMetrics();
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
        mCancelOpX = mScreenWidth / 2;
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCancelLocation != null && isShowPathView) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#0a091b"));

            Path path = new Path();
            path.reset();
            path.addCircle(mCancelOpX, mCancelOpY, mTopMoveOffset, Path.Direction.CCW);

            canvas.drawPath(path, paint);
            canvas.clipPath(path, Region.Op.REPLACE);

            // 需优化
            drawSkew45Grid(canvas);
            drawHorizontalGrid(canvas);
        }
    }

    private void drawHorizontalGrid(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2f);
        paint.setColor(Color.parseColor("#20FFFFFF"));
        // 行
        float row = 0f;
        for (int r = 0; r <= mScreenHeight / FOREGROUND; r++) {
            canvas.drawLine(0f, row * FOREGROUND, mScreenWidth, row * FOREGROUND, paint);
            row += FOREGROUND;
        }

        // 列
        int column = 0;
        for (int col = 0; col < mScreenWidth / FOREGROUND; col++) {
            canvas.drawLine(column * FOREGROUND, 0f, column * FOREGROUND, mScreenHeight, paint);
            column += FOREGROUND;
        }
        canvas.restore();
    }

    private void drawSkew45Grid(Canvas canvas) {
        canvas.save();
        canvas.save();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2f);
        paint.setColor(Color.parseColor("#10FFFFFF"));
        // 行
        float row = 0f;
        for (int r = 0; r <= mScreenHeight / BACKGROUND; r++) {
            canvas.drawLine(row * BACKGROUND, 0f, 0f, row * BACKGROUND, paint);
            row += BACKGROUND;
        }
        // 列
        int column = 0;
        for (int col = mScreenWidth; col >= 0; col--) {
            canvas.drawLine(mScreenWidth - column * BACKGROUND, 0f, mScreenWidth, column * BACKGROUND, paint);
            column += BACKGROUND;
        }
        canvas.restore();
    }

    /**
     * long click update path anim
     */
    public void updatePathAnim() {
        if (mCancelLocation != null) {
            isShowPathView = true;
            int[] location = mCancelLocation.getLocation();
            mCancelOpY = location[1];
            AnimatorSet set = new AnimatorSet();
//            ValueAnimator lr = updateLRPath();
            ValueAnimator top = updateTopPath();
            set.playTogether(/*lr,*/ top);
            set.setDuration(600);
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.start();
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 动画已经结束
                    mCancelLocation.getCancelView().setOnLongClickListener(null);
                }
            });
        }
    }

    private ValueAnimator updateLRPath() {
        ValueAnimator path = ValueAnimator.ofInt(0, mScreenWidth / 2);
        path.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (Integer) animation.getAnimatedValue();
//                mBottomMoveOffset = mLeftMoveOffset = mRightMoveOffset = offset;

                postInvalidate();
            }
        });
        return path;
    }

    private ValueAnimator updateTopPath() {
        ValueAnimator path = ValueAnimator.ofInt(0, mScreenHeight);
        path.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (Integer) animation.getAnimatedValue();
                mTopMoveOffset = offset;
                postInvalidate();
            }
        });
        return path;
    }

    public interface ICancelOrderLocation {
        int[] getLocation();

        View getCancelView();
    }

    public void setLocationListener(ICancelOrderLocation listener) {
        mCancelLocation = listener;
    }
}
