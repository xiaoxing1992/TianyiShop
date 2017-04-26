package tianyishop.weiwei.com.tianyishop.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.app.GoodsInfoActivity;
import tianyishop.weiwei.com.tianyishop.app.GoodsInfoInAct;
import tianyishop.weiwei.com.tianyishop.app.GuideActivity;
import tianyishop.weiwei.com.tianyishop.bean.GoodsInfoBean;
import tianyishop.weiwei.com.tianyishop.bean.GoodsInfoContentBean;
import tianyishop.weiwei.com.tianyishop.fragment.ChanpinCanshuFragment;
import tianyishop.weiwei.com.tianyishop.fragment.ChanpinContentFragment;
import tianyishop.weiwei.com.tianyishop.fragment.PinglunFragment;
import tianyishop.weiwei.com.tianyishop.util.DpUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/20.
 */

public class GoodInfoAdapter extends RecyclerView.Adapter {
    private final LayoutInflater layoutInflater;
    public static final int BANNER_GOODSINFO = 0;
    public static final int GOODSTITLE = 1;
    public static final int GOODSACT = 2;
    public static final int RELDETAILS = 3;
    public static final int GOODSCONTENT = 4;
    Context mContext;
    GoodsInfoBean.DataBean datas;
    private int is;
    //当前类型
    private int currentType = BANNER_GOODSINFO;

    public GoodInfoAdapter(Context mContext, GoodsInfoBean.DataBean datas) {
        this.mContext = mContext;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER_GOODSINFO) {
            return new BannerGoodsViewHolder(mContext, layoutInflater.from(mContext).inflate(R.layout.goods_info_viewpager, null));
        } else if (viewType == GOODSTITLE) {
            return new GoodsTitleViewHolder(mContext, layoutInflater.from(mContext).inflate(R.layout.goods_info_title, null));
        } else if (viewType == GOODSACT) {
            return new GoodsActViewHolder(mContext, layoutInflater.from(mContext).inflate(R.layout.goods_act, null));
        } else if (viewType == RELDETAILS) {
            return new DelViewHolder(mContext, layoutInflater.from(mContext).inflate(R.layout.goods_del, null));
        } else if (viewType == GOODSCONTENT) {
            return new GoodsContentViewHolder(mContext, layoutInflater.from(mContext).inflate(R.layout.goods_content, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER_GOODSINFO) {
            BannerGoodsViewHolder bannerGoodsViewHolder = (BannerGoodsViewHolder) holder;
            /* List<GoodsInfoBean.DataBean.GoodsBean.GalleryBean> gallery = datas.goods.gallery;
              Log.e("-------------",gallery.get(0).original_url);*/
            bannerGoodsViewHolder.setData(position);
        } else if (getItemViewType(position) == GOODSTITLE) {
            GoodsTitleViewHolder goodsTitleViewHolder = (GoodsTitleViewHolder) holder;
            goodsTitleViewHolder.setData(position);

        } else if (getItemViewType(position) == GOODSACT) {
            GoodsActViewHolder goodsActViewHolder = (GoodsActViewHolder) holder;
            goodsActViewHolder.setData(position);
        } else if (getItemViewType(position) == RELDETAILS) {
            DelViewHolder delViewHolder = (DelViewHolder) holder;
            delViewHolder.setData(position);
        } else if (getItemViewType(position) == GOODSCONTENT) {
            GoodsContentViewHolder goodsContentViewHolder = (GoodsContentViewHolder) holder;
            goodsContentViewHolder.setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER_GOODSINFO:
                currentType = BANNER_GOODSINFO;
                break;
            case GOODSTITLE:
                currentType = GOODSTITLE;
                break;
            case GOODSACT:
                currentType = GOODSACT;
                break;
            case RELDETAILS:
                currentType = RELDETAILS;
                break;
            case GOODSCONTENT:
                currentType = GOODSCONTENT;
                break;
        }

        return currentType;
    }

    class BannerGoodsViewHolder extends RecyclerView.ViewHolder {
        private int marginLeft;
        private final Context context;
        private final ViewPager goods_info_top_viewpager;
        private final LinearLayout goods_info_top_ll;
        private final LinearLayout goods_info_top_ll2;
        private final ImageView goods_info_sel_point;

        public BannerGoodsViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            goods_info_top_viewpager = (ViewPager) itemView.findViewById(R.id.goods_info_top_viewpager);
            goods_info_top_ll = (LinearLayout) itemView.findViewById(R.id.goods_info_top_ll);
            goods_info_sel_point = (ImageView) itemView.findViewById(R.id.goods_info_sel_point);
            goods_info_top_ll2 = (LinearLayout) itemView.findViewById(R.id.goods_info_top_ll2);


        }

        public void setData(int position) {

            ArrayList<ImageView> imgList = new ArrayList<>();
            List<GoodsInfoBean.DataBean.GoodsBean.GalleryBean> gallery = datas.goods.gallery;

            int dip = DpUtil.dip2px(context, 8);
            int dip2 = DpUtil.dip2px(context, 8);


            //**************************************************************************
            /*int dip = DpUtil.dip2px(context, 8);
            int dip2 = DpUtil.dip2px(context, 8);*/
            // Log.e("-------------", dip + "=====" + dip);
            if (gallery.size() > 2) {

                goods_info_sel_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

                //根据图片的数量不同   设置原点的第一个位置   本次设置成死的
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip,
                        dip);
                params.leftMargin = dip2;
                if (gallery.size() == 5) {
                    params.setMargins(0, 0, 191, 0);
                } else if (gallery.size() == 4) {
                    params.setMargins(0, 0, 48 + 96, 0);
                }
                goods_info_sel_point.setLayoutParams(params);

                for (int i = 0; i < gallery.size(); i++) {
                    ImageView imageView = new ImageView(context);
                    View view = new View(context);
                    view.setBackgroundResource(R.drawable.goods_info_point_select);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context).load(gallery.get(i).original_url).into(imageView);
                    imgList.add(imageView);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip, dip);
                    if (i != 0) {
                        layoutParams.leftMargin = dip2;
                    }
                    view.setEnabled(false);
                    goods_info_top_ll.addView(view, layoutParams);

                }
            } else {
                for (int i = 0; i < gallery.size(); i++) {
                    ImageView imageView = new ImageView(context);
                    View view = new View(context);
                    view.setBackgroundResource(R.drawable.goods_info_point_select);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context).load(gallery.get(i).original_url).into(imageView);
                    imgList.add(imageView);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip, dip);
                    if (i != 0) {
                        layoutParams.leftMargin = dip2;
                    }
                    view.setEnabled(false);
                    goods_info_top_ll.addView(view, layoutParams);

                }
            }
            GoodsInfoViewPagerAdapter goodsInfoViewPagerAdapter = new GoodsInfoViewPagerAdapter(context, imgList);
            goods_info_top_viewpager.setAdapter(goodsInfoViewPagerAdapter);

            goods_info_top_viewpager.addOnPageChangeListener(new MyPageChangeListener());


            //    goods_info_sel_point.set

        }

        class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {


            @Override
            public void onGlobalLayout() {
                goods_info_sel_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                marginLeft = goods_info_top_ll.getChildAt(1).getLeft() - goods_info_top_ll.getChildAt(0).getLeft();
                is = DpUtil.px2dip(context, marginLeft);
                //   Log.e("ssssssssssssss", i + "");

            }
        }

        class MyPageChangeListener implements ViewPager.OnPageChangeListener {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                float leftMarginTwo = (position + positionOffset) * marginLeft;

                LinearLayout.LayoutParams parma = (LinearLayout.LayoutParams) goods_info_sel_point.getLayoutParams();

                parma.leftMargin = (int) leftMarginTwo * 2;
                goods_info_sel_point.setLayoutParams(parma);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }
    }

    class GoodsTitleViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final TextView goods_info_collection;
        private final TextView goods_info_name;
        private final TextView goods_info_shop_price;
        private final TextView goods_info_market_price;
        private final ImageView goods_info_coupon;
        private final ImageView goods_info_pledge;
        private final TextView goods_info_shipping_fee;
        private final TextView goods_info_sales;
        private final TextView goods_info_collect_count;

        public GoodsTitleViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            goods_info_collection = (TextView) itemView.findViewById(R.id.goods_info_collection);
            goods_info_name = (TextView) itemView.findViewById(R.id.goods_info_name);
            goods_info_shop_price = (TextView) itemView.findViewById(R.id.goods_info_shop_price);
            goods_info_market_price = (TextView) itemView.findViewById(R.id.goods_info_market_price);
            goods_info_coupon = (ImageView) itemView.findViewById(R.id.goods_info_coupon);
            goods_info_pledge = (ImageView) itemView.findViewById(R.id.goods_info_pledge);
            goods_info_shipping_fee = (TextView) itemView.findViewById(R.id.goods_info_shipping_fee);
            goods_info_sales = (TextView) itemView.findViewById(R.id.goods_info_sales);
            goods_info_collect_count = (TextView) itemView.findViewById(R.id.goods_info_collect_count);

        }

        public void setData(int position) {
            GoodsInfoBean.DataBean.GoodsBean goods = datas.goods;
            goods_info_name.setText(goods.goods_name);
            goods_info_shop_price.setText("￥" + goods.shop_price);
            goods_info_market_price.setText("￥" + goods.market_price);
            goods_info_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            boolean is_coupon_allowed = goods.is_coupon_allowed;
            if (is_coupon_allowed) {
                goods_info_coupon.setVisibility(View.VISIBLE);
            } else {
                goods_info_coupon.setVisibility(View.GONE);
            }

            goods_info_shipping_fee.setText("￥" + goods.shipping_fee);
            int sales_volume = goods.sales_volume;
            if (sales_volume > 9999) {
                double price = sales_volume / 1000;
                goods_info_sales.setText(price + "万");
            } else {
                goods_info_sales.setText(sales_volume + "");
            }
            int collect_count = goods.collect_count;
            if (collect_count > 9999) {
                double collect_counts = collect_count / 1000;
                goods_info_collect_count.setText(collect_counts + "万");
            } else {
                goods_info_collect_count.setText(collect_count + "");
            }
        }
    }

    class GoodsActViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final LinearLayout goods_postage;
        private final LinearLayout goods_meet;
        private final LinearLayout goods_tips;

        public GoodsActViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            goods_postage = (LinearLayout) itemView.findViewById(R.id.goods_postage);
            goods_meet = (LinearLayout) itemView.findViewById(R.id.goods_meet);
            goods_tips = (LinearLayout) itemView.findViewById(R.id.goods_tips);
        }

        public void setData(int position) {
            goods_postage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String description = datas.activity.get(0).description;
                    Intent intent = new Intent(context, GoodsInfoInAct.class);
                    intent.putExtra("index", 0);
                    intent.putExtra("title", datas.activity.get(0).title);
                    intent.putExtra("html_url", description);
                    context.startActivity(intent);
                }
            });
            goods_meet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String description = datas.activity.get(1).description;
                    Intent intent = new Intent(context, GoodsInfoInAct.class);
                    intent.putExtra("index", 1);
                    intent.putExtra("title", datas.activity.get(1).title);
                    intent.putExtra("html_url", description);
                    context.startActivity(intent);
                }
            });
            goods_tips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String description = datas.activity.get(2).description;
                    Intent intent = new Intent(context, GoodsInfoInAct.class);
                    intent.putExtra("index", 2);
                    intent.putExtra("title", datas.activity.get(2).title);
                    intent.putExtra("html_url", description);
                    context.startActivity(intent);
                }
            });
        }
    }


    class DelViewHolder extends RecyclerView.ViewHolder {


        private final Context context;
        private final RecyclerView goods_del_recylerview;
        private final LinearLayout tuijianshop_ll;

        public DelViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            goods_del_recylerview = (RecyclerView) itemView.findViewById(R.id.goods_del_recylerview);
            tuijianshop_ll = (LinearLayout) itemView.findViewById(R.id.tuijianshop_ll);
        }

        public void setData(int position) {
            goods_del_recylerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            List<GoodsInfoBean.DataBean.GoodsRelDetailsBean> goodsRelDetails = datas.goodsRelDetails;
            if (goodsRelDetails != null && goodsRelDetails.size() > 0) {
                GoodsDelAdapter goodsDelAdapter = new GoodsDelAdapter(context, datas.goodsRelDetails);
                goods_del_recylerview.setAdapter(goodsDelAdapter);
                tuijianshop_ll.setVisibility(View.VISIBLE);
            } else {
                tuijianshop_ll.setVisibility(View.GONE);
            }

        }
    }

    class GoodsContentViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final TextView cp_xq_tv;
        private final TextView cp_cs_tv;
        private final TextView cp_pl_tv;
        // private final FrameLayout goods_content_fl;
        private FragmentManager manager;
        private ChanpinContentFragment chanpinContentFragment;
        private ChanpinCanshuFragment chanpinCanshuFragment;
        private PinglunFragment pinglunFragment;
        ArrayList<GoodsInfoContentBean> list = new ArrayList<>();
        int currentPosition = 1;
        private final LinearLayout goods_content_ll_one;
        private final LinearLayout goods_content_ll_two;
        private final LinearLayout goods_content_ll_three;
        //   private final ListView cp_listview;

        public GoodsContentViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            cp_xq_tv = (TextView) itemView.findViewById(R.id.cp_xq_tv);
            cp_cs_tv = (TextView) itemView.findViewById(R.id.cp_cs_tv);
            cp_pl_tv = (TextView) itemView.findViewById(R.id.cp_pl_tv);
            // cp_listview = (ListView) itemView.findViewById(R.id.cp_listview);
            goods_content_ll_one = (LinearLayout) itemView.findViewById(R.id.goods_content_ll_one);
            goods_content_ll_two = (LinearLayout) itemView.findViewById(R.id.goods_content_ll_two);
            goods_content_ll_three = (LinearLayout) itemView.findViewById(R.id.goods_content_ll_three);
            //  goods_content_fl = (FrameLayout) itemView.findViewById(R.id.goods_content_fl);
        }

        public void setData(int position) {
            //布局默认选中条目
            cp_xq_tv.setSelected(true);
            String goods_desc = datas.goods.goods_desc;
            JSONArray jsonArray = JSON.parseArray(goods_desc);
            for (int i = 0; i < jsonArray.size(); i++) {
                //   GoodsInfoContentBean goodsInfoContentBean = new GoodsInfoContentBean();
                ImageView imageView = new ImageView(mContext);
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String url = jsonObject.getString("url");
               /* goodsInfoContentBean.height = jsonObject.getString("height");
                goodsInfoContentBean.url = jsonObject.getString("url");
                goodsInfoContentBean.width = jsonObject.getString("width");*/
                // list.add(goodsInfoContentBean);
                Glide.with(mContext).load(url).into(imageView);
                goods_content_ll_one.addView(imageView);
            }
            //  final CpListViewAdapter cpListViewAdapter = new CpListViewAdapter(context, list);

            List<GoodsInfoBean.DataBean.GoodsBean.AttributesBean> attributes = datas.goods.attributes;
            for (int i = 0; i < attributes.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.goods_info_canshu_layout, null);
                TextView attr_name_tv = (TextView) view.findViewById(R.id.attr_name_tv);
                TextView attr_value_tv = (TextView) view.findViewById(R.id.attr_value_tv);
                attr_name_tv.setText(attributes.get(i).attr_name);
                attr_value_tv.setText(attributes.get(i).attr_value);
                goods_content_ll_two.addView(view);

            }


            List<GoodsInfoBean.DataBean.CommentsBean> comments = datas.comments;
            for (int i = 0; i < comments.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.goods_info_connents_layout, null);
                ImageView user_comment_img = (ImageView) view.findViewById(R.id.user_comment_img);
                TextView user_comment_username = (TextView) view.findViewById(R.id.user_comment_username);
                TextView user_comment_creattime = (TextView) view.findViewById(R.id.user_comment_creattime);
                TextView user_comment_content = (TextView) view.findViewById(R.id.user_comment_content);
                Glide.with(context).load(comments.get(i).user.icon).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(user_comment_img);
                user_comment_username.setText(comments.get(i).user.nick_name);
                String createtime = comments.get(i).createtime;
                String[] split = createtime.split("\\.");
                String s = split[2];
                String[] split1 = s.split("\\ ");
                user_comment_creattime.setText(split[0] + "年" + split[1] + "月" + split1[0] + "日");
                user_comment_content.setText(comments.get(i).content);
                goods_content_ll_three.addView(view);
            }
            cp_pl_tv.setText("评论(" + comments.size() + ")");
           /* GoodsInfoActivity goodsInfoActivity = (GoodsInfoActivity) context;
            manager = goodsInfoActivity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String goods_desc = datas.goods.goods_desc;
            chanpinContentFragment = new ChanpinContentFragment(goods_desc);
            chanpinCanshuFragment = new ChanpinCanshuFragment();
            pinglunFragment = new PinglunFragment();*/
           /* transaction.add(R.id.goods_content_fl, chanpinContentFragment);
            transaction.add(R.id.goods_content_fl, chanpinCanshuFragment);
            transaction.add(R.id.goods_content_fl, pinglunFragment);
            transaction.show(chanpinContentFragment);
            transaction.hide(chanpinCanshuFragment);
            transaction.hide(pinglunFragment);
            transaction.commit();*/
            cp_xq_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* currentPosition = 1;
                    Log.e("===============", list.size() + "");
                    cpListViewAdapter.notifyDataSetChanged();*/
                    cp_xq_tv.setSelected(true);
                    cp_cs_tv.setSelected(false);
                    cp_pl_tv.setSelected(false);
                    goods_content_ll_one.setVisibility(View.VISIBLE);
                    goods_content_ll_two.setVisibility(View.GONE);
                    goods_content_ll_three.setVisibility(View.GONE);
                   /* FragmentTransaction transaction = manager.beginTransaction();
                    transaction.show(chanpinContentFragment);
                    transaction.hide(chanpinCanshuFragment);
                    transaction.hide(pinglunFragment);
                    transaction.commit();*/
                }
            });
            cp_cs_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //currentPosition = 2;
                    cp_xq_tv.setSelected(false);
                    cp_cs_tv.setSelected(true);
                    cp_pl_tv.setSelected(false);
                    goods_content_ll_one.setVisibility(View.GONE);
                    goods_content_ll_two.setVisibility(View.VISIBLE);
                    goods_content_ll_three.setVisibility(View.GONE);
                   /* FragmentTransaction transaction = manager.beginTransaction();
                    transaction.show(chanpinCanshuFragment);
                    transaction.hide(chanpinContentFragment);
                    transaction.hide(pinglunFragment);
                    transaction.commit();*/
                }
            });
            cp_pl_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //currentPosition = 3;
                    cp_xq_tv.setSelected(false);
                    cp_cs_tv.setSelected(false);
                    cp_pl_tv.setSelected(true);
                    goods_content_ll_one.setVisibility(View.GONE);
                    goods_content_ll_two.setVisibility(View.GONE);
                    goods_content_ll_three.setVisibility(View.VISIBLE);
                   /* FragmentTransaction transaction = manager.beginTransaction();
                    transaction.show(pinglunFragment);
                    transaction.hide(chanpinContentFragment);
                    transaction.hide(chanpinCanshuFragment);
                    transaction.commit();*/
                }
            });

/*
            if (currentPosition == 1) {
                cp_listview.setAdapter(cpListViewAdapter);
            } else if (currentPosition == 2) {

            } else if (currentPosition == 3) {

            }*/
        }
    }
}
