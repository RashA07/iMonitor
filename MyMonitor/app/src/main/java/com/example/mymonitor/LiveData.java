package com.example.mymonitor;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.mymonitor.provider.Reading;
import com.example.mymonitor.provider.FirebaseViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DecimalFormat;
import java.util.HashMap;


public class LiveData extends Fragment {

    private static HashMap<String, Reading> readingHashMap = new HashMap<>();
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

    private static DecimalFormat df = new DecimalFormat("0.00");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_data, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        notificationManager = NotificationManagerCompat.from(this.getContext());
        super.onActivityCreated(savedInstanceState);

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
            }
        });

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
//            ADJUST THIS
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

        final DatabaseReference ref = database.getReference(userDevice);

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

//              Update to the last recorded value on firebase
                snapshot.getRef().orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String heartRate;
                        String Spo2;
                        String Temp;
//                        String Date;
//                        String Time;
//                        String ID;
//                        String key;
                        for (DataSnapshot item : snapshot.getChildren()) {

                            heartRate = item.child("heart_rate").getValue().toString();
                            Spo2 = item.child("blood_oxygen").getValue().toString();
                            Temp = item.child("temperature").getValue().toString();
//                            Date = item.child("date").getValue().toString();
//                            Time = item.child("time").getValue().toString();
//                            ID = item.child("id").getValue().toString();
                            heart_reading.setText(heartRate);
                            sp_reading.setText(Spo2);
                            temp_reading.setText(Temp);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                keys = readingHashMap.keySet().toArray();


                if (readingHashMap.isEmpty()){
                    heart_avg.setText("No data");
                    sp_avg.setText("No data");
                    temp_avg.setText("No data");
                } else {

                    for (int i = 0; i < readingHashMap.size(); i++) {

                        Reading readings = readingHashMap.get(keys[i]);
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


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}


