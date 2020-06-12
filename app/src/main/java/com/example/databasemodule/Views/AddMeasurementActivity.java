package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.EnergyDao;
import com.example.databasemodule.Controllers.HUMDao;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Controllers.PRESSDao;
import com.example.databasemodule.Controllers.TEMPDao;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Energy;
import com.example.databasemodule.Models.HUM;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.Models.PRESS;
import com.example.databasemodule.Models.TEMP;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class AddMeasurementActivity extends AppCompatActivity {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    MeasurementDao measurementDao;
    TEMPDao tempDao;
    EnergyDao energyDao;
    HUMDao humDao;
    PRESSDao pressDao;

    EditText tempEditText;
    EditText humEditText;
    EditText pressEditText;
    EditText batVEditText;
    EditText batIEditText;
    EditText solarVEditText;
    EditText solarIEditText;
    EditText nodeUEditText;
    EditText nodeIEditText;
    EditText timestampEditText;

    public ArrayList<User> myDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication)getApplication()).getComponent().inject(this);
        measurementDao = mAppDatabase.measurementDao();
        tempDao = mAppDatabase.tempDao();
        energyDao = mAppDatabase.energyDao();
        humDao = mAppDatabase.humDao();
        pressDao = mAppDatabase.pressDao();

        tempEditText = findViewById(R.id.tempEditText);
        humEditText = findViewById(R.id.humEditText);
        pressEditText = findViewById(R.id.pressEditText);
        batVEditText = findViewById(R.id.batVEditText);
        batIEditText = findViewById(R.id.batIEditText);
        solarVEditText = findViewById(R.id.solarVEditText);
        solarIEditText = findViewById(R.id.solarIEditText);
        nodeUEditText = findViewById(R.id.nodeUEditText);
        nodeIEditText = findViewById(R.id.nodeIEditText);
        timestampEditText = findViewById(R.id.timestampEditText);

        myDataset = new ArrayList();


    }

    public void addMesurementToDatabase(View view) {
        sendMeasurementToDatabase();
        Toast.makeText(this, "Dodano zmienne pomiarowe do bazy danych", Toast.LENGTH_SHORT).show();
    }

    public void sendMeasurementToDatabase() {
        Measurement measurement = new Measurement();
        Energy energy = new Energy();
        TEMP temp = new TEMP();
        HUM hum = new HUM();
        PRESS press = new PRESS();

        measurement.TEMP = Double.parseDouble(tempEditText.getText().toString());
        measurement.HUM = Double.parseDouble(humEditText.getText().toString());
        measurement.PRESS = Double.parseDouble(pressEditText.getText().toString());
        measurement.BAT_V = Double.parseDouble(batVEditText.getText().toString());
        measurement.BAT_I = Double.parseDouble(batIEditText.getText().toString());
        measurement.SOLAR_V = Double.parseDouble(solarVEditText.getText().toString());
        measurement.SOLAR_I = Double.parseDouble(solarIEditText.getText().toString());
        measurement.NODE_U = Double.parseDouble(nodeUEditText.getText().toString());
        measurement.NODE_I = Double.parseDouble(nodeIEditText.getText().toString());

        energy.BAT_V = Double.parseDouble(batVEditText.getText().toString());
        energy.BAT_I = Double.parseDouble(batIEditText.getText().toString());
        energy.SOLAR_V = Double.parseDouble(solarVEditText.getText().toString());
        energy.SOLAR_I = Double.parseDouble(solarIEditText.getText().toString());
        energy.NODE_U = Double.parseDouble(nodeUEditText.getText().toString());
        energy.NODE_I = Double.parseDouble(nodeIEditText.getText().toString());
        energy.TIMESTAMP = Long.parseLong(timestampEditText.getText().toString());
        temp.value = Double.parseDouble(tempEditText.getText().toString());
        temp.TIMESTAMP = Long.parseLong(timestampEditText.getText().toString());
        hum.value = Double.parseDouble(humEditText.getText().toString());
        hum.TIMESTAMP = Long.parseLong(timestampEditText.getText().toString());
        press.value = Double.parseDouble(pressEditText.getText().toString());
        press.TIMESTAMP = Long.parseLong(timestampEditText.getText().toString());


        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> measurementDao.insertAllMeasurements(measurement));
        Executor.IOThread(() -> energyDao.insertAllEnergy(energy));
        Executor.IOThread(() -> tempDao.insertAllTemps(temp));
        Executor.IOThread(() -> humDao.insertAllHums(hum));
        Executor.IOThread(() -> pressDao.insertAllPresses(press));
    }
}