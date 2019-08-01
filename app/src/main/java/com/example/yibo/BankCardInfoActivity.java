package com.example.yibo;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BankCardInfoActivity extends AppCompatActivity {

    private List<BankCard> bankCardList = new ArrayList<>();

    Button addBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 判断Android>5.0才能使用，使内容延伸到状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            // 使其状态栏呈现背景色
            getWindow().setStatusBarColor(Color.rgb(60,60,60));
        }
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_bank_card_info);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.bank_card_toolbar);
        setSupportActionBar(toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }

        //初始化银行卡信息
        initBankCards();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.bank_card_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        BankCardAdapter adapter = new BankCardAdapter(bankCardList);
        recyclerView.setAdapter(adapter);

        //
        addBC = (Button)findViewById(R.id.bank_card_add_button);
        addBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BankCardInfoActivity.this, "add", Toast.LENGTH_SHORT).show();
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

    private void initBankCards(){
        BankCard bc1 = new BankCard("建设银行", "储蓄卡", "**** **** **** 6629",R.drawable.bank_jianshe, R.drawable.bank_jianshe_background);
        bankCardList.add(bc1);
    }
}
