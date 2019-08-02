package com.example.yibo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.*;

import java.util.ArrayList;
import java.util.List;


public class OutdoorNavigationActivity extends AppCompatActivity {

    //定位相关
    private double LOC_LAT = 0.0;

    private double LOC_LON = 0.0;
    /**
     * 定位SDK的核心类
     */
    public LocationClient mLocationClient;      //
    private MainActivity.MyLocationListener mLocationListener;

    private MapView mapView;        //显示地图

    private BaiduMap baiduMap;      //地图的总控制器

    // 自定义定位图标
    private BitmapDescriptor mIconLocation;
    //方向传感器
    private MyOrientationListener mOrientationListener;//实例化方向传感器
    private float mCurrentX;

    private Button finish_button;

    private FloatingActionButton requestFButton;

    private Button startRoutrButton;

    private Button navigationButton;

    private boolean isRequest = false;//是否手动触发请求定位
    private boolean isFirstLocate = true;       //是否首次定位//防止多次调用animateMapStatus()方法，第一次定位时调用即可

    // 路线规划相关
    private RoutePlanSearch mSearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 判断Android>5.0才能使用，使内容延伸到状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            // 好的当前活动的DecorView,在改变UI显示
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            // 使其状态栏呈现透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //setHasOptionsMenu(true);//加上这句话，menu才会显示出来

        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());     //注册定位监听器
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_outdoor_navigation);
        mapView = (MapView) findViewById(R.id.outdoor_navigation_bmapView);
        baiduMap = mapView.getMap();        //获得BaiduMap实例
        //baiduMap.setMyLocationEnabled(true);        //开启光标显示功能
        //baiduMap.setMyLocationEnabled(false);        //关闭光标显示功能
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(OutdoorNavigationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(OutdoorNavigationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(OutdoorNavigationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(OutdoorNavigationActivity.this, permissions, 1);
        } else {
            requestLocation();
        }

        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        finish_button = (Button) findViewById(R.id.outdoor_navigation_finish_button);
        //根据点击事件结束导航
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OutdoorNavigationActivity.this, "导航结束", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //点击按钮手动请求定位
        requestFButton = (FloatingActionButton) findViewById(R.id.outdoor_navigation_real_time_position_request_fbutton);
        requestFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLocationClient.requestLocation();

                LatLng point = new LatLng(LOC_LAT, LOC_LON);
                MapStatusUpdate u = MapStatusUpdateFactory
                        .newLatLng(point);
        /*u = MapStatusUpdateFactory.newLatLngZoom(point,
                mBaiduMap.getMaxZoomLevel());*/
                baiduMap.animateMapStatus(u);
                baiduMap.setMapStatus(u);
                //将光标显示出来
                MyLocationData.Builder locationBuilder = new MyLocationData.
                        Builder();
                locationBuilder.latitude(LOC_LAT);       //传入当前纬度
                locationBuilder.longitude(LOC_LON);     //传入当前经度
                MyLocationData locationData = locationBuilder.build();          //生成实例
                baiduMap.setMyLocationData(locationData);
                Toast.makeText(OutdoorNavigationActivity.this, "定位中……", Toast.LENGTH_SHORT).show();
            }
        });

        //路线规划
        initPoutePlan();
        startRoutrButton = (Button) findViewById(R.id.outdoor_RoutrPlan_button);
        startRoutrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarRoute();
            }
        });
    }


    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            Toast.makeText(this, "nav to " + location.getAddrStr(), Toast.LENGTH_SHORT).show();
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());        //将当前位置的纬经度值保存至LatLng中
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);      //将LatLng对象传入
            baiduMap.animateMapStatus(update);      //将地图移动到当前纬经度表示的位置上
            update = MapStatusUpdateFactory.zoomTo(16f);        //设置缩放级别
            baiduMap.animateMapStatus(update);      //完成设置缩放级别
            isFirstLocate = false;      //防止多次调用animateMapStatus()方法，第一次定位时调用即可
        }
        //将光标显示出来
        /*MyLocationData.Builder locationBuilder = new MyLocationData.
                Builder();
        locationBuilder.latitude(location.getLatitude());       //传入当前纬度
        locationBuilder.longitude(location.getLongitude());     //传入当前经度
        MyLocationData locationData = locationBuilder.build();          //生成实例
        baiduMap.setMyLocationData(locationData);*/
        MyLocationData data = new MyLocationData.Builder()//
                .direction(mCurrentX)//
                .accuracy(location.getRadius())//
                .latitude(location.getLatitude())//
                .longitude(location.getLongitude())//
                .build();
        baiduMap.setMyLocationData(data);
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000);       //设置定位更新的间隔
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);  //强制指定只使用GPS进行定位
        option.setIsNeedAddress(true);      //表示需要获取当前位置详细的地址信息
        option.setOpenGps(true);//打开GPS，默认不打卡
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//设置GPS，默认高精度
        option.setCoorType("bd09ll");//设置坐标系，默认“gcj02”
        //设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);
        //设置是否需要设备方向结果
        option.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(option);

        // 初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow);

        mOrientationListener = new MyOrientationListener(this);

        mOrientationListener
                .setmOnOrientationListener(new MyOrientationListener.OnOrientationListener() {

                    @Override
                    public void onOrientationChanged(float x) {
                        mCurrentX = x;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            // 开启定位
            mLocationClient.start();
            // 开启方向传感器
            mOrientationListener.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();     //停止定位
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);       //关闭光标显示功能
        // 停止方向传感器
        mOrientationListener.stop();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {         //定位监听器

        @Override
        public void onReceiveLocation(final BDLocation location) {      //显示BDLocation中的位置信息

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
                    currentPosition.append("经线：").append(location.getLongitude()).append("\n");
                    currentPosition.append("国家：").append(location.getCountry()).append("\n");
                    currentPosition.append("省：").append(location.getProvince()).append("\n");
                    currentPosition.append("市：").append(location.getCity()).append("\n");
                    currentPosition.append("区：").append(location.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(location.getStreet()).append("\n");
                    currentPosition.append("定位方式：");
                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS");
                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络");
                    }
                }
            });

            MyLocationData data = new MyLocationData.Builder()//
                    .direction(mCurrentX)//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            baiduMap.setMyLocationData(data);

            if (location.getLocType() == BDLocation.TypeGpsLocation         //将BDLocation对象传给navigateTo()方法，将地图移动到设备所在位置
                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
                LOC_LAT = location.getLatitude();
                LOC_LON = location.getLongitude();
                // 设置自定义图标
                MyLocationConfiguration config = new MyLocationConfiguration(
                        com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
                baiduMap.setMyLocationConfigeration(config);
            }
        }


        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

    //路线规划初始化
    private void initPoutePlan() {
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
    }

    // 路线规划模块
    public OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(OutdoorNavigationActivity.this, "路线规划:未找到结果,检查输入", Toast.LENGTH_SHORT).show();
                //禁止定位
                isFirstLocate = false;
            }
            assert result != null;
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                result.getSuggestAddrInfo();
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                baiduMap.clear();
                Toast.makeText(OutdoorNavigationActivity.this, "路线规划:搜索完成", Toast.LENGTH_SHORT).show();
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
            //禁止定位
            isFirstLocate = false;
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult var1) {
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult var1) {
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult var1) {
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult var1) {
        }
    };

    //开始规划
    private void StarRoute() {
        SDKInitializer.initialize(getApplicationContext());
        // 设置起、终点信息
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

}