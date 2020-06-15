package com.example.databasemodule.Views.frontEnd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class DateTimePicker {
    public void showDateTimeDialog(final EditText dateTimeIn, final Activity activity){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        dateTimeIn.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(activity, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        };
        new DatePickerDialog(activity, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public long getTimestamp(EditText textView){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date date = new Date();
        if(textView.getHint().toString().equals("Od") && textView.getText().toString().isEmpty()){
            return 0;
        } else if(textView.getHint().toString().equals("Do") && textView.getText().toString().isEmpty()){
            date.setTime(currentTimeMillis());
            return date.getTime();
        } else {
            try {
                date = simpleDateFormat.parse(textView.getText().toString());
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String timestampToString(Long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }
}
