package com.example.yibo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

public class ParkingLotInformationActivity extends AppCompatActivity {

    TextView parkingLotName;
    TextView parkingLotSize;
    TextView parkingLotRemainSize;
    ImageView parkingLotImage;
    Button reservationButton;
    Button outdoorButton;
    Button indoorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 判断Android>5.0才能使用，使内容延伸到状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            // 使其状态栏呈现背景色
            getWindow().setStatusBarColor(Color.rgb(238,173,14));
        }
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        setContentView(R.layout.activity_parking_lot_information);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.parking_lot_information_toolbar);
        setSupportActionBar(toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }

        //
        parkingLotName = (TextView)findViewById(R.id.parking_lot_info_name_text);
        parkingLotSize = (TextView)findViewById(R.id.parking_lot_info_size_text);
        parkingLotRemainSize = (TextView)findViewById(R.id.parking_lot_info_remain_size_text);
        parkingLotImage = (ImageView)findViewById(R.id.parking_lot_info_image);
        reservationButton = (Button)findViewById(R.id.parking_lot_info_reservation_button);
        indoorButton = (Button)findViewById(R.id.parking_lot_info_indoor_navigation_button);
        outdoorButton = (Button)findViewById(R.id.parking_lot_info_outdoor_navigation_button);
        String name = "山大三号停车场";
        int size = 10;
        int remainSize = 10;
        parkingLotName.setText("停车场名称：" + name);
        parkingLotSize.setText("车位总数：" + size);
        parkingLotRemainSize.setText("剩余车位数：" + remainSize);
        parkingLotImage.setImageResource(R.drawable.lg);
        reservationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder dialog = new AlertDialog.Builder(ParkingLotInformationActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("您是否确定预约该停车场？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Toast.makeText(ParkingLotInformationActivity.this, "你点了确定", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ParkingLotInformationActivity.this, "你点了取消", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        indoorButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ParkingLotInformationActivity.this, IndoorNavigationActivity.class);
                startActivity(intent);
            }
        });
        outdoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingLotInformationActivity.this, OutdoorNavigationActivity.class);
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
