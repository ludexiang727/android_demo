package demo.com.testdemo.widget.uber.mytravel.view;

import java.util.List;

import demo.com.testdemo.widget.uber.mytravel.model.TravelModel;

/**
 * Created by ludexiang on 16/8/19.
 */
public interface IListView {
    void setData(List<TravelModel> data);
    void notifyUpdate();
}
