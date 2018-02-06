package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;

import demo.com.testdemo.widget.uber.wait.WaitForResponseView;
import demo.com.testdemo.widget.uber.wait.WaitResponseController;

/**
 * Created by ludexiang on sliding_view16/8/10.
 */
public class UberWaitForActivity extends Activity {

    private WaitResponseController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new WaitResponseController(this);
        setContentView(mController.getView());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mController.startLoading();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mController.stopLoading();
    }
}
