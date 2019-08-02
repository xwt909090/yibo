package com.example.yibo;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private double LOC_LAT = 0.0;

    private double LOC_LON = 0.0;

    private static final String TAG = "YIBO";

    /**
     * 定位SDK的核心类
     */
    public LocationClient mLocationClient;      //
    private MyLocationListener mLocationListener;

    private TextView positionText;

    private MapView mapView;        //显示地图

    private BaiduMap baiduMap;      //地图的总控制器

    // 自定义定位图标
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener mOrientationListener;//实例化方向传感器
    private float mCurrentX;

    private Button start_stop_car_button;

    private Button personal_center_button;

    private FloatingActionButton requestFButton;

    private boolean isRequest = false;//是否手动触发请求定位
    private boolean isFirstLocate = true;       //是否首次定位//防止多次调用animateMapStatus()方法，第一次定位时调用即可

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
        Log.d(TAG,"onCreate");
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());     //注册定位监听器
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();        //获得BaiduMap实例
        //baiduMap.setMyLocationEnabled(true);        //开启光标显示功能
        //baiduMap.setMyLocationEnabled(false);        //关闭光标显示功能

        positionText = (TextView) findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }

        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        start_stop_car_button = (Button)findViewById(R.id.start_parking_button);
        personal_center_button = (Button)findViewById(R.id.personal_center_button);
        //根据点击事件启动开始停车
        start_stop_car_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, StartParkingActivity.class);
                startActivity(intent);
            }
        });
        //根据点击事件启动个人中心
        personal_center_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
            }
        });

        //点击按钮手动请求定位
        requestFButton = (FloatingActionButton) findViewById(R.id.real_time_position_request_fbutton);
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
                Toast.makeText(MainActivity.this, "定位中……", Toast.LENGTH_SHORT).show();
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

    private void initLocation(){
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
    protected void onStart(){
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            // 开启定位
            mLocationClient.start();
            // 开启方向传感器
            mOrientationListener.start();
        }
        Log.d(TAG,"onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onStop(){
        super.onStop();
        //baiduMap.setMyLocationEnabled(false);
        // 停止定位
        //mLocationClient.stop();
        // 停止方向传感器
        //mOrientationListener.stop();
        Log.d(TAG,"onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        mLocationClient.stop();     //停止定位
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);       //关闭光标显示功能
        // 停止方向传感器
        mOrientationListener.stop();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart");
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

            runOnUiThread(new Runnable(){
                @Override
                public void run(){
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
                    //positionText.setText(currentPosition);
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


        public void onConnectHotSpotMessage(String s, int i){

        }

    }

}
