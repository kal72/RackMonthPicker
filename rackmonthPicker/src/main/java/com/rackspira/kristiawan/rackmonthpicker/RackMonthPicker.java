package com.rackspira.kristiawan.rackmonthpicker;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.MonthButtonListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.util.MonthOfYear;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristiawan on 31/12/16.
 */

public class RackMonthPicker {

    private AlertDialog mAlertDialog;
    private RackMonthPicker.Builder builder;
    private Context context;
    private Button mPositiveButton;
    private Button mNegativeButton;
    private DateMonthDialogListener dateMonthDialogListener;
    private OnCancelMonthDialogListener onCancelMonthDialogListener;
    private List<MonthRadioButton> monthRadioButtonList;
    private boolean isBuild = false;

    public RackMonthPicker(Context context) {
        this.context = context;
        monthRadioButtonList = new ArrayList<>();
        builder = new Builder();
    }

    public void show() {
        if (isBuild) {
            mAlertDialog.show();
        } else {
            builder.build();
            isBuild = true;
        }
    }

    public RackMonthPicker setPositiveButton(DateMonthDialogListener dateMonthDialogListener) {
        this.dateMonthDialogListener = dateMonthDialogListener;
        mPositiveButton.setOnClickListener(builder.positiveButtonClick());
        return this;
    }

    public RackMonthPicker setNegativeButton(OnCancelMonthDialogListener onCancelMonthDialogListener) {
        this.onCancelMonthDialogListener = onCancelMonthDialogListener;
        mNegativeButton.setOnClickListener(builder.negativeButtonClick());
        return this;
    }

    public RackMonthPicker setPositiveText(String text){
        mPositiveButton.setText(text);
        return this;
    }

    public RackMonthPicker setNegativeText(String text){
        mNegativeButton.setText(text);
        return this;
    }

    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private class Builder implements MonthButtonListener {

        private MonthRadioButton monthRadioButton;
        private TextView mTitleView;
        private TextView mYear;
        private int year = 2017;
        private AlertDialog.Builder alertBuilder;
        private View contentView;

        private Builder() {
            alertBuilder = new AlertDialog.Builder(context);

            contentView = LayoutInflater.from(context).inflate(R.layout.date_month_dialog_view, null);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);

            mTitleView = (TextView) contentView.findViewById(R.id.title);
            mYear = (TextView) contentView.findViewById(R.id.text_year);
            mYear.setText(year + "");

            Button next = (Button) contentView.findViewById(R.id.btn_next);
            next.setOnClickListener(nextButtonClick());

            Button previous = (Button) contentView.findViewById(R.id.btn_previous);
            previous.setOnClickListener(previousButtonClick());

            mPositiveButton = (Button) contentView.findViewById(R.id.btn_p);
            mNegativeButton = (Button) contentView.findViewById(R.id.btn_n);

            GridLayout gridLayout = (GridLayout) contentView.findViewById(R.id.radiogroup);
            for (int i = 1; i < 13; i++) {
                MonthRadioButton radioButton = new MonthRadioButton(context);
                radioButton.setIdMonth(i);
                radioButton.setMonth(MonthOfYear.getMonth(i - 1));
                radioButton.setButtonDrawable(MonthOfYear.getIcons(i - 1));
                if (i == 1) {
                    monthRadioButton = radioButton;
                    radioButton.setChecked(true);
                    mTitleView.setText(MonthOfYear.getMonth(i - 1) + ", " + year);
                }

                if (i == 2) {
                    radioButton.setEndDate(28);
                }

                if (i == 4 || i == 6 || i == 9 || i == 11) {
                    radioButton.setEndDate(30);
                }

                radioButton.setMonthListener(this, radioButton);
//                monthRadioButton = radioButton;
                monthRadioButtonList.add(radioButton);
                gridLayout.addView(radioButton);
            }
        }

        public void build() {
            mAlertDialog = alertBuilder.create();
            mAlertDialog.show();
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);
            mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.material_dialog_window);
            mAlertDialog.getWindow().setContentView(contentView);
        }

        public View.OnClickListener nextButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year++;
                    mYear.setText(year + "");
                    mTitleView.setText(monthRadioButton.getMonth() + ", " + year);
                }
            };
        }

        public View.OnClickListener previousButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year--;
                    mYear.setText(year + "");
                    mTitleView.setText(monthRadioButton.getMonth() + ", " + year);
                }
            };
        }

        public View.OnClickListener positiveButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateMonthDialogListener.onDateMonth(
                            monthRadioButton.getIdMonth(),
                            monthRadioButton.getStartDate(),
                            monthRadioButton.getEndDate(),
                            year, mTitleView.getText().toString());

                    mAlertDialog.dismiss();
                }
            };
        }

        public View.OnClickListener negativeButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCancelMonthDialogListener.onCancel(mAlertDialog);
                }
            };
        }

        @Override
        public void monthClick(MonthRadioButton objectMonth) {
            mTitleView.setText(objectMonth.getMonth() + ", " + year);
            monthRadioButton = objectMonth;
            for (MonthRadioButton radioButton : monthRadioButtonList) {
                if (radioButton != objectMonth) {
                    radioButton.setChecked(false);
                }
            }
        }
    }
}
