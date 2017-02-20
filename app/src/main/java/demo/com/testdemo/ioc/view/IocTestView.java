package demo.com.testdemo.ioc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import demo.com.testdemo.R;
import demo.com.testdemo.ioc.IocView;

/**
 * Created by ludexiang on 2017/2/14.
 */

public class IocTestView extends AbsViewGroup {

    @IocView(id=R.id.ioc_view_text)
    private TextView mTxtView;
    @IocView(id=R.id.ioc_view_btn1, click = "btnClick")
    private Button btn1;
    @IocView(id=R.id.ioc_view_btn2)
    private Button btn2;

    public IocTestView(Context context) {
        this(context, null);
    }

    public IocTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IocTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View getView() {
        return inflate(mContext, R.layout.ioc_view_layout, this);
    }

    public void btnClick(View v) {
        // // TODO: 2017/2/14  
        Toast.makeText(getContext(), "反射回调点击事件", Toast.LENGTH_SHORT).show();
    }
}
