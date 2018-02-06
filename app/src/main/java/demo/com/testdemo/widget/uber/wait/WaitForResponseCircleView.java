package demo.com.testdemo.widget.uber.wait;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by ludexiang on sliding_view16/8/sliding_view16.
 */
public class WaitForResponseCircleView extends View {
    private final float mCenterX;
    private float mCenterY;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float mCircleRadius;
    private float mCircleRadiusOffset;

    public WaitForResponseCircleView(Context context) {
        this(context, null);
    }

    public WaitForResponseCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitForResponseCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final DisplayMetrics outMetrics = getResources().getDisplayMetrics();

        mCircleRadius = outMetrics.heightPixels;
        mCenterX = outMetrics.widthPixels / 2;
        mCenterY = outMetrics.heightPixels - 80;

        zoom();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#0a091b"));

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        Log.e("ldx", "cir onDra..." +mCenterX  +" " + mCenterY);
        mPath.addCircle(mCenterX, mCenterY, mCircleRadiusOffset, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
    }

    public Animator scaleAnim() {
//        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0f, 1f);
//        set.setInterpolator(new LinearInterpolator());
//        set.playTogether(scaleX, scaleY);
        ValueAnimator radius = ValueAnimator.ofFloat(0, mCircleRadius);
        radius.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleRadiusOffset = (Float) animation.getAnimatedValue();
                Log.e("ldx", "offset .. " + mCircleRadiusOffset);
                postInvalidate();
            }
        });
        return radius;
    }

    private void zoom() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0f, 0f);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(scaleX, scaleY);
        set.setDuration(0);
        set.start();
    }
}
