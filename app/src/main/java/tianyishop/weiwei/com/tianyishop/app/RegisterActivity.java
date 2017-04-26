package tianyishop.weiwei.com.tianyishop.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register_button;
    private EditText regist_pwd_edittext;
    private EditText regist_account_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        register_button = (Button) findViewById(R.id.register_button);
        CheckBox yuedu_cb = (CheckBox) findViewById(R.id.yuedu_cb);
        TextView tiaokuan_tv = (TextView) findViewById(R.id.tiaokuan_tv);
        ImageView login_register_back_iv = (ImageView) findViewById(R.id.login_register_back_iv);
        TextView login_register_title_name = (TextView) findViewById(R.id.login_register_title_name);
        TextView register_tv = (TextView) findViewById(R.id.register_tv);
        regist_pwd_edittext = (EditText) findViewById(R.id.regist_pwd_edittext);
        regist_account_edittext = (EditText) findViewById(R.id.regist_account_edittext);
        login_register_title_name.setText("注册");
        register_tv.setVisibility(View.INVISIBLE);
        register_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                String account = regist_account_edittext.getText().toString().trim();
                String pwd = regist_pwd_edittext.getText().toString().trim();
                if (!TextUtils.isEmpty(account)) {
                    if (!TextUtils.isEmpty(pwd)) {
                        registMethod(account, pwd);
                    } else {
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "账户不能为空", Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }

    private void registMethod(String account, String pwd) {
        String url = AppUrl.url_ser + "/bullking1/register?name=" + account + "&pwd=" + pwd;
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (str != null) {
                            if (str.contains("register succeed")) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            } else if (str.contains("register nothing")) {
                                Toast.makeText(RegisterActivity.this, "注册失败，账户已存在", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        });
    }
}
