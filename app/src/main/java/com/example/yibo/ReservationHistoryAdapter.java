package com.example.yibo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ReservationHistoryAdapter extends RecyclerView.Adapter<ReservationHistoryAdapter.ViewHolder> {

    private List<ReservationHistory> mRHList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View rHView;
        TextView plname;
        TextView plnum;
        TextView psnum;
        TextView cnum;
        TextView time;

        public ViewHolder(View view){
            super(view);
            rHView = view;
            plname = (TextView)view.findViewById(R.id.reservation_history_parking_lot_name_text);
            plnum = (TextView)view.findViewById(R.id.reservation_history_parking_lot_num_text);
            psnum = (TextView)view.findViewById(R.id.reservation_history_parking_spot_num_text);
            cnum = (TextView)view.findViewById(R.id.reservation_history_car_num_text);
            time = (TextView)view.findViewById(R.id.reservation_history_time_text);
        }
    }

    public ReservationHistoryAdapter(List<ReservationHistory> rHList){
        mRHList = rHList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_history_item, parent, false);
        //点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.rHView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ReservationHistory rh = mRHList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + rh.getParkingLotName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ReservationHistory rH = mRHList.get(position);
        holder.plname.setText("停车场名：" + rH.getParkingLotName());
        holder.plnum.setText("停车场号：" + rH.getParkingLotNum());
        holder.psnum.setText("停车位号：" + rH.getParkingSpotNum());
        holder.cnum.setText("车牌号：" + rH.getCarNum());
        holder.time.setText("预约时间：" + rH.getTime());
    }
    @Override
    public int getItemCount(){
        return mRHList.size();
    }
}

