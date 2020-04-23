package com.example.databasemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.databasemodule.Views.AddDataActivity;
import com.example.databasemodule.Views.DownloadDataActivity;



public class MainActivity extends AppCompatActivity {

    DbWorkerThread dbWorkerThread;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbWorkerThread = new DbWorkerThread("dbWorkerThread");
        dbWorkerThread.start();
        db = AppDatabase.getDatabase(this);
    }

    public void downloadData(View view) {
        Intent intent = new Intent(this, DownloadDataActivity.class);
        startActivity(intent);
    }

    public void addData(View view) {
        Intent intent = new Intent(this, AddDataActivity.class);
        startActivity(intent);
    }
}