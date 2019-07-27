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
import android.widget.Toast;

public class PaymentInformationActivity extends AppCompatActivity {

    TextView payInfoText;

    Button payButton;

    TextView priceText;

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
        setContentView(R.layout.activity_payment_information);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.payment_info_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.payment_info_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("支付信息");
        //点击事件
        payInfoText = (TextView)findViewById(R.id.payment_info_text);
        payButton = (Button)findViewById(R.id.payment_info_pay_button);
        priceText = (TextView)findViewById(R.id.payment_info_price_text);
        StringBuilder sb = new StringBuilder();
        String username = "飞翔的企鹅";
        String startTime = "2019-01-01 10:00:00";
        String busNum = "辽J33688";
        String str = "\n" + "用户名：" + username + "\n\n";
        sb.append(str);
        str = "车牌号：" + busNum + "\n\n";
        sb.append(str);
        str = "开始时间：" + startTime + "\n\n";
        sb.append(str);
        str = sb.toString();
        payInfoText.setText(str);
        double fee = 0.1;
        priceText.setText(fee + "元");
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentInformationActivity.this, "支付成功！", Toast.LENGTH_SHORT).show();
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
