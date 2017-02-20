//package demo.com.testdemo.dynamicprices.view;
//
//import android.content.Context;
//import android.os.Build;
//import android.os.CountDownTimer;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.widget.RelativeLayout;
//
//import com.didi.onecar.base.R;
//import com.didi.onecar.utils.LogUtil;
//import com.didi.sdk.webview.BaseWebView;
//import com.didi.sdk.webview.WebViewModel;
//import com.didi.sdk.webview.jsbridge.JavascriptBridge;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//
///**
// * Created by ludexiang on 2017/2/14.
// */
//
//public class DynamicPriceView extends AbDynamicPriceViewGroup {
//    private LayoutInflater mInflater;
//    private BaseWebView mWebView;
//
//    public DynamicPriceView(Context context) {
//        this(context, null);
//    }
//
//    public DynamicPriceView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public DynamicPriceView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    private void init(Context context) {
//        mInflater = LayoutInflater.from(context);
//        View view = mInflater.inflate(R.layout.oc_dynamic_price_normal_layout, this);
//        mWebView = (BaseWebView) view.findViewById(R.id.dynamic_price_web);
//        WebSettings settings = mWebView.getSettings();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            settings.setDisplayZoomControls(false);
//        } else {
//            settings.setSupportZoom(false);
//        }
//        settings.setJavaScriptEnabled(true);
//    }
//
//    private void loadWebView(WebViewModel model) {
//        mWebView.setVisibility(View.VISIBLE);
//        if (model == null) {
//            mWebView.reload();
//        } else {
//            mWebView.loadUrl(model.url);
//        }
//    }
//
//    CountDownTimer mTimer = new CountDownTimer(5000, 5000) {
//
//        public void onTick(long millisUntilFinished) {
//
//        }
//
//        public void onFinish() {
//        }
//
//    };
//
//    @Override
//    public View getView() {
//        return this;
//    }
//
//    /**
//     * 一次动调
//     * @param webViewModel
//     */
//    @Override
//    public void showDynamicPrice(WebViewModel webViewModel, HashMap<String, JavascriptBridge.Function> map) {
//        if (webViewModel == null) {
//            LogUtil.e("WebView can't use null webViewModel,please check the model again");
//            return;
//        }
//        JavascriptBridge javascriptBridge = new JavascriptBridge(mWebView);
//        mWebView.setWebViewSetting(webViewModel);
//        if (map != null) {
//            Iterator<Map.Entry<String, JavascriptBridge.Function>> iter = map.entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry<String, JavascriptBridge.Function> entry = (Map.Entry) iter.next();
//                String key = entry.getKey();
//                JavascriptBridge.Function function = entry.getValue();
//                if (!TextUtils.isEmpty(key) && function != null) {
//                    javascriptBridge.addFunction(key, function);
//                }
//            }
//        }
//        mWebView.setJavascriptBridge(javascriptBridge);
//
//        setVisibility(View.VISIBLE);
//        loadWebView(webViewModel);
//    }
//
//    /**
//     * native view 附加到当前View中
//     * @param view
//     */
//    @Override
//    protected void addToViewGroup(View view) {
//        super.addToViewGroup(view);
//        if (view != null) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT);
//            removeAllViews();
//            addView(view, params);
//        }
//    }
//}
