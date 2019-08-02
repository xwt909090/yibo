package com.example.yibo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<Car> mCarsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View carView;
        Button CarNum;

        public ViewHolder(View view){
            super(view);
            carView = view;
            CarNum = (Button)view.findViewById(R.id.my_carNum_button);
        }
    }

    public CarAdapter(List<Car> CarsList){
        mCarsList = CarsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        //点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.carView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Car car = mCarsList.get(position);
                //final View v1 = v;
                //Toast.makeText(v1.getContext(), "you clicked view " + car.getCarNumber(), Toast.LENGTH_SHORT).show();
                //弹出选项对话框
                //CarNumDialog dialog = new CarNumDialog(v.getContext());
                /*LayoutInflater in = LayoutInflater.from(v.getContext());
                View view1 = in.inflate(R.layout.car_num_dialog, null);
                dialog.setContentView(view1);*/
                /*dialog.setTitle("选项");
                dialog.setDefault("设为默认", new CarNumDialog.OnDefaultListtener() {
                    @Override
                    public void onDefault(CarNumDialog mdialog) {
                        int position1 = holder.getAdapterPosition();
                        Car c1 = mCarsList.get(position1);
                        Toast.makeText(v1.getContext(), "default: " + c1.getCarNumber(),Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setDelete("删除", new CarNumDialog.OnDeleteListtener() {
                    @Override
                    public void onDelete(CarNumDialog mdialog) {
                        int position2 = holder.getAdapterPosition();
                        Car c2 = mCarsList.remove(position2);
                        Toast.makeText(v1.getContext(), "delete: " + c2.getCarNumber(), Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();*/
            }
        });
        return holder;
    }

    public void addData(int position) {
        mCarsList.add(position,new Car("ff"));
        notifyItemInserted(position);
    }

    public void remove(int i) {
        mCarsList.remove(i);
        notifyItemRemoved(i);
        notifyDataSetChanged();

    }

    public interface OnItemClickListener{  //自定义接口回调设置点击事件
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        Car car = mCarsList.get(position);
        holder.CarNum.setText(car.getCarNumber());
        holder.CarNum.setKeyListener(null);
        holder.CarNum.setCursorVisible(false);
        holder.CarNum.setFocusable(false);
        holder.CarNum.setFocusableInTouchMode(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ps = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(ps);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                int ps=holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(ps);
                return false;
            }
        });
    }

    @Override
    public int getItemCount(){
        return mCarsList.size();
    }
}
