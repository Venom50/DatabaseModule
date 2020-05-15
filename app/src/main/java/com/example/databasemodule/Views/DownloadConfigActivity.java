package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.ConfigDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Config;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class DownloadConfigActivity extends AppCompatActivity {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    ConfigDao configDao;

    ArrayList<Config> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_config);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication)getApplication()).getComponent().inject(this);
        configDao = mAppDatabase.configDao();

        myDataset = new ArrayList<>();
        getAllConfigFromDatabase();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.configRecyclerView);
        ConfigListAdapter adapter = new ConfigListAdapter(this, myDataset);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getAllConfigFromDatabase() {
        myDataset.clear();

        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> myDataset.addAll(configDao.selectAllConfigs()));
    }
}