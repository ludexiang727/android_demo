package demo.com.testdemo.widget.interf;

import android.view.View;

/**
 * Created by ludexiang on 2018/1/31.
 */

public interface ISlideView {
  void setContent(String content, int contentSize);
  void setListener(IViewListener listener);
  void move(float scale);

  interface IViewListener {
    void move(float scale);
    void moveEnd();
  }

  View getView();
}
