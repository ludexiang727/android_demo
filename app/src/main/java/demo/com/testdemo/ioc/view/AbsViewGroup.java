package demo.com.testdemo.ioc.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import demo.com.testdemo.ioc.IocView;

/**
 * Created by ludexiang on 2017/2/14.
 */

public abstract class AbsViewGroup extends RelativeLayout {
    protected Context mContext;

    public AbsViewGroup(Context context) {
        this(context, null);
    }

    public AbsViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getView();
        initIocView();
    }

    protected abstract View getView();

    private void initIocView() {
        Field[] fields = getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(this) != null) {
                        continue;
                    }
                    IocView iocView = field.getAnnotation(IocView.class);
                    if (iocView != null) {
                        int viewId = iocView.id();
                        if (viewId != 0) {
                            field.set(this, findViewById(viewId));
                        }

                        setListener(field, iocView.click());

                    }
                } catch (Exception e) {

                }

            }
        }
    }

    private void setListener(Field field, String methodName) {
        if (field == null) {
            return;
        }
        try {
            Object obj = field.get(this);
            ((View) obj).setOnClickListener(new AbEventListener(this).click(methodName));
        } catch (Exception e) {

        }

    }

    final class AbEventListener implements OnClickListener {
        private Object mObj;
        private String mMethodName;

        AbEventListener(Object obj) {
            mObj = obj;
        }

        private AbEventListener click(String methodName) {
            mMethodName = methodName;
            return this;
        }

        @Override
        public void onClick(View v) {
            invokeClick(v);
        }

        private void invokeClick(Object...params) {
            if (TextUtils.isEmpty(mMethodName)) {
                return;
            }
            if (mObj == null) {
                return;
            }
            Method method;
            try {
                method = mObj.getClass().getDeclaredMethod(mMethodName, View.class);
                if (method != null) {
                    method.setAccessible(true);
                    method.invoke(mObj, params);
                }
            } catch (Exception e) {

            }

        }
    }
}
