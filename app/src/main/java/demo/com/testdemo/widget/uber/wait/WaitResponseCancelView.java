package demo.com.testdemo.widget.uber.wait;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by ludexiang on sliding_view16/8/18.
 */
public class WaitResponseCancelView extends SurfaceView implements SurfaceHolder.Callback {
    private final int mScreenWidth;
    private final int mScreenHeight;
    private ICancelOrderLocation mCancelLocation;
    private SurfaceHolder mSurfaceHolder;
//    private Rect mRect = new Rect();

    private int mTopMoveOffset;
    private int mCancelOpX;
    private int mCancelOpY;
    private boolean isShowPathView = false;
    private final int FOREGROUND = 10;
    private final int BACKGROUND = 8;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    private Paint mHorPaint = new Paint();
    private Paint mSkewPaint = new Paint();

    private Rect mClipRect = new Rect();
    private boolean isThreadFlag = false;

    private Thread mDrawThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isThreadFlag) {
                Canvas canvas = null;
                try {
                    synchronized (mSurfaceHolder) {
                        canvas = mSurfaceHolder.lockCanvas();
                        canvas.drawColor(Color.TRANSPARENT);

                        Log.e("ldx", "fuc , ...... " + mTopMoveOffset);
                        mPath.reset();
                        mPath.addCircle(mCancelOpX, mCancelOpY, mTopMoveOffset, Path.Direction.CCW);
                        canvas.clipPath(mPath, Region.Op.REPLACE);
                        canvas.drawPath(mPath, mPaint);

                        // dst
//                canvas.clipRect(mClipRect);
//                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawRect(mClipRect, mPaint);
//                mPaint.setXfermode(null);

                        // 需优化
                        drawSkew45Grid(canvas);
                        drawHorizontalGrid(canvas);
                        Thread.sleep(50);
                    }
                } catch (Exception e) {

                } finally {
                    if (canvas != null) {
                        // 解除锁定，并提交修改内容，更新屏幕
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    });

    public WaitResponseCancelView(Context context) {
        this(context, null);
    }

    public WaitResponseCancelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitResponseCancelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setZOrderOnTop(true);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        final DisplayMetrics outMetrics = getResources().getDisplayMetrics();
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;

        mClipRect.set(0, 0, mScreenWidth, mScreenHeight);
        mCancelOpX = mScreenWidth / 2;

        initPaint();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#0a091b"));

        mHorPaint.setAntiAlias(true);
        mHorPaint.setStrokeWidth(2f);
        mHorPaint.setColor(Color.parseColor("#20FFFFFF"));

        mSkewPaint.setAntiAlias(true);
        mSkewPaint.setStrokeWidth(2f);
        mSkewPaint.setColor(Color.parseColor("#10FFFFFF"));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("ldx", "surfaceCreated ......");
        isThreadFlag = true;
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("ldx", "surfaceChanged ......");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("ldx", "surfaceDestroy ......");
        isThreadFlag = false;
        try {
            mDrawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawHorizontalGrid(Canvas canvas) {
        canvas.save();
        // 行
        float row = 0f;
        for (int r = 0; r <= mScreenHeight / FOREGROUND; r++) {
            canvas.drawLine(0f, row * FOREGROUND, mScreenWidth, row * FOREGROUND, mHorPaint);
            row += FOREGROUND;
        }

        // 列
        int column = 0;
        for (int col = 0; col < mScreenWidth / FOREGROUND; col++) {
            canvas.drawLine(column * FOREGROUND, 0f, column * FOREGROUND, mScreenHeight, mHorPaint);
            column += FOREGROUND;
        }
        canvas.restore();
    }

    private void drawSkew45Grid(Canvas canvas) {
        canvas.save();
        // 行
        float row = 0f;
        for (int r = 0; r <= mScreenHeight / BACKGROUND; r++) {
            canvas.drawLine(row * BACKGROUND, 0f, 0f, row * BACKGROUND, mSkewPaint);
            row += BACKGROUND;
        }
        // 列
        int column = 0;
        for (int col = mScreenWidth; col >= 0; col--) {
            canvas.drawLine(mScreenWidth - column * BACKGROUND, 0f, mScreenWidth, column * BACKGROUND, mSkewPaint);
            column += BACKGROUND;
        }
        canvas.restore();
    }

    /**
     * long click update mPath anim
     */
    public void updatePathAnim() {
        if (mCancelLocation != null) {
            isShowPathView = true;
            int[] location = mCancelLocation.getLocation();
            mCancelOpY = location[1];
            AnimatorSet set = new AnimatorSet();
            ValueAnimator top = updateTopPath();
            set.play(top);
            set.setDuration(500);
            set.setInterpolator(new LinearInterpolator());
            set.start();
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isThreadFlag = false;
                    // 动画已经结束
                    mCancelLocation.getCancelView().setOnLongClickListener(null);
                }
            });
        }
    }

    private ValueAnimator updateTopPath() {
        ValueAnimator path = ValueAnimator.ofInt(0, mScreenHeight);
        path.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (Integer) animation.getAnimatedValue();
                mTopMoveOffset = offset;
                mClipRect.set(mCancelOpX - mTopMoveOffset, mCancelOpY - mTopMoveOffset,
                        mCancelOpX + mTopMoveOffset, mCancelOpY + mTopMoveOffset);
//                postInvalidate(mCancelOpX - mTopMoveOffset, mCancelOpY - mTopMoveOffset,
//                        mCancelOpX + mTopMoveOffset, mCancelOpY + mTopMoveOffset);
            }
        });
        return path;
    }

    public interface ICancelOrderLocation {
        int[] getLocation();

        View getCancelView();
    }

    public void setLocationListener(ICancelOrderLocation listener) {
        mCancelLocation = listener;
    }
}
