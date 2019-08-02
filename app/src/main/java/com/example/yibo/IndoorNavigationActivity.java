package com.example.yibo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.IdentityHashMap;

public class IndoorNavigationActivity extends AppCompatActivity {

    Button checkStatusButton;
    EditText parkingSpotEdit;
    Button parkingButton;

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
        setContentView(R.layout.activity_indoor_navigation);

        //
        checkStatusButton = (Button)findViewById(R.id.indoor_check_my_parking_status_button);
        parkingSpotEdit = (EditText)findViewById(R.id.indoor_parking_spot_num_edittext);
        parkingButton = (Button)findViewById(R.id.indoor_parking_button);

        boolean flag = false;
        if(flag){
            checkStatusButton.setVisibility(View.VISIBLE);
            parkingSpotEdit.setVisibility(View.GONE);
            parkingButton.setVisibility(View.GONE);
        }
        else{
            checkStatusButton.setVisibility(View.GONE);
            parkingSpotEdit.setVisibility(View.VISIBLE);
            parkingButton.setVisibility(View.VISIBLE);
        }

        checkStatusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(IndoorNavigationActivity.this, ParkingDetailsActivity.class);
                startActivity(intent);
            }
        });
        parkingSpotEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Toast.makeText(IndoorNavigationActivity.this, "请先输入您实际预约的停车位(已预约用户只能停车在预约车位)",Toast.LENGTH_SHORT).show();
                }
            }
        });
        parkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parkingSpot = parkingSpotEdit.getText().toString().trim();
                if("".equals(parkingSpot) || !isNaN.isInteger(parkingSpot)){
                    Toast.makeText(IndoorNavigationActivity.this, "停车失败，请重新输入您实际预约的停车位(已预约用户只能停车在预约车位)",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(IndoorNavigationActivity.this,"停车成功", Toast.LENGTH_SHORT).show();
                    checkStatusButton.setVisibility(View.VISIBLE);
                    parkingSpotEdit.setVisibility(View.GONE);
                    parkingButton.setVisibility(View.GONE);
                    Intent intent = new Intent(IndoorNavigationActivity.this, ParkingDetailsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
