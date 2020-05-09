package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.util.Measure;
import android.os.Bundle;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class DownloadMeasurementsActivity extends AppCompatActivity {

    @Inject
    AppDatabase mAppDatabase;
    MeasurementDao measurementDao;

    public ArrayList<Measurement> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_measurements);

        ((TestApplication)getApplication()).getComponent().inject(this);
        measurementDao = mAppDatabase.measurementDao();

        myDataset = new ArrayList<>();
        getAllMeasurementsFromDatabase();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.measurementRecyclerView);
        MeasurementListAdapter adapter = new MeasurementListAdapter(this, myDataset);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getAllMeasurementsFromDatabase() {
        myDataset.clear();
        Executor.IOThread(() -> myDataset.addAll(measurementDao.selectAllMeasurements()));
    }
}