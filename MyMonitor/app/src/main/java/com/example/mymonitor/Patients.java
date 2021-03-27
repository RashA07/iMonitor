package com.example.mymonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.recyclerview.PatientsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.RecyclerItemClickListener;
import com.google.gson.Gson;

import java.util.Objects;

public class Patients extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PatientsRecyclerViewAdapter adapter;
    FirebaseViewModel mViewModel;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        context = this;


        Objects.requireNonNull(getSupportActionBar()).setTitle(User.getName());

        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        recyclerView = findViewById(R.id.patients_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PatientsRecyclerViewAdapter();
        mViewModel.getUserPatients(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, Navigation.class);
                        System.out.println(context);
                        intent.putExtra("PATIENT_DATA", new Gson().toJson(adapter.getPatientByIndex(position)));
                        intent.putExtra("PATIENT_KEY", adapter.getPatientKeyByIndex(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

}
