package com.example.mymonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mymonitor.provider.Reading;
import com.example.mymonitor.provider.FirebaseViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class LiveData extends Fragment {

    FirebaseViewModel mLiveDataViewModel;
    DatabaseReference mLiveData;
    TextView temp_reading;
    TextView temp_avg;
    TextView heart_reading;
    TextView heart_avg;
    TextView sp_reading;
    TextView sp_avg;
    String userDevice;
    Random rand = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_data, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            System.out.println("YES");
            userDevice = bundle.getString("userDevice");
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        temp_reading = view.findViewById(R.id.card_temp_current_reading);
        temp_avg = view.findViewById(R.id.card_temp_average_reading);
        heart_reading = view.findViewById(R.id.card_heart_current_reading);
        heart_avg = view.findViewById(R.id.card_heart_average_reading);
        sp_reading = view.findViewById(R.id.card_sp_current_reading);
        sp_avg = view.findViewById(R.id.card_sp_average_reading);

        Button button = (Button) view.findViewById(R.id.button_livedata);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                randomDataGenerator();
            }
        });

        mLiveDataViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        // LiveData observation
//        mLiveDataViewModel.getAllItems().observe(this, newData -> {
//            adapter.setData(newData);
//            adapter.notifyDataSetChanged();
//            //tv.setText(newData.size() + "");
//        });
        mLiveData = mLiveDataViewModel.getReadings(User.getDevice());
        mLiveData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reading data = dataSnapshot.getValue(Reading.class);
                temp_reading.setText(data.getTemperature());
                heart_reading.setText(data.getHeartRate());
                sp_reading.setText(data.getSP02());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    public void randomDataGenerator(){


        int n = rand.nextInt(10);
        mLiveData.push().setValue(new Reading(Integer.toString(90+n), Integer.toString(90+n), Integer.toString(35+n)));
    }
}

