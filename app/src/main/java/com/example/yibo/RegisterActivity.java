package com.example.yibo;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEdit, passwordEdit, confirmEdit, nameEdit, phoneEdit, busNumEdit;

    private TextView usernameConditionText, passwordConditionText, confirmConditionText, busNumConditionText, nameConditionText, phoneConditionText;

    private Button registerButton;

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
        setContentView(R.layout.activity_register);
        //引入自定义ToolBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.register_collapsing_toolbar);
        //显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //actionBar.setTitle("登录");
            actionBar.setDisplayShowTitleEnabled(false);        //隐藏左侧标题
            actionBar.setDisplayHomeAsUpEnabled(true);      //显示返回箭头
        }
        collapsingToolbar.setTitle("注册");

        //
        usernameEdit = (EditText)findViewById(R.id.register_username_edittext);
        passwordEdit = (EditText)findViewById(R.id.register_password_edittext);
        confirmEdit = (EditText)findViewById(R.id.register_confirm_password_edittext);
        nameEdit = (EditText)findViewById(R.id.register_name_edittext);
        phoneEdit = (EditText)findViewById(R.id.register_phone_number_edittext);
        busNumEdit = (EditText)findViewById(R.id.register_bus_number_edittext);
        usernameConditionText = (TextView)findViewById(R.id.register_username_condition_text);
        passwordConditionText = (TextView)findViewById(R.id.register_password_condition_text);
        confirmConditionText = (TextView)findViewById(R.id.register_confirm_condition_text);
        busNumConditionText = (TextView)findViewById(R.id.register_bus_number_condition_text);
        phoneConditionText = (TextView)findViewById(R.id.register_phone_number_condition_text);
        nameConditionText = (TextView)findViewById(R.id.register_name_condition_text);
        registerButton = (Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameConditionText.getVisibility() == View.GONE && passwordConditionText.getVisibility() == View.GONE &&
                        confirmConditionText.getVisibility() == View.GONE && nameConditionText.getVisibility() == View.GONE &&
                            phoneConditionText.getVisibility() == View.GONE && busNumConditionText.getVisibility() == View.GONE){
                    Toast.makeText(RegisterActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        phoneEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        usernameEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        passwordEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        confirmEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        nameEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());
        busNumEdit.setOnFocusChangeListener(new MyOnFocusChangeListener());

    }
    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        String username = usernameEdit.getText().toString().trim(), pwd = passwordEdit.getText().toString().trim();
        String confirm = confirmEdit.getText().toString().trim(), name = nameEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim(), busNum = busNumEdit.getText().toString().trim();
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            int i = v.getId();
            switch (i) {
                case R.id.register_username_edittext:
                    username = usernameEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if("".equals(username) || username.length() < 2 || username.length() > 20) {
                            usernameConditionText.setText("用户名由字母、数字或“_”组成，长度不少于2位，不多于20位");
                            usernameConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            usernameConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if("".equals(username) || username.length() < 2 || username.length() > 20){
                            usernameConditionText.setText("您设置的用户名有误，请重新设置您的用户名");
                            usernameConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            usernameConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.register_password_edittext:
                    pwd = passwordEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if("".equals(pwd) || pwd.length() < 6) {
                            passwordConditionText.setText("密码不少于6位字符");
                            passwordConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            passwordConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if("".equals(pwd) || pwd.length() < 6){
                            passwordConditionText.setText("您设置的密码有误，请重新设置您的密码");
                            passwordConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            passwordConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.register_confirm_password_edittext:
                    pwd = passwordEdit.getText().toString().trim();
                    confirm = confirmEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if(!pwd.equals(confirm)) {
                            confirmConditionText.setText("请再次输入密码");
                            confirmConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            confirmConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if(!pwd.equals(confirm)){
                            confirmConditionText.setText("两次密码输入的不一致，请重新输入");
                            confirmConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            confirmConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.register_name_edittext:
                    name = nameEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if("".equals(name)) {
                            nameConditionText.setText("请输入您的真实姓名");
                            nameConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            nameConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if("".equals(name)){
                            nameConditionText.setText("请输入您的真实姓名");
                            nameConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            nameConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.register_phone_number_edittext:
                    phone = phoneEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if("".equals(phone) || (!isNaN.isDouble(phone)) || phone.length() != 11) {
                            phoneConditionText.setText("请输入您的手机号");
                            phoneConditionText.setVisibility(View.VISIBLE);
                        }
                        else{
                            phoneConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if("".equals(phone) || (!isNaN.isDouble(phone)) || phone.length() != 11){
                            phoneConditionText.setText("请填写有效的电话号码(只能为数字)");
                            phoneConditionText.setVisibility(View.VISIBLE);
                        }
                        else{
                            phoneConditionText.setVisibility(View.GONE);
                        }
                    }
                    break;
                case R.id.register_bus_number_edittext:
                    busNum = busNumEdit.getText().toString().trim();
                    if(hasFocus){
                        // 此处为得到焦点时的处理内容
                        if("".equals(busNum)) {
                            busNumConditionText.setText("车牌号格式为：鲁K12345，其中字母应为大写");
                            busNumConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            busNumConditionText.setVisibility(View.GONE);
                        }
                    }
                    else{
                        // 此处为失去焦点时的处理内容
                        if("".equals(busNum)){
                            busNumConditionText.setText("请按照格式填写有效车牌号");
                            busNumConditionText.setVisibility(View.VISIBLE);
                        }
                        else {
                            busNumConditionText.setVisibility(View.GONE);
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
