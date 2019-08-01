package com.example.yibo;



import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ReservationHistoryActivity extends AppCompatActivity {

    private List<ReservationHistory> reservationHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 判断Android>5.0才能使用，使内容延伸到状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            // 使其状态栏呈现背景色
            getWindow().setStatusBarColor(Color.rgb(238,173,14));
        }
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_reservation_history);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.reservation_history_toolbar);
        setSupportActionBar(toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }

        initReservationHistory();      //初始化停车场数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.reservation_history_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ReservationHistoryAdapter adapter = new ReservationHistoryAdapter(reservationHistoryList);
        recyclerView.setAdapter(adapter);

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
    private void initReservationHistory(){
        for(int i = 0;i < 6;i++){
            ReservationHistory rh1 = new ReservationHistory("山大一号停车场", 10, 2, "鲁K123456", "2019-01-01 10:00:00");
            reservationHistoryList.add(rh1);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
