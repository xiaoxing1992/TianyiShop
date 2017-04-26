package tianyishop.weiwei.com.tianyishop.app;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.CartProvider;
import tianyishop.weiwei.com.tianyishop.bean.CartGoodsBean;
import tianyishop.weiwei.com.tianyishop.fragment.user.bean.UserIdBean;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;
import tianyishop.weiwei.com.tianyishop.util.SharedPfUtil;

public class LoginPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView account_login_tv;
    private TextView phone_login_tv;
    private TextView other_login_tv;
    private TextView register_tv;
    private Button login_button;
    private EditText account_edittext;
    private EditText pwd_edittext;
    private UserIdBean userIdBean;
    private FormBody.Builder params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_register);

        initView();
    }

    private void initView() {
        account_login_tv = (TextView) findViewById(R.id.account_login_tv);
        phone_login_tv = (TextView) findViewById(R.id.phone_login_tv);
        other_login_tv = (TextView) findViewById(R.id.other_login_tv);
        register_tv = (TextView) findViewById(R.id.register_tv);
        login_button = (Button) findViewById(R.id.login_button);
        account_edittext = (EditText) findViewById(R.id.account_edittext);
        pwd_edittext = (EditText) findViewById(R.id.pwd_edittext);


        //设置下划线
        other_login_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        account_login_tv.setSelected(true);

        account_login_tv.setOnClickListener(this);
        phone_login_tv.setOnClickListener(this);
        register_tv.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_login_tv:
                account_login_tv.setSelected(true);
                phone_login_tv.setSelected(false);
                break;
            case R.id.phone_login_tv:
                account_login_tv.setSelected(false);
                phone_login_tv.setSelected(true);
                break;
            case R.id.register_tv:
                Intent intent = new Intent(LoginPhoneActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_translate, R.anim.exit_translate);
                break;
            case R.id.login_button:
                String account = account_edittext.getText().toString().trim();
                String pwd = pwd_edittext.getText().toString().trim();
                if (!TextUtils.isEmpty(account)) {
                    if (!TextUtils.isEmpty(pwd)) {
                        loginMethod(account, pwd);
                    } else {
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "账户不能为空", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    private void loginMethod(String account, String pwd) {
        String url = AppUrl.url_ser + "/bullking1/login";

        FormBody.Builder params = new FormBody.Builder();
        params.add("name", account);
        params.add("pwd", pwd);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .post(params.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                userIdBean = new Gson().fromJson(str, UserIdBean.class);
                SharedPfUtil.putSharedId(LoginPhoneActivity.this, "user_id", userIdBean.id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (str != null) {
                            if (str.contains("login succeed")) {
                                Toast.makeText(LoginPhoneActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                SharedPfUtil.putSharedContent(LoginPhoneActivity.this, "is_login", true);
                                //如果登录成功 将数据同步到服务器
                                syncServerMethod();
                                finish();
                                //并 删除内存中和本地缓存
                               /* if (CartProvider.getInstance().getAllData() != null && CartProvider.getInstance().getAllData().size() > 0) {
                                    for (Iterator iterator = CartProvider.getInstance().getAllData().iterator(); iterator.hasNext(); ) {

                                        CartGoodsBean cart = (CartGoodsBean) iterator.next();
                                        //这行代码放在前面
                                        int position = CartProvider.getInstance().getAllData().indexOf(cart);
                                        //1.删除本地缓存的
                                        CartProvider.getInstance().deleteData(cart);
                                        //2.删除当前内存的
                                        iterator.remove();
                                    }
                                }*/
                                // SharedPfUtil.deleteShared(LoginPhoneActivity.this);

                            } else if (str.contains("login nothing")) {
                                Toast.makeText(LoginPhoneActivity.this, "登录失败，账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });
    }

    private void syncServerMethod() {
        String url = AppUrl.url_ser + "/bullking1/cart";


        List<CartGoodsBean> allData = CartProvider.getInstance().getAllData();
        for (int i = 0; i < allData.size(); i++) {
            params = new FormBody.Builder();
            params.add("productID", allData.get(i).id);
            params.add("count", allData.get(i).number + "");
            params.add("pic", allData.get(i).goods_img);
            params.add("price", allData.get(i).shop_price + "");
            params.add("name", allData.get(i).goods_name);
            int user_id = SharedPfUtil.getSharedId(LoginPhoneActivity.this, "user_id");
            params.add("userID", user_id + "");
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .post(params.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

            }
        });
    }
}
