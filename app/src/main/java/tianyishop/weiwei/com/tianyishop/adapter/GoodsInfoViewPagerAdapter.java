package tianyishop.weiwei.com.tianyishop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/20.
 */

public class GoodsInfoViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<ImageView> imgList;

    public GoodsInfoViewPagerAdapter(Context context, ArrayList<ImageView> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgList.get(position));
        return imgList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
