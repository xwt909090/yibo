package com.example.yibo;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StartParkingActivity extends AppCompatActivity {

    private List<ParkingLot> parkingLotList = new ArrayList<>();

    private ImageView searchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 判断Android>5.0才能使用，使内容延伸到状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            // 使其状态栏呈现背景色
            getWindow().setStatusBarColor(Color.rgb(238,173,14));
        }
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_start_parking);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.start_parking_toolbar);
        setSupportActionBar(toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }

        initParkingLots();      //初始化停车场数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.start_parking_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ParkingLotAdapter adapter = new ParkingLotAdapter(parkingLotList);
        recyclerView.setAdapter(adapter);

        //点击事件
        searchImage = (ImageView)findViewById(R.id.start_parking_search_image);
        searchImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(StartParkingActivity.this, "search", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //点击返回箭头时，关闭当前活动，返回上一个活动
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initParkingLots(){
        for(int i = 0;i < 3;i++){
            ParkingLot pl1 = new ParkingLot("山大一号停车场", 10, 1.5, 43);
            parkingLotList.add(pl1);
            ParkingLot pl2 = new ParkingLot("山大二号停车场", 10, 1.5, 43);
            parkingLotList.add(pl2);
            ParkingLot pl3 = new ParkingLot("山大三号停车场", 10, 1.5, 43);
            parkingLotList.add(pl3);
            ParkingLot pl4 = new ParkingLot("山大四号停车场", 10, 1.5, 43);
            parkingLotList.add(pl4);
            ParkingLot pl5 = new ParkingLot("山大五号停车场", 10, 1.5, 43);
            parkingLotList.add(pl5);
            ParkingLot pl6 = new ParkingLot("山大六号停车场", 10, 1.5, 43);
            parkingLotList.add(pl6);

        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
