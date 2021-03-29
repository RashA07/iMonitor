package com.example.mymonitor.provider;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mymonitor.Navigation;
import com.example.mymonitor.Patients;
import com.example.mymonitor.User;
import com.example.mymonitor.UserEnum;
import com.example.mymonitor.listener.DeviceCheckListener;
import com.example.mymonitor.listener.PatientListener;
import com.example.mymonitor.recyclerview.AllClinicsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.ClinicsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.DevicesRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.PatientsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FirebaseViewModel extends AndroidViewModel {

    DatabaseReference mRef;
    DatabaseReference mPatients;
    DatabaseReference mClinics;

    public FirebaseViewModel(@NonNull Application application) {
        super(application);
        mRef= FirebaseDatabase.getInstance().getReference();
        mPatients = mRef.child("Patients");
        mClinics = mRef.child("Clinics");
    }

    public DatabaseReference getReadings(String device){
        return mRef.child(device).child("data");
    }

    public void insertPatient(Patient patient) {
        mPatients.push().setValue(patient);
    }

    public void insertClinic(Clinic clinic) {
        mClinics.push().setValue(clinic);
    }


    public void loginUser(final String name, final String phoneNo, final Context c) {
        Query query = mPatients.orderByChild("details/name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot patient : dataSnapshot.getChildren()) {
                        if (Objects.equals(patient.child("details/phoneNo").getValue(String.class), phoneNo)){
                            new User(patient.getKey(), name, patient.child("device/name").getValue(String.class), UserEnum.PATIENT);
                            Intent intent = new Intent(c, Navigation.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                            c.startActivity(intent);
                        }
                    }
                } else {
                    Query query = mClinics.orderByChild("name").equalTo(name);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot clinic : dataSnapshot.getChildren()) {
                                    if (Objects.equals(clinic.child("phoneNo").getValue(String.class), phoneNo)){
                                        new User(clinic.getKey(), name, null, UserEnum.CLINIC);
                                        Intent intent = new Intent(c, Patients.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                                        c.startActivity(intent);
                                    }
                                }
                            } else {

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUserClinics(final ClinicsRecyclerViewAdapter adapter){

        // listen to user's clinic list
        mPatients.child(User.getKey()).child("clinics").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    final Map<String,String> data = (HashMap<String,String>) dataSnapshot.getValue();

                    for (final Map.Entry<String, String> entry : data.entrySet()){
                        // listen to clinics' details
                        mClinics.child(entry.getValue()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    System.out.println(dataSnapshot);
                                    Clinic clinic = dataSnapshot.getValue(Clinic.class);
                                    adapter.addData(entry.getValue(), clinic);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getUserDevices(final DevicesRecyclerViewAdapter adapter){
        mPatients.child(User.getKey()).child("device").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    List<HashMap<String, String>> data = (List<HashMap<String, String>>) dataSnapshot.getValue();
                    // not sure if its one device or more, for now is one
                    List<Device> data = new ArrayList<>();
                    data.add(dataSnapshot.getValue(Device.class));
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // for usertype = clinic
    public void getUserPatients(final PatientsRecyclerViewAdapter adapter){

        mClinics.child(User.getKey()).child("patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final Map<String,String> data = (HashMap<String,String>) dataSnapshot.getValue();

                    for (final Map.Entry<String, String> entry : data.entrySet()){
                        // listen to patients' details
                        mPatients.child(entry.getValue()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Patient patient = dataSnapshot.getValue(Patient.class);
                                    adapter.addData(entry.getValue(), patient);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllClinics(final AllClinicsRecyclerViewAdapter adapter){

        mClinics.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot clinic_dataSnapshot : dataSnapshot.getChildren()) {
                        System.out.println(clinic_dataSnapshot);

                        Clinic clinic = clinic_dataSnapshot.getValue(Clinic.class);
                        adapter.addData(clinic_dataSnapshot.getKey(), clinic);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // SUPER UGLY CODE (from getUserClinics above)
        mPatients.child(User.getKey()).child("clinics").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    final Map<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();

                    adapter.addSelectedData(new ArrayList<>(data.values()));
                    adapter.notifyDataSetChanged();


                }
            }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    // for usertype = patient
    public void addUserClinic(final String clinic_key){

        mPatients.child(User.getKey()).child("clinics").push().setValue(clinic_key);
        mClinics.child(clinic_key).child("patients").push().setValue(User.getKey());

    }

    public void getPatient(String patient_key, final PatientListener patient){
        mPatients.child(patient_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    patient.dataChanged(dataSnapshot.getValue(Patient.class));

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean checkDevice(String device_name, final DeviceCheckListener deviceCheck){
        System.out.println("DEVICE CHECK");

        mRef.child(device_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    deviceCheck.onDenied();
                } else{
                    deviceCheck.onApprove();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return false;

    }

    public void spamData(String device_name){

        class FakeData{
            private String id;
            private String date;
            private String time;
            private String heart_rate;
            private String temperature;
            private String blood_oxygen;

            public FakeData(String id, String date, String time, Float value) {
                this.id = id;
                this.date = date;
                this.time = time;
                this.heart_rate = Float.toString(value+90);
                this.temperature = Float.toString(value+35);
                this.blood_oxygen = Float.toString(value+90);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getHeart_rate() {
                return heart_rate;
            }

            public void setHeart_rate(String heart_rate) {
                this.heart_rate = heart_rate;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getBlood_oxygen() {
                return blood_oxygen;
            }

            public void setBlood_oxygen(String blood_oxygen) {
                this.blood_oxygen = blood_oxygen;
            }
        }

        float rdm;
        DecimalFormat df = new DecimalFormat("0.00");
        LocalDateTime date = LocalDateTime.now().minusDays(360);
        DateTimeFormatter date_only = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter time_only = DateTimeFormatter.ofPattern("HH:mm:ss");


        for (int i = 0; i < 500; i++) {

//        leftLimit + (new Random().nextFloat() * (rightLimit - leftLimit));
            rdm = 0 + (new Random().nextFloat() * 10);
            date = date.plusHours(1);
            mRef.child(device_name).child("data").push().setValue(new FakeData(device_name, date.format(date_only), date.format(time_only), Float.parseFloat(df.format(rdm))));


        }



    }

}
