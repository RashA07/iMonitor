package com.example.mymonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mymonitor.provider.FirebaseViewModel;

public class SignIn extends AppCompatActivity {

    private EditText name;
    private EditText phoneNo;
    FirebaseViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        name = findViewById(R.id.signin_name);
        phoneNo = findViewById(R.id.signin_phoneno);

        mViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
    }

    public void signIn(View view){

        String name_string = name.getText().toString();
        String phoneNo_string = phoneNo.getText().toString();

        mViewModel.loginUser(name_string, phoneNo_string,this);
//
//        if (mViewModel.loginUser(name_string, phoneNo_string)){
//
//            Intent intent = new Intent(this, Navigation.class);
//            startActivity(intent);
//        }

    }

    public void signUp(View view) {

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
