package com.example.databasemodule.Views.frontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.databasemodule.R;

public class DataMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_menu);
    }


    public void openTabels(View view) {
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
    }

    public void openCharts(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }

    public void oneRaports(View view) {
        Intent intent = new Intent(this, RaportAcitivity.class);
        startActivity(intent);
    }


}
