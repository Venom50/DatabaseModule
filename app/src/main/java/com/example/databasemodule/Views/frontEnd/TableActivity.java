package com.example.databasemodule.Views.frontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

public class TableActivity extends AppCompatActivity {

    private final int TEMP = 0;
    private final int HUM = 1;
    private final int PRESS = 2;
    private final int BAT_V = 3;
    private final int BAT_I = 4;
    private final int SOLAR_V = 5;
    private final int SOLAR_I = 6;
    private final int NODE_V = 7;
    private final int NODE_I = 8;

    @Inject
    AppDatabase mAppDatabase;
    EnergyDao energyDao;
    HUMDao humDao;
    PRESSDao pressDao;
    TEMPDao tempDao;

    Spinner spinner;
    LinearLayout listView;
    public ArrayList<String> dataToShow;
    public ArrayList<Energy> energy;
    public ArrayList<HUM> hums;
    public ArrayList<PRESS> presses;
    public ArrayList<TEMP> temps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        ((TestApplication)getApplication()).getComponent().inject(this);
        energyDao = mAppDatabase.energyDao();
        humDao = mAppDatabase.humDao();
        pressDao = mAppDatabase.pressDao();
        tempDao = mAppDatabase.tempDao();

        dataToShow = new ArrayList<>();
        energy = new ArrayList<>();
        hums = new ArrayList<>();
        presses = new ArrayList<>();
        temps = new ArrayList<>();
        getAllData();

        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (LinearLayout) findViewById(R.id.listView);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDataToShow((int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getAllData(){
        energy.clear();
        hums.clear();
        presses.clear();
        temps.clear();

        Executor.IOThread(() -> energy.addAll(energyDao.selectAllEnergy()));
        Executor.IOThread(() -> hums.addAll(humDao.selectAllHums()));
        Executor.IOThread(() -> presses.addAll(pressDao.selectAllPresses()));
        Executor.IOThread(() -> temps.addAll(tempDao.selectAllTemps()));
    }

    public void setDataToShow(int index){
        dataToShow.clear();
        switch (index){
            case TEMP:
                for(TEMP temp : temps){
                    dataToShow.add(roundDouble(temp.value) + "\u2103 " + timestampToString(temp.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case HUM:
                for(HUM hum : hums){
                    dataToShow.add(roundDouble(hum.value) + "% " + timestampToString(hum.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case PRESS:
                for(PRESS press : presses){
                    dataToShow.add(roundDouble(press.value) + "hPa " + timestampToString(press.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case BAT_V:
                for(Energy energy : energy){
                    dataToShow.add(roundDouble(energy.BAT_V) + "V " + timestampToString(energy.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case BAT_I:
                for(Energy energy : energy){
                    dataToShow.add(roundDouble(energy.BAT_I) + "mA " + timestampToString(energy.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case SOLAR_I:
                for(Energy energy : energy){
                    dataToShow.add(roundDouble(energy.SOLAR_I) + "mA " + timestampToString(energy.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case SOLAR_V:
                for(Energy energy : energy){
                    dataToShow.add(roundDouble(energy.SOLAR_V) + "V " + timestampToString(energy.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case NODE_V:
                for(Energy energy : energy){
                    dataToShow.add(roundDouble(energy.NODE_U) + "V " + timestampToString(energy.TIMESTAMP));
                }
                setLayoutToShow();
                break;
            case NODE_I:
                for(Energy energy : energy){
                    dataToShow.add(roundDouble(energy.NODE_I) + "mA " + timestampToString(energy.TIMESTAMP));
                }
                setLayoutToShow();
                break;
        }
    }

    private void setLayoutToShow(){
        listView.removeAllViews();
        for (String str : dataToShow) {
            TextView textView = new TextView(this);
            textView.setText(str);
            textView.setTextSize(20);
            listView.addView(textView);
        }
    }

    private String timestampToString(Long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    private String roundDouble(Double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
}
