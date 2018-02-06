package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import demo.com.testdemo.R;
import demo.com.testdemo.controller.MaskGuideController;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view14.
 */
public class MaskGuideActivity extends Activity {
    private MaskGuideController mMaskGuideController;
    private TextView mFootBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mask_guid_content_layout);

        mFootBar = (TextView) findViewById(R.id.footbar);
        mMaskGuideController = new MaskGuideController();
//        mMaskGuideController.setLocation(mFootBar);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mMaskGuideController.show(MaskGuideActivity.this, mFootBar);
            }
        });
    }
}
