package tianyishop.weiwei.com.tianyishop.home.adapter;

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
import tianyishop.weiwei.com.tianyishop.home.bean.HomeBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/15.
 */

public class BestSelersRecyclerViewAdapter extends RecyclerView.Adapter<BestSelersRecyclerViewAdapter.BestSelersViewHolder> {


    private final Context context;
    List<HomeBean.DataBean.BestSellersBean.GoodsListBeanX> goodsList;

    public BestSelersRecyclerViewAdapter(Context context, List<HomeBean.DataBean.BestSellersBean.GoodsListBeanX> goodsList) {

        this.context = context;
        this.goodsList = goodsList;
    }

    @Override
    public BestSelersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bestselers_item, null);

        return new BestSelersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BestSelersViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return goodsList.size() - 4;
    }

    class BestSelersViewHolder extends RecyclerView.ViewHolder {

        private final ImageView bestselers_img;
        private final TextView bestselers_title;
        private final TextView bestselers_new_price;
        private final TextView bestselers_old_price;
        private final LinearLayout ll_root;

        public BestSelersViewHolder(View itemView) {
            super(itemView);
            bestselers_img = (ImageView) itemView.findViewById(R.id.bestselers_img);
            bestselers_title = (TextView) itemView.findViewById(R.id.bestselers_title);
            bestselers_new_price = (TextView) itemView.findViewById(R.id.bestselers_new_price);
            bestselers_old_price = (TextView) itemView.findViewById(R.id.bestselers_old_price);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);

        }

        public void setData(final int position) {
            HomeBean.DataBean.BestSellersBean.GoodsListBeanX goodsListBeanX = goodsList.get(position);
            Glide.with(context).load(goodsListBeanX.goods_img).into(bestselers_img);
            bestselers_title.setText(goodsListBeanX.goods_name);
            bestselers_new_price.setText("￥" + goodsListBeanX.shop_price);
            bestselers_old_price.setText("￥" + goodsListBeanX.market_price);
            bestselers_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bestSelersOnItemListener!=null){
                        bestSelersOnItemListener.onItemClick(position);
                    }
                }
            });

        }
    }

    BestSelersOnItemListener bestSelersOnItemListener;

    public interface BestSelersOnItemListener {
        void onItemClick(int position);
    }


    public void setBestSelersOnItemListener(BestSelersOnItemListener bestSelersOnItemListener) {
        this.bestSelersOnItemListener = bestSelersOnItemListener;
    }
}
