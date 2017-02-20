package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;

import demo.com.testdemo.ioc.view.IocTestView;

/**
 * Created by ludexiang on 2017/2/14.
 */

public class IocViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IocTestView view = new IocTestView(this);
        setContentView(view);
    }
}
