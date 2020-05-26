package com.example.databasemodule.Views.emulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.databasemodule.R;

public class EmulatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);
    }

    public void openGetActivity(View view) {
        final Intent intent = new Intent(this, GetActivity.class);
        startActivity(intent);
    }

    public void openSetActivity(View view) {
        final Intent intent = new Intent(this, SetActivity.class);
        startActivity(intent);
    }

    public void openListActivity(View view) {
        final Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void openDeleteActivity(View view) {
        final Intent intent = new Intent(this, DeleteActivity.class);
        startActivity(intent);
    }
}