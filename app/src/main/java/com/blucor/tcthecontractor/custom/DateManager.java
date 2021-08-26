package com.blucor.tcthecontractor.custom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DateManager {
    public Calendar mCalendar;

    public DateManager(){
        mCalendar = Calendar.getInstance();
    }

    //Get the elements of the month
    public List<Date> getDays(){
        //Keep current state
        Date startDate = mCalendar.getTime();

        //Calculate the total number of squares to be displayed in GridView
        int count = getWeeks() * 7 ;

        //Calculate the number of days for the previous month displayed on the calendar for the current month
        mCalendar.set(Calendar.DATE, 1);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        mCalendar.add(Calendar.DATE, -dayOfWeek);

        List<Date> days = new ArrayList<>();

        for (int i = 0; i < count; i ++){
            days.add(mCalendar.getTime());
            mCalendar.add(Calendar.DATE, 1);
        }

        //Restore state
        mCalendar.setTime(startDate);

        return days;
    }

    //Check if it is this month
    public boolean isCurrentMonth(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        String currentMonth = format.format(mCalendar.getTime());
        if (currentMonth.equals(format.format(date))){
            return true;
        }else {
            return false;
        }
    }

    //Get the number of weeks
    public int getWeeks(){
        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    //Get the day of the week
    public int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //To the next month
    public void nextMonth(){
        mCalendar.add(Calendar.MONTH, 1);
    }

    //To the previous month
    public void prevMonth(){
        mCalendar.add(Calendar.MONTH, -1);
    }
}
