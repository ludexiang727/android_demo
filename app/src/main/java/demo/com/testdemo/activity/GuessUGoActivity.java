package demo.com.testdemo.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import demo.com.testdemo.R;

/**
 * Created by ludexiang on 16/9/21.
 */
public class GuessUGoActivity extends Activity {
    private TextView endText;
    private PopupWindow mPopupWindow;
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guess_u_go_layout);

        endText = (TextView) findViewById(R.id.end_adr);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();

        density = metrics.density;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void showGuessUGoPop() {
        int[] location = location(endText);
        CharSequence txt = endText.getHint();
        Rect rect = new Rect();
        mPopupWindow = new PopupWindow(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.guess_u_go_pop_layout, null);
        mPopupWindow.setContentView(view);
        mPopupWindow.showAtLocation(endText, Gravity.BOTTOM, 120, 40);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        showGuessUGoPop();
    }

    private int[] location(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }
}
