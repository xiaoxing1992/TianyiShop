package tianyishop.weiwei.com.tianyishop.fragment.community.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.R;
import tianyishop.weiwei.com.tianyishop.base.BaseFragment;
import tianyishop.weiwei.com.tianyishop.fragment.community.activity.OtherMapActivity;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/12.
 */

public class CommunityFragment extends BaseFragment {

    private MapView mymap;
    BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
    BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
    BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.drawable.icon_markc);

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.communityfragment, null);
        mymap = (MapView) view.findViewById(R.id.mymap);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();

        BaiduMap map = mymap.getMap();

        //普通地图
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //卫星地图
        // map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //开启交通图
        map.setTrafficEnabled(true);
        //热力图
        //map.setBaiduHeatMapEnabled(true);
        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .draggable(true);//设置手势拖拽
        //将marker添加到地图上
        //   Marker marker = (Marker) (map.addOverlay(option));
        MarkerOptions icon = new MarkerOptions().position(point).icon(bitmap).zIndex(9).draggable(true);
        icon.animateType(MarkerOptions.MarkerAnimateType.drop);
        map.addOverlay(icon);

        map.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng latLng = marker.getPosition();
                //经度
                double longitude = latLng.longitude;
                //纬度
                double latitude = latLng.latitude;
            }

            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });


        //设置动画
        List<BitmapDescriptor> bitList = new ArrayList<BitmapDescriptor>();
        bitList.add(bdA);
        bitList.add(bdB);
        bitList.add(bdC);

        LatLng latLngA = new LatLng(39.853175, 116.400244);
        OverlayOptions ooA = new MarkerOptions().position(latLngA).icons((ArrayList<BitmapDescriptor>) bitList)
                .zIndex(0).period(10).draggable(true);
        final Marker mMarkerA = (Marker) (map.addOverlay(ooA));

        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mMarkerA == marker) {
                    Intent intent = new Intent(mContext, OtherMapActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
}
