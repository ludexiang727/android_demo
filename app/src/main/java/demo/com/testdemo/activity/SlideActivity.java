package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import demo.com.testdemo.R;
import demo.com.testdemo.widget.SlideView;
import demo.com.testdemo.widget.SlideViewLayout;

/**
 * Created by ludexiang on 2018/sliding_view1/31.
 */

public class SlideActivity extends Activity {

  private SlideView mSlideView;
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.slide_layout);
  }

}
