package com.example.databasemodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.databasemodule.Views.AddDataActivity;
import com.example.databasemodule.Views.emulator.EmulatorActivity;
import com.example.databasemodule.Views.frontEnd.AdminActivity;
import com.example.databasemodule.Views.frontEnd.DataMenuActivity;

public class MainActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin = findViewById(R.id.login);
        mPassword = findViewById(R.id.password);
    }

    public void login(View view) {
        //if admin start AdminActivity
        //if user start UserActivity
        //if wrongLoginData()
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    private void wrongLoginData(){
        Toast.makeText(this, "Błędne dane logowania", Toast.LENGTH_SHORT).show();
    }
}