package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button register,login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        onRegisterButtonClick();
        onLoginButtonClick();
    }

    @Override
    protected void onStart() {
        super.onStart();

       if(mAuth.getCurrentUser() != null){
           startActivity(new Intent(StartActivity.this,MainActivity.class));
           finish();
       }
    }

    private void onRegisterButtonClick() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }

    private void onLoginButtonClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
