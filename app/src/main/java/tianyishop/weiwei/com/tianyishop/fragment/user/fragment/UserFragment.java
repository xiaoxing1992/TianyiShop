package tianyishop.weiwei.com.tianyishop.fragment.user.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.app.LoginPhoneActivity;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.util.SharedPfUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/12.
 */

public class UserFragment extends BaseFragment {

    private Button user_login_button;
    private ImageView user_login_img;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.userfragment, null);
        user_login_button = (Button) view.findViewById(R.id.user_login_button);
        user_login_img = (ImageView) view.findViewById(R.id.user_login_img);


        boolean is_login = SharedPfUtil.getSharedContent(mContext, "is_login");
        if (is_login) {
            user_login_button.setVisibility(View.INVISIBLE);
            user_login_img.setImageResource(R.drawable.user_icon_set);
        } else {
            user_login_button.setVisibility(View.VISIBLE);
            user_login_img.setImageResource(R.drawable.user_icon_no_sets);
        }
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        user_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginPhoneActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean is_login = SharedPfUtil.getSharedContent(mContext, "is_login");
        if (is_login) {
            user_login_button.setVisibility(View.INVISIBLE);
            user_login_img.setImageResource(R.drawable.user_icon_set);
        } else {
            user_login_button.setVisibility(View.VISIBLE);
            user_login_img.setImageResource(R.drawable.user_icon_no_sets);
        }
    }
}
