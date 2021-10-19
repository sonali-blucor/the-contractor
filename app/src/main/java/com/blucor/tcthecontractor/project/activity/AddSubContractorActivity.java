package com.blucor.tcthecontractor.project.activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.custom.CustomSubContractorAutoCompleteTextChangedListener;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.SubContractor;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.rv_adapters.AutocompleteSubContractorCustomArrayAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddSubContractorActivity extends BaseAppCompatActivity {
    private TextInputEditText edt_firm_name;
    private TextInputEditText edt_full_name;
    private TextInputEditText edt_address;
    private TextInputEditText edt_mobile;
    private TextInputEditText edt_email;
    private TextInputEditText edt_pan_cart_no;
    private TextInputEditText edt_aadhar_cart_no;
    private TextInputEditText edt_gst_no;
    private TextInputEditText edt_bank_details;
    private SubContractor subContractor;
    private Button btn_register;
    public EditText edt_search;
    public RelativeLayout rl_search;
    public ScrollView rl_add_sub_contractor;
    public ListView lst_search_sub_contractor;
    public AutocompleteSubContractorCustomArrayAdapter adapter;
    private ArrayList<SubContractor> contractors;
    private ProjectsModel project;
   // private String sub_contractor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_contractor);

        edt_firm_name = findViewById(R.id.edt_firm_name);
        edt_full_name = findViewById(R.id.edt_full_name);
        edt_address = findViewById(R.id.edt_address);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_pan_cart_no = findViewById(R.id.edt_pan_cart_no);
        edt_aadhar_cart_no = findViewById(R.id.edt_aadhar_cart_no);
        edt_gst_no = findViewById(R.id.edt_gst_no);
        edt_bank_details = findViewById(R.id.edt_bank_details);

        edt_search = findViewById(R.id.edt_search_sub_contractor);
        btn_register = findViewById(R.id.btn_register);
        rl_search = findViewById(R.id.rl_search_sub_contractor);
        rl_add_sub_contractor = findViewById(R.id.sv_add_sub_contractor);
        lst_search_sub_contractor = findViewById(R.id.lst_sub_contractor);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSubContractor();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
            loadAllSubContractors();
        }
    }

    public void loadAllSubContractors() {
        showLoader();
        int id = project.id;
        RetrofitClient.getApiService().getAllProjectSubContractors(id).enqueue(new Callback<List<SubContractor>>() {
            @Override
            public void onResponse(Call<List<SubContractor>> call, Response<List<SubContractor>> response) {
                if (response.code() == 200 && response.body() != null) {
                    contractors = (ArrayList<SubContractor>) response.body();
                    setListViewAdapter(contractors);
                } else {
                    Toast.makeText(AddSubContractorActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<SubContractor>> call, Throwable t) {
                Toast.makeText(AddSubContractorActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });

        /*
        gets all sub contractor by id
        RetrofitClient.getApiService().getAllSubContractorsByContractor(id).enqueue(new Callback<SubContractorAddSearchModel>() {
            @Override
            public void onResponse(Call<SubContractorAddSearchModel> call, Response<SubContractorAddSearchModel> response) {
                if(response.code() == 200 && response.body() != null) {
                    SubContractorAddSearchModel model = response.body();
                    contractors = model.sub_contractors;
                    //sub_contractor_id = model.sub_contractor_id;

                    //.setText(""+client_id);
                    setListViewAdapter(contractors);

                } else {
                    Toast.makeText(AddSubContractorActivity.this, "Unable to get sub contractors", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<SubContractorAddSearchModel> call, Throwable t) {
                Toast.makeText(AddSubContractorActivity.this, "Error in loading sub contractor : "+t.getMessage(), Toast.LENGTH_LONG).show();
                stopLoader();
            }
        });*/
    }

    private void registerSubContractor() {
        String firm_name = edt_firm_name.getText().toString();
        String full_name = edt_full_name.getText().toString();
        String address = edt_address.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String email = edt_email.getText().toString();
        String pan_cart_no = edt_pan_cart_no.getText().toString();
        String aadhar_cart_no = edt_aadhar_cart_no.getText().toString();
        String gst_no = edt_gst_no.getText().toString();
        String bank_details = edt_bank_details.getText().toString();
        User user = DatabaseUtil.on().getAllUser().get(0);
        int id = user.server_id;

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().storeSubContractor(firm_name, full_name, id, mobile, email, "NULL",address,pan_cart_no,
                    aadhar_cart_no,gst_no,bank_details).enqueue(new Callback<SubContractor>() {
                @Override
                public void onResponse(Call<SubContractor> call, Response<SubContractor> response) {
                    if (response.code() == 200 && response.body() != null) {
                        subContractor = response.body();
                        sendIntent();
                    } else {
                        Toast.makeText(AddSubContractorActivity.this, "Error in inserting sub contractor", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<SubContractor> call, Throwable t) {
                    Toast.makeText(AddSubContractorActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    private void sendIntent(){
        Intent intent = new Intent();
        intent.putExtra(AppKeys.SUB_CONTRACTOR, subContractor);
        setResult(AppKeys.SUB_CONTRACTOR_RESULT, intent);
        finish();
    }

    private boolean validateData() {
        boolean isValid = false;
        String firm_name = edt_firm_name.getText().toString();
        String full_name = edt_full_name.getText().toString();
        String address = edt_address.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String email = edt_email.getText().toString();
        String pan_cart_no = edt_pan_cart_no.getText().toString();
        String aadhar_cart_no = edt_aadhar_cart_no.getText().toString();
        String gst_no = edt_gst_no.getText().toString();
        String error = "Empty Feild";

        if (firm_name.isEmpty() || firm_name.equalsIgnoreCase("")) {
            edt_firm_name.setError(error);
            edt_firm_name.requestFocus();
            isValid = false;
        } else if (full_name.isEmpty() || full_name.equalsIgnoreCase("")) {
            edt_full_name.setError(error);
            edt_full_name.requestFocus();
            isValid = false;
        } else if (address.isEmpty() || address.equalsIgnoreCase("")) {
            edt_address.setError(error);
            edt_address.requestFocus();
            isValid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("Invalid Email");
            edt_email.requestFocus();
            isValid = false;
        }else if(!Patterns.PHONE.matcher(mobile).matches()) {
            edt_mobile.setError("Invalid Mobile Number");
            edt_mobile.requestFocus();
            isValid = false;
        } else if(mobile.length() < 10) {
            edt_mobile.setError("Invalid Mobile Number");
            edt_mobile.requestFocus();
            isValid = false;
        } else if(mobile.isEmpty() || mobile.equalsIgnoreCase("")) {
            edt_mobile.setError(error);
            edt_mobile.requestFocus();
            isValid = false;
        } else {
            edt_firm_name.setError(null);
            edt_full_name.setError(null);
            edt_email.setError(null);
            edt_address.setError(null);
            edt_mobile.setError(null);
            isValid = true;
        }

        return isValid;
    }

    public void setListViewAdapter(ArrayList<SubContractor> sub_contractors) {
        if (sub_contractors.size() <= 0) {
            rl_add_sub_contractor.setVisibility(View.VISIBLE);
            lst_search_sub_contractor.setVisibility(View.GONE);
            rl_search.setVisibility(View.GONE);
        } else {
            rl_add_sub_contractor.setVisibility(View.GONE);
            lst_search_sub_contractor.setVisibility(View.VISIBLE);
            rl_search.setVisibility(View.VISIBLE);
        }
        edt_search.addTextChangedListener(new CustomSubContractorAutoCompleteTextChangedListener(AddSubContractorActivity.this));

        // set the custom ArrayAdapter
        adapter = new AutocompleteSubContractorCustomArrayAdapter(AddSubContractorActivity.this, R.layout.list_view_row, sub_contractors);
        try {
            lst_search_sub_contractor.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("TAG",e.getMessage());
        }
        lst_search_sub_contractor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subContractor = sub_contractors.get(position);
                edt_search.setText("" + subContractor.fname + " " + subContractor.lname);
                sendIntent();
            }
        });
    }

    @Override
    public void onBackPressed() {
        sendIntent();
    }
}