package com.example.databasemodule.Views.frontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.EnergyDao;
import com.example.databasemodule.Controllers.HUMDao;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Controllers.PRESSDao;
import com.example.databasemodule.Controllers.TEMPDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Energy;
import com.example.databasemodule.Models.HUM;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.Models.PRESS;
import com.example.databasemodule.Models.TEMP;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;
import com.example.databasemodule.Views.emulator.GetActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import kotlinx.coroutines.ExecutorCoroutineDispatcher;

public class ChartActivity extends AppCompatActivity {

    @Inject
    AppDatabase mAppDatabase;
    MeasurementDao measurementDao;
    TEMPDao tempDao;
    PRESSDao pressDao;
    HUMDao humDao;
    EnergyDao energyDao;

    private final long HOUR = 3600000L;
    public ArrayList<Measurement> myDataset;
    public ArrayList<TEMP> temps;
    public ArrayList<PRESS> presses;
    public ArrayList<HUM> hums;
    public ArrayList<Energy> energy;
    public ArrayList<Entry> dataToShow;
    LineChart lineChart;
    Spinner mSpinner;
    EditText dateFrom;
    EditText dateTo;
    DateTimePicker dateTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ((TestApplication)getApplication()).getComponent().inject(this);
        measurementDao = mAppDatabase.measurementDao();
        tempDao = mAppDatabase.tempDao();
        pressDao = mAppDatabase.pressDao();
        humDao = mAppDatabase.humDao();
        energyDao = mAppDatabase.energyDao();

        myDataset = new ArrayList<>();
        dataToShow = new ArrayList<>();
        temps = new ArrayList<>();
        presses = new ArrayList<>();
        hums = new ArrayList<>();
        energy = new ArrayList<>();
        dateTimePicker = new DateTimePicker();
        getAllData();

        lineChart = (LineChart)findViewById(R.id.lineChart);
        mSpinner = (Spinner)findViewById(R.id.chartSpinner);
        dateFrom = findViewById(R.id.dateFrom);
        dateFrom.setKeyListener(null);
        dateTo = findViewById(R.id.dateTo);
        dateTo.setKeyListener(null);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDataToShow((int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker.showDateTimeDialog(dateFrom, ChartActivity.this);
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker.showDateTimeDialog(dateTo, ChartActivity.this);
            }
        });
    }


    private void getAllData() {
        myDataset.clear();

        Executor.IOThread(() -> myDataset.addAll(measurementDao.selectAllMeasurements()));
        Executor.IOThread(() -> temps.addAll(tempDao.selectAllTemps()));
        Executor.IOThread(() -> hums.addAll(humDao.selectAllHums()));
        Executor.IOThread(() -> presses.addAll(pressDao.selectAllPresses()));
        Executor.IOThread(() -> energy.addAll(energyDao.selectAllEnergy()));
    }

    private void setDataToShow(int id) {
        dataToShow.clear();
        switch (id){
            case 0:
                for (TEMP temp : temps) {
                    setData((float)temp.value, temp.TIMESTAMP);
                }
                break;
            case 1:
                for (HUM hum : hums) {
                    setData((float)hum.value, hum.TIMESTAMP);
                }
                break;
            case 2:
                for (PRESS press : presses) {
                    setData((float)press.value, press.TIMESTAMP);
                }
                break;
            case 3:
                for (Energy energy : energy) {
                    setData((float)energy.BAT_V, energy.TIMESTAMP);
                }
                break;
            case 4:
                for (Energy energy : energy) {
                    setData((float)energy.BAT_I, energy.TIMESTAMP);
                }
                break;
            case 5:
                for (Energy energy : energy) {
                    setData((float)energy.SOLAR_V, energy.TIMESTAMP);
                }
                break;
            case 6:
                for (Energy energy : energy) {
                    setData((float)energy.SOLAR_I, energy.TIMESTAMP);
                }
                break;
            case 7:
                for (Energy energy : energy) {
                    setData((float)energy.NODE_U, energy.TIMESTAMP);
                }
                break;
            case 8:
                for (Energy energy : energy) {
                    setData((float)energy.NODE_I, energy.TIMESTAMP);
                }
                break;
        }
        setChartData();
    }

    private void setChartData(){
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss");
            @Override
            public String getFormattedValue(float value) {
                return mFormat.format(new Date((long)value));
            }
        });

        lineChart.clear();
        LineDataSet lineDataSet = new LineDataSet(dataToShow, mSpinner.getSelectedItem().toString());
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setValueTextSize(12f);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setBackgroundColor(Color.CYAN);
        lineChart.invalidate();
    }

    private void setData(float value, long timestamp){

        dataToShow.add(new Entry(timestamp, value));
    }
}
