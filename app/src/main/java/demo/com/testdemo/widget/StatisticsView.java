package demo.com.testdemo.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import demo.com.testdemo.R;
import demo.com.testdemo.model.HistogramModel;

/**
 * Created by ludexiang on 16/7/4.
 */
public class StatisticsView extends RelativeLayout {

    private LayoutInflater mInflater;

    // 柱状图parent
    private LinearLayout mHistogramParent;
    // 网格
    private StatisticsGridView mStatisticsGridView;

    private LinearLayout mMonthdayParent;

    private BrokenLineView mBlackView;
    private BrokenLineView mGreenView;
    private BrokenLineView mRedView;


    public StatisticsView(Context context) {
        this(context, null);
    }

    public StatisticsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.statistics_view_layout, this, true);

        init();
    }

    public void setLists(final List<HistogramModel> lists) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mStatisticsGridView.setHistogramLayout(mHistogramParent, mMonthdayParent, mBlackView, mGreenView, mRedView);
                mStatisticsGridView.setList(lists);
            }
        });
    }

    private void init() {

        mStatisticsGridView = (StatisticsGridView) findViewById(R.id.statistics_grid_view);
        mHistogramParent = (LinearLayout) findViewById(R.id.histogram__parent);
        mMonthdayParent = (LinearLayout) findViewById(R.id.month_day_parent);
        mBlackView = (BrokenLineView) findViewById(R.id.black_line);
        mGreenView = (BrokenLineView) findViewById(R.id.green_line);
        mRedView = (BrokenLineView) findViewById(R.id.red_line);

    }
}
