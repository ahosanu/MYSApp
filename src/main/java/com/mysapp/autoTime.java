package com.mysapp;

import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class autoTime extends Thread{
    private Text Time;
    private Text AmPm;
    private Text Dates;

    public autoTime(Text time, Text amPm, Text date) {
        Time = time;
        AmPm = amPm;
        Dates = date;
        this.start();
    }

    @Override
    public void run() {
        DateFormat TimeFormat = new SimpleDateFormat("hh:mm:ss");
        DateFormat AmPmFormat = new SimpleDateFormat("a");
        DateFormat DatesFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date;
        while (true){
            date = new Date();
            Dates.setText(DatesFormat.format(date));
            Time.setText(TimeFormat.format(date));
            AmPm.setText(AmPmFormat.format(date));

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
