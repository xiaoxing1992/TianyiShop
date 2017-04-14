package tianyishop.weiwei.com.tianyishop.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.view.CustomVideoView;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/11.
 */

public class GuideFragment extends Fragment {

    private CustomVideoView customVideoView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customVideoView = new CustomVideoView(getContext());
        int index = getArguments().getInt("index");
        Uri uri = null;

        switch (index) {
            case 1:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_1);
                break;
            case 2:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_2);
                break;
            case 3:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_3);
                break;
        }
        customVideoView.playVideo(uri);
        return customVideoView;
    }

    //释放资源
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
        }
    }
}
