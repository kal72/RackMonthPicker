package com.rackspira.kristiawan.rackmonthpicker.util;

import com.rackspira.kristiawan.rackmonthpicker.R;

/**
 * Created by kristiawan on 31/12/16.
 */

public class MonthOfYear {
    private static String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static int icons[] = {
            R.drawable.radio_button_background_jan,
            R.drawable.radio_button_background_feb,
            R.drawable.radio_button_background_mar,
            R.drawable.radio_button_background_apr,
            R.drawable.radio_button_background_may,
            R.drawable.radio_button_background_jun,
            R.drawable.radio_button_background_jul,
            R.drawable.radio_button_background_aug,
            R.drawable.radio_button_background_sep,
            R.drawable.radio_button_background_oct,
            R.drawable.radio_button_background_nov,
            R.drawable.radio_button_background_dec
    };

    public static String getMonth(int idx) {
        return months[idx];
    }

    public static int getIcons(int idx) {
        return icons[idx];
    }
}
