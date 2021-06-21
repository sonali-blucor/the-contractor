package com.blucor.thecontractor.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.client.ClientAddAndSearchActivity;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.Client;
import com.blucor.thecontractor.models.Contract_Type;
import com.blucor.thecontractor.models.Project_Type;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.rv_adapters.ContractTypeAdapter;
import com.blucor.thecontractor.rv_adapters.ProjectTypeAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends BaseAppCompatActivity {

    private TextInputEditText mEdtProjectName;
    private TextInputEditText mEdtProjectType;
    private TextInputEditText mEdtContractType;
    private TextInputEditText mEdtAddress;
    private TextInputEditText mEdtPStartDate;
    private TextInputEditText mEdtPEndDate;
    private TextInputEditText mEdtAddClient;
    private TextView mTvPDuration;
    private long start_date = 0;
    private long end_date = 0;
    private EditText etAddress1;
    private EditText etAddress2;
    private EditText etCity;
    private EditText etZip;
    private EditText etState;
    private EditText etCountry;
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String zip = "";
    private String state = "Maharashtra";
    private String country = "India";
    private Dialog dialog;
    private boolean isFieldEmpty;
    private boolean isAddressInput;
    private Button btnCompleteAddress;
    private List<Contract_Type> contract_types;
    private List<Project_Type> project_types;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        mEdtProjectName = findViewById(R.id.edt_project_name);
        mEdtProjectType = findViewById(R.id.edt_project_type);
        mEdtContractType = findViewById(R.id.edt_contract_type);
        mEdtPStartDate = findViewById(R.id.edt_project_start_date);
        mEdtPEndDate = findViewById(R.id.edt_project_end_date);
        mEdtAddress = findViewById(R.id.edt_project_location);
        mEdtAddClient = findViewById(R.id.edt_add_client);
        mTvPDuration = findViewById(R.id.tv_project_duration);

        getDetails();
    }

    public void onClickDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddProjectActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);
                start_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                String date = sdf.format(cal.getTimeInMillis());
                mEdtPStartDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public void showPopupViewForPType(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddProjectActivity.this);
            builder.setTitle("Choose Project Type: ");
            AlertDialog alert = builder.create();
            ProjectTypeAdapter adapter = new ProjectTypeAdapter(AddProjectActivity.this, project_types);
            ListView lst_project_type = new ListView(AddProjectActivity.this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mEdtProjectType.setText(project_types.get(position).project_type);
                    alert.dismiss();
                }
            });
            builder.setCancelable(false)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            alert.show();
        } catch (Exception e) {
            Log.e("AddProjectActivity",""+e.getMessage());
        }
    }

    public void showPopupViewForCType(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProjectActivity.this);
        builder.setTitle("Choose Contract Type: ");
        AlertDialog alert = builder.create();
        ContractTypeAdapter adapter = new ContractTypeAdapter(AddProjectActivity.this, contract_types);
        ListView lst_contract_type = new ListView(AddProjectActivity.this);
        lst_contract_type.setAdapter(adapter);
        alert.setView(lst_contract_type);
        lst_contract_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEdtContractType.setText(contract_types.get(position).contract_type);
                alert.dismiss();
            }
        });
        builder.setCancelable(false)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        alert.show();
    }

    public void onClickToSubmit(View view) {
        if (is_valid_data()) {
            User user = DatabaseUtil.on().getAllUser().get(0);
            String ProjectName = mEdtProjectName.getText().toString();
            String ProjectType = mEdtProjectType.getText().toString();
            String ContractType = mEdtContractType.getText().toString();
            String Address = mEdtAddress.getText().toString();
            String PStartDate = mEdtPStartDate.getText().toString();
            String PEndDate = mEdtPEndDate.getText().toString();
            String PDuration = mTvPDuration.getText().toString();

            showLoader();

            RetrofitClient.getApiService().saveProject(ProjectName,ProjectType,ContractType,client.id,Address,PStartDate,PEndDate,PDuration,user.server_id).enqueue(new Callback<ProjectsModel>() {
                @Override
                public void onResponse(Call<ProjectsModel> call, Response<ProjectsModel> response) {
                    if (response!= null && response.code() == 201) {
                        if(response.body() != null) {
                            ProjectsModel project = response.body();
                            Toast.makeText(AddProjectActivity.this, "Project added with id : " + project.id, Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(AppKeys.PROJECT,project);
                            ScreenHelper.redirectToClass(AddProjectActivity.this,ScheduleActivity.class,bundle);
                            finish();
                        }else {
                            Toast.makeText(AddProjectActivity.this, "Unable to add project", Toast.LENGTH_SHORT).show();
                        }
                    } else if(response.code() == 500) {
                        Toast.makeText(AddProjectActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddProjectActivity.this, "Project is already exists", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<ProjectsModel> call, Throwable t) {
                    Toast.makeText(AddProjectActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    public void onClickEndDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(start_date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddProjectActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);

                end_date = cal.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                String date = sdf.format(cal.getTimeInMillis());
                mEdtPEndDate.setText(date);
                long days = TimeUnit.MILLISECONDS.toDays(Math.abs(cal.getTimeInMillis() - start_date));
                mTvPDuration.setText(days+" Days");
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(start_date);
        datePickerDialog.show();
    }

    public void onClickAddClient(View view) {
        Intent intent = new Intent(AddProjectActivity.this,ClientAddAndSearchActivity.class);
        startActivityForResult(intent,120);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120) {
            try {
                if (data.hasExtra("client")) {
                    client = data.getParcelableExtra("client");
                    mEdtAddClient.setText(client.fname+" "+client.lname);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickAddProjectLocation(View view) {
        popupAddressDialog();
    }

    private void popupAddressDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_address, null);

        etAddress1 = view.findViewById(R.id.et_shipping_address_1);
        etAddress2 = view.findViewById(R.id.et_shipping_address_2);
        etCity = view.findViewById(R.id.et_shipping_city);
        etZip = view.findViewById(R.id.et_shipping_zip);
        etState = view.findViewById(R.id.et_shipping_state);
        etCountry = view.findViewById(R.id.et_shipping_country);
        btnCompleteAddress = view.findViewById(R.id.btn_complete_address_inner);

        etAddress1.setText(address1);
        etAddress2.setText(address2);
        etZip.setText(zip);
        etCity.setText(city);
        etState.setText(state);
        etCountry.setText(country);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        btnCompleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddress();
                if (!isFieldEmpty) {
                    isAddressInput = true;
                    hideKeyboardFrom(AddProjectActivity.this,view);
                    String Address = address1+","+address2+","+city+","+zip+","+state+","+country;
                    mEdtAddress.setText(Address);
                    dialog.dismiss();
                } else {
                    Toast.makeText(AddProjectActivity.this, "Field Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validateAddress() {
        address1 = etAddress1.getText().toString().trim();
        address2 = etAddress2.getText().toString().trim();
        city = etCity.getText().toString().trim();
        zip = etZip.getText().toString().trim();
        state = etState.getText().toString().trim();
        country = etCountry.getText().toString().trim();

        if (address1.equals("")) {
            isFieldEmpty = true;
            etAddress1.setBackgroundResource(R.drawable.edittext_error);
            etAddress1.setError("Empty Field");
            etAddress1.requestFocus();
        } else {
            isFieldEmpty = false;
            etAddress1.setBackgroundResource(R.drawable.edittext_round);
        }
        if (city.equals("")) {
            isFieldEmpty = true;
            etCity.setBackgroundResource(R.drawable.edittext_error);
            etCity.setError("Empty Field");
            etCity.requestFocus();
        } else {
            isFieldEmpty = false;
            etCity.setBackgroundResource(R.drawable.edittext_round);
        }
        if (zip.equals("")) {
            isFieldEmpty = true;
            etZip.setBackgroundResource(R.drawable.edittext_error);
            etZip.setError("Empty Field");
            etZip.requestFocus();
        } else if(zip.length() != 6) {
            isFieldEmpty = true;
            etZip.setBackgroundResource(R.drawable.edittext_error);
            etZip.setError("Zip Code cannot be less than 6");
            etZip.requestFocus();
        } else {
            isFieldEmpty = false;
            etZip.setBackgroundResource(R.drawable.edittext_round);
        }
        if (state.equals("")) {
            isFieldEmpty = true;
            etState.setError("Empty Field");
            etState.requestFocus();
            etState.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etState.setBackgroundResource(R.drawable.edittext_round);
        }
        if (country.equals("")) {
            isFieldEmpty = true;
            etCountry.setError("Empty Field");
            etCountry.requestFocus();
            etCountry.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etCountry.setBackgroundResource(R.drawable.edittext_round);
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void getDetails() {
        showLoader();
        RetrofitClient.getApiService().getContractType().enqueue(new Callback<List<Contract_Type>>() {
            @Override
            public void onResponse(Call<List<Contract_Type>> call, Response<List<Contract_Type>> response) {
                List<Contract_Type> contract_types = response.body();
                if(contract_types != null && contract_types.size() > 0) {
                    AddProjectActivity.this.contract_types = contract_types;
                    getProjectTypes();
                }
            }

            @Override
            public void onFailure(Call<List<Contract_Type>> call, Throwable t) {
                Log.e("getcontractdetails",""+t.getMessage());
                stopLoader();
            }
        });

    }

    private void getProjectTypes() {
        RetrofitClient.getApiService().getProjectType().enqueue(new Callback<List<Project_Type>>() {
            @Override
            public void onResponse(Call<List<Project_Type>> call, Response<List<Project_Type>> response) {
                List<Project_Type> project_types = response.body();
                if(project_types != null && project_types.size() > 0) {
                    AddProjectActivity.this.project_types = project_types;
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<Project_Type>> call, Throwable t) {
                Log.e("getcontractdetails",""+t.getMessage());
                stopLoader();
            }
        });
    }

    private boolean is_valid_data() {
        boolean isValid = false;
        if(mEdtProjectName.getText().toString().isEmpty() || mEdtProjectName.getText().toString().equalsIgnoreCase("")) {
            mEdtProjectName.setError("Empty Field!");
            mEdtProjectName.requestFocus();
            isValid = false;
        } else if(mEdtProjectType.getText().toString().isEmpty() || mEdtProjectType.getText().toString().equalsIgnoreCase("")) {
            mEdtProjectType.setError("Please select project type");
            mEdtProjectType.requestFocus();
            isValid = false;
        } else if(mEdtContractType.getText().toString().isEmpty() || mEdtContractType.getText().toString().equalsIgnoreCase("")) {
            mEdtContractType.setError("Please select contract type");
            mEdtContractType.requestFocus();
            isValid = false;
        } else if(mEdtAddress.getText().toString().isEmpty() || mEdtAddress.getText().toString().equalsIgnoreCase("")) {
            mEdtAddress.setError("Empty Field!");
            mEdtAddress.requestFocus();
            isValid = false;
        } else if(mEdtAddClient.getText().toString().isEmpty() || mEdtAddClient.getText().toString().equalsIgnoreCase("")) {
            mEdtAddClient.setError("Please select client");
            mEdtAddClient.requestFocus();
            isValid = false;
        } else if(client == null) {
            mEdtAddClient.setError("Please select client");
            mEdtAddClient.requestFocus();
            isValid = false;
        } else if(start_date == 0) {
            mEdtPStartDate.setError("Please select start date");
            mEdtPStartDate.requestFocus();
            isValid = false;
        } else if(end_date == 0) {
            mEdtPEndDate.setError("Please select end date");
            mEdtPEndDate.requestFocus();
            isValid = false;
        } else if(mEdtPStartDate.getText().toString().isEmpty() || mEdtPStartDate.getText().toString().equalsIgnoreCase("")) {
            mEdtPStartDate.setError("Please select start date");
            mEdtPStartDate.requestFocus();
            isValid = false;
        } else if(mEdtPEndDate.getText().toString().isEmpty() || mEdtPEndDate.getText().toString().equalsIgnoreCase("")) {
            mEdtPEndDate.setError("Please select end date");
            mEdtPEndDate.requestFocus();
            isValid = false;
        } else if(mTvPDuration.getText().toString().isEmpty() || mTvPDuration.getText().toString().equalsIgnoreCase("") || mTvPDuration.getText().toString().equalsIgnoreCase("0 Days")) {
            Toast.makeText(this, "Duration not calculated", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            mEdtProjectName.setError(null);
            mEdtProjectType.setError(null);
            mEdtContractType.setError(null);
            mEdtAddress.setError(null);
            mEdtPStartDate.setError(null);
            mEdtPEndDate.setError(null);
            mEdtAddClient.setError(null);
            isValid = true;
        }

        return isValid;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddProjectActivity.this, ProjectManagementMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}