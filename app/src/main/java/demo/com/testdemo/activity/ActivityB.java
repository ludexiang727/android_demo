package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ludexiang on 2017/10/19.
 */

public class ActivityB extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ldx", "B --> onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ldx", "B --> onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ldx", "B --> onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ldx", "B --> onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ldx", "B --> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ldx", "B --> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ldx", "B --> onDestroy");
    }
}
