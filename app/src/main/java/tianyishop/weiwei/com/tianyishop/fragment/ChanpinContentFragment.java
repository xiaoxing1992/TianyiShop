package tianyishop.weiwei.com.tianyishop.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/21.
 */

public class ChanpinContentFragment extends BaseFragment {

    private final String goods_desc;
    // private LinearLayout goods_info_xiangqing_ll;

    public ChanpinContentFragment(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_info_xiangqing_layout, null);

        // goods_info_xiangqing_ll = (LinearLayout) view.findViewById(R.id.goods_info_xiangqing_ll);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();

        JSONArray jsonArray = JSON.parseArray(goods_desc);
        for (int i = 0; i < jsonArray.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObject.getString("height");
            String url = jsonObject.getString("url");
            jsonObject.getString("width");

            Glide.with(mContext).load(url).into(imageView);
            // goods_info_xiangqing_ll.addView(imageView);
        }

    }
}
