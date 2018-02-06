package demo.com.testdemo.widget.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import demo.com.testdemo.R;
import demo.com.testdemo.utils.Utils;

/**
 * Created by ludexiang on 2016/sliding_view11/14.
 */

public class ConfirmGuideView {

    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mParentView;

    public ConfirmGuideView(Context context, View view) {
        mContext = context;
        mParentView = view;
    }

    public void showPop() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.car_confirm_guide_view_layout, null);
        mPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mParentView, Gravity.TOP | Gravity.LEFT, getXY()[0], getXY()[1]);
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    private int[] getXY() {
        int[] location = new int[2];
        mParentView.getLocationOnScreen(location);
        location[0] = location[0] / 2;
        location[1] = location[1] + mParentView.getHeight() + Utils.dip2px(mContext, 5);
        return location;
    }
}
