package tianyishop.weiwei.com.tianyishop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.bean.GoodsInfoContentBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/21.
 */

public class CpListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<GoodsInfoContentBean> list;

    public CpListViewAdapter(Context context, ArrayList<GoodsInfoContentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.goods_info_xiangqing_layout, null);
            holder.goods_info_xiangqing_img = (ImageView) view.findViewById(R.id.goods_info_xiangqing_img);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).url).into(holder.goods_info_xiangqing_img);
        return view;
    }

    class ViewHolder {
        ImageView goods_info_xiangqing_img;
    }
}
