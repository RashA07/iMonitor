package com.example.mymonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.mymonitor.listener.PatientListener;
import com.example.mymonitor.provider.FirebaseViewModel;
import com.example.mymonitor.provider.Patient;
import com.example.mymonitor.provider.PatientDetails;
import com.google.gson.Gson;

public class PatientProfile extends Fragment {

    private TextView name;
    private TextView phoneNo;
    private String userId;
    private FirebaseViewModel mViewModel;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userId  = bundle.getString("userId");
        }

        name = view.findViewById(R.id.text_patient_name);
        phoneNo = view.findViewById(R.id.text_patient_setphoneno);

        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        mViewModel.getPatient(userId, new PatientListener() {
            @Override
            public void dataChanged(Patient patient) {
                PatientDetails details = patient.getDetails();
                name.setText(details.getName());
                phoneNo.setText(details.getPhoneNo());

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_profile, container, false);
    }
}
