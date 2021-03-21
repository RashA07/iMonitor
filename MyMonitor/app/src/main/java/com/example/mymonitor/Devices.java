package com.example.mymonitor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.recyclerview.ClinicsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.DevicesRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.RecyclerItemClickListener;


public class Devices extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DevicesRecyclerViewAdapter adapter;
    FirebaseViewModel mViewModel;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        recyclerView = view.findViewById(R.id.devices_recyclerview);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DevicesRecyclerViewAdapter();
        mViewModel.getUserDevices(adapter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }
}

