package com.example.databasemodule.Bluetooth.common.logger;

public interface LogNode {

    void println(int priority, String tag, String msg, Throwable tr);

}
