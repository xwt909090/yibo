package com.example.yibo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.util.List;

public class ParkingLotAdapter extends RecyclerView.Adapter<ParkingLotAdapter.ViewHolder> {

    private List<ParkingLot> mParkingLotList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View parkingLotView;
        TextView parkingLotName;
        TextView parkingLotSize;
        TextView parkingLotPrice;
        TextView parkingLotDistance;
        ImageView parkingLotLocation;

        public ViewHolder(View view){
            super(view);
            parkingLotView = view;
            parkingLotName = (TextView)view.findViewById(R.id.parking_lot_name_text);
            parkingLotSize = (TextView)view.findViewById(R.id.parking_lot_size_text);
            parkingLotPrice = (TextView)view.findViewById(R.id.parking_lot_price_text);
            parkingLotDistance = (TextView)view.findViewById(R.id.parking_lot_distance_text);
            parkingLotLocation = (ImageView)view.findViewById(R.id.parking_lot_location_image);
        }
    }

    public ParkingLotAdapter(List<ParkingLot> parkingLotList){
        mParkingLotList = parkingLotList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_lot_item, parent, false);
        //点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.parkingLotView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                ParkingLot parkinglot = mParkingLotList.get(position);
                Intent intent = new Intent(v.getContext(), ParkingLotInformationActivity.class);
                
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "you clicked view " + parkinglot.getParkingLotName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ParkingLot parkinglot = mParkingLotList.get(position);
        holder.parkingLotName.setText(parkinglot.getParkingLotName());
        holder.parkingLotSize.setText(parkinglot.getParkingLotSize() + "个车位");
        holder.parkingLotPrice.setText(""+ parkinglot.getParkingLotPrice() + "元/小时");
        holder.parkingLotDistance.setText(parkinglot.getDistance() + "米");
    }

    @Override
    public int getItemCount(){
        return mParkingLotList.size();
    }
}
