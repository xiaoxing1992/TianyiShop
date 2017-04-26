package tianyishop.weiwei.com.tianyishop.fragment.classify.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.fragment.classify.adapter.ClassifyFragmentAdapter;
import tianyishop.weiwei.com.tianyishop.fragment.classify.bean.ClassifyBean;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/12.
 */

public class ClassifyFragment extends BaseFragment {

    private RecyclerView classify_frag_rcy;
    private SwipeRefreshLayout classify_frag_swip;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.classifyfragment, null);
        classify_frag_rcy = (RecyclerView) view.findViewById(R.id.classify_frag_rcy);
        classify_frag_swip = (SwipeRefreshLayout) view.findViewById(R.id.classify_frag_swip);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        classify_frag_swip.setColorSchemeResources(R.color.colorPrimary, R.color.bottomTextColorChecked, R.color.colorThree, R.color.colorSi);
        classify_frag_swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        classify_frag_swip.setRefreshing(false);
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
                .url(AppUrl.url_classify)
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
        ClassifyBean classifyBean = JSON.parseObject(response, ClassifyBean.class);
        ClassifyBean.DataBean data = classifyBean.data;

        ClassifyFragmentAdapter classifyFragmentAdapter = new ClassifyFragmentAdapter(mContext, data);
        classify_frag_rcy.setAdapter(classifyFragmentAdapter);
        classify_frag_rcy.setLayoutManager(new GridLayoutManager(mContext, 1));
    }
}
