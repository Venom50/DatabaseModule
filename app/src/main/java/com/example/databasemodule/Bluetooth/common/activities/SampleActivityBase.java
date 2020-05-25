package com.example.databasemodule.Bluetooth.common.activities;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.example.databasemodule.Bluetooth.common.logger.Log;
import com.example.databasemodule.Bluetooth.common.logger.LogWrapper;

public class SampleActivityBase extends FragmentActivity {

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeLogging();
    }

    public void initializeLogging() {
        final LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        Log.i(TAG, "Ready");
    }
}
