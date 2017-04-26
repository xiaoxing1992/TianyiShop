package tianyishop.weiwei.com.tianyishop.fragment;

import android.view.LayoutInflater;
import android.view.View;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/21.
 */

public class PinglunFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_info_pinglun_fragment, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
