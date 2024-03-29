package com.example.yibo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParkingDetailsActivity extends AppCompatActivity {

    TextView infoText;

    Button offButton;

    TextView stoppingTimeText;

    TextView pricingText;

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

        setContentView(R.layout.activity_parking_details);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.parking_details_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.parking_details_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("停车详情");
        //点击事件
        infoText = (TextView)findViewById(R.id.parking_details_info_text);
        offButton = (Button)findViewById(R.id.parking_details_off_button);
        stoppingTimeText = (TextView)findViewById(R.id.parking_details_stopping_time_text);
        pricingText = (TextView)findViewById(R.id.parking_details_current_pricing_text);
        StringBuilder sb = new StringBuilder();
        String username = "飞翔的企鹅";
        int parkingSpotNum = 11;
        String startTime = "2019-01-01 10:00:00";
        String status = "已停车";
        String busNum = "辽J33688";
        String parkingLotsName = "山大二号停车场";

        String str = "您当前的停车信息如下：\n\n" + "用户名：" + username + "\n\n";
        sb.append(str);
        str = "停车位编号：" + parkingSpotNum + "\n\n";
        sb.append(str);
        str = "开始时间：" + startTime + "\n\n";
        sb.append(str);
        str = "状态：" + status + "\n\n";
        sb.append(str);
        str = "车牌号：" + busNum + "\n\n";
        sb.append(str);
        str = "停车场名：  " + parkingLotsName + "\n\n";
        sb.append(str);
        str = sb.toString();
        infoText.setText(str);
        String st = "00:03:58";
        double fee = 0.1;
        stoppingTimeText.setText(st);
        pricingText.setText(fee + "元");
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingDetailsActivity.this, PaymentInformationActivity.class);
                startActivity(intent);
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
