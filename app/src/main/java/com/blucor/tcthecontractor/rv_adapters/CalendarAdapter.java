package com.blucor.tcthecontractor.rv_adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.custom.DateManager;
import com.blucor.tcthecontractor.custom.OnCalenderClick;
import com.blucor.tcthecontractor.models.HolidayModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarAdapter extends BaseAdapter {
    private List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;
    private ArrayList<HolidayModel> selectedDays;

    //After expanding the custom cell, define Wiget here
    private static class ViewHolder {
        public TextView dateText;
        public TextView explainationText;
    }

    public CalendarAdapter(Context context,ArrayList<HolidayModel> selectedDays){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
        this.selectedDays = selectedDays;
    }

    @Override
    public int getCount() {
        return dateArray.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null);
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById(R.id.dateText);
            holder.explainationText = convertView.findViewById(R.id.explainationText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        //Specify cell size
        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeeks() ) / mDateManager.getWeeks());
        convertView.setLayoutParams(params);

        //Display only the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
        holder.dateText.setText(dateFormat.format(dateArray.get(position)));

        //Gray out cells other than this month
        if (mDateManager.isCurrentMonth(dateArray.get(position))){
            int present = isPresentInSelectedDays(dateArray.get(position));
            if( present != 0) {
                convertView.setBackgroundColor(Color.CYAN);
                convertView.setClickable(false);
                holder.dateText.setClickable(false);
                holder.explainationText.setText(""+selectedDays.get(present).note);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
                //convertView.setClickable(true);
                holder.explainationText.setText("");
            }
        } else {
            convertView.setBackgroundColor(Color.LTGRAY);
            holder.explainationText.setText("");
            //convertView.setClickable(true);
        }

        //Sunday to red, Saturday to blue
        int colorId;
        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;

            default:
                colorId = Color.BLACK;
                break;
        }
        holder.dateText.setTextColor(colorId);

       /* Date selectedDate = dateArray.get(position);
        if (selectedDays.contains(selectedDate)) {
            holder.dateText.setBackgroundColor(Color.CYAN);
            holder.dateText.setTextColor(Color.WHITE);
        }*/

        return convertView;
    }

    private int isPresentInSelectedDays(Date selectedDate) {
        Calendar cal_selected_date = Calendar.getInstance();
        cal_selected_date.setTimeInMillis(selectedDate.getTime());

        if(selectedDays == null) {
            return 0;
        } else if(selectedDays.size() <= 0) {
            return 0;
        } else {
            int isPresent = 0;
            for (int i = 0; i < selectedDays.size(); i++) {
                String str = selectedDays.get(i).date;
                Date date = null;

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal_date = Calendar.getInstance();
                assert date != null;
                cal_date.setTimeInMillis(date.getTime());

                if (cal_date.get(Calendar.DAY_OF_MONTH) == cal_selected_date.get(Calendar.DAY_OF_MONTH) && cal_date.get(Calendar.MONTH) == cal_selected_date.get(Calendar.MONTH) && cal_date.get(Calendar.YEAR) == cal_selected_date.get(Calendar.YEAR)) {
                    isPresent = i;
                }
            }

            return isPresent;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Date getItem(int position) {
        return dateArray.get(position);
    }

    //Get display month
    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("MMM yyyy", Locale.US);
        return format.format(mDateManager.mCalendar.getTime());
    }

    //Next month display
    public void nextMonth(){
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    //Previous month display
    public void prevMonth(){
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    public DateManager getDateManager() {
        return mDateManager;
    }
}