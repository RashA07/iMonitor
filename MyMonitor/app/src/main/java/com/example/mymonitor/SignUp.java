package com.example.mymonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.mymonitor.provider.Clinic;
import com.example.mymonitor.provider.Device;
import com.example.mymonitor.provider.Patient;
import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.provider.PatientDetails;

public class SignUp extends AppCompatActivity {

    private EditText name;
    private EditText phoneNo;
    private EditText device_name;
    private CheckBox isClinic;
    FirebaseViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        name = findViewById(R.id.signup_name);
        phoneNo = findViewById(R.id.signup_phoneno);
        isClinic = findViewById(R.id.signup_clinic);
        device_name = findViewById(R.id.signup_device_name);
        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
    }

    public void signUp(View view) {

        if (isClinic.isChecked()){
            mViewModel.insertClinic(new Clinic(name.getText().toString(), phoneNo.getText().toString(),true));
        }
        else {
            PatientDetails details = new PatientDetails(name.getText().toString(), phoneNo.getText().toString());
            Device device = new Device(device_name.getText().toString(),"online");
            mViewModel.insertPatient(new Patient(details, device));
        }
        finish();
    }
}
