package tianyishop.weiwei.com.tianyishop.base;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)
                //        .displayer(new RoundedBitmapDisplayer(20))  //这三句会产生图片不加载
                //至于为什么   请高手赐教
                //    .showImageOnFail(R.mipmap.ic_launcher)
                //   .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration loaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheSizePercentage(20).diskCacheFileCount(1000).diskCacheSize(5 * 1024 * 1024)
                .defaultDisplayImageOptions(options)
                .memoryCacheExtraOptions(480, 800)
                .build();

        ImageLoader.getInstance().init(loaderConfiguration);


    }
}
