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
import android.widget.Toast;
import org.w3c.dom.Text;

public class RetrievePasswordActivity extends AppCompatActivity {

    private EditText originEdit, firstNewEdit, secondNewEdit;
    private TextView firstConditionText, secondConditionText, originConditionText;
    private Button confirmButton;

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
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_retrieve_password);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.retrieve_password_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.retrieve_password_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("修改密码");
        originEdit = (EditText)findViewById(R.id.retrieve_password_origin_edittext);
        firstNewEdit = (EditText)findViewById(R.id.retrieve_password_first_new_edittext);
        secondNewEdit = (EditText)findViewById(R.id.retrieve_password_second_new_edittext);
        firstConditionText = (TextView)findViewById(R.id.retrieve_password_first_new_condition_text);
        secondConditionText = (TextView)findViewById(R.id.retrieve_password_second_new_condition_text);
        originConditionText = (TextView)findViewById(R.id.register_password_origin_condition_text);
        confirmButton = (Button)findViewById(R.id.retrieve_password_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(originConditionText.getVisibility() == View.GONE &&
                        firstConditionText.getVisibility() == View.GONE &&
                            secondConditionText.getVisibility() == View.GONE) {
                    Toast.makeText(RetrievePasswordActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    ActivityCollector.finishAll();
                }
                else{
                    Toast.makeText(RetrievePasswordActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        originEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        firstNewEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        secondNewEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
    }
    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        String origin = originEdit.getText().toString().trim(), first = firstNewEdit.getText().toString().trim(), second = secondNewEdit.getText().toString().trim();
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            int i = v.getId();
            switch (i) {
                case R.id.retrieve_password_origin_edittext:
                    origin = originEdit.getText().toString().trim();
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        if ("".equals(origin)) {
                            originConditionText.setText("请输入原密码");
                            originConditionText.setVisibility(View.VISIBLE);
                        } else {
                            originConditionText.setVisibility(View.GONE);
                        }
                    } else {
                        // 此处为失去焦点时的处理内容
                        if ("".equals(origin)) {
                            originConditionText.setText("请输入原密码");
                            originConditionText.setVisibility(View.VISIBLE);
                        } else {
                            originConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.retrieve_password_first_new_edittext:
                    first = firstNewEdit.getText().toString().trim();
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        if ("".equals(first) || first.length() < 6) {
                            firstConditionText.setText("新密码应不少于6位字符");
                            firstConditionText.setVisibility(View.VISIBLE);
                        } else {
                            firstConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if("".equals(first) || first.length() < 6){
                            firstConditionText.setText("您设置的密码有误，请重新设置您的密码");
                            firstConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            firstConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.retrieve_password_second_new_edittext:
                    second = secondNewEdit.getText().toString().trim();
                    first = firstNewEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if(!first.equals(second)) {
                            secondConditionText.setText("请再次输入新密码");
                            secondConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            secondConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if(!first.equals(second)){
                            secondConditionText.setText("两次密码输入的不一致，请重新输入");
                            secondConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            secondConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
            }
        }
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
