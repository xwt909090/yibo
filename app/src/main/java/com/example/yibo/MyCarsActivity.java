package com.example.yibo;

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
import android.widget.EditText;
import android.widget.TextView;

public class MyCarsActivity extends AppCompatActivity {

    private TextView myDefaultCarsText;

    private EditText myCarsEditText;

    private Button addCarsButton;

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
        setContentView(R.layout.activity_my_cars);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_cars_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.my_cars_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("我的车辆");
        //点击事件
        myDefaultCarsText = (TextView)findViewById(R.id.my_default_cars_text);
        myCarsEditText = (EditText)findViewById(R.id.my_cars_edittext);
        addCarsButton = (Button)findViewById(R.id.add_cars_button);
        StringBuilder sb = new StringBuilder();
        String defaultCar = "鲁K632145";
        String str = "您当前的默认车辆为：" + defaultCar + "";
        sb.append(str);
        str = sb.toString();
        myDefaultCarsText.setText(str);
        myCarsEditText.setKeyListener(null);
        myCarsEditText.setCursorVisible(false);
        myCarsEditText.setFocusable(false);
        myCarsEditText.setFocusableInTouchMode(false);
        myCarsEditText.setText("鲁K123456");
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
