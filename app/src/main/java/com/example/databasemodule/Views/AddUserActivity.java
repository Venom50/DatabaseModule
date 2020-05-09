package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class AddUserActivity extends AppCompatActivity {

    @Inject
    AppDatabase mAppDatabase;
    UserDao userDao;

    EditText loginEditText;
    EditText passwordEditText;
    CheckBox adminCheckBox;

    public ArrayList<User> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ((TestApplication)getApplication()).getComponent().inject(this);
        userDao = mAppDatabase.userDao();

        myDataset = new ArrayList();

        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        adminCheckBox = (CheckBox) findViewById(R.id.adminCheckBox);
    }

    public void addUserToDatabase(View view) {
        sendUserToDatabase();
        Toast.makeText(this, "Użytkownik został dodany do bazy", Toast.LENGTH_SHORT).show();
    }

    public void countUsers(View view) {
        countUsersFromDatabase();
        for(User user : myDataset) {
            Toast.makeText(this, user.login, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendUserToDatabase() {
        User user = new User();

        user.login = loginEditText.getText().toString();
        user.password = passwordEditText.getText().toString();
        user.isAdmin = adminCheckBox.isChecked();

        Executor.IOThread(() -> userDao.insertUsers(user));
    }

    public void countUsersFromDatabase() {
        myDataset.clear();

        Executor.IOThread(() -> myDataset.addAll(userDao.getAllUsers()));
    }
}