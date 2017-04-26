package tianyishop.weiwei.com.tianyishop.fragment.classify.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.app.GoodsInfoActivity;
import tianyishop.weiwei.com.tianyishop.fragment.classify.bean.ClassifyBean;
import tianyishop.weiwei.com.tianyishop.view.SpacesItemDecoration;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/19.
 */

public class ClassifyFragmentAdapter extends RecyclerView.Adapter {
    public static final int SHUXING = 0;

    public static final int GONGXIAO = 1;

    public static final int FUZHI = 2;

    public static final int MINGXING = 3;
    Context mContext;
    ClassifyBean.DataBean data;
    //当前类型
    int currentType = SHUXING;

    public ClassifyFragmentAdapter(Context mContext, ClassifyBean.DataBean data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SHUXING) {
            return new ShuxingViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.classify_shuxing_layout, parent, false));
        } else if (viewType == GONGXIAO) {
            return new GongxiaoViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.classify_gongxiao_layout, parent, false));
        } else if (viewType == FUZHI) {
            return new FuzhiViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.classify_fuzhi_layout, parent, false));
        } else if (viewType == MINGXING) {
            return new MingxingViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.classify_mingxing_layout, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == SHUXING) {
            ShuxingViewHolder shuxingViewHolder = (ShuxingViewHolder) holder;
            shuxingViewHolder.setData(position);
        } else if (getItemViewType(position) == GONGXIAO) {
            GongxiaoViewHolder gongxiaoViewHolder = (GongxiaoViewHolder) holder;
            gongxiaoViewHolder.setData(position);
        } else if (getItemViewType(position) == GONGXIAO) {
            FuzhiViewHolder fuzhiViewHolder = (FuzhiViewHolder) holder;
            fuzhiViewHolder.setData(position);
        } else if (getItemViewType(position) == MINGXING) {
            MingxingViewHolder mingxingViewHolder = (MingxingViewHolder) holder;
            mingxingViewHolder.setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case SHUXING:
                currentType = SHUXING;
                break;
            case GONGXIAO:
                currentType = GONGXIAO;
                break;
            case FUZHI:
                currentType = FUZHI;
                break;
            case MINGXING:
                currentType = MINGXING;
                break;
        }
        return currentType;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class ShuxingViewHolder extends RecyclerView.ViewHolder {


        private final Context context;

        public ShuxingViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
        }

        public void setData(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "这是" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    class GongxiaoViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        public GongxiaoViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
        }

        public void setData(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "这是" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    class FuzhiViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        public FuzhiViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
        }

        public void setData(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "这是" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    class MingxingViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final RecyclerView mingxing_recycler_view;

        public MingxingViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            mingxing_recycler_view = (RecyclerView) itemView.findViewById(R.id.mingxing_recycler_view);
        }

        public void setData(int position) {
            mingxing_recycler_view.setLayoutManager(new GridLayoutManager(context, 2));
            MingxingRecyclerAdapter mingxingRecyclerAdapter = new MingxingRecyclerAdapter(context, data.goodsBrief);
            mingxing_recycler_view.addItemDecoration(new SpacesItemDecoration(20));
            mingxing_recycler_view.setAdapter(mingxingRecyclerAdapter);

            mingxingRecyclerAdapter.setMingXingOnListener(new MingxingRecyclerAdapter.MingXingOnListener() {
                @Override
                public void onClick(int position) {
                    //Toast.makeText(context, "这是" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    String goodsId = data.goodsBrief.get(position).id;
                    intent.putExtra("id", goodsId);
                    context.startActivity(intent);

                }
            });

        }
    }
}

