package com.example.mymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mymonitor.provider.PatientDetails;
import com.google.gson.Gson;

public class PatientProfile extends AppCompatActivity {

    private PatientDetails details;
    private TextView name;
    private TextView phoneNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent i = getIntent();
        details = new Gson().fromJson(i.getStringExtra("PATIENT_DATA"), PatientDetails.class);
        name = findViewById(R.id.text_patient_name);
        phoneNo = findViewById(R.id.text_patient_setphoneno);

        name.setText(details.getName());
        phoneNo.setText(details.getPhoneNo());


    }
}
