package demo.com.testdemo.floatoperation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import demo.com.testdemo.R;
import demo.com.testdemo.floatoperation.IFloatOperation;
import demo.com.testdemo.floatoperation.operation.Operation;


/**
 * Created by ludexiang on 2017/9/27.
 */

public class FloatOperationView extends RelativeLayout implements IFloatOperation, View.OnClickListener {
    private Context mContext;
    private ImageView mCancel;
    private TextView mTitle;
    private LinearLayout mRootLayout;
    private static final int COLUMN = 4;
    private Dialog mBottomUpDlg;
    private int mMargin;
    private int mMultiMargin;
    private View mTempView;
    private RelativeLayout mOperationRoot;

    private OnOperationItemClickListener mListener;
    private int mScreenHeight;
    private AnimatorSet set = new AnimatorSet();
    private int mExtraTxtWidth;
    private static final String CN_COUNTRY = "CN";
    private String mCountry;
    private int mLineMargin, mMultiLineMargin;

    public FloatOperationView(Context context) {
        this(context, null);
    }

    public FloatOperationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatOperationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        mBottomUpDlg = new Dialog(mContext, R.style.FloatOperationDialog);
        mMargin = getResources().getDimensionPixelOffset(R.dimen.oc_float_operation_margin_line);
        mMultiMargin = getResources().getDimensionPixelOffset(R.dimen.oc_float_operation_margin_multi_line);
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mExtraTxtWidth = getResources().getDimensionPixelOffset(R.dimen.oc_float_operation_extra_width);
        mLineMargin = getResources().getDimensionPixelOffset(R.dimen.oc_float_operation_extra_line_margin);
        mMultiLineMargin = getResources().getDimensionPixelOffset(R.dimen.oc_float_operation_extra_multiline_margin);
        mBottomUpDlg.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    close();
                    return true;
                }
                return false;
            }
        });
        mCountry = getResources().getConfiguration().locale.getCountry();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.oc_float_operations_layout, this, true);
        mCancel = (ImageView) view.findViewById(R.id.float_operation_cancel);
        mTitle = (TextView) view.findViewById(R.id.float_operations_title);
        mRootLayout = (LinearLayout) view.findViewById(R.id.float_operations_root);
        mTempView = view.findViewById(R.id.float_background);
        mOperationRoot = (RelativeLayout) view.findViewById(R.id.float_parent_root);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void setTitle(int titleRes) {
        mTitle.setText(titleRes);
    }

    @Override
    public void setTitle(String titleTxt) {
        mTitle.setText(titleTxt);
    }

    @Override
    public void close() {
        startAnim(false);
        mContext = null;
    }

    @Override
    public void show() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomUpDlg.addContentView(this, params);
        mBottomUpDlg.show();
        startAnim(true);
    }

    private void startAnim(final boolean isUp) {
        if (set.isRunning()) {
            return;
        }
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mTempView, "alpha", isUp ? 0f : 0.5f, isUp ? 0.5f : 0f);
        ObjectAnimator translate = ObjectAnimator.ofFloat(mOperationRoot, "translationY",
                isUp ? mScreenHeight : 0, isUp ? 0 : mScreenHeight);
        set.playTogether(alpha, translate);
        set.setDuration(500);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isUp) {
                    set.removeAllListeners();
                    mBottomUpDlg.dismiss();
                }
            }
        });
    }

    @Override
    public void setOperation(List<Operation> operations) {
        if (operations == null) {
            return;
        }

        int len = operations.size();
        // default center
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = mMargin;
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        if (len <= COLUMN) {
            boolean flag = false;
            for (int i = 0; i < len; i++) {
                Operation op = operations.get(i);
                TextView view = addExtraTextView(op.text);
                flag |= view.getVisibility() == View.VISIBLE;

            }

            params.bottomMargin = flag ? mMultiMargin : mMargin;
        }

        int row = 0;

        LinearLayout rowRoot = null; /** 每行的总父布局 **/

        /**每行总父布局的LayoutParams*/
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        rowParams.gravity = Gravity.CENTER;
        rowParams.weight = 1;

        for (int i = 0; i < len; i++) {
            if (row > 3) { // 最多展示4行
                break;
            }

            if (rowRoot == null) {
                rowRoot = new LinearLayout(mContext);
                rowRoot.setOrientation(LinearLayout.HORIZONTAL);
            }

            RelativeLayout itemParentRoot = new RelativeLayout(mContext); /** 每个item的总布局 **/

            final Operation op = operations.get(i);

            RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.oc_float_operations_item, null);
            ImageView img = (ImageView) itemView.findViewById(R.id.float_operation_item_img);
            TextView title = (TextView) itemView.findViewById(R.id.float_operation_item_title);

            itemView.setClickable(true);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (op != null) {
                        if (mListener != null) {
                            mListener.onItemClick(op);
                        }
                        close();
                    }

                }
            });

            img.setImageResource(op.imageResId);
            title.setText(op.text);

            if (len > COLUMN) {
                if (row > 0) {
                    // >= 2 line 修改上下边距
                    params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.bottomMargin = mMultiMargin;
                } else {
                    params.bottomMargin = mMultiMargin;
                }
            }

            itemParentRoot.addView(itemView, params); // 包含 上下 margin , itemRoot 包含icon 和 第一行标题
            itemParentRoot.addView(addExtraTextView(op.text), getExtraLayoutParams(row)); // 添加第二行文案

            rowRoot.addView(itemParentRoot, rowParams);

            if (row > 0 && rowRoot != null && rowRoot.getChildCount() < COLUMN && i == len - 1) {
                // 父布局weight 4
                rowRoot.setWeightSum(COLUMN);

                // 多行展示，不满一行左对齐
                mRootLayout.addView(rowRoot, row);
                return;
            }

            if (len > COLUMN && rowRoot.getChildCount() == COLUMN) {
                mRootLayout.addView(rowRoot, row);

                row++;
                rowRoot = null;
            }

            if (len <= COLUMN && i == len - 1) { // operation item <= COLUMN
                mRootLayout.addView(rowRoot, row);
            }
        }
    }

    /**
     * 如果文案超出一行则添加第二行
     * @return
     */
    private TextView addExtraTextView(int txtId) {
        TextView extraTxt = new TextView(mContext);
        String text = getResources().getString(txtId);
        // 中文一行最多展示6字符 英文14字符(算空格)
        int start = mCountry.equals(CN_COUNTRY) ? 6 : 14;
        if (text.length() > start) {
            extraTxt.setVisibility(View.VISIBLE);
            text = text.substring(start, text.length());
        } else {
            extraTxt.setVisibility(View.GONE);
        }
        extraTxt.setText(text.trim());
        extraTxt.setLines(1);
        extraTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        extraTxt.setTextColor(Color.parseColor("#333333"));
        return extraTxt;
    }

    private RelativeLayout.LayoutParams getExtraLayoutParams(int row) {
        RelativeLayout.LayoutParams extraParams = new RelativeLayout.LayoutParams(mExtraTxtWidth, FrameLayout.LayoutParams.WRAP_CONTENT);
        extraParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        extraParams.topMargin = row == 0 ? mLineMargin : mMultiLineMargin;
        return  extraParams;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.float_operation_cancel) {
            close();
        }
    }

    @Override
    public void setItemClickListener(OnOperationItemClickListener listener) {
        mListener = listener;
    }
}
