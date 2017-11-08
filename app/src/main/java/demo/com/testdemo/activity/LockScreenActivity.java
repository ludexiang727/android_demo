package demo.com.testdemo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import demo.com.testdemo.R;
import demo.com.testdemo.lockscreen.widget.BoldTextView;

/**
 * Created by ludexiang on 2017/3/16.
 */

public class LockScreenActivity extends Activity {
    BoldTextView textView;
    TextView city, Carnumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);

        textView = (BoldTextView) findViewById(R.id.lock_driver_info);
        city = (TextView) findViewById(R.id.lock_screen_city);
        Carnumber = (TextView) findViewById(R.id.lock_screen_car_number);

        String str = "京I78903";
        String number = addPoint(str);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < 20 ; i++) {
                    textView.setTextStr("" + i, "公里");
                    textView.postInvalidate();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        city.setText(number.substring(0, 1));
//        Carnumber.setText(number.substring(1, number.length()));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String str = "京I78903";
//        String number = addPoint(str);
//        new Handler(getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0 ; i < 20 ; i++) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    textView.setTextStr("" + i, "公里");
//                    textView.postInvalidate();
//                }
//            }
//        }, 2000);
//
//        city.setText(number.substring(0, 1));
//        Carnumber.setText(number.substring(1, number.length()));
    }

    private String addPoint(String number) {
        StringBuffer buffer = new StringBuffer();
        String city = number.substring(0, 2);
        String no = number.substring(2, number.length());
        buffer.append(city).append("·").append(no);
        return buffer.toString();
    }
}
