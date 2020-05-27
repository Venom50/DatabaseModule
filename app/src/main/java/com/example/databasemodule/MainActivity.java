package com.example.databasemodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.databasemodule.Views.AddDataActivity;
import com.example.databasemodule.Views.emulator.EmulatorActivity;
import com.example.databasemodule.Views.frontEnd.DataMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void downloadData(View view) {
        Intent intent = new Intent(this, DataMenuActivity.class);
        startActivity(intent);
    }

    public void addData(View view) {
        Intent intent = new Intent(this, AddDataActivity.class);
        startActivity(intent);
    }

    public void openEmulatorActivity(final View view) {
        final Intent intent = new Intent(this, EmulatorActivity.class);
        startActivity(intent);
    }
}