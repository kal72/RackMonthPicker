package com.rackspira.kristiawan.monthpickerdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.rackspira.kristiawan.rackmonthpicker.RackMonthPicker;
import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RackMonthPicker rackMonthPicker = new RackMonthPicker(this)
                .setLocale(Locale.ENGLISH)
                .setSelectedMonth(4)
                .setSelectedYear(2018)
                .setColorTheme(R.color.colorPrimary)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        System.out.println(month);
                        System.out.println(startDate);
                        System.out.println(endDate);
                        System.out.println(year);
                        System.out.println(monthLabel);
                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
//        new RackMonthPicker(this)
//                .setPositiveButton(new DateMonthDialogListener() {
//                    @Override
//                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
//
//                    }
//                })
//                .setNegativeButton(new OnCancelMonthDialogListener() {
//                    @Override
//                    public void onCancel(AlertDialog dialog) {
//
//                    }
//                }).show();

        Button button = (Button) findViewById(R.id.btn_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rackMonthPicker.show();
            }
        });
    }
}
