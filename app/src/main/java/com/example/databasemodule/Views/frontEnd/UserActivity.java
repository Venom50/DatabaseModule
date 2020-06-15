package com.example.databasemodule.Views.frontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.databasemodule.R;
import com.example.databasemodule.Views.AddDataActivity;
import com.example.databasemodule.Views.emulator.EmulatorActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public void downloadData(View view) {
        Intent intent = new Intent(this, DataMenuActivity.class);
        startActivity(intent);
    }

    public void openEmulatorActivity(final View view) {
        final Intent intent = new Intent(this, UserEmulatorActivity.class);
        startActivity(intent);
    }
}