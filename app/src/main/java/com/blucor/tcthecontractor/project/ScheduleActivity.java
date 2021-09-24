package com.blucor.tcthecontractor.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.custom.CalendarView;
import com.blucor.tcthecontractor.custom.OnCalenderClick;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.HolidayModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.ScheduleModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends BaseAppCompatActivity {
    //private WeekDaysCheckBox wd_schedule;
    private EditText edt_project_name;
    private EditText edt_project_status;
    private EditText edt_no_of_days;
    private EditText edt_project_week;
    private RatingBar rt_bar_schedule;
    private Button btn_schedule;
    private ProjectsModel project;
    private ScheduleModel schedule;
    private boolean is_scheduled;
    private String on_going = "On Going";
    private String complete = "Complete";
    private CalendarView calendarView;
    private LinearLayout llv_calender;
    private ImageView img_close;
    private ArrayList<HolidayModel> selectedDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //wd_schedule = findViewById(R.id.wd_schedule);
        edt_project_name = findViewById(R.id.edt_project_name);
        edt_project_status = findViewById(R.id.edt_project_status);
        edt_no_of_days = findViewById(R.id.edt_no_of_days);
        edt_project_week = findViewById(R.id.edt_project_week);
        rt_bar_schedule = findViewById(R.id.rt_bar_schedule);
        calendarView = findViewById(R.id.calendar_view);
        btn_schedule = findViewById(R.id.btn_schedule);
        llv_calender = findViewById(R.id.llv_calender);
        img_close = findViewById(R.id.img_close);

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

        edt_project_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llv_calender.setVisibility(View.VISIBLE);
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llv_calender.setVisibility(View.GONE);
            }
        });



        /*for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,i);
            Date date = new Date(calendar.getTimeInMillis());
            selectedDays.add(date);
        }*/

        getScheduleDetails();
    }

    private void addToHoliday(long date) {
        Date selected_date = new Date();
        selected_date.setTime(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = sdf.format(selected_date);

        HolidayModel date_model = new HolidayModel();
        date_model.date = str_date;

        if (calendarView.getDateManager().isCurrentMonth(selected_date)) {
            int present = isPresentInSelectedDays(selected_date);
            if (present != 0) {
                // Toast.makeText(this, "Is Already Present in holidays", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(ScheduleActivity.this).create();
                dialog.setTitle("Do you want to remove this day from holiday?");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDays.remove(present);
                        calendarView.setSelectedDayArray(selectedDays);
                        dialog.dismiss();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(ScheduleActivity.this).create();
                dialog.setTitle("Do you want to add this day to holiday?");
                EditText edt_event_name = new EditText(ScheduleActivity.this);
                edt_event_name.setHint("Event Name");
                dialog.setView(edt_event_name);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (edt_event_name.getText().toString().trim().length() > 0) {
                            edt_event_name.setError(null);
                            date_model.note = edt_event_name.getText().toString();
                            selectedDays.add(date_model);
                            calendarView.setSelectedDayArray(selectedDays);
                            dialog.dismiss();
                        } else {
                            edt_event_name.setError("Empty Event Name");
                            edt_event_name.requestFocus();
                        }
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        } else {
            //Toast.makeText(this, "Is Already Present in holidays", Toast.LENGTH_SHORT).show();
        }
    }

    private int isPresentInSelectedDays(Date selectedDate) {
        Calendar cal_selected_date = Calendar.getInstance();
        cal_selected_date.setTimeInMillis(selectedDate.getTime());

        if (selectedDays == null) {
            return 0;
        } else if (selectedDays.size() <= 0) {
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

    private void popupProjectStatusDialog() {
        String[] status_list = new String[]{on_going, complete};
        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
        builder.setTitle("Select Project Status");
        builder.setItems(status_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String status = status_list[which];
                edt_project_status.setText("" + status);
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
                    stopLoader();
                }
            });
        } else {
            stopLoader();
            Toast.makeText(this, "Project not received", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSchedule() {
        if (is_scheduled) {
            edt_project_name.setText("" + schedule.project_name);
            edt_no_of_days.setText("" + schedule.no_of_days);
            /*if (schedule.project_status == 1) {
                edt_project_status.setText(complete);
            } else {
                edt_project_status.setText(on_going);
            }*/
            edt_project_status.setText("" + schedule.project_status);
            //wd_schedule.setSelectedWeekDays(schedule.week_days);
            rt_bar_schedule.setRating(schedule.rating);
        } else {
            edt_project_name.setText("" + project.project_name);
            String num_days = project.duration.toLowerCase().replace("days", "").trim();
            if (num_days.toLowerCase().contains("day")) {
                num_days = num_days.toLowerCase().replace("day", "").trim();
            }
            int numDays = Integer.parseInt(num_days);
            edt_no_of_days.setText("" + numDays);
        }

        getDateDetails();
    }

    private void saveScheduleToDb() {
        String project_status = edt_project_status.getText().toString();
        //String week_days = wd_schedule.selectedWeekDays().toString();
        String week_days = "";
        int num_of_days = Integer.parseInt(edt_no_of_days.getText().toString());
        float ratings = rt_bar_schedule.getRating();
        int project_status_integer = 0;
        if (project_status.equalsIgnoreCase(complete)) {
            project_status_integer = 1;
        }

        String schedule_dates = new Gson().toJson(selectedDays);

        showLoader();
        RetrofitClient.getApiService().saveOrUpdateSchedule(project.id, project_status, num_of_days, week_days, project_status_integer, ratings, schedule_dates).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    schedule = response.body();
                    Toast.makeText(ScheduleActivity.this, "Project Scheduled Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ScheduleActivity.this, ProjectListActivity.class);
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
                Toast.makeText(ScheduleActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        } /*else if (wd_schedule.selectedWeekDays().size() <= 0) {
            Toast.makeText(this, "Please Select Holidays", Toast.LENGTH_SHORT).show();
            return false;
        } */ else if (rt_bar_schedule.getRating() <= 0) {
            Toast.makeText(this, "Please Rate for project", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            edt_no_of_days.setError(null);
            edt_project_name.setError(null);
            edt_project_status.setError(null);
            return true;
        }
    }

    private void getDateDetails() {
        showLoader();
        RetrofitClient.getApiService().getHolidaysByProjectId(project.id).enqueue(new Callback<ArrayList<HolidayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HolidayModel>> call, Response<ArrayList<HolidayModel>> response) {
                if (response.code() == 200) {
                    selectedDays = response.body();
                    createDate();
                    stopLoader();
                } else {
                    stopLoader();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HolidayModel>> call, Throwable t) {
                stopLoader();
            }
        });
    }

    private void createDate() {
       /* selectedDays = new ArrayList<>();

        for (int i = 0; i < holidayModels.size(); i++) {
            String date_str = holidayModels.get(i).start;
            if (date_str != null && date_str.length() != 0) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = format.parse(date_str);
                    selectedDays.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }*/

        calendarView.setSelectedDayArray(selectedDays);
        calendarView.setOnCalenderClickListener(new OnCalenderClick() {
            @Override
            public void onItemClick(int position, long date) {
                //Toast.makeText(ScheduleActivity.this, "Item Clicked : "+position, Toast.LENGTH_SHORT).show();
                addToHoliday(date);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(this, ProjectMenuActivity.class, bundle);
    }
}