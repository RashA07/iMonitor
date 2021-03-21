package com.example.mymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mymonitor.provider.Clinic;
import com.google.gson.Gson;

public class ClinicProfile extends AppCompatActivity {

    private Clinic clinic;
    private TextView name;
    private TextView phoneNo;
    private TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_profile);

        Intent i = getIntent();
        clinic = new Gson().fromJson(i.getStringExtra("CLINIC_DATA"), Clinic.class);
        name = findViewById(R.id.text_clinic_name);
        phoneNo = findViewById(R.id.text_clinic_setphoneno);
        status = findViewById(R.id.text_clinic_setstatus);

        name.setText(clinic.getName());
        phoneNo.setText(clinic.getPhoneNo());
        status.setText(clinic.getStatusString());


    }
}
