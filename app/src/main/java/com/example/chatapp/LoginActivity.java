package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        onLoginButtonClick();
    }

    private void onLoginButtonClick() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                loginUser(email,password);
            }
        });

    }

    private void loginUser(String email,String password) {
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Please Wait...");
        pd.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
