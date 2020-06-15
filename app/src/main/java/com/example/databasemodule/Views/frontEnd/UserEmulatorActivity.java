package com.example.databasemodule.Views.frontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.databasemodule.R;

public class UserEmulatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_emulator);
    }

    public void openGetActivity(View view) {
        final Intent intent = new Intent(this, GetActivityView.class);
        startActivity(intent);
    }

    public void openListActivity(View view) {
        final Intent intent = new Intent(this, ListActivityView.class);
        startActivity(intent);
    }
}