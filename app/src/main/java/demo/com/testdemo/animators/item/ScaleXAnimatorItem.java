package demo.com.testdemo.animators.item;

import android.view.View;
import demo.com.testdemo.animators.ViewAnimator;

public class ScaleXAnimatorItem extends ViewAnimator.BaseAnimItem {
    private float mFrom;
    private float mTo;

    public ScaleXAnimatorItem(float from, float to) {
        mFrom = from;
        mTo = to;
    }

    @Override
    public void onViewAttached(View view) {
        view.setScaleX(mFrom);
    }

    @Override
    public void onUpdate(View view, float fraction) {
        float value = mFrom + (mTo - mFrom) * fraction;
        view.setScaleX(value);
    }
}
