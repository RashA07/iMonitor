package com.example.mymonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mymonitor.provider.Patient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.Objects;

public class Navigation extends AppCompatActivity {

    private BottomNavigationView navBar;
    private String userId;
    private String userDevice;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bar);



        if (User.getUsertype() == UserEnum.CLINIC){
            Patient p = new Gson().fromJson(getIntent().getStringExtra("PATIENT_DATA"), Patient.class);

            System.out.println("DETAILS");
            System.out.println(p.getDetails().getName());

            Objects.requireNonNull(getSupportActionBar()).setTitle(p.getDetails().getName());
            userId = getIntent().getStringExtra("PATIENT_KEY");
            userDevice = p.getDevice().getName();
        } else if (User.getUsertype() == UserEnum.PATIENT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(User.getName());

            userId = User.getKey();
            userDevice = User.getDevice();
        }



        loadFragment(new LiveData());


        navBar = findViewById(R.id.navigationView);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_liveData:  loadFragment(new LiveData()); break;
                    case R.id.navigation_analysis:  loadFragment(new Analysis()); break;
                    case R.id.navigation_devices:  loadFragment(new Devices()); break;
                    case R.id.navigation_clinics:  loadFragment(new Clinics()); break;
                }
                return true;
            }
        });

    }

    private void loadFragment(Fragment frag){
        System.out.println(userDevice);
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("userDevice", userDevice);

        frag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, frag, null)
                .commit();
    }
}
