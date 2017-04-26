package tianyishop.weiwei.com.tianyishop.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import tianyishop.weiwei.com.tianyishop.R;

public class GoodsInfoInAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info_in);
        WebView goods_info_web_view = (WebView) findViewById(R.id.goods_info_web_view);
        ImageView goods_info_act_ic = (ImageView) findViewById(R.id.goods_info_act_ic);
        TextView goods_info_title_names = (TextView) findViewById(R.id.goods_info_title_names);
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);
        String title = intent.getStringExtra("title");

        String html_url = intent.getStringExtra("html_url");
        if (index == 0) {
            goods_info_title_names.setText(title);
            goods_info_web_view.loadUrl(html_url);
        } else if (index == 1) {
            goods_info_title_names.setText(title);
            goods_info_web_view.loadUrl(html_url);
        } else if (index == 2) {
            goods_info_title_names.setText(title);
            goods_info_web_view.loadUrl(html_url);
        }
        goods_info_act_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
