package com.example.mbhatt1.em2arcade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    public TextView title;
    public Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        title = findViewById(R.id.textView);
    }

    public void login(View view){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void register(View view){
        Intent registerIntent = new Intent(this, LoginActivity.class);
        startActivity(registerIntent);
    }

}
