package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class DownloadUsersActivity extends AppCompatActivity {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    UserDao userDao;

    public ArrayList<User> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_users);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication)getApplication()).getComponent().inject(this);
        userDao = mAppDatabase.userDao();

        myDataset = new ArrayList<User>();
        getAllUsersFromDatabase();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.configRecyclerView);
        UserListAdapter adapter = new UserListAdapter(this, myDataset);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getAllUsersFromDatabase() {
        myDataset.clear();
        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> myDataset.addAll(userDao.getAllUsers()));
    }
}
