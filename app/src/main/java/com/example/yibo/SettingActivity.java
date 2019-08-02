package com.example.yibo;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button switchAccountButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 判断Android>5.0才能使用，使内容延伸到状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            // 使其状态栏呈现背景色
            getWindow().setStatusBarColor(Color.rgb(238,173,14));
        }
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_setting);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }

        //
        switchAccountButton = (Button)findViewById(R.id.switch_account_button);
        exitButton = (Button)findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                ActivityCollector.finishAll();
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
