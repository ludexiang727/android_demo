package demo.com.testdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import demo.com.testdemo.activity.SlideActivity;
import demo.com.testdemo.activity.SupportActivity;
import java.util.ArrayList;
import java.util.List;

import demo.com.testdemo.activity.ActivityA;
import demo.com.testdemo.activity.CircleProActivity;
import demo.com.testdemo.activity.GuessUGoActivity;
import demo.com.testdemo.activity.LockScreenActivity;
import demo.com.testdemo.activity.MaskGuideActivity;
import demo.com.testdemo.activity.PopupActivity;
import demo.com.testdemo.activity.StatisticsActivity;
import demo.com.testdemo.activity.TestActivity;
import demo.com.testdemo.activity.UberLoginActivity;
import demo.com.testdemo.activity.UberWaitForActivity;
import demo.com.testdemo.floatoperation.operation.Operation;
import demo.com.testdemo.floatoperation.presenter.FloatOperationPresenter;
import demo.com.testdemo.floatoperation.view.FloatOperationView;
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

    public void onBold(View v) {
        Intent intent = new Intent(this, LockScreenActivity.class);
        startActivity(intent);
    }

    public void onTest(View v) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void onFloat(View v) {
        List<Operation> mMoreOperations = new ArrayList<>();
        mMoreOperations.add(Operation.OP_SHARE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_EMERGENCY_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
        mMoreOperations.add(Operation.OP_NO_CAR_POOL);
//        mMoreOperations.add(Operation.OP_HELP_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_MODIFY_STATION_HORIZONTAL);
        mMoreOperations.add(Operation.OP_CARPOOL_PRICE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
////
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_SHARE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_EMERGENCY_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_HELP_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_IM_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_MORE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_CARPOOL_PRICE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);

//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_SHARE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_EMERGENCY_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_HELP_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_IM_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_MORE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_CARPOOL_PRICE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
//
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_SHARE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_EMERGENCY_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_HELP_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_IM_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_MORE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_CARPOOL_PRICE_HORIZONTAL);
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);
//
//        mMoreOperations.add(Operation.OP_TIPS_HORIZONTAL);

        FloatOperationPresenter floatOperation = new FloatOperationPresenter(this);
        floatOperation
                .buildFloatOperation(new FloatOperationView(this))
                .buildTitle("更多")
                .buildOperation(mMoreOperations)
                .show();
    }

    public void onMethondCycle(View v) {
        Intent intent = new Intent(this, ActivityA.class);
        startActivity(intent);
    }

    public void onSlideView(View v) {
        Intent intent = new Intent(this, SlideActivity.class);
        startActivity(intent);
    }

    public void onFragments(View v) {
        Intent intent = new Intent(this, SupportActivity.class);
        startActivity(intent);
    }
}
