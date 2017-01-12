package demo.com.testdemo.widget.uber.mytravel.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import demo.com.testdemo.R;
import demo.com.testdemo.widget.uber.mytravel.model.TravelModel;

/**
 * Created by ludexiang on 16/8/19.
 */
public class TravelItemParent extends RelativeLayout implements View.OnClickListener {
    private LayoutInflater mInflater;

    private ImageView mDelete;
    private TextView mTime;
    private TextView mType;
    private TextView mStartAdr;
    private TextView mEndAdr;
    private TextView mOrderStatus;

    public TravelItemParent(Context context) {
        this(context, null);
    }

    public TravelItemParent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TravelItemParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.ub_my_travel_item, this, true);

        initView(view);
    }

    public void initView(View view) {
        mDelete = (ImageView) view.findViewById(R.id.travel_item_delete);
        mTime = (TextView) view.findViewById(R.id.travel_time);
        mType = (TextView) view.findViewById(R.id.travel_type);
        mOrderStatus = (TextView) view.findViewById(R.id.travel_order_status);
        mStartAdr = (TextView) view.findViewById(R.id.travel_start_address);
        mEndAdr = (TextView) view.findViewById(R.id.travel_end_address);

        mDelete.setOnClickListener(this);
    }

    public void setModel(TravelModel model) {
        mTime.setText(model.mTravelTime);
        mType.setText(model.mTravelType);
        switch (model.mTravelStatus) {
            case 0: {
                mOrderStatus.setText("已完成");
                break;
            }
            case 1: {
                mOrderStatus.setText("已取消");
                break;
            }
            case 2: {
                mOrderStatus.setText("行程中");
                break;
            }
            case 3: {
                mOrderStatus.setText("待评价");
                break;
            }
        }
        mStartAdr.setText(model.mStartAddr);
        mEndAdr.setText(model.mEndAddr);
    }

    @Override
    public void onClick(View v) {

    }
}
