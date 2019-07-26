package com.example.yibo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.leon.lib.settingview.LSettingItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalCenterActivity extends AppCompatActivity {

    private CircleImageView mprofileImage;

    private TextView musername;

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
        setContentView(R.layout.activity_personal_center);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.personal_center_toolbar);
        setSupportActionBar(toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }

        //登录界面
        mprofileImage = (CircleImageView)findViewById(R.id.profile_image);
        musername = (TextView)findViewById(R.id.username_text);
        mprofileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PersonalCenterActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
            }
        });
        musername.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PersonalCenterActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
            }
        });
        /**
         * TODO 实现底部菜单对应布局控件事件
         * */
        LSettingItem myInfo =(LSettingItem)findViewById(R.id.my_information_item);
        LSettingItem myParking =(LSettingItem)findViewById(R.id.my_parking_item);
        LSettingItem myCars =(LSettingItem)findViewById(R.id.my_cars_item);
        LSettingItem yiboShop =(LSettingItem)findViewById(R.id.yibo_shop_item);
        LSettingItem securityCenter =(LSettingItem)findViewById(R.id.security_center_item);
        LSettingItem usualAddress =(LSettingItem)findViewById(R.id.usual_address_item);
        LSettingItem setting =(LSettingItem)findViewById(R.id.setting_item);
        myInfo.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent = new Intent(PersonalCenterActivity.this, MyInformationActivity.class);
                startActivity(intent);
            }
        });
        myParking.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent = new Intent(PersonalCenterActivity.this,MyParkingActivity.class);
                startActivity(intent);
            }
        });
        myCars.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent = new Intent(PersonalCenterActivity.this,MyCarsActivity.class);
                startActivity(intent);
            }
        });
        yiboShop.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {

            }
        });
        securityCenter.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {

            }
        });
        usualAddress.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {

            }
        });
        setting.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {

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

}
