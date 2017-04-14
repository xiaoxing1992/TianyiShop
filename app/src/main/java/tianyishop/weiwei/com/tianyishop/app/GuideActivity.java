package tianyishop.weiwei.com.tianyishop.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.fragment.GuideFragment;
import tianyishop.weiwei.com.tianyishop.util.DpUtil;
import tianyishop.weiwei.com.tianyishop.util.SharedPfUtil;

/**
 * 用途：引导页
 * 作者：任正威
 * 时间：2017-04-11
 */
public class GuideActivity extends AppCompatActivity {

    private int marginLeft;
    private ViewPager guide_vp;
    private Button bt_start;
    private List<Fragment> fragments;
    private LinearLayout splash_ll;
    private ImageView splash_point;

    private void assignViews() {
        guide_vp = (ViewPager) findViewById(R.id.guide_vp);
        bt_start = (Button) findViewById(R.id.bt_start);
        splash_ll = (LinearLayout) findViewById(R.id.splash_ll);
        splash_point = (ImageView) findViewById(R.id.splash_point);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        boolean isFirst = SharedPfUtil.getSharedContent(this, "isFirst");
        if (isFirst) {
            startActivity(new Intent(GuideActivity.this, SplashActivity.class));
            finish();
        }
        //初始化控件
        assignViews();


        initData();

        initEvents();
        splash_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }


    /**
     * 初始化数据，添加三个Fragment
     */
    private void initData() {
        fragments = new ArrayList<>();

        Fragment fragment1 = new GuideFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);

        Fragment fragment2 = new GuideFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("index", 2);
        fragment2.setArguments(bundle2);
        fragments.add(fragment2);

        Fragment fragment3 = new GuideFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("index", 3);
        fragment3.setArguments(bundle3);
        fragments.add(fragment3);

        int dip = DpUtil.dip2px(this, 10);
        int dip2 = DpUtil.dip2px(this, 18);
        for (int i = 0; i < fragments.size(); i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.splashpoint_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip, dip);
            if (i != 0) {
                layoutParams.leftMargin = dip2;
            }
            view.setEnabled(false);
            splash_ll.addView(view, layoutParams);
        }
    }

    private void initEvents() {
        /**
         * 设置ViewPager的适配器和滑动监听
         */
        guide_vp.setOffscreenPageLimit(3);
        guide_vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        guide_vp.addOnPageChangeListener(new MyPageChangeListener());
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPfUtil.putSharedContent(GuideActivity.this, "isFirst", true);
                startActivity(new Intent(GuideActivity.this, SplashActivity.class));
                finish();
            }
        });
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            float leftMarginTwo = (position + positionOffset) * marginLeft;
            LinearLayout.LayoutParams parma = (LinearLayout.LayoutParams) splash_point.getLayoutParams();
            splash_point.setLayoutParams(parma);
            parma.leftMargin = (int) leftMarginTwo * 2;

        }

        @Override
        public void onPageSelected(int position) {


            //button的显示与隐藏
            if (position == fragments.size() - 1) {
                bt_start.setVisibility(ViewPager.VISIBLE);
            } else {
                bt_start.setVisibility(ViewPager.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {


        @Override
        public void onGlobalLayout() {
            splash_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            marginLeft = splash_ll.getChildAt(1).getLeft() - splash_ll.getChildAt(0).getLeft();
            //    int i = DpUtil.px2dip(GuideActivity.this, marginLeft);
            // Log.e("ssssssssssssss", i + "");
        }
    }
}
