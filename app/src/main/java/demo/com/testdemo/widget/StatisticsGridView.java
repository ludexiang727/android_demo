package demo.com.testdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import demo.com.testdemo.model.HistogramModel;

/**
 * Created by ludexiang on 16/7/1.
 */
public class StatisticsGridView extends BaseScrollerView {
    private Context mContext;
    private Paint mPaint;
    private LinearLayout mHistogramLayout;
    private LinearLayout mMonthdayParent;
    private BrokenLineView mBlackBrokenLine, mGreenBrokenLine, mRedBrokenLine;

    private static final int HISTOGRAM_WIDTH = 60;


    private int mCurrentMonth = 7;
    private int mCurrentMonthDays = 31;
    private int mLeftMargin = 0;
    private List<HistogramModel> mHistogramList;

    public StatisticsGridView(Context context) {
        this(context, null);
    }

    public StatisticsGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setHistogramLayout(LinearLayout histogram, LinearLayout monthdayparent,
                                   BrokenLineView blackLine, BrokenLineView greenLine,
                                   BrokenLineView redLine) {
        mHistogramLayout = histogram;
        mMonthdayParent = monthdayparent;
        mBlackBrokenLine = blackLine;
        mGreenBrokenLine = greenLine;
        mRedBrokenLine = redLine;
    }

    public void setList(List<HistogramModel> list) {
        mHistogramList = list;
    }

    public void setCurrentMonth(int currentMonth, int currentDays) {
        mCurrentMonth = currentMonth;
        mCurrentMonthDays = currentDays;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1f);
        if (mHistogramLayout != null && mHistogramLayout.getChildCount() > 0) {
            mHistogramLayout.removeAllViews();
        }

        if (mMonthdayParent != null && mMonthdayParent.getChildCount() > 0) {
            mMonthdayParent.removeAllViews();
        }

        int position = 0;
        // 行
        float row = 0f;
        for (int r = 0; r <= 31; r++) {
            canvas.drawLine(0f, row * 2.5f, mHistogramLayout.getMeasuredWidth(), row * 2.5f, mPaint);
            row += 10;
        }

        // 列
        int column = 0;
        for (int col = 0; col < mCurrentMonthDays * 2; col++) {
            canvas.drawLine(column * 2f, 0f, column * 2f, getMeasuredHeight(), mPaint);

            drawHistogramView(position, col);

            column += HISTOGRAM_WIDTH / 2;
            position = drawBrokeLine(position, col);
        }

    }

    private int drawHistogramView(final int position, int r) {
        if (r % 2 == 0) {
            int gridViewHeight = getMeasuredHeight();
            HistogramView histogramView = new HistogramView(mContext);
            histogramView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ldx", "aaaaaa position " + position);
                }
            });

            HistogramModel model = mHistogramList.get(position);
            histogramView.setHistogram(getMeasuredHeight(), gridViewHeight - model.getWhiteRectTop(),
                    gridViewHeight - model.getWhiteRectTop() - model.getBlueRectTop(),
                    gridViewHeight - model.getWhiteRectTop() - model.getBlueRectTop() - model.getRedRectTop());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.width = HISTOGRAM_WIDTH;
            params.height = getMeasuredHeight();
            mHistogramLayout.addView(histogramView, params);

            // 占位符
            if (r != (mCurrentMonthDays - 1) * 2) {
                View view = new View(mContext);
                mHistogramLayout.addView(view, params);
                histogramView.postInvalidate();
            }

            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            View viewSpec = new View(mContext);
            TextView month = new TextView(mContext);
            params.width = HISTOGRAM_WIDTH;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            month.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            month.setText("7/" + (r / 2 == 0 ? 1 : r / 2 + 1));
            mMonthdayParent.addView(month, params);
            mMonthdayParent.addView(viewSpec, params);
        }
        return position;
    }

    private synchronized int drawBrokeLine(int position, int r) {
//        if (r == 0 || r == 2 || r == 4 || r == 6 || r == 8 || r == 10) {
        if (r % 2 == 0) {
            int gridViewHeight = getMeasuredHeight();
            HistogramModel model = mHistogramList.get(position);
            mBlackBrokenLine.setBrokeLinePoint(r, gridViewHeight - model.getBlackPoint());

            mGreenBrokenLine.setBrokeLinePoint(r, gridViewHeight - model.getGreenPoint());
//
            mRedBrokenLine.setBrokeLinePoint(r, gridViewHeight - model.getRedPoint());

            position++;
        }
        return position;
    }

}
