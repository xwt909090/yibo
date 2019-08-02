package com.example.yibo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyCarsActivity extends AppCompatActivity {

    private TextView myDefaultCarsText;

    private Button addCarsButton;

    String defaultCar = "鲁K632145";

    private List<Car> carsList = new ArrayList<>();

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
        addCarsButton = (Button)findViewById(R.id.add_cars_button);
        String str = "您当前的默认车辆为：" + defaultCar + "";
        myDefaultCarsText.setText(str);
        addCarsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MyCarsActivity.this);
                builder.setTitle("请输入要添加的车牌号");
                final EditText addCarNumEdit = new EditText(MyCarsActivity.this);
                builder.setView(addCarNumEdit);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cn = addCarNumEdit.getText().toString().trim();
                        if("".equals(cn)){
                            Toast.makeText(MyCarsActivity.this, "添加失败，请输入有效的车牌号", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            carsList.add(new Car(cn));
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
                AlertDialog dialog = builder.create();  //创建对话框
                dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
                dialog.show();
            }
        });

        initCars();     //初始化车牌信息
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_cars_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final CarAdapter adapter = new CarAdapter(carsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final int position1 = position;
                //弹出选项对话框
                CarNumDialog dialog = new CarNumDialog(MyCarsActivity.this);
                dialog.setTitle("选项");
                dialog.setDefault("设为默认", new CarNumDialog.OnDefaultListtener() {
                    @Override
                    public void onDefault(CarNumDialog mdialog) {
                        Car c1 = carsList.get(position1);
                        defaultCar = c1.getCarNumber();
                        String str = "您当前的默认车辆为：" + defaultCar + "";
                        myDefaultCarsText.setText(str);
                        Toast.makeText(MyCarsActivity.this, "默认车辆设置成功！", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setDelete("删除", new CarNumDialog.OnDeleteListtener() {
                    @Override
                    public void onDelete(CarNumDialog mdialog) {
                        Car c2 = carsList.get(position1);
                        if(c2.getCarNumber().equals(defaultCar)){
                            Toast.makeText(MyCarsActivity.this, "默认车辆不能删除！", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            adapter.remove(position1);
                        }
                    }
                });
                dialog.show();
            }
            @Override
            public void onItemLongClick(int position) {
                //adapter.remove(position);
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

    private void initCars(){
        for(int i = 0;i < 1;i++){
            Car car1 = new Car("鲁K1");
            carsList.add(car1);
            Car car2 = new Car("鲁K12");
            carsList.add(car2);
            Car car3 = new Car("鲁K123");
            carsList.add(car3);
            Car car4 = new Car("鲁K1234");
            carsList.add(car4);
            Car car5 = new Car("鲁K12345");
            carsList.add(car5);
            Car car6 = new Car("鲁K123456");
            carsList.add(car6);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
