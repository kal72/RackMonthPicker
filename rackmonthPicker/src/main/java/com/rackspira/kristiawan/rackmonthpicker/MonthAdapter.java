package com.rackspira.kristiawan.rackmonthpicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kristiawan on 9/8/2017.
 */

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthHolder> {

    private String[] months = new String[0];
    private OnSelectedListener listener;
    private int selectedItem = 0;
    private Context context;

    public MonthAdapter(Context context, OnSelectedListener listener) {
        this.context = context;
        this.listener = listener;
        months = new DateFormatSymbols(Locale.ENGLISH).getShortMonths();
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MonthHolder(LayoutInflater.from(context).inflate(R.layout.item_view_month, parent, false));
    }

    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {
        holder.textViewMonth.setText(months[position]);
        holder.itemView.setSelected(selectedItem == position ? true : false);
    }

    @Override
    public int getItemCount() {
        return months.length;
    }

    public void setLocale(Locale locale) {
        months = new DateFormatSymbols(locale).getShortMonths();
        notifyDataSetChanged();
    }

    public int getMonth() {
        return selectedItem + 1;
    }

    public int getStartDate() {
        return 1;
    }

    public int getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, selectedItem + 1);
        cal.set(Calendar.DAY_OF_MONTH, selectedItem + 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    class MonthHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewMonth;

        public MonthHolder(View itemView) {
            super(itemView);
            textViewMonth = (TextView) itemView.findViewById(R.id.text_month);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedItem);
            selectedItem = getAdapterPosition();
            notifyItemChanged(selectedItem);
            listener.onContentSelected();
        }
    }

    interface OnSelectedListener{
        void onContentSelected();
    }
}
