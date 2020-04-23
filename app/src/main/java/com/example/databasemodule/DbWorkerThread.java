package com.example.databasemodule;

import android.os.Handler;
import android.os.HandlerThread;

public class DbWorkerThread extends HandlerThread {

    private Handler mWorkerHandler;

    public DbWorkerThread(String threadName) {
        super(threadName);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task) {
        mWorkerHandler.post(task);
    }
}
