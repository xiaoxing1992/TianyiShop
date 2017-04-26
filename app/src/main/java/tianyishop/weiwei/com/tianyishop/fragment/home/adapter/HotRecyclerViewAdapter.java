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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.fragment.home.bean.HomeBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/17.
 */

public class HotRecyclerViewAdapter extends RecyclerView.Adapter<HotRecyclerViewAdapter.HotVierHolder> {
    Context context;
    List<HomeBean.DataBean.SubjectsBean> subjectsBeanList;

    public HotRecyclerViewAdapter(Context context, List<HomeBean.DataBean.SubjectsBean> subjectsBeanList) {
        this.context = context;
        this.subjectsBeanList = subjectsBeanList;
    }

    @Override
    public HotVierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotVierHolder(LayoutInflater.from(context).inflate(R.layout.hot_item, null));
    }

    @Override
    public void onBindViewHolder(HotVierHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return subjectsBeanList.size();
    }

    class HotVierHolder extends RecyclerView.ViewHolder {


        private final ImageView hot_recycler_img;
        private final LinearLayout hot_recycler_add_ll;
        private final LinearLayout hot_recycler_more;

        public HotVierHolder(View itemView) {
            super(itemView);
            hot_recycler_img = (ImageView) itemView.findViewById(R.id.hot_recycler_img);
            hot_recycler_add_ll = (LinearLayout) itemView.findViewById(R.id.hot_recycler_add_ll);
            hot_recycler_more = (LinearLayout) itemView.findViewById(R.id.hot_recycler_more);


        }


        public void setData(final int position) {
            hot_recycler_img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(subjectsBeanList.get(position).image).into(hot_recycler_img);
            hot_recycler_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "图片" + position, Toast.LENGTH_SHORT).show();
                }
            });

            View view = null;
            HomeBean.DataBean.SubjectsBean subjectsBean = subjectsBeanList.get(position);
            List<HomeBean.DataBean.SubjectsBean.GoodsListBean> goodsList1 = subjectsBean.goodsList;
            for (int i = 0; i < 6; i++) {
                view = LayoutInflater.from(context).inflate(R.layout.bestselers_item, null);
                ImageView bestselers_img = (ImageView) view.findViewById(R.id.bestselers_img);
                TextView bestselers_title = (TextView) view.findViewById(R.id.bestselers_title);
                TextView bestselers_new_price = (TextView) view.findViewById(R.id.bestselers_new_price);
                TextView bestselers_old_price = (TextView) view.findViewById(R.id.bestselers_old_price);
                Glide.with(context).load(goodsList1.get(i).goods_img).into(bestselers_img);
                bestselers_title.setText(goodsList1.get(i).goods_name);
                bestselers_new_price.setText("￥" + goodsList1.get(i).shop_price);
                bestselers_old_price.setText("￥" + goodsList1.get(i).market_price);
                bestselers_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hotRecyclerOnItemListener.onItemClick(position);
                      //  Toast.makeText(context, "点击的是" + finalI, Toast.LENGTH_SHORT).show();
                    }
                });

                hot_recycler_add_ll.addView(view);

            }
            hot_recycler_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "点击的是更多图片", Toast.LENGTH_SHORT).show();
                    hotImageMoreOnListener.onClick(position);
                }
            });


        }
    }

    HotRecyclerOnItemListener hotRecyclerOnItemListener;

    public interface HotRecyclerOnItemListener {
        void onItemClick(int position);
    }



    public void setHotRecyclerOnItemListener(HotRecyclerOnItemListener hotRecyclerOnItemListener) {
        this.hotRecyclerOnItemListener = hotRecyclerOnItemListener;
    }

    HotImageMoreOnListener hotImageMoreOnListener;

    public interface HotImageMoreOnListener {
        void onClick(int position);
    }


    public void setHotImageMoreOnListener(HotImageMoreOnListener hotImageMoreOnListener) {
        this.hotImageMoreOnListener = hotImageMoreOnListener;
    }
}
