package tianyishop.weiwei.com.tianyishop.fragment.shopping.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.CartProvider;
import tianyishop.weiwei.com.tianyishop.bean.CartGoodsBean;
import tianyishop.weiwei.com.tianyishop.fragment.shopping.pay.PayResult;
import tianyishop.weiwei.com.tianyishop.fragment.shopping.pay.PayUtils;

public class AffirmActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView goods_info_back_iv;
    private TextView goods_info_tv;
    private LinearLayout queren_dingdan_ll;
    private TextView queren_num_tv;
    int num = 0;
    private TextView queren_money_tv;
    float price = 0;
    private TextView queren_shopcart_total;
    private Button queren_btn_check_out;
    private LinearLayout pay_alipay_select_ll;
    private LinearLayout pay_wxpay_select_ll;
    private CheckBox cb_ali_querendingdan_good;
    private CheckBox cb_wx_querendingdan_good;
    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AffirmActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AffirmActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(AffirmActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;

                default:
                    break;
            }
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_affirm);

        initView();

        initEvents();
    }


    private void initView() {
        goods_info_back_iv = (ImageView) findViewById(R.id.goods_info_back_iv);

        goods_info_tv = (TextView) findViewById(R.id.goods_info_tv);
        goods_info_tv.setText("确认订单");


        ImageView goods_shopping_cart_ib = (ImageView) findViewById(R.id.goods_shopping_cart_ib);
        goods_shopping_cart_ib.setVisibility(View.INVISIBLE);
        ImageView goods_info_share_iv = (ImageView) findViewById(R.id.goods_info_share_iv);
        goods_info_share_iv.setVisibility(View.INVISIBLE);


        LinearLayout address_ll = (LinearLayout) findViewById(R.id.address_ll);
        queren_dingdan_ll = (LinearLayout) findViewById(R.id.queren_dingdan_ll);
        queren_num_tv = (TextView) findViewById(R.id.queren_num_tv);
        queren_money_tv = (TextView) findViewById(R.id.queren_money_tv);
        LinearLayout baoyou_ll = (LinearLayout) findViewById(R.id.baoyou_ll);
        EditText liuyan_et = (EditText) findViewById(R.id.liuyan_et);
        pay_alipay_select_ll = (LinearLayout) findViewById(R.id.pay_alipay_select_ll);
        pay_wxpay_select_ll = (LinearLayout) findViewById(R.id.pay_wxpay_select_ll);
        queren_shopcart_total = (TextView) findViewById(R.id.queren_shopcart_total);
        queren_btn_check_out = (Button) findViewById(R.id.queren_btn_check_out);
        cb_ali_querendingdan_good = (CheckBox) findViewById(R.id.cb_ali_querendingdan_good);
        cb_wx_querendingdan_good = (CheckBox) findViewById(R.id.cb_wx_querendingdan_good);


        goods_info_back_iv.setOnClickListener(this);
        queren_btn_check_out.setOnClickListener(this);
        pay_alipay_select_ll.setOnClickListener(this);
        pay_wxpay_select_ll.setOnClickListener(this);
        cb_ali_querendingdan_good.setOnClickListener(this);
        cb_wx_querendingdan_good.setOnClickListener(this);
    }

    private void initEvents() {

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        //  Log.e("=========================", json);
        List<CartGoodsBean> cartGoodsBeanList = new Gson().fromJson(json, new TypeToken<List<CartGoodsBean>>() {
        }.getType());
        for (int i = 0; i < cartGoodsBeanList.size(); i++) {
            View view = LayoutInflater.from(AffirmActivity.this).inflate(R.layout.queren_good_item, null);
            LinearLayout queren_cart_ll = (LinearLayout) view.findViewById(R.id.queren_cart_ll);
            ImageView queren_child_good_img = (ImageView) view.findViewById(R.id.queren_child_good_img);
            TextView queren_child_good_title_name_tv = (TextView) view.findViewById(R.id.queren_child_good_title_name_tv);
            TextView queren_child_good_shop_price_tv = (TextView) view.findViewById(R.id.queren_child_good_shop_price_tv);
            ImageView queren_child_jianhao_img = (ImageView) view.findViewById(R.id.queren_child_jianhao_img);
            TextView queren_child_pay_num = (TextView) view.findViewById(R.id.queren_child_pay_num);
            ImageView queren_child_jiahao_img = (ImageView) view.findViewById(R.id.queren_child_jiahao_img);

            num = num + cartGoodsBeanList.get(i).number;
            price += cartGoodsBeanList.get(i).shop_price * cartGoodsBeanList.get(i).number;
            Glide.with(AffirmActivity.this).load(cartGoodsBeanList.get(i).goods_img).into(queren_child_good_img);
            queren_child_good_title_name_tv.setText(cartGoodsBeanList.get(i).goods_name);
            queren_child_good_shop_price_tv.setText("￥" + cartGoodsBeanList.get(i).shop_price + "");
            queren_child_pay_num.setText(cartGoodsBeanList.get(i).number + "");

            queren_dingdan_ll.addView(view);
        }

        queren_num_tv.setText("共计" + num + "件商品");
        queren_money_tv.setText("总计：￥" + price);
        queren_shopcart_total.setText("实付：￥" + price);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_info_back_iv:
                finish();
                break;
            case R.id.queren_btn_check_out:
                //转跳付款界面
                if (cb_ali_querendingdan_good.isChecked()) {
                    List<CartGoodsBean> allData = CartProvider.getInstance().getAllData();
                    String goods_name = allData.get(0).goods_name;
                    alipay(goods_name);

                } else if (cb_wx_querendingdan_good.isChecked()) {


                }
                break;
            case R.id.pay_alipay_select_ll:
                cb_ali_querendingdan_good.setChecked(true);
                cb_wx_querendingdan_good.setChecked(false);
                break;
            case R.id.pay_wxpay_select_ll:
                cb_ali_querendingdan_good.setChecked(false);
                cb_wx_querendingdan_good.setChecked(true);

                break;
            case R.id.cb_ali_querendingdan_good:
                cb_wx_querendingdan_good.setChecked(false);
                break;
            case R.id.cb_wx_querendingdan_good:
                cb_ali_querendingdan_good.setChecked(false);
                break;
        }
    }

    private void alipay(String goods_name) {
        String orderinfo = PayUtils.getOrderInfo(goods_name, "购买一部手机", "0.01");
        String signinfo = PayUtils.getSignInfo(orderinfo);
        final String totalInfo = PayUtils.getTotalInfo(orderinfo, signinfo);

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(AffirmActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(totalInfo, true);
                Log.i("TAG", "走了pay支付方法.............");

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }
}
