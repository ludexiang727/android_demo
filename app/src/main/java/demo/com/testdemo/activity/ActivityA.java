package demo.com.testdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2017/10/19.
 */

public class ActivityA extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ldx", "A --> onCreate");
        setContentView(R.layout.layout_test_a);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ldx", "A --> onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ldx", "A --> onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ldx", "A --> onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ldx", "A --> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ldx", "A --> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ldx", "A --> onDestroy");
    }

    public void startB(View v) {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }
}
