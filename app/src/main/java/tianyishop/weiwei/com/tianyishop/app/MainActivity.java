package tianyishop.weiwei.com.tianyishop.app;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.fragment.classify.fragment.ClassifyFragment;
import tianyishop.weiwei.com.tianyishop.fragment.community.fragment.CommunityFragment;
import tianyishop.weiwei.com.tianyishop.fragment.home.fragment.HomeFragment;
import tianyishop.weiwei.com.tianyishop.fragment.shopping.fragment.ShoppingFragment;
import tianyishop.weiwei.com.tianyishop.fragment.user.fragment.UserFragment;
import tianyishop.weiwei.com.tianyishop.view.NoScrollViewPager;

public class
MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NoScrollViewPager main_viewpager;
    private RadioGroup main_rb;
    private RadioButton buttom_home_rb_id;
    private RadioButton buttom_classify_rb_id;
    private RadioButton buttom_community_rb_id;
    private RadioButton buttom_shopping_rb_id;
    private RadioButton buttom_user_rb_id;
    private FragmentManager manager;
    private HomeFragment homeFragment;
    private ClassifyFragment classifyFragment;
    private CommunityFragment communityFragment;
    private ShoppingFragment shoppingFragment;
    private UserFragment userFragment;
    private BaseFragment lastFragment;
    private int position = 0;
    List<BaseFragment> fragList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //该效果是只有5.0（API 21）及以上系统才支持
        if (Build.VERSION.SDK_INT >= 21) {
            //获得Activity中的 decorView
        /*    View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //设置属性
            decorView.setSystemUiVisibility(option);*/
            //将状态栏变为透明
            getWindow().setStatusBarColor(Color.parseColor("#FCFCFC"));
        }
        findViews();

        initEvents();
    }


    private void findViews() {
        //    main_viewpager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        main_rb = (RadioGroup) findViewById(R.id.main_rb);
        buttom_home_rb_id = (RadioButton) findViewById(R.id.buttom_home_rb_id);
        buttom_classify_rb_id = (RadioButton) findViewById(R.id.buttom_classify_rb_id);
        buttom_community_rb_id = (RadioButton) findViewById(R.id.buttom_community_rb_id);
        buttom_shopping_rb_id = (RadioButton) findViewById(R.id.buttom_shopping_rb_id);
        buttom_user_rb_id = (RadioButton) findViewById(R.id.buttom_user_rb_id);
        //  main_rb.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        buttom_home_rb_id.setOnClickListener(this);
        buttom_classify_rb_id.setOnClickListener(this);
        buttom_community_rb_id.setOnClickListener(this);
        buttom_shopping_rb_id.setOnClickListener(this);
        buttom_user_rb_id.setOnClickListener(this);
    }

    private void initEvents() {
        homeFragment = new HomeFragment();
        classifyFragment = new ClassifyFragment();
        communityFragment = new CommunityFragment();
        shoppingFragment = new ShoppingFragment();
        userFragment = new UserFragment();
        fragList.add(homeFragment);
        fragList.add(classifyFragment);
        fragList.add(communityFragment);
        fragList.add(shoppingFragment);
        fragList.add(userFragment);
        manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.main_fl, homeFragment);
        fragmentTransaction.add(R.id.main_fl, classifyFragment);
        fragmentTransaction.add(R.id.main_fl, communityFragment);
        fragmentTransaction.add(R.id.main_fl, shoppingFragment);
        fragmentTransaction.add(R.id.main_fl, userFragment);
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.hide(classifyFragment);
        fragmentTransaction.hide(communityFragment);
        fragmentTransaction.hide(shoppingFragment);
        fragmentTransaction.hide(userFragment);
        fragmentTransaction.commit();
        //  main_viewpager.setAdapter(new MyMainPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttom_home_rb_id:
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.show(homeFragment);
                fragmentTransaction.hide(classifyFragment);
                fragmentTransaction.hide(communityFragment);
                fragmentTransaction.hide(shoppingFragment);
                fragmentTransaction.hide(userFragment);
                fragmentTransaction.commit();
                // main_viewpager.setCurrentItem(0);
                break;
            case R.id.buttom_classify_rb_id:
                FragmentTransaction fragmentTransaction2 = manager.beginTransaction();
                fragmentTransaction2.show(classifyFragment);
                fragmentTransaction2.hide(homeFragment);
                fragmentTransaction2.hide(communityFragment);
                fragmentTransaction2.hide(shoppingFragment);
                fragmentTransaction2.hide(userFragment);
                fragmentTransaction2.commit();
                // main_viewpager.setCurrentItem(1);
                break;
            case R.id.buttom_community_rb_id:
                FragmentTransaction fragmentTransaction3 = manager.beginTransaction();
                fragmentTransaction3.show(communityFragment);
                fragmentTransaction3.hide(classifyFragment);
                fragmentTransaction3.hide(homeFragment);
                fragmentTransaction3.hide(shoppingFragment);
                fragmentTransaction3.hide(userFragment);
                fragmentTransaction3.commit();
                // main_viewpager.setCurrentItem(2);
                break;
            case R.id.buttom_shopping_rb_id:
                FragmentTransaction fragmentTransaction4 = manager.beginTransaction();
                fragmentTransaction4.show(shoppingFragment);
                fragmentTransaction4.hide(classifyFragment);
                fragmentTransaction4.hide(communityFragment);
                fragmentTransaction4.hide(homeFragment);
                fragmentTransaction4.hide(userFragment);
                fragmentTransaction4.commit();
                // main_viewpager.setCurrentItem(3);
                break;
            case R.id.buttom_user_rb_id:
                FragmentTransaction fragmentTransaction5 = manager.beginTransaction();
                fragmentTransaction5.show(userFragment);
                fragmentTransaction5.hide(classifyFragment);
                fragmentTransaction5.hide(communityFragment);
                fragmentTransaction5.hide(shoppingFragment);
                fragmentTransaction5.hide(homeFragment);
                fragmentTransaction5.commit();
                // main_viewpager.setCurrentItem(4);
                break;
        }
    }


  /*  class MyMainPagerAdapter extends FragmentPagerAdapter {


        public MyMainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList.size();
        }
    }*/

}
