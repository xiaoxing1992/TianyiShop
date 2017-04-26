package tianyishop.weiwei.com.tianyishop.fragment.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.app.GoodsInfoActivity;
import tianyishop.weiwei.com.tianyishop.app.LoginPhoneActivity;
import tianyishop.weiwei.com.tianyishop.app.MainActivity;
import tianyishop.weiwei.com.tianyishop.app.RegisterActivity;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.base.CartProvider;
import tianyishop.weiwei.com.tianyishop.bean.CartGoodsBean;
import tianyishop.weiwei.com.tianyishop.fragment.shopping.activity.AffirmActivity;
import tianyishop.weiwei.com.tianyishop.fragment.shopping.adapter.ShopCartAdapter;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;
import tianyishop.weiwei.com.tianyishop.util.SharedPfUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/12.
 */

public class ShoppingFragment extends BaseFragment {


    private LinearLayout ll_empty_shopcart;
    private TextView cart_title_edit_tv;
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;
    private LinearLayout ll_check_all;
    private LinearLayout ll_delete;
    private RecyclerView cart_recyclerview;
    private ShopCartAdapter shopCartAdapter;
    private CheckBox checkbox_all;
    private TextView tv_shopcart_total;
    private CheckBox cb_all;
    private TextView cart_title_name_tv;
    private Button btn_delete;
    private Button btn_check_out;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.shoppingfragment, null);
        ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);


        cart_recyclerview = (RecyclerView) view.findViewById(R.id.cart_recyclerview);

        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        ll_check_all = (LinearLayout) view.findViewById(R.id.ll_check_all);
        ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
        btn_check_out = (Button) view.findViewById(R.id.btn_check_out);
        cart_title_edit_tv = (TextView) view.findViewById(R.id.cart_title_edit_tv);
        cart_title_name_tv = (TextView) view.findViewById(R.id.cart_title_name_tv);
        checkbox_all = (CheckBox) view.findViewById(R.id.checkbox_all);
        tv_shopcart_total = (TextView) view.findViewById(R.id.tv_shopcart_total);
        cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        initListener();
        cart_title_edit_tv.setTag(ACTION_EDIT);
        cart_title_edit_tv.setText("编辑");
        ll_check_all.setVisibility(View.VISIBLE);
        boolean is_login = SharedPfUtil.getSharedContent(mContext, "is_login");

        showData();


    }

    private void showDataServer() {
        String url = AppUrl.url_ser + "/bullking1/cart?userID=" + SharedPfUtil.getSharedId(mContext, "user_id");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        Request request = builder.get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (str != null) {
                            // Log.e("=============", str);
                        }
                    }
                });


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        boolean is_login = SharedPfUtil.getSharedContent(mContext, "is_login");
        showData();
    }

    private void initListener() {

        cart_title_edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) cart_title_edit_tv.getTag();
                if (tag == ACTION_EDIT) {
                    showDelete();
                } else if (tag == ACTION_COMPLETE) {
                    hideDelete();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCartAdapter.deleteData();
                shopCartAdapter.showTotalPrice();
                //显示空空如也
                checkData();
                shopCartAdapter.checkAll();

            }
        });

        btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        //判断是否登录
        //程序默认是未登录状态
        List<CartGoodsBean> newList = new ArrayList<>();
        List<CartGoodsBean> cartGoodsBeanList = CartProvider.getInstance().getAllData();
        for (int i = 0; i < cartGoodsBeanList.size(); i++) {

            boolean isChildSelected = cartGoodsBeanList.get(i).isChildSelected;

            if (!isChildSelected) {
                newList.add(cartGoodsBeanList.get(i));
            }
        }
        boolean is_login = SharedPfUtil.getSharedContent(mContext, "is_login");
        if (is_login) {
            //转跳支付界面
            final Intent intent = new Intent(mContext, AffirmActivity.class);
            String toJson = new Gson().toJson(newList);

            intent.putExtra("json", toJson);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, LoginPhoneActivity.class);
            startActivity(intent);
        }
        //否  转跳登录界面

    }

    private void checkData() {
        if (shopCartAdapter != null && shopCartAdapter.getItemCount() > 0) {
            cart_title_edit_tv.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.VISIBLE);
            ll_delete.setVisibility(View.VISIBLE);
        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            cart_title_edit_tv.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }
    }

    private void hideDelete() {
        cart_title_edit_tv.setText("编辑");
        cart_title_edit_tv.setTag(ACTION_EDIT);
        ll_delete.setVisibility(View.GONE);
        ll_check_all.setVisibility(View.VISIBLE);
        shopCartAdapter.checkAll_none(true);
        shopCartAdapter.showChildEditNum((int) cart_title_edit_tv.getTag());
        shopCartAdapter.specialUpdate();
        shopCartAdapter.showTotalPrice();
        shopCartAdapter.checkAll();
    }


    private void showDelete() {
        cart_title_edit_tv.setText("完成");
        cart_title_edit_tv.setTag(ACTION_COMPLETE);
        ll_delete.setVisibility(View.VISIBLE);
        ll_check_all.setVisibility(View.GONE);
        shopCartAdapter.checkAll_none(false);
        shopCartAdapter.showChildEditNum((int) cart_title_edit_tv.getTag());
        shopCartAdapter.specialUpdate();
        shopCartAdapter.checkAll();
    }

    private void showData() {
        List<CartGoodsBean> cartGoodsBeanList = CartProvider.getInstance().getAllData();
        CartProvider cartProvider = CartProvider.getInstance();
        if (cartGoodsBeanList != null && cartGoodsBeanList.size() > 0) {
            //有数据
            cart_title_edit_tv.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            shopCartAdapter = new ShopCartAdapter(mContext, cartGoodsBeanList, checkbox_all, tv_shopcart_total, cb_all, cartProvider);
            cart_recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            ll_check_all.setVisibility(View.VISIBLE);
            cart_recyclerview.setAdapter(shopCartAdapter);
        } else {
            //没有数据
            cart_title_name_tv.setText("购物车");
            cart_title_edit_tv.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            ll_check_all.setVisibility(View.GONE);
        }
    }

}
