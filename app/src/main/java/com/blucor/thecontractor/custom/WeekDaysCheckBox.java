package com.blucor.thecontractor.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.blucor.thecontractor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekDaysCheckBox extends RadioGroup {
    private CheckBox rb_sun;
    private CheckBox rb_mon;
    private CheckBox rb_tue;
    private CheckBox rb_wed;
    private CheckBox rb_thu;
    private CheckBox rb_fri;
    private CheckBox rb_sat;

    private ArrayList<String> listDays;
    private final String str_sun = "SUN";
    private final String str_mon = "MON";
    private final String str_tue = "TUE";
    private final String str_wed = "WED";
    private final String str_thu = "THU";
    private final String str_fri = "FRI";
    private final String str_sat = "SAT";

    private final Context mContext;


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
        listDays = new ArrayList<>();
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

        rb_sun.setButtonDrawable(R.drawable.ic_sun_selector);
        rb_mon.setButtonDrawable(R.drawable.ic_mon_selector);
        rb_tue.setButtonDrawable(R.drawable.ic_tue_selector);
        rb_wed.setButtonDrawable(R.drawable.ic_wed_selector);
        rb_thu.setButtonDrawable(R.drawable.ic_thu_selector);
        rb_fri.setButtonDrawable(R.drawable.ic_fri_selector);
        rb_sat.setButtonDrawable(R.drawable.ic_sat_selector);

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

    public void setSelectedWeekDays(String weekDays) {
        getArrayFromString(weekDays);
        setCheckboxAccordingToList(listDays);
    }

    private void setCheckboxAccordingToList(List<String> myList) {
        for (int i = 0; i < myList.size(); i++) {
            setcheckedFromString(myList.get(i));
        }
    }

    private void setcheckedFromString(String s) {
        if(s.equalsIgnoreCase(str_sun)) {
            rb_sun.setChecked(true);
        } else if(s.equalsIgnoreCase(str_mon)) {
            rb_mon.setChecked(true);
        } else if(s.equalsIgnoreCase(str_tue)) {
            rb_tue.setChecked(true);
        } else if(s.equalsIgnoreCase(str_wed)) {
            rb_wed.setChecked(true);
        } else if(s.equalsIgnoreCase(str_thu)) {
            rb_thu.setChecked(true);
        } else if(s.equalsIgnoreCase(str_fri)) {
            rb_fri.setChecked(true);
        } else if(s.equalsIgnoreCase(str_sat)) {
            rb_sat.setChecked(true);
        }
    }

    private void getArrayFromString(String officeDays) {
        if (officeDays.contains("[")) {
            officeDays = officeDays.replace("[", "");
        }
        if (officeDays.contains("]")) {
            officeDays = officeDays.replace("]", "");
        }
        listDays = new ArrayList<>(Arrays.asList(officeDays.split(",")));
    }
}
