package tianyishop.weiwei.com.tianyishop.util;

import android.content.Context;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/11.
 */

public class DpUtil {

    //根据手机的分辨率从dip的单位转成为px（像素）
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //根据手机的分辨率从px（像素）的单位转成为 dip
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
