package demo.com.testdemo.widget.uber.wait;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on 16/8/10.
 */
public class WaitForResponseView extends RelativeLayout implements View.OnLongClickListener,
        WaitForResponseCancelOrderView.ICancelOrderLocation {

    private TextView mTitleBar;
    private WaitForResponseProgress mWaitResponseProgress;
    private WaitForResponseMaskGuideView mWaitResponseMaskGuide;
    private TextView mCancelInfo;
    private ImageView mWaitResponseCancelOrder;
    private WaitForResponseCancelOrderView mCancelOrderPathView;

    private boolean isLoading;

    public WaitForResponseView(Context context) {
        this(context, null);
    }

    public WaitForResponseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitForResponseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.uber_wait_for_response_layout, this, true);
        mTitleBar = (TextView) view.findViewById(R.id.wait_for_response_title_bar);
        mWaitResponseProgress = (WaitForResponseProgress) view.findViewById(R.id.wait_for_response_progress);
        mWaitResponseMaskGuide = (WaitForResponseMaskGuideView) view.findViewById(R.id.wait_for_response_mask_guide);
        mWaitResponseCancelOrder = (ImageView) view.findViewById(R.id.wait_for_response_cancel_order);
        mCancelOrderPathView = (WaitForResponseCancelOrderView) view.findViewById(R.id.wait_for_response_path_view);
        mCancelInfo = (TextView) view.findViewById(R.id.wait_for_response_cancel_info);

        mCancelOrderPathView.setLocationListener(this);
        mWaitResponseCancelOrder.setOnLongClickListener(this);
    }

    public void setLoading(boolean loading) {
        if (isLoading != loading) {
            isLoading = loading;
            if (isLoading) {
                mWaitResponseMaskGuide.setVisibility(View.VISIBLE);
                mWaitResponseProgress.playAnim();
            } else {
                mWaitResponseProgress.release();
            }
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.wait_for_response_cancel_order) {
            updateTitle();
            mCancelOrderPathView.setVisibility(View.VISIBLE);
            mCancelOrderPathView.updatePathAnim();
        }
        return false;
    }

    private void updateTitle() {
        mCancelInfo.setVisibility(View.VISIBLE);
        mCancelInfo.setText("正在取消预约");
    }

    @Override
    public int[] getLocation() {
        int[] location = new int[2];
        mWaitResponseCancelOrder.getLocationOnScreen(location);
        return location;
    }

    @Override
    public View getCancelView() {
        return mWaitResponseCancelOrder;
    }
}
