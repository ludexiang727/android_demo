package demo.com.testdemo.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.MeasureSpec;

import demo.com.testdemo.R;
import demo.com.testdemo.utils.AppUtils;
import demo.com.testdemo.widget.MaskGuideLinePath;
import demo.com.testdemo.widget.MaskGuideView;


/**
 * Created by ludexiang on sliding_view16/sliding_view7/sliding_view13.
 */
public class MaskGuideController implements MaskGuideLinePath.ICallback {
    private final int Y = 450; // view de 高度

    private MaskGuideView mContainer;
    private RelativeLayout mParentView;
    private TextView mStartAddress;
    private ImageView mGuideIcon;
    private TextView mWelcomeTip;
    private TextView mNotifyTip;
    private TextView mIKnow;
    private MaskGuideLinePath mMaskLinePath;

    private PopupWindow mMaskPopupWindow;
    private Context mContext;

    private AnimationDrawable mDrawableAnim;
    private float mTranslationX;
    private int mIconWidth;
//    private ILocationXY iLocation;

//    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            mMaskLinePath.measure(width, height);
//            Log.e("ldx", "aaaaaa " + mMaskLinePath.getMeasuredWidth() + " " + mMaskLinePath.getMeasuredHeight());
//        }
//    };

    private View initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.guide_mask_layout, null);
        mParentView = (RelativeLayout) view.findViewById(R.id.mask_guide_view);
        mContainer = (MaskGuideView) view.findViewById(R.id.mask_guide_container);
        mStartAddress = (TextView) view.findViewById(R.id.mask_guide_start_adr);
        mGuideIcon = (ImageView) view.findViewById(R.id.mask_guide_icon);
        mWelcomeTip = (TextView) view.findViewById(R.id.mask_guide_welcome);
        mNotifyTip = (TextView) view.findViewById(R.id.mask_guide_notify_tip);
        mIKnow = (TextView) view.findViewById(R.id.mask_guide_i_know);
        mMaskLinePath = (MaskGuideLinePath) view.findViewById(R.id.mask_guide_line_path);
        mIKnow.setOnClickListener(mClickListener);
        mStartAddress.setOnClickListener(mClickListener);
        mMaskLinePath.setListener(this);
//        mMaskLinePath.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
        return view;
    }

    private void initPopupWindow(View view, View parent) {
        mMaskPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mMaskPopupWindow.setOutsideTouchable(true);
        mMaskPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMaskPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        mMaskPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mContainer = null;
                mMaskPopupWindow = null;
//                iLocation = null;

                mGuideIcon.setVisibility(View.GONE);
                if (mDrawableAnim != null) {
                    mDrawableAnim.stop();
                }
            }
        });

    }

    public void show(final Context context, View parent) {
        if (mContainer == null) {
            View view = initView(context);
            initPopupWindow(view, parent);
            final int y = 1526;//mMaskGuideView.updateLocation(iLocation.getMaskGuideLocation());
            Log.e("ldx", "y == " + y + " height " + mGuideIcon.getMeasuredWidth() + " measure height " + mContainer.getMeasuredHeight());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMaskLinePath.setStartXY(AppUtils.dip2px(context, 70), 500);
                    AnimatorSet set = new AnimatorSet();
                    AnimatorSet y1 = translationY(mWelcomeTip, mWelcomeTip.getY() + 20, 0, true);
                    AnimatorSet y2 = translationY(mNotifyTip, mNotifyTip.getY() + 20, 0, true);
                    AnimatorSet y3 = translationY(mIKnow, 0, 0, true);
//                    set.play(y1).with(y2).before(y3);
                    set.playTogether(y1, y2, y3);
                    set.setDuration(800);
                    set.start();

                }
            }, 500);
            mContainer.setY(y - Y);
        }
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.mask_guide_start_adr
                    || view.getId() == R.id.mask_guide_i_know) {
                mMaskLinePath.playAnim(MaskGuideLinePath.LINE_HEIGHT, 0, 600, 300);
                AnimatorSet set = new AnimatorSet();
                AnimatorSet y1 = translationY(mWelcomeTip, 0, mWelcomeTip.getY() + 20, false);
                AnimatorSet y2 = translationY(mNotifyTip, 0, mNotifyTip.getY() + 20, false);
                AnimatorSet y3 = translationY(mIKnow, 0, 0, false);
                set.play(y1).with(y2).after(y3);
                set.setDuration(600);
                set.start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (mMaskPopupWindow != null) {
                            mMaskPopupWindow.dismiss();
                        }
                    }
                });

            }
        }
    };

//    public void setLocation(ILocationXY location) {
//        iLocation = location;
//    }

    public boolean isShowing() {
        return mMaskPopupWindow.isShowing();
    }

    private int[] getLocation(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    @Override
    public void startFrameAnim(final RectF rectF, boolean isExitAnim) {
        int width = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mGuideIcon.measure(width, height);
        mIconWidth = mGuideIcon.getMeasuredWidth();
        mTranslationX = -AppUtils.dip2px(mContext, (rectF.right - rectF.left) / 2 - mIconWidth / 2);

        if (!isExitAnim) {
            mDrawableAnim = (AnimationDrawable) mGuideIcon.getDrawable();
            if (mDrawableAnim != null) {
                if (mDrawableAnim.isRunning()) {
                    return;
                }
                mDrawableAnim.start();
            }
            mGuideIcon.setVisibility(View.VISIBLE);
            enterAnim();
        } else {
            exitAnim();
        }
    }


    private void enterAnim() {
        ObjectAnimator translation = ObjectAnimator.ofFloat(mGuideIcon, "translationX", 0, mTranslationX);
        translation.setDuration(300);
        translation.setInterpolator(new LinearInterpolator());
        translation.start();
    }

    private ObjectAnimator exitAnim() {
        ObjectAnimator translation = ObjectAnimator.ofFloat(mGuideIcon, "translationX", mTranslationX,
                -mMaskLinePath.getMeasuredWidth());
        translation.setInterpolator(new LinearInterpolator());
        translation.setDuration(300);
        translation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mGuideIcon.setVisibility(View.GONE);
            }
        });
        translation.start();
        return translation;
    }

    private AnimatorSet translationY(View view, float from, float to, boolean upTrans) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", from, to);
        translationY.setInterpolator(new OvershootInterpolator());

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", upTrans ? 0f : 1f, upTrans ? 1f : 0f);
        set.playTogether(translationY, alpha);
        return set;
    }
}
