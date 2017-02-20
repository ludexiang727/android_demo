package demo.com.testdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import demo.com.testdemo.activity.CircleProActivity;
import demo.com.testdemo.activity.GuessUGoActivity;
import demo.com.testdemo.activity.IocViewActivity;
import demo.com.testdemo.activity.MaskGuideActivity;
import demo.com.testdemo.activity.PopupActivity;
import demo.com.testdemo.activity.StatisticsActivity;
import demo.com.testdemo.activity.UberLoginActivity;
import demo.com.testdemo.activity.UberWaitForActivity;
import demo.com.testdemo.lockscreen.LockScreenActivity;
import demo.com.testdemo.lockscreen.receiver.LockScreenReceiver;
import demo.com.testdemo.widget.uber.mytravel.MyTravelActivity;

public class MainActivity extends AppCompatActivity {
    private LockScreenReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new LockScreenReceiver();
        registerReceiver();
    }

    public void onStatistics(View v) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void onMaskGuide(View v) {
        Intent intent = new Intent(this, MaskGuideActivity.class);
        startActivity(intent);
    }

    public void onUberLogin(View v) {
        Intent intent = new Intent(this, UberLoginActivity.class);
        startActivity(intent);
    }

    public void onUberWait(View v) {
        Intent intent = new Intent(this, UberWaitForActivity.class);
        startActivity(intent);
    }

    public void onUberTravel(View v) {
        Intent intent = new Intent(this, MyTravelActivity.class);
        startActivity(intent);
    }

    public void onGuessUGo(View v) {
        Intent intent = new Intent(this, GuessUGoActivity.class);
        startActivity(intent);
    }

    public void onPopup(View v) {
        Intent intent = new Intent(this, PopupActivity.class);
        startActivity(intent);
    }

    public void onDownLoadPro(View v) {
        Intent intent = new Intent(this, CircleProActivity.class);
        startActivity(intent);
    }

    public void onIocView(View v) {
        Intent intent = new Intent(this, IocViewActivity.class);
        startActivity(intent);
    }

    public void onLockScreen(View v) {
        Intent intent = new Intent(this, LockScreenActivity.class);
        startActivity(intent);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
    }

    private void unRegisterReceiver() {
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }
}
