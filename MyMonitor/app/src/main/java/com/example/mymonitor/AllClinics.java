package com.example.mymonitor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.recyclerview.ClinicsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


public class AllClinics extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ClinicsRecyclerViewAdapter adapter;
    FirebaseViewModel mViewModel;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clinics);

        mContext = this;

        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        recyclerView = findViewById(R.id.all_clinics_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ClinicsRecyclerViewAdapter();
        mViewModel.getAllClinics(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(true);
                        builder.setTitle("Add Clinic to your contacts?");
                        builder.setMessage("You will be sharing your health data with the clinic. Proceed?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        mViewModel.addUserClinic(adapter.getKeyByIndex(position));

                                    }
                                });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


    }


}

