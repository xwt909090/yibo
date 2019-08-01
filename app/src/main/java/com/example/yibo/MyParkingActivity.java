package com.example.yibo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyParkingActivity extends AppCompatActivity {

    private TextView myParkingText;

    private Button cancelMyReservationButton;

    private Button viewHistoryButton;

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
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_my_parking);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_parking_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.my_parking_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("我的停车");
        //点击事件
        myParkingText = (TextView)findViewById(R.id.my_parking_text);
        cancelMyReservationButton = (Button)findViewById(R.id.cancel_my_reservation_button);
        viewHistoryButton = (Button)findViewById(R.id.view_history_button);
        StringBuilder sb = new StringBuilder();
        String username = "飞翔的企鹅";
        int parkingSpotNum = 11;
        String startTime = "2019-01-01 10:00:00";
        String currentTime = "2019-01-02 10:00:00";
        String status = "预约中";
        String busNum = "辽J33688";
        String parkingLotsName = "山大二号停车场";
        int parkingLotsNum = 2;
        double fee = 0.4;
        String str = "您当前的停车信息如下：\n\n" + "用户名：" + username + "\n\n";
        sb.append(str);
        str = "停车位编号：" + parkingSpotNum + "\n\n";
        sb.append(str);
        str = "开始时间：" + startTime + "\n\n";
        sb.append(str);
        str = "当前时间：" + currentTime + "\n\n";
        sb.append(str);
        str = "状态：" + status + "\n\n";
        sb.append(str);
        str = "车牌号：" + busNum + "\n\n";
        sb.append(str);
        str = "停车场名：  " + parkingLotsName + "\n\n";
        sb.append(str);
        str = "停车场编号：  " + parkingLotsNum + "\n\n";
        sb.append(str);
        str = "当前应缴费用：  " + fee + "\n";
        sb.append(str);
        str = sb.toString();
        myParkingText.setText(str);
        cancelMyReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyParkingActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("您是否确定取消预约？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Toast.makeText(MyParkingActivity.this, "你点了确定", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MyParkingActivity.this, "你点了取消", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
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
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
