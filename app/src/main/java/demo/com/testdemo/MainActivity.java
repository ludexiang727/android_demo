package demo.com.testdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import demo.com.testdemo.activity.CircleProActivity;
import demo.com.testdemo.activity.GuessUGoActivity;
import demo.com.testdemo.activity.MaskGuideActivity;
import demo.com.testdemo.activity.PopupActivity;
import demo.com.testdemo.activity.StatisticsActivity;
import demo.com.testdemo.activity.UberLoginActivity;
import demo.com.testdemo.activity.UberWaitForActivity;
import demo.com.testdemo.widget.uber.mytravel.MyTravelActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
