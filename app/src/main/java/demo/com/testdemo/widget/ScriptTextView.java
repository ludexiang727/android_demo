package demo.com.testdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2018/2/9.
 * 带角标的TextView
 */

public class ScriptTextView extends AppCompatTextView {

  private static final int DIRECTION_RIGHT = 0;
  private static final int DIRECTION_LEFT = 1;
  /**
   * 角标位置
   * default bottom
   */
  private int scriptLoc;
  private int scriptColor;
  /**
   * default right
   */
  private int scriptDirection;
  private String scriptText;

  public ScriptTextView(Context context) {
    this(context, null);
  }

  public ScriptTextView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ScriptTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScriptTextView);
    scriptText = a.getString(R.styleable.ScriptTextView_script_text);
    scriptColor = a.getColor(R.styleable.ScriptTextView_script_color, getCurrentTextColor());
    scriptLoc = a.getInt(R.styleable.ScriptTextView_script_loc, 2);
    scriptDirection = a.getInt(R.styleable.ScriptTextView_script_direction, DIRECTION_RIGHT);
    a.recycle();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(scriptColor);
    paint.setTextSize(20);
    float textWidth = getTextWidth();

    FontMetrics fontMetrics = paint.getFontMetrics();
    float y = fontMetrics.top;
    Path path = new Path();
    if (scriptDirection == DIRECTION_RIGHT) {
      path.addRect(textWidth, 0, textWidth+100, 100, Direction.CW);
      canvas.drawTextOnPath(scriptText, path,0,0, paint);
    }
  }

  private float getTextWidth() {
    TextPaint paint = getPaint();
    return paint.measureText(getText(), 0, getText().length());
  }
}
