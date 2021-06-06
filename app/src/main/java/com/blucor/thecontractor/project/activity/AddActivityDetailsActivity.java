package com.blucor.thecontractor.project.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.SubActivityModel;
import com.blucor.thecontractor.project.AddProjectActivity;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AddActivityDetailsActivity extends AppCompatActivity {
    private TextInputEditText edt_activity_name;
    private TextView main_activity_start_end_date;
    private ListView lst_add_activity;
    private FloatingActionButton btn_submit;
    private ProjectsModel project;
    private TextInputEditText edt_sub_activity_start_date;
    private TextInputEditText edt_sub_activity_end_date;
    private TextInputEditText edt_add_sub_contractor;
    private TextView tv_total_sub_activity_days;
    private Button btn_add_activity;
    private long start_date = 0;
    private long end_date = 0;
    private ArrayList<SubActivityModel> subActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        edt_activity_name = findViewById(R.id.edt_activity_name);
        main_activity_start_end_date = findViewById(R.id.main_activity_start_end_date);
        lst_add_activity = findViewById(R.id.lst_add_activity);
        btn_submit = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        setupActivity();

    }

    private void setupActivity() {
        if (project != null) {
            edt_activity_name.setText(project.main_activity_name);
            String start_and_date = "Start Date : "+project.start_date+" - End Date : "+project.end_date;
            main_activity_start_end_date.setText(start_and_date);

            getProjectActivity();
        }
    }

    private void getProjectActivity() {
        subActivities = new ArrayList<>();
        if (subActivities.size() <= 0) {
            try {
                String dateString = project.start_date;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date = sdf.parse(dateString);

                start_date = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickToSubmit(View view) {
        openAddActivityDialog();
    }

    private void openAddActivityDialog() {
        AlertDialog dialog = new AlertDialog.Builder(AddActivityDetailsActivity.this).create();
        dialog.setTitle("Add Sub Activity");
        View view  = LayoutInflater.from(AddActivityDetailsActivity.this).inflate(R.layout.add_sub_activity_dialog,null);
        edt_sub_activity_start_date = view.findViewById(R.id.edt_sub_activity_start_date);
        edt_sub_activity_end_date = view.findViewById(R.id.edt_sub_activity_end_date);
        edt_add_sub_contractor = view.findViewById(R.id.edt_add_sub_contractor);
        tv_total_sub_activity_days = view.findViewById(R.id.tv_total_sub_activity_days);
        btn_add_activity = view.findViewById(R.id.btn_add_sub_activity);
        btn_add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrUpdateSubActivity();
            }
        });
    }

    private void insertOrUpdateSubActivity() {
        String sub_activity_start_date = edt_sub_activity_start_date.getText().toString();
        String sub_activity_end_date = edt_sub_activity_end_date.getText().toString();
        String total_sub_activity_days = tv_total_sub_activity_days.getText().toString();
    }

    public void onClickAddSubContractor(View view) {
        //start add sub contractor activity here
    }

    public void onClickActivityEndDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(start_date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivityDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);

                end_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(cal.getTimeInMillis());
                edt_sub_activity_end_date.setText(date);
                long days = TimeUnit.MILLISECONDS.toDays(Math.abs(cal.getTimeInMillis() - start_date));
                tv_total_sub_activity_days.append(""+days);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(start_date);
        datePickerDialog.show();
    }

    public void onClickActivityStartDate(View view) {
        Calendar calendar = Calendar.getInstance();
        if (start_date != 0) {
            calendar.setTimeInMillis(start_date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivityDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);
                start_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(cal.getTimeInMillis());
                edt_sub_activity_start_date.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}