package tianyishop.weiwei.com.tianyishop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.bean.GoodsInfoBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/20.
 */

public class GoodsDelAdapter extends RecyclerView.Adapter<GoodsDelAdapter.MyViewHolder> {
    Context context;
    List<GoodsInfoBean.DataBean.GoodsRelDetailsBean> goodsRelDetailsList;

    public GoodsDelAdapter(Context context, List<GoodsInfoBean.DataBean.GoodsRelDetailsBean> goodsRelDetailsList) {
        this.context = context;
        this.goodsRelDetailsList = goodsRelDetailsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.del_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return goodsRelDetailsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        private final ImageView del_goods_img;
        private final TextView del_goods_title;
        private final TextView del_goods_old_price;
        private final TextView del_goods_new_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            del_goods_img = (ImageView) itemView.findViewById(R.id.del_goods_img);
            del_goods_title = (TextView) itemView.findViewById(R.id.del_goods_title);
            del_goods_new_price = (TextView) itemView.findViewById(R.id.del_goods_new_price);
            del_goods_old_price = (TextView) itemView.findViewById(R.id.del_goods_old_price);
        }

        public void setData(int position) {
            Glide.with(context).load(goodsRelDetailsList.get(position).goods_img).into(del_goods_img);
            del_goods_title.setText(goodsRelDetailsList.get(position).goods_name);
            del_goods_new_price.setText("￥" + goodsRelDetailsList.get(position).shop_price);
            del_goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            del_goods_old_price.setText("￥" + 0);
        }
    }
}
