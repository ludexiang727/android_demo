package demo.com.testdemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import demo.com.testdemo.R;
import demo.com.testdemo.fragments.ImageFragment;
import demo.com.testdemo.fragments.TextFragment;

/**
 * Created by ludexiang on 2018/3/2.
 */

public class SupportActivity extends AppCompatActivity {

  TextFragment textFragment;
  ImageFragment imgFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragments_main_layout);

    textFragment = new TextFragment();
    imgFragment = new ImageFragment();
  }

  public void add(View v) {
    //创建事务
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //操作指令
    ft.add(R.id.fl_content, textFragment);
    //提交事务
    ft.commit();
  }

  public void remove(View v) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //从Activity中移除一个Fragment，如果被移除的Fragment没有添加到回退栈（回退栈后面会详细说），这个Fragment实例将会被销毁。
    ft.remove(textFragment);
    ft.commit();
  }

  public void replace(View v) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //使用另一个Fragment替换当前的，实际上就是remove()然后add()的合体
    ft.replace(R.id.fl_content, imgFragment);
    ft.commit();
  }

  public void attach(View v) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //重建视图，附件到UI上并显示
    ft.attach(textFragment);
    ft.commit();
  }

  public void detach(View v) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //会将view从UI中移除,和remove()不同,此时fragment的状态依然由FragmentManager维护
    ft.detach(textFragment);
    ft.commit();
  }

  public void show(View v) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //显示之前隐藏的Fragment
    ft.show(textFragment);
    ft.commit();
  }

  public void hide(View v) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //当你的fragment数量固定很少时隐藏当前的Fragment，仅仅是设为不可见，并不会销毁
    ft.hide(textFragment);
    ft.commit();
  }
}