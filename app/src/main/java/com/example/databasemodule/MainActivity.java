package com.example.databasemodule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.Tools.RSA;
import com.example.databasemodule.Views.AddDataActivity;
import com.example.databasemodule.Views.emulator.EmulatorActivity;
import com.example.databasemodule.Views.frontEnd.AdminActivity;
import com.example.databasemodule.Views.frontEnd.DataMenuActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;
    @Inject
    AppDatabase mAppDatabase;
    UserDao userDao;

    SharedPreferences prefs = null;
    RSA rsa = new RSA();

    EditText loginField;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.example.databasemodule", MODE_PRIVATE);
        ((TestApplication) getApplication()).getComponent().inject(this);
        userDao = mAppDatabase.userDao();
        loginField = (EditText) findViewById(R.id.LoginField);
        passwordField = (EditText) findViewById(R.id.PasswordField);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Sprawdzenie czy aplikacja uruchamiana jest pierwszy raz
        if (prefs.getBoolean("firstrun", true)) {

            //Dodaj domyslnego użytkownika na start aplikacji
            User Admin = new User();
            Admin.login = "admin";

            //przykład użycia RSA do zaszyfrowania hasła
            Admin.password = rsa.encryptRSAToString("admin");

            Admin.isAdmin = true;

            Executor.IOThread(() -> userDao.insertUsers(Admin));
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    public void login(View view) {
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();
        ArrayList<User> foundUsers = new ArrayList<>();
        Executor.IOThread(() -> foundUsers.add(userDao.findUser(login, rsa.encryptRSAToString(password))));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (foundUsers.get(0) == null) {
            Toast.makeText(this, "Wrong login or password", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }
    }
}