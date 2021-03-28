package com.example.mymonitor.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonitor.R;
import com.example.mymonitor.provider.Clinic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllClinicsRecyclerViewAdapter extends RecyclerView.Adapter<AllClinicsRecyclerViewAdapter.ViewHolder> {

    private HashMap<String, Clinic> data = new HashMap<>();


    public void addData(String key, Clinic clinic) {

        if (data.containsKey(key)){
            data.replace(key, clinic);
        }
        else {
            data.put(key,clinic);
        }
    }

    public Clinic getClinicByIndex(int position){
        List<String> keys = new ArrayList<>(data.keySet());
        return data.get(keys.get(position));
    }

    public String getKeyByIndex(int position){
        List<String> keys = new ArrayList<>(data.keySet());
        return keys.get(position);
    }

    @NonNull
    @Override
    public AllClinicsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinics_card,parent,false);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllClinicsRecyclerViewAdapter.ViewHolder holder, int position) {

        System.out.println(data);

        Clinic c = getClinicByIndex(position);

        holder.name.setText(c.getName());
        holder.phoneNo.setText(c.getPhoneNo());
        holder.status.setText(c.getStatusString());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView name;
        public TextView phoneNo;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.card_clinic_name);
            phoneNo = itemView.findViewById(R.id.card_clinic_phoneno);
            status = itemView.findViewById(R.id.card_clinic_status);




        }

    }
}