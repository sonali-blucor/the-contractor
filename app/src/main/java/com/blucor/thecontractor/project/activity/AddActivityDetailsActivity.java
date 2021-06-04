package com.blucor.thecontractor.project.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

public class AddActivityDetailsActivity extends AppCompatActivity {

    private boolean isAddOrEdit = false;

    private TextInputEditText mEdtPStartDate;
    private TextView mTvPDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        mEdtPStartDate = (TextInputEditText) findViewById(R.id.edt_act_start_date);
        mTvPDuration = (TextView) findViewById(R.id.tv_act_duration);

       /* mEdtPStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onClickDate(v);
            }
        });*/

        try {
            Bundle bundle = getIntent().getExtras();
            if (getIntent().hasExtra(AppKeys.ACTIVITY_DETAIL_TYPE)) {
                isAddOrEdit = bundle.getBoolean(AppKeys.ACTIVITY_DETAIL_TYPE);
            }
        } catch (Exception e) {
            isAddOrEdit = false;
        }
    }

    public void onClickDate(View view){
        SmoothDateRangePickerFragment smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
            @Override
            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                       int yearStart, int monthStart,
                                       int dayStart, int yearEnd,
                                       int monthEnd, int dayEnd) {
                // grab the date range, do what you want
                String startDate = String.valueOf(dayStart) + "/" + String.valueOf(monthStart) + "/" + String.valueOf(yearStart);
                String endDate = String.valueOf(dayEnd) + "/" + String.valueOf(monthEnd) + "/" + String.valueOf(yearEnd);
                String date = startDate + " to " + endDate;
                mEdtPStartDate.setText(date);
                String days =ScreenHelper.findDifference(startDate, endDate);
                mTvPDuration.setText(days+" Days");
            }
        });

        smoothDateRangePickerFragment.show(getFragmentManager(), "show Project Date Range");
    }

    public void onClickToSubmit(View view) {
        if (isAddOrEdit)
            ScreenHelper.redirectToClass(AddActivityDetailsActivity.this, EditProjectActActivity.class);
        else
            ScreenHelper.redirectToClass(AddActivityDetailsActivity.this, AddProjectActActivity.class);

    }
}