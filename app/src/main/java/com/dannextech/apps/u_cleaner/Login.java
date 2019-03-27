package com.dannextech.apps.u_cleaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginUser(View v){
        Toast.makeText(Login.this,"Login Successfull", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Login.this,Home.class));
    }

    public void signUpUser(View view) {
        startActivity(new Intent(Login.this,CreateAccount.class));
    }
}
