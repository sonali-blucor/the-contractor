package com.blucor.thecontractor.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.blucor.thecontractor.R;

import java.util.ArrayList;

public class WeekDaysCheckBox extends RadioGroup {
    private CheckBox rb_sun;
    private CheckBox rb_mon;
    private CheckBox rb_tue;
    private CheckBox rb_wed;
    private CheckBox rb_thu;
    private CheckBox rb_fri;
    private CheckBox rb_sat;

    private ArrayList<String> listDays;
    private String str_sun = "SUN";
    private String str_mon = "MON";
    private String str_tue = "TUE";
    private String str_wed = "WED";
    private String str_thu = "THU";
    private String str_fri = "FRI";
    private String str_sat = "SAT";

    private Context mContext;


    public WeekDaysCheckBox(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public WeekDaysCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    private void initViews() {

        LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        childParam1.weight = 1f;

        rb_sun = new CheckBox(mContext);
        rb_sun.setLayoutParams(childParam1);
        rb_sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_sun);
                } else {
                    listDays.remove(str_sun);
                }
            }
        });

        rb_mon = new CheckBox(mContext);
        rb_mon.setLayoutParams(childParam1);
        rb_mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_mon);
                } else {
                    listDays.remove(str_mon);
                }
            }
        });

        rb_tue = new CheckBox(mContext);
        rb_tue.setLayoutParams(childParam1);
        rb_tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_tue);
                } else {
                    listDays.remove(str_tue);
                }
            }
        });

        rb_wed = new CheckBox(mContext);
        rb_wed.setLayoutParams(childParam1);
        rb_wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_wed);
                } else {
                    listDays.remove(str_wed);
                }
            }
        });

        rb_thu = new CheckBox(mContext);
        rb_thu.setLayoutParams(childParam1);
        rb_thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_thu);
                } else {
                    listDays.remove(str_thu);
                }
            }
        });

        rb_fri = new CheckBox(mContext);
        rb_fri.setLayoutParams(childParam1);
        rb_fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_fri);
                } else {
                    listDays.remove(str_fri);
                }
            }
        });

        rb_sat = new CheckBox(mContext);
        rb_sat.setLayoutParams(childParam1);
        rb_sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listDays.add(str_sat);
                } else {
                    listDays.remove(str_sat);
                }
            }
        });

        rb_sun.setButtonDrawable(R.drawable.ic_sun);
        rb_mon.setButtonDrawable(R.drawable.ic_mon);
        rb_tue.setButtonDrawable(R.drawable.ic_tue);
        rb_wed.setButtonDrawable(R.drawable.ic_wed);
        rb_thu.setButtonDrawable(R.drawable.ic_thu);
        rb_fri.setButtonDrawable(R.drawable.ic_fri);
        rb_sat.setButtonDrawable(R.drawable.ic_sat);

        this.addView(rb_sun);
        this.addView(rb_mon);
        this.addView(rb_tue);
        this.addView(rb_wed);
        this.addView(rb_thu);
        this.addView(rb_fri);
        this.addView(rb_sat);
        this.setOrientation(HORIZONTAL);
    }

    public ArrayList<String> selectedWeekDays() {
        return listDays;
    }
}
