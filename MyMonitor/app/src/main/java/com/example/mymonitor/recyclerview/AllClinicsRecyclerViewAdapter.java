package com.example.mymonitor.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonitor.R;
import com.example.mymonitor.provider.Clinic;
import com.example.mymonitor.provider.FirebaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllClinicsRecyclerViewAdapter extends RecyclerView.Adapter<AllClinicsRecyclerViewAdapter.ViewHolder> {

    private HashMap<String, Clinic> data = new HashMap<>();
    private List<String> selectedData = new ArrayList<>();


    public void addData(String key, Clinic clinic) {

        if (data.containsKey(key)){
            data.replace(key, clinic);
        }
        else {
            data.put(key,clinic);
        }
    }

    public void addSelectedData(List<String> selectedData) {


        this.selectedData = selectedData;
    }

    public boolean checkSelected(int position){

        System.out.println("checkSELECTED");
        System.out.println(getKeyByIndex(position));
        System.out.println(selectedData);

        return selectedData.contains(getKeyByIndex(position));
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.allclinics_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllClinicsRecyclerViewAdapter.ViewHolder holder, final int position) {

        System.out.println(data);

        Clinic c = getClinicByIndex(position);

        holder.name.setText(c.getName());
        holder.phoneNo.setText(c.getPhoneNo());
        if (checkSelected(position)){
            holder.selected.setText("SELECTED");
        }



//        holder.add.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setCancelable(true);
//                    builder.setTitle("Add Clinic to your contacts?");
//                    builder.setMessage("You will be sharing your health data with the clinic. Proceed?");
//                    builder.setPositiveButton("Confirm",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    mViewModel.addUserClinic(getKeyByIndex(position));
//
//                                }
//                            });
//                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//
//                return true;
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView name;
        public TextView phoneNo;
        public TextView selected;
//        public Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.allclinics_clinic_name);
            phoneNo = itemView.findViewById(R.id.allclinics_clinic_phoneno);
            selected = itemView.findViewById(R.id.allclinics_clinic_selected);
//            add = itemView.findViewById(R.id.button_add_clinic);




        }

    }
}