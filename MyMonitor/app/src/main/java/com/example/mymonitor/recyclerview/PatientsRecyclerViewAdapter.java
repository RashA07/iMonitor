package com.example.mymonitor.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonitor.R;
import com.example.mymonitor.provider.Clinic;
import com.example.mymonitor.provider.Patient;
import com.example.mymonitor.provider.PatientDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder> {

    private HashMap<String, Patient> data = new HashMap<>();

    public void addData(String key, Patient patient) {

        if (data.containsKey(key)){
            data.replace(key, patient);
        }
        else {
            data.put(key,patient);
        }
    }

    public Patient getPatientByIndex(int position){
        List<String> keys = new ArrayList<>(data.keySet());
        return data.get(keys.get(position));
    }

    public String getPatientKeyByIndex(int position){
        List<String> keys = new ArrayList<>(data.keySet());
        return keys.get(position);
    }

    @NonNull
    @Override
    public PatientsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patients_card,parent,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsRecyclerViewAdapter.ViewHolder holder, int position) {

//        System.out.println(data);
//
        PatientDetails details = getPatientByIndex(position).getDetails();

        holder.name.setText(details.getName());
        holder.phoneNo.setText(details.getPhoneNo());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView name;
        public TextView phoneNo;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.card_patient_name);
            phoneNo = itemView.findViewById(R.id.card_patient_phoneno);




        }

    }
}