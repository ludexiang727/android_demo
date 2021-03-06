package demo.com.testdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view2.
 */
public class BrokenLineView extends BaseScrollerView {

    private static final float HISTOGRAM_WIDTH = 60;
    private static final float CENTER_LEFT = HISTOGRAM_WIDTH / 2;

    private List<Integer> mPointList = new ArrayList<Integer>();
    private List<Integer> mPointCenter = new ArrayList<Integer>();

    private Paint mBrokeLinePaint;
    private Path mBrokePointPath = new Path();
    private int mLineColor;

    public BrokenLineView(Context context) {
        this(context, null);
    }

    public BrokenLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrokenLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BrokenLineView);
        mLineColor = a.getColor(R.styleable.BrokenLineView_broken_line_color, 0);
        a.recycle();

        mBrokeLinePaint = new Paint();
        mBrokeLinePaint.setAntiAlias(true);
        mBrokeLinePaint.setColor(mLineColor);
        mBrokeLinePaint.setStyle(Paint.Style.FILL);

        setWillNotDraw(false);

    }

    public void setBrokeLinePoint(int centerY, int whitePoint) {
        mPointList.add(whitePoint);
        mPointCenter.add(centerY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path linePath = new Path();
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(mLineColor);
        linePaint.setStrokeWidth(2f);
        linePaint.setStyle(Paint.Style.STROKE);

        linePath.reset();
        if (mPointList != null && mPointList.size() > 0) {
            for (int i = 0; i < mPointList.size(); i++) {
//                Log.e("ldx", i + "  fuck onDraw.........." + CENTER_LEFT + " " + (mPointCenter.get(i) * HISTOGRAM_WIDTH) + " y :: " + (mPointList.get(i) * 1f));
                // 划线
                if (i == 0) {
                    linePath.moveTo(CENTER_LEFT + mPointCenter.get(0) * HISTOGRAM_WIDTH, mPointList.get(0) * 1f);
                } else {
                    linePath.lineTo(CENTER_LEFT + mPointCenter.get(i) * HISTOGRAM_WIDTH, mPointList.get(i) * 1f);
                }
                // 画圆点
                mBrokePointPath.addCircle(CENTER_LEFT + mPointCenter.get(i) * HISTOGRAM_WIDTH, mPointList.get(i) * 1f, 6, Path.Direction.CCW);
            }
            canvas.drawPath(mBrokePointPath, mBrokeLinePaint);
            canvas.drawPath(linePath, linePaint);
        }

        // TODO 不加上此代码划线会出现闭合状况
        mPointList.clear();

    }
}
