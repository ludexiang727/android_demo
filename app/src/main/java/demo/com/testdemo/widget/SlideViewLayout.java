package demo.com.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import demo.com.testdemo.R;
import demo.com.testdemo.widget.interf.ISlideView;

/**
 * Created by ludexiang on 2018/sliding_view1/31.
 */

public class SlideViewLayout extends RelativeLayout implements ISlideView {

  private TextView mContent;
  private SlideView mSlideView;

  public SlideViewLayout(Context context) {
    this(context, null);
  }

  public SlideViewLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SlideViewLayout(Context context, AttributeSet attrs,int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflate(context, R.layout.slide_view, this);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mContent = (TextView) findViewById(R.id.slide_view_content);
    mSlideView = (SlideView) findViewById(R.id.slide_view);
  }

  @Override
  public void setContent(String content, int contentSize) {
    mContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    mContent.setText(content);
  }

  @Override
  public void setListener(IViewListener listener) {
    mSlideView.setListener(listener);
  }

  @Override
  public void move(float scale) {
    mContent.setAlpha(1f - scale);
  }

  @Override
  public View getView() {
    return this;
  }
}
