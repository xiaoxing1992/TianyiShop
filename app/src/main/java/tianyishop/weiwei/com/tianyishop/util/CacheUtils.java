package tianyishop.weiwei.com.tianyishop.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/23.
 */

public class CacheUtils {

    private static final String SP_NAME = "cart_news";

    //取数据
    public static String getString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        return preferences.getString(key, "");
    }

    //存数据
    public static void saveString(Context context, String key, String values) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, values).commit();
    }
}
