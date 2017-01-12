package demo.com.testdemo.widget.uber.mytravel;

import android.os.Bundle;

import demo.com.testdemo.R;
import demo.com.testdemo.widget.uber.mytravel.base.BaseTravelActivity;
import demo.com.testdemo.widget.uber.mytravel.presenter.TravelPresenter;
import demo.com.testdemo.widget.uber.mytravel.view.custom.TravelListView;

/**
 * Created by ludexiang on 16/8/19.
 */
public class MyTravelActivity extends BaseTravelActivity {
    private TravelListView mListView;
    private TravelPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ub_my_travel_layout);

        mListView = (TravelListView) findViewById(android.R.id.list);
        mPresenter = new TravelPresenter(mListView);
    }
}
