package com.example.mymonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymonitor.listener.DeviceCheckListener;
import com.example.mymonitor.provider.Clinic;
import com.example.mymonitor.provider.Device;
import com.example.mymonitor.provider.Patient;
import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.provider.PatientDetails;

import java.util.zip.DeflaterInputStream;

public class SignUp extends AppCompatActivity {

    private EditText name;
    private EditText phoneNo;
    private EditText device_name;
    private CheckBox isClinic;
    private TextView device_error;
    FirebaseViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        name = findViewById(R.id.signup_name);
        phoneNo = findViewById(R.id.signup_phoneno);
        isClinic = findViewById(R.id.signup_clinic);
        device_name = findViewById(R.id.signup_device_name);
        device_error = findViewById(R.id.signup_device_error);
        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
    }

    public void signUp(View view) {

        device_error.setText("");

        if (isClinic.isChecked()){
            mViewModel.insertClinic(new Clinic(name.getText().toString(), phoneNo.getText().toString(),true));
            finish();
        }
        else {

            mViewModel.checkDevice(device_name.getText().toString(), new DeviceCheckListener() {
                @Override
                public void onApprove() {
                    PatientDetails details = new PatientDetails(name.getText().toString(), phoneNo.getText().toString());
                    Device device = new Device(device_name.getText().toString(),"online");
                    mViewModel.insertPatient(new Patient(details, device));
                    finish();

                }

                @Override
                public void onDenied() {

                    device_error.setText("Device does not exist");

                }
            });


        }

    }
}
