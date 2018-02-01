package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2018/1/31.
 */

public class SlideActivity extends Activity {
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.slide_view);

  }
}
