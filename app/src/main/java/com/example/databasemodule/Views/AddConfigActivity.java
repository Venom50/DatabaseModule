package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.ConfigDao;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Config;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import javax.inject.Inject;

public class AddConfigActivity extends AppCompatActivity {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    ConfigDao configDao;

    EditText tempFEditText;
    EditText humFEditText;
    EditText pressFEditText;
    EditText energyFEditText;
    EditText timestampEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_config);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication)getApplication()).getComponent().inject(this);
        configDao = mAppDatabase.configDao();

        tempFEditText = findViewById(R.id.tempFEditText);
        humFEditText = findViewById(R.id.humFEditText);
        pressFEditText = findViewById(R.id.pressFEditText);
        energyFEditText = findViewById(R.id.energyFEditText);
        timestampEditText = findViewById(R.id.timestampEditText);
    }

    public void addConfigToDatabase(View view) {
        sendConfigToDatabase();
        Toast.makeText(this, "Dane konfiguracyjne zostały dodane do bazy", Toast.LENGTH_SHORT).show();
    }

    public void sendConfigToDatabase() {
        Config config = new Config();

        config.TEMP_F = Double.parseDouble(tempFEditText.getText().toString());
        config.HUM_F = Double.parseDouble(humFEditText.getText().toString());
        config.PRESS_F = Double.parseDouble(pressFEditText.getText().toString());
        config.ENERGY_F = Double.parseDouble(energyFEditText.getText().toString());
        config.TIMESTAMP = timestampEditText.getText().toString();

        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> configDao.insertAllConfigs(config));
    }
}