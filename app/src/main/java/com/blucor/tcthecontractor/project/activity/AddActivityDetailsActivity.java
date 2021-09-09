package com.blucor.tcthecontractor.project.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.custom.CalendarView;
import com.blucor.tcthecontractor.custom.CustomCalenderDatePickerDialog;
import com.blucor.tcthecontractor.custom.OnCustomCalenderDateSetListener;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.Activity;
import com.blucor.tcthecontractor.models.ActivityResponseModel;
import com.blucor.tcthecontractor.models.HolidayModel;
import com.blucor.tcthecontractor.models.InsertSubActivityResponseModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.InsertActivityResponseModel;
import com.blucor.tcthecontractor.models.SubActivityModel;
import com.blucor.tcthecontractor.models.SubContractor;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.ScheduleActivity;
import com.blucor.tcthecontractor.rv_adapters.SubActivityAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddActivityDetailsActivity extends BaseAppCompatActivity {
    private TextInputEditText edt_activity_name;
    private TextView main_activity_start_end_date;
    private ListView lst_sub_activity;
    private FloatingActionButton btn_submit;
    private ProjectsModel project;
    private TextInputEditText edt_sub_activity_start_date;
    private TextInputEditText edt_sub_activity_name;
    private TextInputEditText edt_sub_activity_end_date;
    private TextInputEditText edt_add_sub_contractor;
    private TextView tv_total_sub_activity_days;
    private Button btn_add_activity;
    private long start_date = 0;
    private long end_date = 0;
    private ActivityResponseModel model;
    private AlertDialog dialog;
    private List<SubActivityModel> subActivities;
    private SubContractor subContractor;
    private SubActivityAdapter adapter;
    private ArrayList<HolidayModel> selectedDays;
    private CustomCalenderDatePickerDialog dialog_end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        edt_activity_name = findViewById(R.id.edt_activity_name);
        main_activity_start_end_date = findViewById(R.id.main_activity_start_end_date);
        lst_sub_activity = findViewById(R.id.lst_sub_activity);
        btn_submit = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        if (intent.hasExtra(AppKeys.ACTIVTY)) {
            model = intent.getParcelableExtra(AppKeys.ACTIVTY);
            project.main_activity_id = model.activity.activity_id;
            project.main_activity_name = model.activity.activity_name;
           // setProjectSubActivity();
        }

        getProjectHolidays();

    }

    private void getProjectHolidays() {
        showLoader();
        RetrofitClient.getApiService().getHolidaysByProjectId(project.id).enqueue(new Callback<ArrayList<HolidayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HolidayModel>> call, Response<ArrayList<HolidayModel>> response) {
                if (response.code() == 200) {
                    selectedDays = response.body();
                    //createDate();
                    stopLoader();
                    getProjectActivityDetails();
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

    private void getProjectActivityDetails() {
        showLoader();
        RetrofitClient.getApiService().getProjectActivityDetails(project.id,project.main_activity_id).enqueue(new Callback<ActivityResponseModel>() {
            @Override
            public void onResponse(Call<ActivityResponseModel> call, Response<ActivityResponseModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    model = response.body();
                    setProjectSubActivity();
                } else {
                    Toast.makeText(AddActivityDetailsActivity.this, "No data received", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<ActivityResponseModel> call, Throwable t) {
                Toast.makeText(AddActivityDetailsActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }


    private void setProjectSubActivity() {
        //subActivities = new ArrayList<>();
        if (model.subActivities.size() > 0) {
            try {
                edt_activity_name.setText(model.activity.activity_name);
                main_activity_start_end_date.setText(project.start_date+" - "+project.end_date);
                String dateString = project.start_date;
                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                Date date = sdf.parse(dateString);

                start_date = date.getTime();

                subActivities = model.subActivities;
                adapter = new SubActivityAdapter(AddActivityDetailsActivity.this,subActivities);
                lst_sub_activity.setAdapter(adapter);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickToSubmit(View view) {
        if (edt_activity_name.getText().toString().isEmpty() || edt_activity_name.getText().toString().equalsIgnoreCase("")) {
            edt_activity_name.setError("Please set activity name first");
        } else {
            edt_activity_name.setError(null);
            insertOrUpdateActivity();
        }
    }

    private void insertOrUpdateActivity() {
        showLoader();
        RetrofitClient.getApiService().insertOrUpdateActivity(project.id,edt_activity_name.getText().toString(),project.start_date,project.end_date).enqueue(new Callback<InsertActivityResponseModel>() {
            @Override
            public void onResponse(Call<InsertActivityResponseModel> call, Response<InsertActivityResponseModel> response) {
                if (response.code() == 200 && response != null) {
                    InsertActivityResponseModel responseModel = response.body();
                    if (responseModel.success) {
                        Activity activity = responseModel.activity;
                        project.main_activity_name = activity.activity_name;
                        project.main_activity_id = activity.activity_id;
                        openAddActivityDialog();
                    } else {
                        Toast.makeText(AddActivityDetailsActivity.this, ""+responseModel.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddActivityDetailsActivity.this, "Error ", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<InsertActivityResponseModel> call, Throwable t) {
                Toast.makeText(AddActivityDetailsActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void openAddActivityDialog() {
        dialog = new AlertDialog.Builder(AddActivityDetailsActivity.this).create();
        dialog.setTitle("Add Sub Activity");
        View view  = LayoutInflater.from(AddActivityDetailsActivity.this).inflate(R.layout.add_sub_activity_dialog,null);
        edt_sub_activity_name = view.findViewById(R.id.edt_sub_activity_name);
        edt_sub_activity_start_date = view.findViewById(R.id.edt_sub_activity_start_date);
        edt_sub_activity_end_date = view.findViewById(R.id.edt_sub_activity_end_date);
        edt_add_sub_contractor = view.findViewById(R.id.edt_add_sub_contractor);
        edt_add_sub_contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivityDetailsActivity.this, AddSubContractorActivity.class);
                intent.putExtra(AppKeys.PROJECT,project);
                startActivityForResult(intent,AppKeys.SUB_CONTRACTOR_RESULT);
            }
        });
        tv_total_sub_activity_days = view.findViewById(R.id.tv_total_sub_activity_days);
        btn_add_activity = view.findViewById(R.id.btn_add_sub_activity);
        btn_add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_valid()) {
                    insertOrUpdateSubActivity();
                }
            }
        });
        dialog.setView(view);
        dialog.show();

    }

    private boolean is_valid() {
        if (edt_sub_activity_name.getText().toString().isEmpty() || edt_sub_activity_name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter sub activity name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_sub_activity_start_date.getText().toString().isEmpty() || edt_sub_activity_start_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please select sub activity start date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_sub_activity_end_date.getText().toString().isEmpty() || edt_sub_activity_end_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please select sub activity end date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_add_sub_contractor.getText().toString().isEmpty() || edt_add_sub_contractor.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please select sub contractor", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppKeys.SUB_CONTRACTOR_RESULT) {
            if (data.hasExtra(AppKeys.SUB_CONTRACTOR) && data.getParcelableExtra(AppKeys.SUB_CONTRACTOR) != null) {
                subContractor = data.getParcelableExtra(AppKeys.SUB_CONTRACTOR);
                edt_add_sub_contractor.setText(""+subContractor.fname+" "+subContractor.lname);
                if (dialog!=null && !dialog.isShowing()) {
                    dialog.show();
                }
            } else {
                Toast.makeText(this, "No Sub Contractor Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertOrUpdateSubActivity() {
        int activity_id = project.main_activity_id;
        String sub_activity_name = edt_sub_activity_name.getText().toString();
        String sub_activity_start_date = edt_sub_activity_start_date.getText().toString();
        String sub_activity_end_date = edt_sub_activity_end_date.getText().toString();
        int sub_contractor_id = subContractor.id;
        String duration = tv_total_sub_activity_days.getText().toString().replace("Total Days : ","").trim();

        showLoader();
        RetrofitClient.getApiService().saveOrUpdateSubActivity(activity_id,sub_activity_name,sub_activity_start_date,sub_activity_end_date,sub_contractor_id,duration).enqueue(new Callback<InsertSubActivityResponseModel>() {
            @Override
            public void onResponse(Call<InsertSubActivityResponseModel> call, Response<InsertSubActivityResponseModel> response) {
                if(subActivities == null) {
                    subActivities = new ArrayList<>();
                    adapter = new SubActivityAdapter(AddActivityDetailsActivity.this,subActivities);
                    lst_sub_activity.setAdapter(adapter);
                }

                SubActivityModel subActivity = response.body().sub_activity;
                subActivity.sub_contractor_id = response.body().sub_contractor.id;
                subActivity.sub_contractor_first_name = response.body().sub_contractor.fname;
                subActivity.sub_contractor_last_name = response.body().sub_contractor.lname;
                subActivity.sub_contractor_mobile = response.body().sub_contractor.mobile;
                subActivity.sub_contractor_email = response.body().sub_contractor.email;
                subActivities.add(response.body().sub_activity);
                adapter.notifyDataSetChanged();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<InsertSubActivityResponseModel> call, Throwable t) {
                stopLoader();
                Log.e("Sub activity","Error : "+t.getMessage());
            }
        });

    }

    public void onClickActivityEndDate(View view) {
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start_date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivityDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);

                end_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                String date = sdf.format(cal.getTimeInMillis());
                edt_sub_activity_end_date.setText(date);
                long days = TimeUnit.MILLISECONDS.toDays(Math.abs(cal.getTimeInMillis() - start_date));
                tv_total_sub_activity_days.append(""+days);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(start_date);
        datePickerDialog.show();*/
        endDateDialog();
    }

    private void startDateDialog() {
        CustomCalenderDatePickerDialog dialog = new CustomCalenderDatePickerDialog(AddActivityDetailsActivity.this, new OnCustomCalenderDateSetListener() {
            @Override
            public void onCustomCalenderDateSet(int position, long date) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date);
                start_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                String date_str = sdf.format(cal.getTimeInMillis());
                edt_sub_activity_start_date.setText(date_str);
            }
        });
        dialog.setSelectedDayArray(selectedDays);
        dialog.show();
    }

    private void endDateDialog() {
        dialog_end_date = new CustomCalenderDatePickerDialog(AddActivityDetailsActivity.this, new OnCustomCalenderDateSetListener() {
            @Override
            public void onCustomCalenderDateSet(int position, long date) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date);

                end_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                String date_str = sdf.format(cal.getTimeInMillis());
                edt_sub_activity_end_date.setText(date_str);
                long days = TimeUnit.MILLISECONDS.toDays(Math.abs(cal.getTimeInMillis() - start_date));
                int num_of_holidays = getNumberOfHolidays(dialog_end_date,days);
                tv_total_sub_activity_days.append(""+num_of_holidays);
            }
        });
        dialog_end_date.setSelectedDayArray(selectedDays);
        dialog_end_date.show();
    }

    /*private void getDateDetails() {
        showLoader();
        RetrofitClient.getApiService().getHolidays().enqueue(new Callback<ArrayList<HolidayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HolidayModel>> call, Response<ArrayList<HolidayModel>> response) {
                if (response.code() == 200) {
                    selectedDays = response.body();
                    //createDate();
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
    }*/

    private int getNumberOfHolidays(CustomCalenderDatePickerDialog dialog,long days) {
        int numberOfHolidays = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start_date);

        Calendar calendar_end_date = Calendar.getInstance();
        calendar_end_date.setTimeInMillis(end_date);

        /*do {
           calendar.add(Calendar.DAY_OF_MONTH,1);
           numberOfHolidays++;
        } while(calendar.get(Calendar.DAY_OF_MONTH) == calendar_end_date.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.MONTH) == calendar_end_date.get(Calendar.MONTH) && calendar.get(Calendar.YEAR) == calendar_end_date.get(Calendar.YEAR));
        */

        for (int i = 0; i < days; i++) {

            calendar.add(Calendar.DAY_OF_MONTH,1);

            if(dialog.isPresentInSelectedDays(new Date(calendar.getTimeInMillis())) != 0) {
                continue;
            }

            numberOfHolidays++;

            if(calendar.get(Calendar.DAY_OF_MONTH) == calendar_end_date.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.MONTH) == calendar_end_date.get(Calendar.MONTH) && calendar.get(Calendar.YEAR) == calendar_end_date.get(Calendar.YEAR)) {
                break;
            }
        }


        return numberOfHolidays;
    }

    public void onClickActivityStartDate(View view) {
        /*Calendar calendar = Calendar.getInstance();
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

                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                String date = sdf.format(cal.getTimeInMillis());
                edt_sub_activity_start_date.setText(date);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(start_date);
        datePickerDialog.show();*/
        startDateDialog();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this, AddProjectActActivity.class,bundle);
    }
}