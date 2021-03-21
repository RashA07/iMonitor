package com.example.mymonitor.recyclerview;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonitor.R;
import com.example.mymonitor.provider.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DevicesRecyclerViewAdapter extends RecyclerView.Adapter<DevicesRecyclerViewAdapter.ViewHolder> {

    private List<Device> data = new ArrayList<>();

    public void setData(List<Device> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DevicesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.devices_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DevicesRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.status.setText(data.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView name;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.card_device_name);
            status = itemView.findViewById(R.id.card_device_status);




        }

    }
}