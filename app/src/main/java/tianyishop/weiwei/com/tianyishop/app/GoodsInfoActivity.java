package tianyishop.weiwei.com.tianyishop.app;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.adapter.GoodInfoAdapter;
import tianyishop.weiwei.com.tianyishop.base.CartProvider;
import tianyishop.weiwei.com.tianyishop.bean.CartGoodsBean;
import tianyishop.weiwei.com.tianyishop.bean.GoodsInfoBean;
import tianyishop.weiwei.com.tianyishop.fragment.home.bean.HomeBean;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView goods_info_recyclerview;
    private GoodsInfoBean.DataBean datas;
    private List<GoodsInfoBean.DataBean.GoodsBean.GalleryBean> gallery;
    private ImageView goods_info_back_iv;
    private TextView gift_recevice_tv;
    private TextView goods_send_tv;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info);

        initView();

        initData();

    }


    private void initView() {

        goods_info_recyclerview = (RecyclerView) findViewById(R.id.goods_info_recyclerview);
        goods_info_back_iv = (ImageView) findViewById(R.id.goods_info_back_iv);
        gift_recevice_tv = (TextView) findViewById(R.id.gift_recevice_tv);
        goods_send_tv = (TextView) findViewById(R.id.goods_send_tv);
        goods_info_back_iv.setOnClickListener(this);
        gift_recevice_tv.setOnClickListener(this);
        goods_send_tv.setOnClickListener(this);
    }


    private void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String s = AppUrl.url_goodsinfo + id;
        //ok       Log.e("-------------",s);
        getServerData(id);
    }

    private void getServerData(String id) {
        OkHttpUtils
                .get()
                .url(AppUrl.url_goodsinfo + id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                    }


                });

    }

    private void processData(String response) {
        GoodsInfoBean goodsInfoBean = JSON.parseObject(response, GoodsInfoBean.class);
        datas = goodsInfoBean.data;
        initEvents();
    }


    private void initEvents() {
        GoodInfoAdapter goodInfoAdapter = new GoodInfoAdapter(GoodsInfoActivity.this, datas);
        goods_info_recyclerview.setAdapter(goodInfoAdapter);
        goods_info_recyclerview.setLayoutManager(new GridLayoutManager(GoodsInfoActivity.this, 1));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_info_back_iv:
                finish();
                break;
            case R.id.gift_recevice_tv:
                showDialog();
                num = 1;
                break;
            case R.id.goods_send_tv:

                break;
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_bottom, null);
        //初始化控件
        ImageView dialog_close_img = (ImageView) inflate.findViewById(R.id.dialog_close_img);
        ImageView dialog_goods_img = (ImageView) inflate.findViewById(R.id.dialog_goods_img);
        TextView dialog_goods_shop_price = (TextView) inflate.findViewById(R.id.dialog_goods_shop_price);
        TextView dialog_goods_stock_number = (TextView) inflate.findViewById(R.id.dialog_goods_stock_number);
        TextView dialog_goods_restrict_tv = (TextView) inflate.findViewById(R.id.dialog_goods_restrict_tv);
        final ImageView jianhao_img = (ImageView) inflate.findViewById(R.id.jianhao_img);
        final TextView pay_num = (TextView) inflate.findViewById(R.id.pay_num);
        final ImageView jiahao_img = (ImageView) inflate.findViewById(R.id.jiahao_img);
        Button add_button = (Button) inflate.findViewById(R.id.add_button);
        String goods_img = datas.goods.goods_img;
        Glide.with(GoodsInfoActivity.this).load(goods_img).into(dialog_goods_img);
        dialog_goods_shop_price.setText("￥" + datas.goods.shop_price);
        dialog_goods_stock_number.setText("库存" + datas.goods.sales_volume + "件");
        dialog_goods_restrict_tv.setText("限购" + datas.goods.restrict_purchase_num + "件");

        jianhao_img.setEnabled(false);
        pay_num.setText("1");

        jiahao_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jianhao_img.setEnabled(true);
                if (num > datas.goods.restrict_purchase_num - 1) {
                    jiahao_img.setEnabled(false);
                } else {
                    num = num + 1;
                    pay_num.setText(String.valueOf(num));
                    if (num == 5) {
                        jiahao_img.setEnabled(false);
                    }
                }
            }
        });

        jianhao_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (num == 1) {
                    // jianhao_img.setEnabled(false);
                } else {
                    num = num - 1;
                    pay_num.setText(String.valueOf(num));
                    if (num < 5) {
                        jiahao_img.setEnabled(true);
                    }
                    if (num < 2) {
                        jianhao_img.setEnabled(false);
                    }
                }
            }
        });
        dialog_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                num = 1;
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第一种方法模仿的是淘宝加入购物车逻辑
                // addCartFirstMethod();
                //第二种方法模仿的是京东加入购物车逻辑
                addCartSecondMethod();
                Toast.makeText(GoodsInfoActivity.this, "恭喜，添加购物车成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框


               /* View popView = LayoutInflater.from(GoodsInfoActivity.this).inflate(
                        R.layout.pop_bottom, null);
                PopupWindow popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置点击窗口外边窗口消失
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                // 设置Pop响应外部区域焦点
                popupWindow.setOutsideTouchable(true);
                // 响应返回键必须的语句
                popupWindow.setBackgroundDrawable(new BitmapDrawable());*/
    }

    private void addCartSecondMethod() {

        CartGoodsBean cartGoodsBean = new CartGoodsBean();
        cartGoodsBean.goods_name = datas.goods.goods_name;
        cartGoodsBean.goods_img = datas.goods.goods_img;
        cartGoodsBean.shop_price = (float) datas.goods.shop_price;
        cartGoodsBean.is_coupon_allowed = datas.goods.is_coupon_allowed;
        cartGoodsBean.number = num;
        cartGoodsBean.id = datas.goods.id;
        //限购数量
        cartGoodsBean.restrict = datas.goods.restrict_purchase_num;
        CartProvider.getInstance().addData(cartGoodsBean);


    }

    private void addCartFirstMethod() {
        //判断是否登录
        //程序默认是未登录状态
        SharedPreferences preferences = getSharedPreferences("login_config", MODE_PRIVATE);
        boolean is_login = preferences.getBoolean("is_login", false);
        if (is_login) {
            Toast.makeText(GoodsInfoActivity.this, "恭喜，添加购物车成功", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(GoodsInfoActivity.this, LoginPhoneActivity.class);
            startActivity(intent);
        }
        //否  转跳登录界面

        //添加成功
    }
}
