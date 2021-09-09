package com.blucor.tcthecontractor.custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.blucor.tcthecontractor.models.HolidayModel;
import com.blucor.tcthecontractor.project.ScheduleActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomCalenderDatePickerDialog extends AlertDialog {
    private CalendarView calendarView;
    private OnCustomCalenderDateSetListener mListener;
    private ArrayList<HolidayModel> selectedDays;

    protected CustomCalenderDatePickerDialog(Context context) {
        super(context);
        initDialog();
    }

    public CustomCalenderDatePickerDialog(Context context, OnCustomCalenderDateSetListener mListener) {
        super(context);
        this.mListener = mListener;
        initDialog();
    }

    protected CustomCalenderDatePickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog();
    }

    protected CustomCalenderDatePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialog();
    }

    private void initDialog() {
        calendarView = new CalendarView(getContext());
        setTitle("Select Date");
        setView(calendarView);
        calendarView.setOnCalenderClickListener(new OnCalenderClick() {
            @Override
            public void onItemClick(int position, long date) {
               addToHoliday(position,date);
            }
        });
    }

    public void setSelectedDayArray(ArrayList<HolidayModel> selectedDays) {
        this.selectedDays = selectedDays;
        calendarView.setSelectedDayArray(selectedDays);
    }

    public DateManager getDateManager() {
        return calendarView.getDateManager();
    }

    public int isPresentInSelectedDays(Date selectedDate) {
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

    private void addToHoliday(int position, long date) {
        Date selected_date = new Date();
        selected_date.setTime(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = sdf.format(selected_date);

        HolidayModel date_model = new HolidayModel();
        date_model.date = str_date;

        if (calendarView.getDateManager().isCurrentMonth(selected_date)){
            int present = isPresentInSelectedDays(selected_date);
            if(present != 0) {

            } else {
                if (mListener != null) {
                    dismiss();
                    mListener.onCustomCalenderDateSet(position,date);
                }
            }
        } else {
            //Toast.makeText(this, "Is Already Present in holidays", Toast.LENGTH_SHORT).show();
        }
    }
}
