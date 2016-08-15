package demo.com.testdemo.widget.uber.wait;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on 16/8/11.
 */
public class WaitForResponseMaskGuideView extends RelativeLayout implements View.OnClickListener {
    private ImageView mMaskWarnImg;
    private TextView mMaskWarnInfo;
    private TextView mMaskWarnWaitTime;
    private TextView mMaskWarnBookSeat;

    private boolean isShowing;

    public WaitForResponseMaskGuideView(Context context) {
        this(context, null);
    }

    public WaitForResponseMaskGuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitForResponseMaskGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.uber_wait_for_response_mask_layout, this, true);

        mMaskWarnImg = (ImageView) view.findViewById(R.id.wait_for_response_mask_warn_img);
        mMaskWarnInfo = (TextView) view.findViewById(R.id.wait_for_response_mask_warn_info);
        mMaskWarnWaitTime = (TextView) view.findViewById(R.id.wait_for_response_mask_wait_time);
        mMaskWarnBookSeat = (TextView) view.findViewById(R.id.wait_for_response_mask_booking_seat);

        setClickable(true);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startAnim(!isShowing);
    }

    public void setWarnInfo(String warnInfo) {
        mMaskWarnInfo.setText(warnInfo);
    }

    public void setWaitTime(String waitTime) {
        mMaskWarnWaitTime.setText(waitTime);
    }

    public void setBookSeat(String bookSeat) {
        mMaskWarnBookSeat.setText(bookSeat);
    }

    private void startAnim(boolean isShow) {
        if (isShowing != isShow) {
            isShowing = isShow;
            if (isShowing) {
                show();
            } else {
                hide();
            }
        }
    }

    private void show() {
        ValueAnimator show = ValueAnimator.ofInt(0, 160);
        show.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setAlpha(animation);
            }
        });
        show.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(View.VISIBLE);
            }
        });
        show.setInterpolator(new AccelerateInterpolator(2f));
        show.start();
    }

    private void hide() {
        ValueAnimator hide = ValueAnimator.ofInt(160, 0);
        hide.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setAlpha(animation);
            }
        });
        hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(View.GONE);
            }
        });
        hide.setInterpolator(new AccelerateInterpolator(2f));
        hide.start();
    }

    /**
     * 设置蒙版的背景alpha
     * @param animation
     */
    private void setAlpha(ValueAnimator animation) {
        int alpha = (Integer) animation.getAnimatedValue();
        setAlpha(alpha);
        setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }
}
