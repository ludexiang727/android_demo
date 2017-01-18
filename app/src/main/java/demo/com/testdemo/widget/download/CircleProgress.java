package demo.com.testdemo.widget.download;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by ludexiang on 2017/1/12.
 */

public class CircleProgress extends View {

    private int startAngle = 270;
    private int swpe = -360;
    // 背景
    private RectF mRoundRectF;
    private final float ROUND = 20;
    private Paint mPaint;
    private RectF mArcRectF;
    private final int PROGRESS_RADIUS = 65;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#a0000000"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateSwpe();
            }
        }, 1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        Log.e("ldx", "widthMode " + widthMode + " heightMode " + heightMode + " " + (widthMode == MeasureSpec.EXACTLY) + " getX " + getX());
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        if (mRoundRectF == null) {
            mRoundRectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }

        if (mArcRectF == null) {
            mArcRectF = new RectF(getMeasuredWidth() / 2 - PROGRESS_RADIUS, getMeasuredHeight() / 2 - PROGRESS_RADIUS,
                    getMeasuredWidth() / 2 + PROGRESS_RADIUS, getMeasuredHeight() / 2 + PROGRESS_RADIUS);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 挖空圆 必须用这个创建layer
        int layerCount = canvas.saveLayer(mRoundRectF, null, Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        //dst
        // draw bg
        canvas.drawRoundRect(mRoundRectF, ROUND, ROUND, mPaint);

        // src
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);

        canvas.drawCircle(getMeasuredWidth() / 2, getHeight() / 2, getWidth() / 3, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerCount);

        paint.setColor(Color.parseColor("#c0000000"));
        canvas.drawArc(mArcRectF, startAngle, swpe, true, paint);
    }

    public void updateSwpe() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (swpe != 0) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    swpe += 10;
                    postInvalidate();
                }


            }
        }).start();
    }
}
