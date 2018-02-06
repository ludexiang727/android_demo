package demo.com.testdemo.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 工具类
 */
public class Utils {

    static final int TIME_CHANGE = 3600000;   //60*60*1000 用于把毫秒转换成小时
    static final int DAY_HOURS = 24;

    /**
     * 将大等于60的描述转换为min:sec格式
     */
    public static String second2Min(int second) {
        int minute = 60;
        if (second < 60) {
            String secd = String.format("%sliding_view1$02d", second);
            return "00:" + secd;
        }
        int minInt = second / minute;
        int secInt = second % minute;
        if (minInt >= 1) {
            String mins = String.format("%sliding_view1$02d", minInt);
            String secd = String.format("%sliding_view1$02d", secInt);
            return mins + ":" + secd;
        }
        return second + "";
    }

    /**
     * 得到手机屏幕宽
     *
     * @return
     */
    public static int getWindowWidth(Activity window) {
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 得到手机屏幕高
     *
     * @return
     */
    public static int getWindowHeight(Activity window) {
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 关闭软键盘
     *
     * @param v
     */
    public static void closeInputMethod(View v) {
        if (v == null)
            return;
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

//    /**
//     * 获取手机屏幕的分辨率
//     */
//    public static String getScreenPixels() {
//        StringBuffer screenInfo = new StringBuffer();
//        screenInfo.append(SystemUtil.getScreenWidth()).append("*").append(SystemUtil.getScreenHeight());
//        return screenInfo.toString();
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private static long lastClickTime;
    private static long delayTime = 800;

    public static String formatePrice(double price) {
        if (price == 0) {
            return "0";
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            return subZeroAndDot(df.format(price));
        }
    }

    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 将日期转成毫秒
     *
     * @author ChenHuajiang
     */
    public static long converDateToMillisecond(String date) {
        if (TextUtils.isEmpty(date))
            return 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long mills = 0;
        try {
            Date d = sdf.parse(date);
            mills = d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mills;
    }

    /**
     * 得到手机当前正在使用的网络类型，如果是wifi的话，返回 wifi字符串，否则返回other_类型编码，参考TelephonyManager
     *
     * @return
     */
    public static String getCurrentApnType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (ni.isConnected()) { // 如果有wifi连接，则选择wifi，不返回代理
            return "wifi";
        }

        TelephonyManager telmanager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int typ = telmanager.getNetworkType();

        String type = "NULL";
        if (typ == TelephonyManager.NETWORK_TYPE_EDGE) {
            type = "EDGE"; // sliding_view2.75G
        }
        if (typ == TelephonyManager.NETWORK_TYPE_GPRS) {
            type = "GPRS"; // 2G
        }
        if (typ == TelephonyManager.NETWORK_TYPE_UMTS) {
            type = "UTMS"; // 3G
        }
        if (typ == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
            type = "UNKNOWN";
        }
        return type;
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        boolean result = false;
        try {
            AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
            boolean isAccessibilityEnabled = am.isEnabled();
            boolean isExploreByTouchEnabled = am.isTouchExplorationEnabled();
            result = isAccessibilityEnabled && isExploreByTouchEnabled;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }

    }

    /**
     * 是否在前台
     */
    public static boolean isAppFront(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = am.getRunningTasks(1);
        if (tasksInfo != null && tasksInfo.size() > 0) {
            if (tasksInfo.get(0).topActivity.getClassName().contains("com.didi")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 防止快速重复点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < delayTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 隐藏输入法
     */
    public static void hideInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断当前应用是否正在运行
     */
    public static boolean isMyAppRunning(Context c) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo info : list) {
            String packageName = info.topActivity.getPackageName();
            if (getPackageName(c).equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取软件的PackageName
     */
    public static String getPackageName(Context context) {
        String pkg = context.getApplicationContext().getPackageName();
        return pkg;
    }

    /**
     * 将long型转化为 “明天 18:00"格式
     */
    public static String convertMillisToString(Context context, long time) {
        return convertMillisToString(context, time, false);
    }

    /**
     * 将long型转化为 “明天 18:00"格式
     */
    public static String convertMillisToString(Context context, long time, boolean forceBeijingZone) {
        String dateString = "";
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (forceBeijingZone) {
            sdf.setTimeZone(getBeijingTimeZone());
        }
//        switch (getDayDiff(System.currentTimeMillis(), time, forceBeijingZone)) {
//            case 0:
//                dateString = ResourcesHelper.getString(context, R.string.car_time_picker_today) + " " + sdf.format(date);
//                break;
//            case sliding_view1:
//                dateString = ResourcesHelper.getString(context, R.string.car_time_picker_tomorrow) + " " + sdf.format(date);
//                break;
//            case sliding_view2:
//                dateString = ResourcesHelper.getString(context, R.string.car_time_picker_after_tomorrow) + " " + sdf.format(date);
//                break;
//            default:
//                Date date2 = new Date(time);
//                SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日 HH:mm");
//                if (forceBeijingZone) {
//                    sdf2.setTimeZone(getBeijingTimeZone());
//                }
//                dateString = sdf2.format(date2);
//                break;
//        }
        return dateString;
    }

    /**
     * 将long型转化为 “明天 18:00"格式
     */
    public static String convertSimpleMillis(Context context, long time, boolean forceBeijingZone) {
        String dateString = "";
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (forceBeijingZone) {
            sdf.setTimeZone(getBeijingTimeZone());
        }
//        switch (getDayDiff(System.currentTimeMillis(), time, forceBeijingZone)) {
//            case 0:
//                dateString = ResourcesHelper.getString(context, R.string.car_time_picker_today) + " " + sdf.format(date);
//                break;
//            case sliding_view1:
//                dateString = ResourcesHelper.getString(context, R.string.car_time_picker_tomorrow) + " " + sdf.format(date);
//                break;
//            case sliding_view2:
//                dateString = ResourcesHelper.getString(context, R.string.car_time_picker_after_tomorrow) + " " + sdf.format(date);
//                break;
//            default:
//                Date date2 = new Date(time);
//                SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日 HH:mm");
//                if (forceBeijingZone) {
//                    sdf2.setTimeZone(getBeijingTimeZone());
//                }
//                dateString = sdf2.format(date2);
//                break;
//        }
        return dateString;
    }

    public static int getDayDiff(long time1, long time2) {
        return getDayDiff(time1, time2, false);
    }


    public static int getDayDiff(long time1, long time2, boolean forceBeijingZone) {
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        if (forceBeijingZone) {
            cal1.setTimeZone(getBeijingTimeZone());
            cal2.setTimeZone(getBeijingTimeZone());
        }
        long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
        long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);

        int hr1 = (int) (ldate1 / TIME_CHANGE); // 60*60*1000
        int hr2 = (int) (ldate2 / TIME_CHANGE);

        int days1 = hr1 / DAY_HOURS;
        int days2 = hr2 / DAY_HOURS;

        int dateDiff = days2 - days1;
        return dateDiff;
    }

    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static TimeZone getBeijingTimeZone() {
        return TimeZone.getTimeZone("GMT+08");
    }

    /**
     * 今天 sliding_view15：40
     *
     * @param millis
     * @return
     */
    public static String getDayOfMillis(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(date);

        Calendar curent = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int days = calendar.get(Calendar.DAY_OF_YEAR) - curent.get(Calendar.DAY_OF_YEAR);
        if (days == 0) {
            time = "今天 " + time;
        } else if (days == 1) {
            time = "明天 " + time;
        } else if (days == 2) {
            time = "后天 " + time;
        }
        return time;
    }

    public static String sha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes());

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 去掉城市名称最后的“市”，例如：北京市->北京
     *
     * @param city 要处理的城市名
     * @return 处理后的城市名
     */
    public static String getSimpleCityName(String city) {
//        if (TextUtil.isEmpty(city)) {
//            return null;
//        } else {
//            int len = city.trim().length();
//            if (city.substring(len - sliding_view1).equalsIgnoreCase("市") && len > sliding_view1) {
//                return city.substring(0, len - sliding_view1);
//            } else {
//                return city;
//            }
//        }
        return null;
    }

    /**
     * 显示输入法
     */
    public static void showInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo network = cm.getActiveNetworkInfo();
        if (network != null) {
            return network.isConnected() || network.isAvailable();
        } else {
            return false;
        }
    }

    /**
     * 将"明天 12月31日 18:00"格式的时间转成long型的
     */
    public static long getTimeMillisFromCalendar(String str) {
        return getTimeMillisFromCalendar(str, true);
    }

    /**
     * 将"明天 12月31日 18:00"格式的时间转成long型的
     */
    public static long getTimeMillisFromCalendar(String str, boolean forceBeijingZone) {
//        String tomorrow = getString(R.string.car_date_time_tomorrow);
//        String afterTomorrow = getString(R.string.car_date_time_after_tomorrow);
//        Calendar calendar = Calendar.getInstance();
//        if (forceBeijingZone) {
//            calendar.setTimeZone(TimeUtil.getBeijingTimeZone());
//        }
////        Date d = null;
//
//        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//        Pattern pattern = Pattern.compile("\\d+日");
//        Matcher matcher = pattern.matcher(str);
//        if (matcher != null && matcher.find()) {
//            int day = Integer.parseInt(str.substring(matcher.start(), matcher.end() - sliding_view1));
//            if (day == currentDay + sliding_view1) {
//                calendar.add(Calendar.DAY_OF_MONTH, sliding_view1);
////            d = calendar.getTime();
//            } else if (day == currentDay + sliding_view2) {
//                calendar.add(Calendar.DAY_OF_MONTH, sliding_view2);
////            d = calendar.getTime();
//            } else {
////            d = calendar.getTime();
//            }
//        }
//        calendar.set(Calendar.HOUR_OF_DAY, getHour(str, sliding_view2));
//        calendar.set(Calendar.MINUTE, getMinutes(str, sliding_view2));
//        calendar.set(Calendar.SECOND, 0);
////        d.setHours(getHour(str,sliding_view2));
////        d.setMinutes(getMinutes(str,sliding_view2));
////        d.setSeconds(0);
//
////        return d.getTime();
//        return calendar.getTimeInMillis();
        return 0l;
    }

    /**
     * 从字符串资源文件读取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
//        return ResourcesHelper.getString(GlobalContext.getContext(), resId);
        return null;
    }

    public static String encode(String string) {
        if (Utils.isTextEmpty(string))
            return "";
        try {
            string = URLEncoder.encode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        return string;
    }

    /**
     * 获取小时数
     */
    public static int getHour(String str, int blankCount) {
        int ret = 0;
        if (str.split(" ").length < (blankCount + 1)) {
            return ret;
        }
        String hh_mm = str.split(" ")[blankCount];
        String s = hh_mm.substring(0, 2);
        try {
            ret = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取分钟数
     */
    private static int getMinutes(String str, int blankCount) {
        int ret = 0;
        if (str.split(" ").length < (blankCount + 1)) {
            return ret;
        }
        String hh_mm = str.split(" ")[blankCount];

        String s = hh_mm.substring(3);
        try {
            ret = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isTextEmpty(String text) {
//        if (TextUtil.isEmpty(text))
//            return true;
        return "null".equalsIgnoreCase(text);
    }


//    /**
//     * 发单门槛掉起预充值界面
//     *
//     * @param businessId 业务线ID
//     * @param price      充值价格
//     */
//    public static void startPrepay(Activity activity, int businessId, float price) {
//        IDidiPayApi api = DidiPayApiFactory.createDidiPay();
//        DidiPrepayData.Param request = getPayParam(businessId, price);
//        api.prePay(activity, request, REQUEST_CODE_PREPAY);
//    }
//
//    public static void startPrepay(Fragment fragment, int businessId, float price, int requestCode) {
//        IDidiPayApi api = DidiPayApiFactory.createDidiPay();
//        DidiPrepayData.Param request = getPayParam(businessId, price);
//        api.prePay(fragment, request, requestCode);
//    }
//
//    private static DidiPrepayData.Param getPayParam(int businessId, float price) {
//        DidiPrepayData.Param request = new DidiPrepayData.Param();
//        // token，请填入真实的 LoginFacade.getToken()
//        request.token = LoginFacade.getToken();
//        request.businessId = businessId;
//        // 充值卡类型
//        request.cardType = businessId == SidConverter.SCAR ? 200 : 210;
//        // 充值金额（单位/元）
//        request.sum = price;
//        return request;
//    }

    /**
     * 得到手机的IMEI号
     *
     * @return
     */
    public static String getIMEI() {
//        try {
//            TelephonyManager mTelephonyMgr = (TelephonyManager) GlobalContext.getContext().getSystemService(Context.TELEPHONY_SERVICE);
//            if (mTelephonyMgr.getDeviceId() == null) {
//                return "";
//            } else
//                return mTelephonyMgr.getDeviceId();
//        } catch (Exception e) {
//            return "";
//        }
        return "";
    }


    private static String sSDCardId = null;

    /**
     * 获取SD卡的ID
     * @return SD卡ID信息, 没有SD卡返回null
     */
    public static String getSDCardId() {
        if (!TextUtils.isEmpty(sSDCardId)) {
            return sSDCardId;
        }
        String pathPattern = "/sys/block/mmcblk%s/device/";
        String typePathPattern = pathPattern + "type";
        String cidPathPattern = pathPattern + "cid";
        for (int i =0 ;i<5; i++) {
            String typePath = String.format(typePathPattern, i);
            String type = readFromPath(typePath);
            if (TextUtils.isEmpty(type)) {
                continue;
            }
            if (!type.equalsIgnoreCase("mmc") && type.equalsIgnoreCase("sd")) {
                continue;
            }
            String cidPath = String.format(cidPathPattern, i);
            String cid = readFromPath(cidPath);
            if (!TextUtils.isEmpty(cid)) {
                sSDCardId = cid;
                break;
            }
        }
        return sSDCardId;
    }

    private static String readFromPath(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 获取SIM的iccid信息,即SIM卡的串号
     * @param ctx
     * @return
     */
    public static String getSimSerialNumber(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            return tm.getSimSerialNumber();
        } catch (Exception e) {
            return null;
        }
    }

    ///////////////////////////// 以下是Bitmap 工具方法

    /**
     * 画空圆
     */
    public static void emptyCircle() {

    }

    /**
     * view获取bitmap
     *
     * @param addViewContent
     * @return
     */
    public static Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(addViewContent.getDrawingCache());
        addViewContent.setDrawingCacheEnabled(false);

        return bitmap;
    }



    public static Bitmap combineBitMap(final Bitmap background, final Bitmap front) {
        //取得底层的宽高
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();

        //创建新bitmap
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);

        if(!background.isRecycled() && front!= null && !front.isRecycled()){
            cv.drawBitmap(background, 0f, 0f, null);//在 0，0坐标开始画入bg
            cv.drawBitmap(front, 19f, 17f, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        }

        return  newbmp;
    }

    /**
     * 将bitmap 转换成 String
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        if (TextUtils.isEmpty(st))
            return null;
        // OutputStream out;
        Bitmap bitmap;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 得到圆形的bitmap
     * @param bitmap
     * @param borderWidth
     * @return
     */
    public static Bitmap getCircularBitmap(Bitmap bitmap, int borderWidth) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        Bitmap resizedBitmap = getResizedBitmap(bitmap, 54, 54);

        int width = resizedBitmap.getWidth() + borderWidth;
        int height = resizedBitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(resizedBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
        return canvasBitmap;
    }
}
