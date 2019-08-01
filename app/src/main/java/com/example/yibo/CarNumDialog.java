package com.example.yibo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CarNumDialog extends Dialog implements View.OnClickListener {

    private TextView mTitle, mMessage, mCanel, mConfirm;

    private Button defaultButton, deleteButton;

    private String title, message, canel, confirm, mdelete, mdefault;

    private OnCancelListtener onCancelListtener;

    private OnConfirmListtener onConfirmListtener;

    private OnDeleteListtener onDeleteListtener;

    private OnDefaultListtener onDefaultListtener;

    public CarNumDialog setTitle(String title) {
        this.title = title;
        return this;
    }
    public CarNumDialog setMessage(String message) {
        this.message = message;
        return this;
    }
    public CarNumDialog setCanel(String canel, OnCancelListtener onCancelListtener) {
        this.canel = canel;
        this.onCancelListtener = onCancelListtener;
        return this;
    }
    public CarNumDialog setConfirm(String confirm, OnConfirmListtener onConfirmListtener) {
        this.confirm = confirm;
        this.onConfirmListtener = onConfirmListtener;
        return this;
    }
    public CarNumDialog(@NonNull Context context) {
        super(context);
    }
    public CarNumDialog(@NonNull Context context, int themeId) {
        super(context, themeId);
    }
    public CarNumDialog setDelete(String del, OnDeleteListtener onDeleteListtener) {
        this.mdelete = del;
        this.onDeleteListtener = onDeleteListtener;
        return this;
    }
    public CarNumDialog setDefault(String def, OnDefaultListtener onDefaultListtener) {
        this.mdefault = def;
        this.onDefaultListtener = onDefaultListtener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_num_dialog);
//如果对话框宽度异常，可以通过下方代码根据设备的宽度来设置弹窗宽度
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        params.width = (int) (point.x * 0.8);
        getWindow().setAttributes(params);
        mTitle = (TextView) findViewById(R.id.car_num_dialog_title);
        defaultButton = (Button)findViewById(R.id.car_num_dialog_setting_default_button);
        deleteButton = (Button)findViewById(R.id.car_num_dialog_delete_button);
        defaultButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        mTitle.setText(title);
        deleteButton.setText(mdelete);
        defaultButton.setText(mdefault);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.car_num_dialog_delete_button:
                if (onDeleteListtener != null){
                    onDeleteListtener.onDelete(this);
                }
                dismiss();
                break;
            case R.id.car_num_dialog_setting_default_button:
                if (onDefaultListtener != null){
                    onDefaultListtener.onDefault(this);
                }
                dismiss();
                break;
        }
    }

//自定义接口形式提供回调方法

    public interface OnCancelListtener {
        void onCancel(CarNumDialog myDialog);
    }

    public interface OnConfirmListtener {
        void onConfirm(CarNumDialog myDialog);
    }

    public interface OnDeleteListtener {
        void onDelete(CarNumDialog myDialog);
    }

    public interface OnDefaultListtener {
        void onDefault(CarNumDialog myDialog);
    }
}