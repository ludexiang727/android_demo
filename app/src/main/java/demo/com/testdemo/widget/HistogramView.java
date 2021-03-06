package demo.com.testdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/1.
 */
public class HistogramView extends View {
    private static final int HISTOGRAM_WIDTH = 60;

    private Rect mRedRect;
    private Rect mBlueRect;
    private Rect mWhiteRect;

    public HistogramView(Context context) {
        this(context, null);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public void setHistogram(int bottom, int whiteHeight, int blueHeight, int redHeight) {
        mWhiteRect = new Rect(0, whiteHeight, HISTOGRAM_WIDTH, bottom);
        mBlueRect = new Rect(0, blueHeight, HISTOGRAM_WIDTH, whiteHeight);
        mRedRect = new Rect(0, redHeight, HISTOGRAM_WIDTH, blueHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        canvas.drawRect(mWhiteRect, paint);

        paint.setColor(Color.parseColor("#3890ff"));
        canvas.drawRect(mBlueRect, paint);

        paint.setColor(Color.parseColor("#fa6901"));
        canvas.drawRect(mRedRect, paint);
    }
}
