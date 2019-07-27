package com.example.yibo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<Car> mCarsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        EditText CarNum;

        public ViewHolder(View view){
            super(view);
            CarNum = (EditText)view.findViewById(R.id.my_carNum_edittext);
        }
    }

    public CarAdapter(List<Car> CarsList){
        mCarsList = CarsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Car car = mCarsList.get(position);
        holder.CarNum.setText(car.getCarNumber());
        holder.CarNum.setKeyListener(null);
        holder.CarNum.setCursorVisible(false);
        holder.CarNum.setFocusable(false);
        holder.CarNum.setFocusableInTouchMode(false);
    }

    @Override
    public int getItemCount(){
        return mCarsList.size();
    }
}
