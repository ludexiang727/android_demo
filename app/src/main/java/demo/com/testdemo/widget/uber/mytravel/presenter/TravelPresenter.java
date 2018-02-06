package demo.com.testdemo.widget.uber.mytravel.presenter;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import demo.com.testdemo.widget.uber.mytravel.model.TravelModel;
import demo.com.testdemo.widget.uber.mytravel.view.IListView;

/**
 * Created by ludexiang on sliding_view16/sliding_view8/19.
 */
public class TravelPresenter {
    private IListView iListView;
    private List<TravelModel> mTravelStruct = new ArrayList<TravelModel>();

    public TravelPresenter(IListView listView) {
        iListView = listView;
        iListView.setData(mTravelStruct);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 5000);
    }

    /**
     * 请求我的行程接口
     */
    public void loadData() {
        // test
        for (int i = 0; i < 10; i++) {
            TravelModel model = new TravelModel();
            model.mTravelTime = "2016-08-sliding_view1"+i;
            if (i % 2 == 0) {
                model.mTravelType = "优选轿车";
            } else {
                model.mTravelType = "人民优步 + 拼车";
            }
            model.mTravelId = i;
            model.mStartAddr = "中关村软件园 " + i;
            model.mEndAddr = "东村家园东门(公交站附近)";
            if (i % 2 == 0) {
                model.mTravelStatus = 0;
            } else if (i % 3 == 0) {
                model.mTravelStatus = 1;
            } else if (i % 4 == 0){
                model.mTravelStatus = 2;
            } else {
                model.mTravelStatus = 3;
            }
            mTravelStruct.add(model);

        }
        iListView.notifyUpdate();
    }
}
