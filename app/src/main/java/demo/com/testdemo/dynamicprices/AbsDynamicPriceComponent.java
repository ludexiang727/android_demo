//package demo.com.testdemo.dynamicprices;
//
//import android.view.ViewGroup;
//
//import com.didi.onecar.base.BaseComponent;
//import com.didi.onecar.base.ComponentParams;
//import com.didi.onecar.component.dynamicprices.presenter.AbsDynamicPricePresenter;
//import com.didi.onecar.component.dynamicprices.view.DynamicPriceView;
//import com.didi.onecar.component.dynamicprices.view.IDynamicPriceView;
//
///**
// * Created by ludexiang on 2017/2/14.
// * 动态调价组件
// */
//public abstract class AbsDynamicPriceComponent extends BaseComponent<IDynamicPriceView, AbsDynamicPricePresenter> {
//
//    @Override
//    protected void bind(ComponentParams params, IDynamicPriceView view, AbsDynamicPricePresenter presenter) {
//
//    }
//
//    @Override
//    protected IDynamicPriceView onCreateView(ComponentParams params, ViewGroup container) {
//        return new DynamicPriceView(params.bizCtx.getContext());
//    }
//
//}
