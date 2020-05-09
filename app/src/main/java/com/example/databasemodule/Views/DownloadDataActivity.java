package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.databasemodule.R;

public class DownloadDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data);
    }

    public void downloadUsers(View view) {
        Intent intent = new Intent(this, DownloadUsersActivity.class);
        startActivity(intent);
    }

    public void downloadMeasurements(View view) {
        Intent intent = new Intent(this, DownloadMeasurementsActivity.class);
        startActivity(intent);
    }

    public void downloadConfig(View view) {
        Intent intent = new Intent(this, DownloadConfigActivity.class);
        startActivity(intent);
    }
}