package com.example.mymonitor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymonitor.provider.Reading;
import com.example.mymonitor.provider.FirebaseViewModel;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class LiveData extends Fragment {

    private static HashMap<String, Reading> readingHashMap = new HashMap<>();
//    private static HashMap<String, Reading> readingHashMapFinal = new HashMap<>();
    FirebaseViewModel mLiveDataViewModel;
    DatabaseReference mLiveData;
    TextView temp_reading;
    TextView temp_avg;
    TextView heart_reading;
    TextView heart_avg;
    TextView sp_reading;
    TextView sp_avg;
    String userDevice;

    Object[] keys;

    float cumulativeHeartRate = 0;
    float cumulativeSPO2 = 0;
    float cumulativeTemp = 0;

    private NotificationManagerCompat notificationManager;


    public static HashMap<String, Reading> getReadingHashMap() {
        return readingHashMap;
    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();


//    Bundle bundle = this.getArguments();
//
//    String UserDevicePath = bundle.getString("userDevice");


//    DatabaseReference ref = database.getReference(test+"/");

    private static DecimalFormat df = new DecimalFormat("0.00");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        notificationManager = NotificationManagerCompat.from(this.getContext());


//        DataFromFirebase();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_data, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        notificationManager = NotificationManagerCompat.from(this.getContext());
        super.onActivityCreated(savedInstanceState);

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            System.out.println("YES");
//            userDevice = bundle.getString("userDevice");
//            DatabaseReference ref = database.getReference(userDevice+"/");
//            System.out.println(userDevice);
//        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            System.out.println("YES");
            userDevice = bundle.getString("userDevice");
        }

        DataFromFirebase(userDevice);




        temp_reading = view.findViewById(R.id.card_temp_current_reading);
        temp_avg = view.findViewById(R.id.card_temp_average_reading);
        heart_reading = view.findViewById(R.id.card_heart_current_reading);
        heart_avg = view.findViewById(R.id.card_heart_average_reading);
        sp_reading = view.findViewById(R.id.card_sp_current_reading);
        sp_avg = view.findViewById(R.id.card_sp_average_reading);



        mLiveDataViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        mLiveData = mLiveDataViewModel.getReadings(userDevice);

        Button button = (Button) view.findViewById(R.id.button_livedata);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLiveDataViewModel.spamData(userDevice);
//                NotifyPatient();

//                Check if arduino is alive
//                    If alive, get live data feed
//                    else: say device is offline

//                randomDataGenerator();
//                DataFromFirebase();
            }
        });

//        mLiveData.orderByKey().limitToLast(1);
//        mLiveData.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                String heartrate = snapshot.child("Heart Rate(BPM)").getValue().toString();
////                String spo2 = snapshot.child("SP02(%)").getValue().toString();
////                String temp = snapshot.child("Temperature (°C)").getValue().toString();
////
////                heart_reading.setText(heartrate);
////                sp_reading.setText(spo2);
////                temp_reading.setText(temp);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//
//        mLiveData.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String heartrate = dataSnapshot.child("Heart Rate(BPM)").getValue().toString();
////                dataSnapshot.getRef().limitToLast(1)
//
////                Reading data = dataSnapshot.getValue(Reading.class);
////
////                temp_reading.setText(data.getTemperature());
//                heart_reading.setText(heartrate);
////                sp_reading.setText(data.getSP02());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//
//
    }

    private void NotifyPatient() {

        Double temperature = Double.parseDouble(temp_reading.getText().toString());
        Double heartrate = Double.parseDouble(heart_reading.getText().toString());
        Double spo2 = Double.parseDouble(sp_reading.getText().toString());
        Double age; // get age of patient


        Double highHeart = 20.00;

        android.app.Notification mBuilder;

        if (heartrate > highHeart && spo2 >22){

//          This will change color of the text view
            heart_reading.setTextColor(Color.parseColor("#FF0000"));

//          This will trigger a notification
            mBuilder = new NotificationCompat.Builder(this.getContext(), Notification.CHANNEL_1_ID)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("HEART RATE WARNING!") // title for notification
                    .setContentText("Heart Rate is reaching Abnormal Levels, please contact clinic")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

        } else{

            mBuilder = new NotificationCompat.Builder(this.getContext(), Notification.CHANNEL_2_ID)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Low Warning") // title for notification
                    .setContentText("Heart Rate is reaching Abnormal Levels, please contact clinic")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

        }
        notificationManager.notify(1, mBuilder);


    }

    private void DataFromFirebase(String userDevice) {

        DatabaseReference ref = database.getReference(userDevice);



        ref.child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heartRate;
                String Spo2;
                String Temp;
                String Date;
                String Time;
                String ID;
                String key;

                for (DataSnapshot item : snapshot.getChildren()) {

                    heartRate = item.child("heart_rate").getValue().toString();
                    Spo2 = item.child("blood_oxygen").getValue().toString();
                    Temp = item.child("temperature").getValue().toString();
                    Date = item.child("date").getValue().toString();
                    Time = item.child("time").getValue().toString();
                    ID = item.child("id").getValue().toString();

                    key = Date + " " + Time;


                    Reading patientReading = new Reading(heartRate, Spo2, Temp, Date, Time, ID);


                    readingHashMap.put(key, patientReading);

                }

                keys = readingHashMap.keySet().toArray();


//                DEBUG AND GET THE LATEST TIME BECAUSE NOW IT WORKS LIKE A HASHMAP AND THATS NOT GOOD
                Object last = keys[15];

                Reading test = readingHashMap.get(last);

//                float trial = Float.parseFloat(test.getHeartRate());

//                if (trial == 0){
//                    temp_reading.setText("Device Offline");
//                    heart_reading.setText("Device Offline");
//                    sp_reading.setText("Device Offline");
//                }
////                else {
                    temp_reading.setText(test.getTemperature());
                    heart_reading.setText(test.getHeartRate());
                    sp_reading.setText(test.getSP02());
//                }

                for (int i = 0; i < readingHashMap.size(); i++) {

                    Reading readings = readingHashMap.get(keys[i]);
//                    System.out.println(readings.getHeartRate());

                    cumulativeHeartRate += Float.parseFloat(readings.getHeartRate());
                    cumulativeSPO2 += Float.parseFloat(readings.getSP02());
                    cumulativeTemp += Float.parseFloat(readings.getTemperature());
                }


                float averageHeartRate = cumulativeHeartRate/keys.length;
                float averageSpo2 = cumulativeSPO2/keys.length;
                float averageTemp = cumulativeTemp/keys.length;

                heart_avg.setText(Float.toString(Float.parseFloat(df.format(averageHeartRate))));
                sp_avg.setText(Float.toString(Float.parseFloat(df.format(averageSpo2))));
                temp_avg.setText(Float.toString(Float.parseFloat(df.format(averageTemp))));

                NotifyPatient();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}


