package tianyishop.weiwei.com.tianyishop.fragment.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.fragment.home.bean.HomeBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/17.
 */

public class DefaultGoodsAdapter extends RecyclerView.Adapter<DefaultGoodsAdapter.DefaultGoodsViewHolder> {
    Context context;
    List<HomeBean.DataBean.DefaultGoodsListBean> defaultGoodsList;

    public DefaultGoodsAdapter(Context context, List<HomeBean.DataBean.DefaultGoodsListBean> defaultGoodsList) {
        this.context = context;
        this.defaultGoodsList = defaultGoodsList;
    }

    @Override
    public DefaultGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultGoodsViewHolder(LayoutInflater.from(context).inflate(R.layout.default_goods_item, null));
    }

    @Override
    public void onBindViewHolder(DefaultGoodsViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return defaultGoodsList.size();
    }

    class DefaultGoodsViewHolder extends RecyclerView.ViewHolder {


        private final LinearLayout default_goods_ll;
        private final ImageView default_goods_img;
        private final TextView default_goods_title;
        private final TextView default_goods_new_price;
        private final TextView default_goods_old_price;

        public DefaultGoodsViewHolder(View itemView) {
            super(itemView);
            //找控件
            default_goods_ll = (LinearLayout) itemView.findViewById(R.id.default_goods_ll);
            default_goods_img = (ImageView) itemView.findViewById(R.id.default_goods_img);
            default_goods_title = (TextView) itemView.findViewById(R.id.default_goods_title);
            default_goods_new_price = (TextView) itemView.findViewById(R.id.default_goods_new_price);
            default_goods_old_price = (TextView) itemView.findViewById(R.id.default_goods_old_price);
        }

        //数据适配
        public void setData(final int position) {
            HomeBean.DataBean.DefaultGoodsListBean defaultGoodsListBean = defaultGoodsList.get(position);
          //  default_goods_img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(defaultGoodsListBean.goods_img).into(default_goods_img);
            default_goods_title.setText(defaultGoodsListBean.goods_name);
            default_goods_new_price.setText("￥" + defaultGoodsListBean.shop_price);
            default_goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            default_goods_old_price.setText("￥" + defaultGoodsListBean.market_price);
            default_goods_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (defaultGoodsOnListener != null) {
                        defaultGoodsOnListener.onClick(position);
                    }
                }
            });
        }



    }

    DefaultGoodsOnListener defaultGoodsOnListener;

    interface DefaultGoodsOnListener {
        void onClick(int position);
    }

    public void setDefaultGoodsOnListener(DefaultGoodsOnListener defaultGoodsOnListener) {
        this.defaultGoodsOnListener = defaultGoodsOnListener;
    }
}
