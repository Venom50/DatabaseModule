package com.example.databasemodule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Use Executor as following way.
Executor.IOThread(() -> userDao.insertUsers(new User());
 */
public class Executor {
    public static void IOThread(Runnable t) {
        ExecutorService IO_EXECUTOR = Executors.newSingleThreadExecutor();
        IO_EXECUTOR.execute(t);
    }
}
