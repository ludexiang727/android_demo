package demo.com.testdemo.widget.uber.mytravel.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ludexiang on sliding_view16/8/19.
 */
public abstract class BaseTravelListView extends ListView {
    public BaseTravelListView(Context context) {
        this(context, null);
    }

    public BaseTravelListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTravelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
