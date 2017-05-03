package tianyishop.weiwei.com.tianyishop.fragment.home.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.alibaba.fastjson.JSON;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.fragment.home.activity.QrActivity;
import tianyishop.weiwei.com.tianyishop.fragment.home.activity.MipcaActivityCapture;
import tianyishop.weiwei.com.tianyishop.fragment.home.activity.WebViewActivity;
import tianyishop.weiwei.com.tianyishop.fragment.home.adapter.HomeFragmentAdapter;
import tianyishop.weiwei.com.tianyishop.fragment.home.bean.HomeBean;
import tianyishop.weiwei.com.tianyishop.util.AppUrl;

import static android.app.Activity.RESULT_OK;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/12.
 */

public class HomeFragment extends BaseFragment {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private SwipeRefreshLayout home_frag_swip;
    private HomeBean.DataBean dataList;
    private RecyclerView home_frag_rcy;
    private ImageView goto_img;
    private ImageButton qr_code_img;
    private ImageButton tuijian_qr_code_img;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.homefragment, null);
        home_frag_swip = (SwipeRefreshLayout) view.findViewById(R.id.home_frag_swip);
        home_frag_rcy = (RecyclerView) view.findViewById(R.id.home_frag_rcy);
        goto_img = (ImageView) view.findViewById(R.id.goto_img);
        qr_code_img = (ImageButton) view.findViewById(R.id.qr_code_img);
        tuijian_qr_code_img = (ImageButton) view.findViewById(R.id.tuijian_qr_code_img);

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

        //置顶的监听
        goto_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_frag_rcy.scrollToPosition(0);
            }
        });

        qr_code_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //6.0以下版本直接运行 就可以扫描二维码
                /*Intent intent = new Intent(MainActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);*/
                //6.0以上版本不能直接运行 否则是空白页面 无法扫描
                /**第一种方式 手动打开权限
                 * 第二种动态添加权限
                 */
                if (AndPermission.hasPermission(mContext, Manifest.permission.CAMERA)) {
                    //有权限 做操作
                    Intent intent = new Intent(mContext, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                } else {
                    //没有权限 申请权限
                    AndPermission.with((Activity) mContext).requestCode(100).permission(Manifest.permission.CAMERA).send();
                }
                Intent intent = new Intent(mContext, MipcaActivityCapture.class);
                startActivity(intent);
            }
        });

        tuijian_qr_code_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QrActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
// 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
                // TODO 相应代码。
            } else if (requestCode == 101) {
                // TODO 相应代码。
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission((Activity) mContext, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog((Activity) mContext, requestCode).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    };


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
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);


        //设置滑动到哪个位置了的监听
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 4) {
                    goto_img.setVisibility(View.GONE);
                } else {
                    goto_img.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });


        home_frag_rcy.setLayoutManager(manager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNIN_GREQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                Log.i("xxx", result.toString());
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", result);
                startActivity(intent);

            }
        }
    }
}
