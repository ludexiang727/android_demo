package demo.com.testdemo.lockscreen.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * Created by ludexiang on 2017/sliding_view2/28.
 */

public class LockScreenRootViewGroup extends RelativeLayout {

    private int mScreenWidth;
    private int mScreenHeight;
    private int mDownX;
    private int mDownY;
    private int mLastMoveY;
    private VelocityTracker mTracker;
    private int mMinVelocity;
    private int mMaxVelocity;
    private IDragListener mDragListener;
    private boolean isMove;

    public LockScreenRootViewGroup(Context context) {
        this(context, null);
    }

    public LockScreenRootViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockScreenRootViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    /**
     * 因为不能拖动地图所以直接返回TRUE
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = (int) (event.getX() + 0.5);
                mDownY = (int) (event.getY() + 0.5);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int x = (int) (event.getX() + 0.5);
                int y = (int) (event.getY() + 0.5);

                if (Math.atan2(mDownY - y, mDownX - x) > 0 && Math.atan2(mDownY - y, mDownX - x) < 180) {
                    isMove = true;
                    // 上滑
                    handleMove((y - mDownY) / 2);
                } else {
                    isMove = false;
                    return false;
                }
                break;
            }
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (!isMove) {
                    return true;
                }
                mTracker.computeCurrentVelocity(1000, mMaxVelocity);
                Log.i("ldx", "velocity " + mTracker.getYVelocity() + " min " + mMinVelocity);
                boolean bottomToUp = mTracker.getYVelocity() < 0;
                boolean isFling = Math.abs(mTracker.getYVelocity()) < mMinVelocity ? false : true;
                handleUp(isFling, bottomToUp);
                recycleTracker();
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void handleMove(int moveY) {
        if (mLastMoveY != moveY) {
            mLastMoveY = moveY;
            int newMoveY = (int) (getTranslationY() + moveY + 0.5);
            float alpha = 1f - Math.abs(newMoveY * 1f / mScreenHeight);
            setTranslationY(newMoveY);
            setAlpha(alpha);
        }
    }

    /**
     * handle up event
     */
    private void handleUp(boolean fling, boolean bottomToUp) {
        int translate = (int) (getTranslationY() + 0.5);
        float scale = (1.0f + ((float) translate) / mScreenHeight);

        int to = fling ? (bottomToUp ? -(mScreenHeight + translate) : 0)
                : (scale > 0.5f ? 0 :  -(mScreenHeight + translate));

        int duration = Math.abs(300 * (to - translate) / mScreenHeight);
        goonMove(translate, to, duration);
    }

    /**
     * 继续滚动
     *
     * @param from
     * @param to
     */
    private void goonMove(float from, final float to, int duration) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator translation = translationY(from, to);
        ValueAnimator alpha = alpha(from, to);
        set.playTogether(translation, alpha);
        set.setDuration(duration);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                if (to < 0) {
//                    if (mDragListener != null) {
//                        mDragListener.drag();
//                    }
//                }
            }
        });
    }

    private ValueAnimator translationY(float from, final float to) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", from, to);
        return translationY;
    }

    private ValueAnimator alpha(float from, float to) {
        float start = from > to ? 1f - Math.abs(from / mScreenHeight) : 1f - to / mScreenHeight;
        float end = from > to ? 0f : 1f;
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", start, end);
        return alpha;
    }

    private void addTracker(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
    }

    private void recycleTracker() {
        if (mTracker != null) {
            mTracker.recycle();
            mTracker = null;
        }
    }

    public void setDragListener(IDragListener listener) {
        mDragListener = listener;
    }

    interface IDragListener {
        void drag();
    }
}
