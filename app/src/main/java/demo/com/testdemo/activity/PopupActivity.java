package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import demo.com.testdemo.R;
import demo.com.testdemo.widget.popupwindow.ConfirmGuideView;

/**
 * Created by ludexiang on 2016/11/14.
 */

public class PopupActivity extends Activity {

    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window_layout);

        mBack = (ImageView) findViewById(R.id.back);
    }

    public void onShow(View v) {
        ConfirmGuideView guideView = new ConfirmGuideView(this, mBack);
        guideView.showPop();
    }
}
