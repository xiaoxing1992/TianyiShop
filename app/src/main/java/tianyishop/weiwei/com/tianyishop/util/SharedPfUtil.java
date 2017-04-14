package tianyishop.weiwei.com.tianyishop.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @类的用途: 引导页工具类
 * @作者: 任正威
 * @date: 2017/4/11.
 */

public class SharedPfUtil {
    public static void putSharedContent(Context context, String firstName, boolean isFirst) {
        SharedPreferences preferences = context.getSharedPreferences("guide_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(firstName, isFirst);
        editor.commit();
    }

    public static boolean getSharedContent(Context context, String firstName) {
        SharedPreferences preferences = context.getSharedPreferences("guide_config", Context.MODE_PRIVATE);
        boolean b = preferences.getBoolean(firstName, false);
        return b;
    }
}
