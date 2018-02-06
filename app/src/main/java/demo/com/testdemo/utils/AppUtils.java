package demo.com.testdemo.utils;

import android.content.Context;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view14.
 */
public class AppUtils {

    public static int dip2px(Context context, float dip) {
        float f = context.getResources().getDisplayMetrics().density;
        return (int) (dip * f + 0.5F);
    }
}
