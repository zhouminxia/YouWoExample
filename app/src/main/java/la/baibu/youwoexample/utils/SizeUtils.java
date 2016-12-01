package la.baibu.youwoexample.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

public class SizeUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getDesity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) dm.xdpi;
    }

    /**
     * 得到设备的宽度的dip
     */

    public static double gettSreenSizeInDip(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        // int height = metric.heightPixels; // 屏幕高度（像素）
        // float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5/2.0）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240 / 320）
        float density = densityDpi / 160;

        return width / density;
    }


    //屏幕的宽度
    public static int getSreenWidthPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    //屏幕的高度
    public static int getSreenHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = safeParseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int safeParseInt(String value) {
        return !TextUtils.isEmpty(value) && TextUtils.isDigitsOnly(value) && value.length() <= 10 ? Integer.parseInt(value) : 0;
    }

    /**
     * @param activity    当前界面
     * @param marginLeft  父控件左边留白 dp
     * @param marginRight 父控件右边留白 dp
     * @param sum         显示几个Item
     * @param itemMargin  Item之间的留白  dp
     * @param rate        宽和高的比率  为1时相等  宽高比率
     * @return
     */
    public static Point autoGetWidthAndHeight(Activity activity, int marginLeft, int marginRight, int sum, int itemMargin, float rate) {
        Point point = new Point();
        Display display = activity.getWindowManager().getDefaultDisplay();
        point.x = (display.getWidth() - SizeUtils.dip2px(activity, marginLeft) - SizeUtils.dip2px(activity, marginRight) - SizeUtils.dip2px(activity, itemMargin * (sum - 1))) / sum;
        if (rate == 1) {
            point.y = point.x;
        } else {
            point.y = (int) (point.x / rate);
        }
        return point;
    }

}
