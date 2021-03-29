package com.example.mymonitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.recyclerview.ClinicsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;


public class Clinics extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ClinicsRecyclerViewAdapter adapter;
    FirebaseViewModel mViewModel;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);



        recyclerView = view.findViewById(R.id.clinics_recyclerview);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ClinicsRecyclerViewAdapter();
        mViewModel.getUserClinics(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ClinicProfile.class);
                        intent.putExtra("CLINIC_DATA", new Gson().toJson(adapter.getClinicByIndex(position)));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        FloatingActionButton fab = view.findViewById(R.id.fab_clinics);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllClinics.class);
                intent.putExtra("PATIENT_CLINICS", new Gson().toJson(adapter.getKeys()));
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clinics, container, false);
    }
}

