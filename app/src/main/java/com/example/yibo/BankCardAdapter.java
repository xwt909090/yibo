package com.example.yibo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.ViewHolder> {

    private List<BankCard> mBankCardList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View bankCardView;
        CircleImageView bcImage;
        TextView bcName;
        TextView bcType;
        TextView bcNum;
        LinearLayout bcLayout;

        public ViewHolder(View view){
            super(view);
            bankCardView = view;
            bcImage = (CircleImageView)view.findViewById(R.id.bank_card_image);
            bcName = (TextView)view.findViewById(R.id.bank_card_name_text);
            bcType = (TextView)view.findViewById(R.id.bank_card_type_text);
            bcNum = (TextView)view.findViewById(R.id.bank_card_num_text);
            bcLayout = (LinearLayout)view.findViewById(R.id.bank_card_item_layout);
        }
    }

    public BankCardAdapter(List<BankCard> bankCardList){
        mBankCardList = bankCardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_card_item, parent, false);
        //点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.bankCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BankCard bc = mBankCardList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + bc.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        BankCard bankCard = mBankCardList.get(position);
        holder.bcImage.setImageResource(bankCard.getImageId());
        holder.bcName.setText(bankCard.getName());
        holder.bcType.setText(bankCard.getType());
        holder.bcNum.setText(bankCard.getNumber());
        holder.bcLayout.setBackgroundResource(bankCard.getBackgroundId());
    }
    @Override
    public int getItemCount(){
        return mBankCardList.size();
    }
}
