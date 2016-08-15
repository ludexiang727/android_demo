package demo.com.testdemo.widget.uber.login;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ludexiang on 16/8/4.
 */
public class BackgroundCircle extends View {
    private static final int LEFT_MARGIN = 100;

    private int mCircleRadius = 50;
    private int mLineHeight = 50;

    private Paint mLinePaint;
    private Path mLinePath;

    private Paint mCirclePaint;

    public BackgroundCircle(Context context) {
        this(context, null);
    }

    public BackgroundCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);

        mLinePath = new Path();
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(3f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(0, 0, mLineHeight, mLineHeight);
        Rect circleRect = new Rect(0, 0, mLineHeight, mLineHeight);

        // row
        for (int i = 0; i < 15; i++) {
            // column
            for (int j = 0; j < 15; j++) {
                if (j % 2 == 0) {
                    drawLine(canvas, rect, i);
                    drawCircle(canvas, circleRect, i);
                } else {
                    drawCircle(canvas, i);
                    drawLine(canvas, i);
                }
            }
        }
    }

    /**
     * first draw line
     * @param canvas
     * @param rect
     * @param y
     */
    private void drawLine(Canvas canvas, Rect rect, int y) {
        // draw |
        mLinePaint.setStrokeWidth(3.0f);
        canvas.drawLine(rect.width(), mLineHeight * 3 * y, rect.width(), mLineHeight * 3 * y + mLineHeight, mLinePaint);
        // draw ——
        mLinePaint.setStrokeWidth(1.0f);
        canvas.drawLine(mLineHeight / 2, rect.height() / 2 + mLineHeight * 3 * y,
                mLineHeight + mLineHeight / 2, rect.height() / 2 + + mLineHeight * 3 * y, mLinePaint);
    }

    private void drawCircle(Canvas canvas, Rect circleRect, int y) {
        // left
        canvas.drawCircle(circleRect.width() / 2, // centerX
                mLineHeight * 2 + (mLineHeight * 3) * y, // centerY
                mCircleRadius / 2, mCirclePaint);
        // right
        canvas.drawCircle(circleRect.width(),
                (mLineHeight + circleRect.height() / 2) + (mLineHeight * 3) * y,
                mCircleRadius / 2, mCirclePaint);
        // top
        canvas.drawCircle(mLineHeight + circleRect.width() / 2,
                (mLineHeight + mCircleRadius / 2 + circleRect.height() / 2) + (mLineHeight * 3) * y,
                mCircleRadius / 2, mCirclePaint);
        // bottom
        canvas.drawCircle(circleRect.width(), (mLineHeight * 2 + circleRect.height() / 2) + (mLineHeight * 3) * y, mCircleRadius / 2, mCirclePaint);
    }

    /**
     * first draw circle
     */
    private void drawCircle(Canvas canvas, int x) {
        // left
        canvas.drawCircle(mLineHeight * 2, // centerX
                mLineHeight / 2, // centerY
                mCircleRadius / 2, mCirclePaint);
//        // right
        canvas.drawCircle(mLineHeight * 3,
                mLineHeight / 2 ,
                mCircleRadius / 2, mCirclePaint);
//        // top
        if (x != 0) {
            canvas.drawCircle(mLineHeight * 2 + mLineHeight / 2,
                    mLineHeight / 2,
                    mCircleRadius / 2, mCirclePaint);
        }
//        // bottom
        canvas.drawCircle(mLineHeight * 2 + mLineHeight / 2, mLineHeight, mCircleRadius / 2, mCirclePaint);
    }

    private void drawLine(Canvas canvas, int y) {
        // draw |
        mLinePaint.setStrokeWidth(3.0f);
        canvas.drawLine((mLineHeight * 2 + mLineHeight / 2), // startX
                (mLineHeight + mLineHeight / 2), // startY
                (mLineHeight * 2 + mLineHeight / 2), // stopX
                (mLineHeight * 2 + mLineHeight / 2), // stopY
                mLinePaint);
        // draw ——
        mLinePaint.setStrokeWidth(1.0f);
        canvas.drawLine(mLineHeight * 2,
                mLineHeight * 2,
                mLineHeight * 2 + mLineHeight,
                mLineHeight * 2,
                mLinePaint);
    }

    private final class Circle {
        int centerX;
        int centerY;
    }
}
