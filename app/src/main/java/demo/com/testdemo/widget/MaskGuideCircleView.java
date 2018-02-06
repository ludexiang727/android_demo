package demo.com.testdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view15.
 */
public class MaskGuideCircleView extends View {

    private RectF canvasRect;

    public MaskGuideCircleView(Context context) {
        this(context, null);
    }

    public MaskGuideCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskGuideCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawBitmap();
    }
}
