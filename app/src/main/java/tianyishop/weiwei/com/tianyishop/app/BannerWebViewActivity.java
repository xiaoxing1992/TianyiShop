package tianyishop.weiwei.com.tianyishop.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import tianyishop.weiwei.com.tianyishop.R;

public class BannerWebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView banner_info_back_ic;
    private TextView banner_info_title_names;
    private ImageView banner_info_share_img;
    private WebView banner_info_web_view;
    private String title;
    private String html_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_web_view);

        initView();
        initEvents();
        initData();
    }


    private void initView() {
        banner_info_back_ic = (ImageView) findViewById(R.id.banner_info_back_ic);
        banner_info_title_names = (TextView) findViewById(R.id.banner_info_title_names);
        banner_info_share_img = (ImageView) findViewById(R.id.banner_info_share_img);
        banner_info_web_view = (WebView) findViewById(R.id.banner_info_web_view);
        banner_info_back_ic.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        html_url = intent.getStringExtra("html_url");
    }

    private void initEvents() {
        banner_info_title_names.setText(title);

        banner_info_web_view.loadUrl(html_url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner_info_back_ic:
                finish();
                break;
        }
    }
}
