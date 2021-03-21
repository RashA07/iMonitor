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
import com.example.mymonitor.recyclerview.ClinicsRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.DevicesRecyclerViewAdapter;
import com.example.mymonitor.recyclerview.PatientsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FirebaseViewModel extends AndroidViewModel {

    DatabaseReference mRef ;
    DatabaseReference mPatients ;
    DatabaseReference mClinics;

    public FirebaseViewModel(@NonNull Application application) {
        super(application);
        mRef= FirebaseDatabase.getInstance().getReference();
        mPatients = mRef.child("Patients");
        mClinics = mRef.child("Clinics");

//        mUser.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });
    }

    public DatabaseReference getReadings(String device){
        return mRef.child(device);
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

                    final List<String> data = (List<String>) dataSnapshot.getValue();

                    for (final String key: data){
                        // listen to clinics' details
                        mClinics.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Clinic clinic = dataSnapshot.getValue(Clinic.class);
                                    adapter.addData(key, clinic);
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

    public void getUserPatients(final PatientsRecyclerViewAdapter adapter){

        mClinics.child(User.getKey()).child("patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final List<String> data = (List<String>) dataSnapshot.getValue();

                    for (final String key: data){
                        // listen to patients' details
                        mPatients.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Patient patient = dataSnapshot.getValue(Patient.class);
                                    adapter.addData(key, patient);
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

}
