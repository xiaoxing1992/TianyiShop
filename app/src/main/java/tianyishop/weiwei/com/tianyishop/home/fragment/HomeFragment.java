package tianyishop.weiwei.com.tianyishop.home.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.home.adapter.HomeFragmentAdapter;
import tianyishop.weiwei.com.tianyishop.home.bean.HomeBean;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/12.
 */

public class HomeFragment extends BaseFragment {

    private SwipeRefreshLayout home_frag_swip;
    private HomeBean.DataBean dataList;
    private RecyclerView home_frag_rcy;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.homefragment, null);
        home_frag_swip = (SwipeRefreshLayout) view.findViewById(R.id.home_frag_swip);
        home_frag_rcy = (RecyclerView) view.findViewById(R.id.home_frag_rcy);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
      //  home_frag_rcy.setNestedScrollingEnabled(false);
        home_frag_swip.setColorSchemeResources(R.color.colorPrimary, R.color.bottomTextColorChecked, R.color.colorThree, R.color.colorSi);
        home_frag_swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home_frag_swip.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        getServerData();
    }

    //获取网络数据
    private void getServerData() {
        OkHttpUtils
                .get()
                .url(AppUrl.url_home)
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

    //解析数据格式
    private void processData(String response) {
        HomeBean homeBean = JSON.parseObject(response, HomeBean.class);
        dataList = homeBean.data;

        HomeFragmentAdapter homeFragmentAdapter = new HomeFragmentAdapter(mContext, dataList);
        home_frag_rcy.setAdapter(homeFragmentAdapter);
        home_frag_rcy.setLayoutManager(new GridLayoutManager(mContext, 1));
    }
}
