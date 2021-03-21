package com.example.mymonitor.recyclerview;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonitor.provider.Reading;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

//    private List<String> data = new ArrayList<>();
    private List<Reading> data;

    public MyRecyclerViewAdapter(){

    }

//    public void setData(List<String> data) {
//        this.data = data;
//    }
    public void setData(List<Reading> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
//        ViewHolder viewHolder = new ViewHolder(v);
//        return viewHolder;
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {

//        System.out.println(data);
//
//        holder.itemName.setText(data.get(position).getName());
//        holder.itemQuantity.setText(data.get(position).getQuantity());
//        holder.itemCost.setText(data.get(position).getCost());
//        holder.itemDesc.setText(data.get(position).getDesc());
//        holder.itemFrozen.setText(data.get(position).getFrozen());
//        holder.itemId.setText(String.valueOf(data.get(position).getId()));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView card_data_name;
        public TextView card_current_reading;
        public TextView card_average_reading;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
//            card_data_name = itemView.findViewById(R.id.card_data_name);
//            card_current_reading = itemView.findViewById(R.id.card_current_reading);
//            card_average_reading = itemView.findViewById(R.id.card_average_reading);




        }

    }
}
