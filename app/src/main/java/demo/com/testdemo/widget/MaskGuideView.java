package demo.com.testdemo.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view13.
 */
public class MaskGuideView extends RelativeLayout {

    private int mScreenHeight;
    private Rect mMatchParent = new Rect();


    public MaskGuideView(Context context) {
        this(context, null);
    }

    public MaskGuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(mMatchParent);
        mScreenHeight = mMatchParent.bottom;
        setWillNotDraw(false);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 用于判断当前是否要减去状态来的高度
     */
    public int updateLocation(int[] location) {
        int statusHeight = statusHeight();
        Log.e("ldx", "height ..." + mMatchParent.height() + " mScrenHeight " + mScreenHeight);
        int locationY = mMatchParent.height() == mScreenHeight ? location[1] : location[1] - statusHeight;
        return locationY;
    }

    private int statusHeight() {
        Rect rect = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }


}
