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
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class TableActivity extends AppCompatActivity {

    @Inject
    AppDatabase mAppDatabase;
    MeasurementDao measurementDao;

    Spinner spinner;
    LinearLayout listView;
    public ArrayList<Measurement> myDataset;
    public ArrayList<String> dataToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        ((TestApplication)getApplication()).getComponent().inject(this);
        measurementDao = mAppDatabase.measurementDao();

        myDataset = new ArrayList<>();
        dataToShow = new ArrayList<>();
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
        myDataset.clear();

        Executor.IOThread(() -> myDataset.addAll(measurementDao.selectAllMeasurements()));
    }

    public void setDataToShow(int index){
        dataToShow.clear();
        switch (index){
            case 0:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).TEMP + " \u2103");
                }
                break;
            case 1:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).HUM + " %");
                }
                break;
            case 2:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).PRESS + " hPa");
                }
                break;
            case 3:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).BAT_V + " mV");
                }
                break;
            case 4:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).BAT_I + " mA");
                }
                break;
            case 5:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).SOLAR_V + " mV");
                }
                break;
            case 6:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).SOLAR_I + " mA");
                }
                break;
            case 7:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).NODE_U + " mV");
                }
                break;
            case 8:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(myDataset.get(i).NODE_I + " mA");
                }
                break;
        }
        setLayoutToShow();
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
}
