package com.kal.rackmonthpicker;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private boolean isBuild = false;

    public RackMonthPicker(Context context) {
        this.context = context;
        builder = new Builder();
    }

    public RackMonthPicker(Activity activity) {
        this.context = activity;
        builder = new Builder();
    }

    public void show() {
        if (isBuild) {
            mAlertDialog.show();
            builder.setDefault();
        } else {
            builder.build();
            isBuild = true;
        }
    }

    /**
     * set action callback when positive button clicked
     *
     * @param dateMonthDialogListener
     * @return
     */
    public RackMonthPicker setPositiveButton(DateMonthDialogListener dateMonthDialogListener) {
        this.dateMonthDialogListener = dateMonthDialogListener;
        mPositiveButton.setOnClickListener(builder.positiveButtonClick());
        return this;
    }

    /**
     * set action callback when negative button clicked
     *
     * @param onCancelMonthDialogListener
     * @return
     */
    public RackMonthPicker setNegativeButton(OnCancelMonthDialogListener onCancelMonthDialogListener) {
        this.onCancelMonthDialogListener = onCancelMonthDialogListener;
        mNegativeButton.setOnClickListener(builder.negativeButtonClick());
        return this;
    }

    /**
     * change text positive button
     *
     * @param text
     * @return
     */
    public RackMonthPicker setPositiveText(String text) {
        mPositiveButton.setText(text);
        return this;
    }

    /**
     * change text negative button
     *
     * @param text
     * @return
     */
    public RackMonthPicker setNegativeText(String text) {
        mNegativeButton.setText(text);
        return this;
    }

    /**
     * set localization show month
     *
     * @param locale
     * @return
     */
    public RackMonthPicker setLocale(Locale locale) {
        builder.setLocale(locale);
        return this;
    }

    /**
     * change default selected month (1 - 12)
     *
     * @param month
     * @return
     */
    public RackMonthPicker setSelectedMonth(int month) {
        builder.setSelectedMonth(month);
        return this;
    }

    /**
     * change default selected year
     *
     * @param year
     * @return
     */
    public RackMonthPicker setSelectedYear(int year) {
        builder.setSelectedYear(year);
        return this;
    }

    /**
     * change color theme
     *
     * @param color
     * @return
     */
    public RackMonthPicker setColorTheme(int color) {
        builder.setColorTheme(color);
        return this;
    }

    public RackMonthPicker setMonthType(MonthType monthType) {
        builder.setMonthType(monthType);
        return this;
    }

    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private class Builder implements MonthAdapter.OnSelectedListener {

        private MonthAdapter monthAdapter;
        private TextView mTitleView;
        private TextView mYear;
        private int year;
        private int month;
        private AlertDialog.Builder alertBuilder;
        private View contentView;

        private Builder() {
            alertBuilder = new AlertDialog.Builder(context);

            contentView = LayoutInflater.from(context).inflate(R.layout.dialog_month_picker, null);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);

            mTitleView = (TextView) contentView.findViewById(R.id.title);
            mYear = (TextView) contentView.findViewById(R.id.text_year);

            ImageView next = (ImageView) contentView.findViewById(R.id.btn_next);
            next.setOnClickListener(nextButtonClick());

            ImageView previous = (ImageView) contentView.findViewById(R.id.btn_previous);
            previous.setOnClickListener(previousButtonClick());

            mPositiveButton = (Button) contentView.findViewById(R.id.btn_p);
            mNegativeButton = (Button) contentView.findViewById(R.id.btn_n);

            monthAdapter = new MonthAdapter(context, this);

            RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(monthAdapter);

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);

            setColorTheme(getColorByThemeAttr(context, android.R.attr.colorPrimary, R.color.color_primary));
        }

        private int getColorByThemeAttr(Context context, int attr, int defaultColor) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            boolean got = theme.resolveAttribute(attr, typedValue, true);
            return got ? typedValue.data : defaultColor;
        }

        //set default config
        private void setDefault() {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);

            monthAdapter.setSelectedItem(month);
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
            monthAdapter.notifyDataSetChanged();
            mYear.setText(year + "");
        }

        public void setLocale(Locale locale) {
            monthAdapter.setLocale(locale);
        }

        public void setSelectedMonth(int index) {
            monthAdapter.setSelectedItem(index);
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
        }

        public void setSelectedYear(int year) {
            this.year = year;
            mYear.setText(year + "");
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
        }

        public void setColorTheme(int color) {
            LinearLayout linearToolbar = (LinearLayout) contentView.findViewById(R.id.linear_toolbar);
            linearToolbar.setBackgroundColor(color);

            monthAdapter.setBackgroundMonth(color);
            mPositiveButton.setTextColor(color);
            mNegativeButton.setTextColor(color);
        }

        public void setMonthType(MonthType monthType){
            monthAdapter.setMonthType(monthType);
        }

        public void build() {
            monthAdapter.setSelectedItem(month);
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
            mYear.setText(year + "");

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
                    mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
                }
            };
        }

        public View.OnClickListener previousButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year--;
                    mYear.setText(year + "");
                    mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
                }
            };
        }

        public View.OnClickListener positiveButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateMonthDialogListener.onDateMonth(
                            monthAdapter.getMonth(),
                            monthAdapter.getStartDate(),
                            monthAdapter.getEndDate(),
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
        public void onContentSelected() {
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
        }
    }
}
