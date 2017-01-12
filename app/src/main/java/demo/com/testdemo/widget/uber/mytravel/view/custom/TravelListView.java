package demo.com.testdemo.widget.uber.mytravel.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import demo.com.testdemo.widget.uber.mytravel.adapter.TravelListViewAdapter;
import demo.com.testdemo.widget.uber.mytravel.base.BaseTravelListView;
import demo.com.testdemo.widget.uber.mytravel.model.TravelModel;
import demo.com.testdemo.widget.uber.mytravel.view.IListView;

/**
 * Created by ludexiang on 16/8/19.
 */
public class TravelListView extends BaseTravelListView implements IListView {

    private TravelListViewAdapter mAdapter;

    public TravelListView(Context context) {
        this(context, null);
    }

    public TravelListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TravelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mAdapter = new TravelListViewAdapter(context);
    }

    @Override
    public void setData(List<TravelModel> data) {
        mAdapter.setData(data);
        // TODO add footbar
        setAdapter(mAdapter);
    }

    @Override
    public void notifyUpdate() {
        mAdapter.notifyDataSetChanged();
    }
}
