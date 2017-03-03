package demo.com.testdemo.lockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

import demo.com.testdemo.R;
import demo.com.testdemo.lockscreen.widget.BoldTextView;

import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

/**
 * Created by ludexiang on 2017/2/15.
 */

public class LockScreenActivity extends Activity {

    private BoldTextView distance;
    private BoldTextView minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        final Window window = getWindow();
//        FLAG_DISMISS_KEYGUARD 加上此flag会导致此activity不可见时不需要滑屏解锁
//        FLAG_SHOW_WHEN_LOCKED 加上此flag锁屏之后展示此activity
//        FLAG_TURN_SCREEN_ON   加上此flag当此activity展示时屏幕变亮

//        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
////
////        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
////                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//
//        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
////
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager.LayoutParams params = window.getAttributes();
        params.flags |= FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        window.setAttributes(params);

        setContentView(R.layout.oc_lock_screen_layout);

        distance = (BoldTextView) findViewById(R.id.lock_distance);
        minute = (BoldTextView) findViewById(R.id.lock_time);

        testDemo();

    }


    private void testDemo() {
        final Random random = new Random(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 20;
                while (count > 1) {
                    try {
                        Thread.sleep(10000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    distance.setTextStr("" + (count - random.nextFloat()), "公里");
                    minute.setTextStr("" + count, "分钟");

                    count--;

                    distance.postInvalidate();
                    minute.postInvalidate();
                }
            }
        }).start();
    }

}
