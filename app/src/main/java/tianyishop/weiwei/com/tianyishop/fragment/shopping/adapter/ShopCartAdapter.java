package tianyishop.weiwei.com.tianyishop.fragment.shopping.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.CartProvider;
import tianyishop.weiwei.com.tianyishop.bean.CartGoodsBean;
import tianyishop.weiwei.com.tianyishop.util.Select_Tools_Single;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/24.
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ShopCartViewHolder> {
    Context context;
    List<CartGoodsBean> cartGoodsBeanList;
    CheckBox checkbox_all;
    TextView tv_shopcart_total;
    CheckBox cb_all;
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;
    int type;
    int maxNum = 100;
    int minNum = 1;
    CartProvider cartProvider;
    private int restrict;


    public ShopCartAdapter(Context context, final List<CartGoodsBean> cartGoodsBeanList, final CheckBox checkbox_all, TextView tv_shopcart_total, final CheckBox cb_all, CartProvider cartProvider) {
        this.context = context;
        this.cartGoodsBeanList = cartGoodsBeanList;
        this.checkbox_all = checkbox_all;
        this.tv_shopcart_total = tv_shopcart_total;
        this.cb_all = cb_all;
        this.cartProvider = cartProvider;

        //首次加载数据


        showTotalPrice();
        checkbox_all.setChecked(true);
        for (int i = 0; i < cartGoodsBeanList.size(); i++) {
            cartGoodsBeanList.get(i).isChildSelected = true;
        }
        showTotalPrice();

        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                CartGoodsBean cartGoodsBean = cartGoodsBeanList.get(position);
                cartGoodsBean.isChildSelected = !cartGoodsBean.isChildSelected;
                notifyItemChanged(position);
                checkAll();
                showTotalPrice();

            }
        });

        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = checkbox_all.isChecked();
                checkAll_none(checked);
                showTotalPrice();
            }
        });

        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = cb_all.isChecked();
                checkAll_none(checked);
                showTotalPrice();
            }
        });
    }

    public void checkAll() {
        if (cartGoodsBeanList != null && cartGoodsBeanList.size() > 0) {
            for (int i = 0; i < cartGoodsBeanList.size(); i++) {
                if (!cartGoodsBeanList.get(i).isChildSelected) {
                    checkbox_all.setChecked(false);
                    cb_all.setChecked(false);
                    return;
                } else {
                    checkbox_all.setChecked(true);
                    cb_all.setChecked(true);
                }
            }
        }
    }

    public void showTotalPrice() {
        tv_shopcart_total.setText(getTotalPrice() / 100f + "");
    }

    private int getTotalPrice() {
        float total = 0;
        if (cartGoodsBeanList != null && cartGoodsBeanList.size() > 0) {
            for (int i = 0; i < cartGoodsBeanList.size(); i++) {
                CartGoodsBean cartGoodsBean = cartGoodsBeanList.get(i);
                if (cartGoodsBean.isChildSelected)
                    total += cartGoodsBean.shop_price * cartGoodsBean.number;
            }
        }
        return (int) (total * 100);
    }

    public void checkAll_none(boolean checked) {
        if (cartGoodsBeanList != null && cartGoodsBeanList.size() > 0) {
            for (int i = 0; i < cartGoodsBeanList.size(); i++) {
                cartGoodsBeanList.get(i).isChildSelected = checked;
                checkbox_all.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkbox_all.setChecked(false);

        }
    }

    public void deleteData() {
        if (cartGoodsBeanList != null && cartGoodsBeanList.size() > 0) {
            for (Iterator iterator = cartGoodsBeanList.iterator(); iterator.hasNext(); ) {

                CartGoodsBean cart = (CartGoodsBean) iterator.next();

                if (cart.isChildSelected) {

                    //这行代码放在前面
                    int position = cartGoodsBeanList.indexOf(cart);
                    //1.删除本地缓存的
                    cartProvider.deleteData(cart);

                    //2.删除当前内存的
                    iterator.remove();

                    //3.刷新数据
                    notifyItemRemoved(position);

                }
            }
        }
    }

    @Override
    public ShopCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopCartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_good_item, null));
    }

    @Override
    public void onBindViewHolder(final ShopCartViewHolder holder, final int position) {
        Glide.with(context).load(cartGoodsBeanList.get(position).goods_img).into(holder.child_good_img);
        holder.child_good_title_name_tv.setText(cartGoodsBeanList.get(position).goods_name);
        holder.child_good_shop_price_tv.setText("￥" + cartGoodsBeanList.get(position).shop_price);
        holder.child_good_number_tv.setText("数量：" + cartGoodsBeanList.get(position).number);
        if (type == ACTION_EDIT) {
            holder.delete_child_num_ll.setVisibility(View.GONE);
            // specialUpdate();
        } else if (type == ACTION_COMPLETE) {
            holder.delete_child_num_ll.setVisibility(View.VISIBLE);
            // specialUpdate();
        }
        holder.cb_child_good.setChecked(cartGoodsBeanList.get(position).isChildSelected);
        // notifyDataSetChanged();
       /* if (recycleview.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || (leagues_recycleview.isComputingLayout() == false)) {
            adpater.notifyDataSetChanged();
        }*/


        holder.child_pay_num.setText(cartGoodsBeanList.get(position).number + "");
        holder.child_jianhao_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cartGoodsBeanList.get(position).number--;
                int value = cartGoodsBeanList.get(position).number;
                value--;
                if (value <= maxNum && value >= minNum) {
                    holder.child_pay_num.setText(value + "");
                    cartGoodsBeanList.get(position).number = value;
                    cartProvider.updataData(cartGoodsBeanList.get(position));
                }
                if (value == maxNum) {
                    holder.child_jianhao_img.setEnabled(true);
                    holder.child_jiahao_img.setEnabled(false);
                } else if (value < maxNum) {
                    holder.child_jianhao_img.setEnabled(true);
                    holder.child_jiahao_img.setEnabled(true);
                } else if (value == minNum) {
                    holder.child_jianhao_img.setEnabled(false);
                    holder.child_jiahao_img.setEnabled(true);
                }

            }
        });
        holder.child_jiahao_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = cartGoodsBeanList.get(position).number;
                value++;
                if (value >= minNum && value <= maxNum) {
                    holder.child_pay_num.setText(value + "");
                    cartGoodsBeanList.get(position).number = value;
                    cartProvider.updataData(cartGoodsBeanList.get(position));
                }
                if (value == maxNum) {
                    holder.child_jianhao_img.setEnabled(true);
                    holder.child_jiahao_img.setEnabled(false);
                } else if (value < maxNum) {
                    holder.child_jianhao_img.setEnabled(true);
                    holder.child_jiahao_img.setEnabled(true);
                } else if (value == minNum) {
                    holder.child_jianhao_img.setEnabled(false);
                    holder.child_jiahao_img.setEnabled(true);
                }
            }
        });

       /* if (value == maxNum) {
            holder.child_jianhao_img.setEnabled(true);
            holder.child_jiahao_img.setEnabled(false);
        } else if (value < maxNum) {
            holder.child_jianhao_img.setEnabled(true);
            holder.child_jiahao_img.setEnabled(true);
        } else if (value == minNum) {
            holder.child_jianhao_img.setEnabled(false);
            holder.child_jiahao_img.setEnabled(true);
        }*/
    }

    @Override
    public int getItemCount() {
        return cartGoodsBeanList.size();
    }

    public void showChildEditNum(int type) {
        this.type = type;

    }

    public void specialUpdate() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);

    }

    class ShopCartViewHolder extends RecyclerView.ViewHolder {


        private final CheckBox cb_child_good;
        private final ImageView child_good_img;
        private final TextView child_good_title_name_tv;
        private final TextView child_good_shop_price_tv;
        private final TextView child_good_number_tv;
        private final ImageView child_jianhao_img;
        private final TextView child_pay_num;
        private final ImageView child_jiahao_img;
        public final LinearLayout delete_child_num_ll;
        private final LinearLayout shangping_cart_ll;

        public ShopCartViewHolder(View itemView) {
            super(itemView);
            cb_child_good = (CheckBox) itemView.findViewById(R.id.cb_child_good);
            shangping_cart_ll = (LinearLayout) itemView.findViewById(R.id.shangping_cart_ll);

            child_good_img = (ImageView) itemView.findViewById(R.id.child_good_img);
            child_good_title_name_tv = (TextView) itemView.findViewById(R.id.child_good_title_name_tv);
            child_good_shop_price_tv = (TextView) itemView.findViewById(R.id.child_good_shop_price_tv);
            child_good_number_tv = (TextView) itemView.findViewById(R.id.child_good_number_tv);
            child_jianhao_img = (ImageView) itemView.findViewById(R.id.child_jianhao_img);
            child_pay_num = (TextView) itemView.findViewById(R.id.child_pay_num);
            child_jiahao_img = (ImageView) itemView.findViewById(R.id.child_jiahao_img);
            delete_child_num_ll = (LinearLayout) itemView.findViewById(R.id.delete_child_num_ll);
            cb_child_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(view, getLayoutPosition());
                    }


                }
            });


        }
    }

    //回调点击事件的监听
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    //回调点击事件的监听
    private OnItemListener onItemListener;

    public interface OnItemListener {
        void onItemListener(List<CartGoodsBean> newList);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
}
