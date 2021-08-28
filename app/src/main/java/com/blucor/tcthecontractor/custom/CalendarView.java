package com.blucor.tcthecontractor.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.rv_adapters.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;


public class CalendarView extends LinearLayout {
    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private CalendarAdapter mCalendarAdapter;
    private OnCalenderClick mListener;
    private ArrayList<Date> selectedDays;
    private Context context;

    public CalendarView(Context context)
    {
        super(context);
        initControl(context);
        this.context = context;
    }

    public CalendarView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
        this.context = context;
    }

    /**
     * Load component XML layout
     */
    private void initControl(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.control_calendar, this);

        // layout is inflated, assign local variables to components
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);
        txtDate = findViewById(R.id.calendar_date_display);
        grid = findViewById(R.id.calendar_grid);
        mCalendarAdapter = new CalendarAdapter(context,null);
        grid.setAdapter(mCalendarAdapter);

        txtDate.setText(mCalendarAdapter.getTitle());
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    Date date = mCalendarAdapter.getItem(position);
                    mListener.onItemClick(position,date.getTime());
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                txtDate.setText(mCalendarAdapter.getTitle());
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                txtDate.setText(mCalendarAdapter.getTitle());
            }
        });

       // this.addView(view);
    }

    public void setOnCalenderClickListener(OnCalenderClick mListener) {
        this.mListener = mListener;
    }

    public void setSelectedDayArray(ArrayList<Date> selectedDays) {
        this.selectedDays = selectedDays;
        updateCalender();
    }

    private void updateCalender() {
        mCalendarAdapter = new CalendarAdapter(getContext(),selectedDays);
        grid.setAdapter(mCalendarAdapter);
    }

}
