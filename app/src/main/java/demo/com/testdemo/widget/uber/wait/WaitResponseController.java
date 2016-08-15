package demo.com.testdemo.widget.uber.wait;

import android.content.Context;

/**
 * Created by ludexiang on 16/8/11.
 */
public class WaitResponseController {
    private WaitForResponseView mResponseView;

    public WaitResponseController(Context context) {
        if (mResponseView == null) {
            mResponseView = new WaitForResponseView(context);
        }
    }

    public WaitForResponseView getView() {
        return mResponseView;
    }

    public void startLoading() {
        if (mResponseView != null) {
            mResponseView.setLoading(true);
        }
    }

    public void stopLoading() {
        if (mResponseView != null && mResponseView.isLoading()) {
            mResponseView.setLoading(false);
        }
    }
}
