package com.blucor.thecontractor.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.custom.WeekDaysCheckBox;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.ScheduleModel;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends BaseAppCompatActivity {
    private WeekDaysCheckBox wd_schedule;
    private EditText edt_project_name;
    private EditText edt_project_status;
    private EditText edt_no_of_days;
    private RatingBar rt_bar_schedule;
    private Button btn_schedule;
    private ProjectsModel project;
    private ScheduleModel schedule;
    private boolean is_scheduled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        wd_schedule = findViewById(R.id.wd_schedule);
        edt_project_name = findViewById(R.id.edt_project_name);
        edt_project_status = findViewById(R.id.edt_project_status);
        edt_no_of_days = findViewById(R.id.edt_no_of_days);
        rt_bar_schedule = findViewById(R.id.rt_bar_schedule);
        btn_schedule = findViewById(R.id.btn_schedule);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidData()) {
                    saveScheduleToDb();
                }
            }
        });

        edt_project_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupProjectStatusDialog();
            }
        });

        getScheduleDetails();
    }

    private void popupProjectStatusDialog() {
        String[] status_list = new String[]{"On Going","Complete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
        builder.setTitle("Select Project Status");
        builder.setItems(status_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String status = status_list[which];
                edt_project_status.setText(status);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getScheduleDetails() {
        showLoader();
        if (project != null) {
            RetrofitClient.getApiService().getScheduleByProjectId(project.id).enqueue(new Callback<List<ScheduleModel>>() {
                @Override
                public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            schedule = response.body().get(0);
                            is_scheduled = true;
                        } else {
                            is_scheduled = false;
                        }
                    } else {
                        is_scheduled = false;
                    }
                    stopLoader();
                    setupSchedule();
                }

                @Override
                public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {
                    Toast.makeText(ScheduleActivity.this, "Error in getting schedule", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Project not recived ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSchedule() {
        if (is_scheduled) {
            edt_project_name.setText(schedule.project_name);
            edt_no_of_days.setText(schedule.no_of_days);
            edt_project_status.setText(schedule.project_status);
            wd_schedule.setSelectedWeekDays(schedule.week_days);
            rt_bar_schedule.setRating(schedule.rating);
        } else {
            edt_project_name.setText(project.project_name);
            String num_days = project.duration.replace("Days","").trim();
            int numDays = Integer.parseInt(num_days);
            edt_no_of_days.setText(""+numDays);
        }
    }

    private void saveScheduleToDb() {
        String project_status = edt_project_status.getText().toString();
        String week_days = wd_schedule.selectedWeekDays().toString();
        int num_of_days = Integer.parseInt(edt_no_of_days.getText().toString());
        float ratings = rt_bar_schedule.getRating();

        showLoader();
        RetrofitClient.getApiService().saveOrUpdateSchedule(project.id,project_status,num_of_days,week_days,ratings).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    schedule  = response.body();
                    Toast.makeText(ScheduleActivity.this, "Project Scheduled Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ScheduleActivity.this,ProjectListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(ScheduleActivity.this, "Error in scheduling", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<ScheduleModel> call, Throwable t) {
                stopLoader();
                Toast.makeText(ScheduleActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidData() {
        String emptyField = "Empty Field!";
        if (edt_project_name.getText().toString().isEmpty() || edt_project_name.getText().toString().equalsIgnoreCase("")) {
            edt_project_name.setError(emptyField);
            edt_project_name.requestFocus();
            return false;
        } else if (edt_project_status.getText().toString().isEmpty() || edt_project_status.getText().toString().equalsIgnoreCase("")) {
            edt_project_status.setError(emptyField);
            edt_project_status.requestFocus();
            return false;
        } else if (edt_no_of_days.getText().toString().isEmpty() || edt_no_of_days.getText().toString().equalsIgnoreCase("")) {
            edt_no_of_days.setError(emptyField);
            edt_no_of_days.requestFocus();
            return false;
        } else if (wd_schedule.selectedWeekDays().size() <= 0) {
            Toast.makeText(this, "Please Select Holidays", Toast.LENGTH_SHORT).show();
            return false;
        } else if (rt_bar_schedule.getRating() <= 0) {
            Toast.makeText(this, "Please Rate for project", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            edt_no_of_days.setError(null);
            edt_project_name.setError(null);
            edt_project_status.setError(null);
            return true;
        }
    }
}