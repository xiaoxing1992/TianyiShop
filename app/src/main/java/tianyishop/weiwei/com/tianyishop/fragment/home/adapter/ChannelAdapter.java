package tianyishop.weiwei.com.tianyishop.fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.fragment.home.bean.HomeBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/15.
 */

public class ChannelAdapter extends BaseAdapter {
    Context context;
    List<HomeBean.DataBean.Ad5Bean> ad5;

    public ChannelAdapter(Context context, List<HomeBean.DataBean.Ad5Bean> ad5) {
        this.context = context;
        this.ad5 = ad5;
    }

    @Override
    public int getCount() {
        return ad5.size();
    }

    @Override
    public Object getItem(int i) {
        return ad5.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChannelViewHolder holder;
        if (view == null) {
            holder = new ChannelViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
            holder.channel_img = (ImageView) view.findViewById(R.id.channel_img);
            holder.channel_title_name = (TextView) view.findViewById(R.id.channel_title_name);
            view.setTag(holder);
        } else {
            holder = (ChannelViewHolder) view.getTag();
        }
        Glide.with(context).load(ad5.get(i).image).into(holder.channel_img);
        holder.channel_title_name.setText(ad5.get(i).title);
        return view;

    }

    class ChannelViewHolder {
        ImageView channel_img;
        TextView channel_title_name;
    }
}
