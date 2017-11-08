package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import demo.com.testdemo.R;

import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by ludexiang on 2017/1/12.
 */

public class CircleProActivity extends Activity {

    private final long CLICK_MIN_TIME = 1500;
    private static long LAST_CLICK_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.download_activity_layout);
    }

    public void isFastClick(View v) {
        boolean flag = isFastClickMt();
        Log.e("ldx", "flag ==> " + flag);
    }

    private boolean isFastClickMt() {
       long curTime = SystemClock.elapsedRealtime();
        if (curTime - LAST_CLICK_TIME > CLICK_MIN_TIME) {
            LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            return false;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();
        return true;
    }
}
