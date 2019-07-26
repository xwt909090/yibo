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

public class LoginAndRegisterActivity extends AppCompatActivity {

    private Button loginButton;

    private TextView retrievePasswordText;

    private TextView freeRegistrationText;

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
        setContentView(R.layout.activity_login_and_register);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.login_and_register_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.login_and_register_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //actionBar.setTitle("登录");
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("登录");
        //点击事件
        loginButton = (Button)findViewById(R.id.login_button);
        retrievePasswordText = (TextView)findViewById(R.id.retrieve_password_text);
        freeRegistrationText = (TextView)findViewById(R.id.free_registration_text);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        retrievePasswordText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        freeRegistrationText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginAndRegisterActivity.this,RegisterActivity.class);
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
}
