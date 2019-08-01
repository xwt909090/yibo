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

public class MyInformationActivity extends AppCompatActivity {

    private TextView myInfoText;

    private Button bankCardInfoButton;

    private Button changePwdButton;

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
        setContentView(R.layout.activity_my_information);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_information_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.my_information_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("我的信息");
        //点击事件
        myInfoText = (TextView)findViewById(R.id.my_information_text);
        bankCardInfoButton = (Button)findViewById(R.id.bankCard_information_button);
        changePwdButton = (Button)findViewById(R.id.change_password_button);
        StringBuilder sb = new StringBuilder();
        String name = "xxx";
        String username = "飞翔的企鹅";
        String phoneNum = "123456789";
        String busNum = "辽J33688";
        double balance = 0.0;
        String str = "尊敬的" + name + "用户，您好！\n" + "您的个人信息如下：\n\n";
        sb.append(str);
        str = "用户名：" + username + "\n\n";
        sb.append(str);
        str = "手机号：" + phoneNum + "\n\n";
        sb.append(str);
        str = "车牌号：" + busNum + "\n\n";
        sb.append(str);
        str = "余额：  " + balance + "\n";
        sb.append(str);
        str = sb.toString();
        myInfoText.setText(str);
        changePwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationActivity.this, RetrievePasswordActivity.class);
                startActivity(intent);
            }
        });
        bankCardInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationActivity.this, BankCardInfoActivity.class);
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
