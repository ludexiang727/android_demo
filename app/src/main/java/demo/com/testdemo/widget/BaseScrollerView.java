package demo.com.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ludexiang on 16/7/4.
 */
public class BaseScrollerView extends View {
    protected int mXScroll;
    private int mScrollX;

    public BaseScrollerView(Context context) {
        this(context, null);
    }

    public BaseScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                mScrollX = (int) event.getRawX();
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                mXScroll = (int) event.getRawX() - mScrollX;
//                break;
//            }
//        }
//        super.dispatchTouchEvent(event);
//        return true;
//    }
}
