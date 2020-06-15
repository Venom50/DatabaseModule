package com.example.databasemodule.Views.emulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.databasemodule.R;
import com.example.databasemodule.Views.frontEnd.DeleteActivityView;
import com.example.databasemodule.Views.frontEnd.GetActivityView;
import com.example.databasemodule.Views.frontEnd.ListActivityView;
import com.example.databasemodule.Views.frontEnd.SetActivityView;

public class EmulatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);
    }

    public void openGetActivity(View view) {
        final Intent intent = new Intent(this, GetActivityView.class);
        startActivity(intent);
    }

    public void openSetActivity(View view) {
        final Intent intent = new Intent(this, SetActivityView.class);
        startActivity(intent);
    }

    public void openListActivity(View view) {
        final Intent intent = new Intent(this, ListActivityView.class);
        startActivity(intent);
    }

    public void openDeleteActivity(View view) {
        final Intent intent = new Intent(this, DeleteActivityView.class);
        startActivity(intent);
    }
}