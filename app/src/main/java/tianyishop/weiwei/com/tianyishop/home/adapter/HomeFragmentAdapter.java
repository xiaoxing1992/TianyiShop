package tianyishop.weiwei.com.tianyishop.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.home.bean.HomeBean;
import tianyishop.weiwei.com.tianyishop.util.GlideImageLoader;
import tianyishop.weiwei.com.tianyishop.view.FullyLinearLayoutManager;

/**
 * @类的用途: 首页数据适配器
 * @作者: 任正威
 * @date: 2017/4/14.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    Context context;
    HomeBean.DataBean dataList;

    private final LayoutInflater layoutInflater;

    public static final int BANNER = 0;

    public static final int CHANNEL = 1;

    public static final int BESTSELERS = 2;

    public static final int ACT = 3;

    public static final int HOT = 4;

    /**
     * 当前类型
     */
    private int currentType = BANNER;


    public HomeFragmentAdapter(Context context, HomeBean.DataBean dataList) {
        this.context = context;
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(context, layoutInflater.inflate(R.layout.banner_viewpager_layout, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(context, layoutInflater.inflate(R.layout.channel_layout, null));
        } else if (viewType == BESTSELERS) {
            return new BestselersViewHolder(context, layoutInflater.inflate(R.layout.bestselers_recycler_layout, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(context, layoutInflater.inflate(R.layout.act_viewpager_layout, null));
        } else if (viewType == HOT) {
            // return new HOTViewHolder(context, layoutInflater.inflate(R.layout.hot__layout, null));
        }

        return null;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(dataList.ad1);

        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(dataList.ad5);

        } else if (getItemViewType(position) == BESTSELERS) {
            BestselersViewHolder bestselersViewHolder = (BestselersViewHolder) holder;
            bestselersViewHolder.setData(dataList.bestSellers);
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(position, dataList.activityInfo.activityInfoList);
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case BESTSELERS:
                currentType = BESTSELERS;
                break;
            case ACT:
                currentType = ACT;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private View itemView;
        private Banner banner;

        public BannerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.itemView = itemView;
            this.banner = (Banner) itemView.findViewById(R.id.home_banner);

        }

        public void setData(List<HomeBean.DataBean.Ad1Bean> ad1) {
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < ad1.size(); i++) {
                String image = ad1.get(i).image;
                imagesUrl.add(image);
            }
            //设置banner样式
            banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(imagesUrl);

            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Accordion);
            //设置标题集合（当banner样式有显示title时）

            // banner.setBannerTitles(titles);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            //banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            banner.start();

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context, "点击的是" + (position - 1), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private GridView channel_gridview;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.channel_gridview = (GridView) itemView.findViewById(R.id.channel_gridview);
        }

        public void setData(List<HomeBean.DataBean.Ad5Bean> ad5) {
            ChannelAdapter channelAdapter = new ChannelAdapter(context, ad5);
            channel_gridview.setAdapter(channelAdapter);


            channel_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(context, "点击的是" + i, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class BestselersViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private List<HomeBean.DataBean.BestSellersBean> data;
        private final TextView bestselers_ti;
        private final RecyclerView bestselers_recycler_view;
        private final LinearLayout bestselers_more;

        public BestselersViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            bestselers_ti = (TextView) itemView.findViewById(R.id.bestselers_ti);
            bestselers_recycler_view = (RecyclerView) itemView.findViewById(R.id.bestselers_recycler_view);
            bestselers_more = (LinearLayout) itemView.findViewById(R.id.bestselers_more);


        }

        public void setData(List<HomeBean.DataBean.BestSellersBean> data) {
            bestselers_ti.setText(data.get(0).name);
            List<HomeBean.DataBean.BestSellersBean.GoodsListBeanX> goodsList = data.get(0).goodsList;
            FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(context, FullyLinearLayoutManager.HORIZONTAL, false);
            bestselers_recycler_view.setNestedScrollingEnabled(false);
            //设置布局管理器
            bestselers_recycler_view.setLayoutManager(linearLayoutManager);
            BestSelersRecyclerViewAdapter recyclerViewAdapter = new BestSelersRecyclerViewAdapter(context, goodsList);

            bestselers_recycler_view.setAdapter(recyclerViewAdapter);

            recyclerViewAdapter.setBestSelersOnItemListener(new BestSelersRecyclerViewAdapter.BestSelersOnItemListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(context, "点击的是" + position, Toast.LENGTH_SHORT).show();
                }
            });
            bestselers_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "点击的是更多功能", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final TextView act_ti;
        private final ViewPager act_viewpager;

        public ActViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            act_ti = (TextView) itemView.findViewById(R.id.act_ti);
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(int position, final List<HomeBean.DataBean.ActivityInfoBean.ActivityInfoListBean> activityInfoList) {

            act_ti.setText("优惠活动");
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);//>=3
            act_viewpager.setPageTransformer(true, new
                    ScaleInTransformer());
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return activityInfoList.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context).load(activityInfoList.get(position).activityImg).into(imageView);
                    container.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "点击的是" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

            act_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(context, "点击的是" + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
}
