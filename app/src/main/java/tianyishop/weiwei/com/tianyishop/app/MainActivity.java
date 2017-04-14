package tianyishop.weiwei.com.tianyishop.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.classify.fragment.ClassifyFragment;
import tianyishop.weiwei.com.tianyishop.community.fragment.CommunityFragment;
import tianyishop.weiwei.com.tianyishop.home.fragment.HomeFragment;
import tianyishop.weiwei.com.tianyishop.shopping.fragment.ShoppingFragment;
import tianyishop.weiwei.com.tianyishop.user.fragment.UserFragment;
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
        findViews();

        initEvents();
    }


    private void findViews() {
        main_viewpager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
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

        main_viewpager.setAdapter(new MyMainPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttom_home_rb_id:
                main_viewpager.setCurrentItem(0);
                break;
            case R.id.buttom_classify_rb_id:
                main_viewpager.setCurrentItem(1);
                break;
            case R.id.buttom_community_rb_id:
                main_viewpager.setCurrentItem(2);
                break;
            case R.id.buttom_shopping_rb_id:
                main_viewpager.setCurrentItem(3);
                break;
            case R.id.buttom_user_rb_id:
                main_viewpager.setCurrentItem(4);
                break;
        }
    }


    class MyMainPagerAdapter extends FragmentPagerAdapter {


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
    }

}
