//package demo.com.testdemo.dynamicprices.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.didi.onecar.component.dynamicprices.view.ioc.IocDynamicView;
//import com.didi.onecar.utils.TextUtil;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
///**
// * Created by ludexiang on 2017/2/14.
// */
//
//public abstract class AbDynamicPriceViewGroup extends RelativeLayout implements IDynamicPriceView {
//    protected Context mContext;
//
//    public AbDynamicPriceViewGroup(Context context) {
//        this(context, null);
//    }
//
//    public AbDynamicPriceViewGroup(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public AbDynamicPriceViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        mContext = context;
//        setVisibility(View.GONE);
//    }
//
//    private void initIocView() {
//        Field[] fields = getClass().getDeclaredFields();
//        if (fields != null && fields.length > 0) {
//            for (Field field : fields) {
//                try {
//                    field.setAccessible(true);
//                    if (field.get(this) != null) {
//                        return;
//                    }
//                    IocDynamicView iocView = field.getAnnotation(IocDynamicView.class);
//                    int viewId = iocView.id();
//                    if (viewId != 0) {
//                        field.set(this, findViewById(viewId));
//                    }
//                    setListener(field, iocView.click());
//                } catch (Exception e) {
//
//                }
//            }
//        }
//    }
//
//    /**
//     * 二次动调
//     * @param view
//     */
//    @Override
//    public void showDynamicPrice(AbDynamicPriceViewGroup view) {
//        addToViewGroup(view);
//        initIocView();
//    }
//
//    /**
//     * 此方法是空实现，业务线不需要覆写此方法
//     * @param view
//     */
//    protected void addToViewGroup(View view) {
//        setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideDynamicView() {
//        setVisibility(View.GONE);
//    }
//
//    /**
//     * 点击事件
//     * @param field
//     * @param methodName
//     */
//    private void setListener(Field field, String methodName) {
//        try {
//            Object obj = field.get(this);
//            if (obj == null) {
//                return;
//            }
//            ((View) obj).setOnClickListener(new EventListener(this).click(methodName));
//        } catch(Exception e) {
//
//        }
//    }
//
//    final class EventListener implements OnClickListener {
//        private Object obj;
//        private String methodName;
//
//        EventListener (Object obj) {
//            this.obj = obj;
//        }
//
//        EventListener click(String name) {
//            methodName = name;
//            return this;
//        }
//
//        @Override
//        public void onClick(View v) {
//            invokeClick(obj, methodName, v);
//        }
//
//        private void invokeClick(Object obj, String name, Object... params) {
//            if (obj == null) return;
//            if (TextUtil.isEmpty(name)) return;
//            Method method;
//            try {
//                method = obj.getClass().getDeclaredMethod(name, View.class);
//                if (method != null) {
//                    method.setAccessible(true);
//                    method.invoke(obj, params);
//                } else {
//                    // TODO: 2017/2/14
//                }
//            } catch (Exception e) {
//
//            }
//
//        }
//    }
//}
