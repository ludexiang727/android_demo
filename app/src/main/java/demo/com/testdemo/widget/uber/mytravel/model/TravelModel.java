package demo.com.testdemo.widget.uber.mytravel.model;

/**
 * Created by ludexiang on 16/8/19.
 */
public class TravelModel {
    public int mTravelId;
    // 订单发生时间
    public String mTravelTime;
    // 订单类型 优选优步 + 拼车
    public String mTravelType;
    // 订单当前状态 已完成、已取消、行程中、待评价
    public int mTravelStatus;

    public String mStartAddr;
    public String mEndAddr;

    public void parse() {

    }
}
