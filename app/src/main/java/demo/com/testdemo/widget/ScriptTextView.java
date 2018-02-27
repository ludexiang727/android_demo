package demo.com.testdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
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

  private static final int LOC_TOP = 0;
  private static final int LOC_MID = 1;
  private static final int LOC_BOTTOM = 2;
  private RectF rectF = new RectF();
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
  private int scriptSize;

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
    scriptSize = a.getDimensionPixelSize(R.styleable.ScriptTextView_script_text_size, 5);
    scriptDirection = a.getInt(R.styleable.ScriptTextView_script_direction, DIRECTION_RIGHT);
    a.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    RectF rect = getTextWidth((String) getText());
    setMeasuredDimension((int) (rect.width() + scriptSize * 2), (int) rect.height());
    rectF.set(rect);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int restore = canvas.save();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(scriptColor);
    paint.setTextSize(scriptSize);
    paint.setStyle(Style.FILL);

    float x = 0f;
    float y = 0f;
    FontMetrics metrics = paint.getFontMetrics();
    float scriptHeight = metrics.descent - metrics.ascent;
    if (scriptDirection == DIRECTION_RIGHT) {
      x = rectF.width() / 2 + 20;
    } else if (scriptDirection == DIRECTION_LEFT) {
      x = 0;
    }
    if (scriptLoc == LOC_TOP) {
      y = (rectF.height() - scriptHeight) / 2 - Math.abs(metrics.ascent) / 6;
    } else if (scriptLoc == LOC_MID) {
      y = (rectF.height() - scriptHeight) / 2 + metrics.descent / 2;
    } else if (scriptLoc == LOC_BOTTOM) {
      y = (rectF.height() - scriptHeight) / 2 + metrics.descent; // ascent 是baseline 到字符最高度的距离因为在baseline之上则为负值
    }

    canvas.translate(x, y);
    canvas.drawText(scriptText, x, y, paint);
    canvas.restoreToCount(restore);

    super.onDraw(canvas);
  }

  /**
   * 测量文案 宽度及高度 返回Rect
   */
  private RectF getTextWidth(String measureText) {
    RectF rectF = new RectF();
    TextPaint paint = getPaint();
    float measureWidth = paint.measureText(measureText);
    FontMetrics metrics = paint.getFontMetrics();
    float measureHeight = metrics.descent - metrics.ascent;
    rectF.set(0, 0, measureWidth, measureHeight);
    return rectF;
  }
}
