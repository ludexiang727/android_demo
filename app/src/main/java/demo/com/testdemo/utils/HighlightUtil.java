package demo.com.testdemo.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将字串中大括号包含的文字高亮显示（橘色）
 */

public class HighlightUtil {

    private static final String regularExpression = "\\{[^}]*\\}";

    public static CharSequence highlight(String input) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(input);

        Stack<Range> matches = new Stack<>();
        while (matcher.find()) {
            matches.push(new Range(matcher.start(), matcher.end()));
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(input);
        String colorStr = "#FC9153";
        int color = Color.parseColor(colorStr);
        while (matches.size() > 0) {
            Range range = matches.pop();
            builder.delete(range.start, range.start + 1);
            builder.delete(range.end - 2, range.end - 1);
            builder.setSpan(new ForegroundColorSpan(color), range.start, range.end - 2,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return builder;
    }

    public static CharSequence highlightWithScale(String input, float scale) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(input);

        Stack<Range> matches = new Stack<>();
        while (matcher.find()) {
            matches.push(new Range(matcher.start(), matcher.end()));
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(input);
        String colorStr = "#FC9153";
        int color = Color.parseColor(colorStr);
        while (matches.size() > 0) {
            Range range = matches.pop();
            builder.delete(range.start, range.start + 1);
            builder.delete(range.end - 2, range.end - 1);
            builder.setSpan(new ForegroundColorSpan(color), range.start, range.end - 2,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(new android.text.style.RelativeSizeSpan(scale), range.start, range
                    .end - 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return builder;
    }

    public static CharSequence highlight(String input, int defaultColor, int highLightColor) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(input);

        Stack<Range> matches = new Stack<>();
        while (matcher.find()) {
            matches.push(new Range(matcher.start(), matcher.end()));
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(input);
        if (matches.size() > 0) {//暂时只支持一个{}
            Range range = matches.pop();
            builder.delete(range.start, range.start + 1);
            builder.delete(range.end - 2, range.end - 1);
            builder.setSpan(new ForegroundColorSpan(highLightColor), range.start, range.end - 2,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(defaultColor), 0, range.start,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(defaultColor), range.end - 2, builder.length(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return builder;
    }

    static class Range {
        public final int start;
        public final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
