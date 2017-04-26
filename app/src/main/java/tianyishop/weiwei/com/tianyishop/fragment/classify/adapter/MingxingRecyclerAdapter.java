package tianyishop.weiwei.com.tianyishop.fragment.classify.adapter;

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
import tianyishop.weiwei.com.tianyishop.fragment.classify.bean.ClassifyBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/19.
 */

public class MingxingRecyclerAdapter extends RecyclerView.Adapter<MingxingRecyclerAdapter.MViewHolder> {
    Context context;
    List<ClassifyBean.DataBean.GoodsBriefBean> goodsBrief;

    public MingxingRecyclerAdapter(Context context, List<ClassifyBean.DataBean.GoodsBriefBean> goodsBrief) {
        this.context = context;
        this.goodsBrief = goodsBrief;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //由于布局相同 ，故 界面复用首页最下方grid布局
        return new MViewHolder(LayoutInflater.from(context).inflate(R.layout.default_goods_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {

        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout default_goods_ll;
        private final ImageView default_goods_img;
        private final TextView default_goods_title;
        private final TextView default_goods_new_price;
        private final TextView default_goods_old_price;

        public MViewHolder(View itemView) {
            super(itemView);
            default_goods_ll = (LinearLayout) itemView.findViewById(R.id.default_goods_ll);
            default_goods_img = (ImageView) itemView.findViewById(R.id.default_goods_img);
            default_goods_title = (TextView) itemView.findViewById(R.id.default_goods_title);
            default_goods_new_price = (TextView) itemView.findViewById(R.id.default_goods_new_price);
            default_goods_old_price = (TextView) itemView.findViewById(R.id.default_goods_old_price);
        }

        public void setData(final int position) {
            default_goods_img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(goodsBrief.get(position).goods_img).into(default_goods_img);
            default_goods_title.setText(goodsBrief.get(position).goods_name);
            default_goods_new_price.setText("￥" + goodsBrief.get(position).shop_price);
            default_goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            default_goods_old_price.setText("￥" + goodsBrief.get(position).market_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mingXingOnListener.onClick(position);
                }
            });

        }
    }

    MingXingOnListener mingXingOnListener;

    public interface MingXingOnListener {
        void onClick(int position);
    }

    public void setMingXingOnListener(MingXingOnListener mingXingOnListener) {
        this.mingXingOnListener = mingXingOnListener;
    }
}
