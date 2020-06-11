package com.example.databasemodule.Views.frontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import javax.inject.Inject;

public class ChartActivity extends AppCompatActivity {

    @Inject
    AppDatabase mAppDatabase;
    MeasurementDao measurementDao;

    public ArrayList<Measurement> myDataset;
    public ArrayList<Entry> dataToShow;
    LineChart lineChart;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ((TestApplication)getApplication()).getComponent().inject(this);
        measurementDao = mAppDatabase.measurementDao();

        myDataset = new ArrayList<>();
        dataToShow = new ArrayList<>();
        getAllData();

        lineChart = (LineChart)findViewById(R.id.lineChart);
        mSpinner = (Spinner)findViewById(R.id.chartSpinner);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDataToShow((int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getAllData() {
        myDataset.clear();

        Executor.IOThread(() -> myDataset.addAll(measurementDao.selectAllMeasurements()));
    }

    private void setDataToShow(int id) {
        dataToShow.clear();
        switch (id){
            case 0:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry((float)i, (float)myDataset.get(i).TEMP));
                }
                break;
            case 1:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).HUM));
                }
                break;
            case 2:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).PRESS));
                }
                break;
            case 3:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).BAT_V));
                }
                break;
            case 4:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).BAT_I));
                }
                break;
            case 5:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).SOLAR_V));
                }
                break;
            case 6:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).SOLAR_I));
                }
                break;
            case 7:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).NODE_U));
                }
                break;
            case 8:
                for (int i = 0; i < myDataset.size(); i++) {
                    dataToShow.add(new Entry(i, (float)myDataset.get(i).NODE_I));
                }
                break;
        }
        setChartData();
    }

    private void setChartData(){
        lineChart.clear();
        LineDataSet lineDataSet = new LineDataSet(dataToShow, "label");
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setValueTextSize(12f);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setBackgroundColor(Color.CYAN);
        lineChart.invalidate();
    }
}
