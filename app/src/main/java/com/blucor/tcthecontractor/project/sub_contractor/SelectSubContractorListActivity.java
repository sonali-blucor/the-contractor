package com.blucor.tcthecontractor.project.sub_contractor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.text.InputFilter;
//akash

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.SubContractor;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.SubContractorListAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectSubContractorListActivity extends BaseAppCompatActivity {
    private TextInputEditText edt_firm_name;
    private TextInputEditText edt_full_name;
    private TextInputEditText edt_address;
    private TextInputEditText edt_mobile;
    private TextInputEditText edt_email;
    private TextInputEditText edt_pan_cart_no;
    private TextInputEditText edt_aadhar_cart_no;
    private TextInputEditText edt_gst_no;
    private TextInputEditText edt_bank_details;

    public RelativeLayout rl_add_sub_contractor;
    private Button btn_register;

    private EditText edt_search_work_form;
    public RecyclerView recycler_view;
    public Button btn_submit_list;
    public ArrayList<SubContractor> selectedSubContractors;
    public List<SubContractor> subContractors;
    public ArrayList<SubContractor> prevSubContractors;
    private SubContractorListAdapter mAdapter;
    private ProjectsModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sub_contractor_list);
        String toolBarTitle = "Sub Contractor";

        edt_firm_name = findViewById(R.id.edt_firm_name);
        edt_full_name = findViewById(R.id.edt_full_name);
        edt_address = findViewById(R.id.edt_address);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_pan_cart_no = findViewById(R.id.edt_pan_cart_no);
        edt_aadhar_cart_no = findViewById(R.id.edt_aadhar_cart_no);
        edt_gst_no = findViewById(R.id.edt_gst_no);
        edt_bank_details = findViewById(R.id.edt_bank_details);

        rl_add_sub_contractor = findViewById(R.id.rl_add_sub_contractor);
        edt_search_work_form = findViewById(R.id.edt_search_sub_contractor_work_form);
        recycler_view = findViewById(R.id.recycler_view_sub_contractor);
        btn_submit_list = findViewById(R.id.btn_submit_sub_contractor_list);
        btn_register = findViewById(R.id.btn_register);

        edt_gst_no.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt_pan_cart_no.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        //capital text (AKASH)

        //Start of dynamic title code---------------------
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(toolBarTitle);
        }
        //End of dynamic title code----------------------


        btn_register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                registerSubContractor();
            }
        });
        selectedSubContractors = new ArrayList<>();
        subContractors = new ArrayList<>();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);

        mAdapter = new SubContractorListAdapter(SelectSubContractorListActivity.this, subContractors);
        recycler_view.setAdapter(mAdapter);
        edt_search_work_form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_submit_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResults();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PREV_SUBCONTRACTORS)) {
            prevSubContractors = intent.getParcelableArrayListExtra(AppKeys.PREV_SUBCONTRACTORS);
            project = intent.getParcelableExtra(AppKeys.PROJECT);
            loadAllSubContractors();
        }
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

        if (validateData()) {
            showLoader();
            RetrofitClient.getApiService().storeSubContractor(firm_name, full_name, id, mobile, email, "NULL",address,pan_cart_no,
                    aadhar_cart_no,gst_no,bank_details
                    ).enqueue(new Callback<SubContractor>() {
                @Override
                public void onResponse(Call<SubContractor> call, Response<SubContractor> response) {
                    Log.e("sub-contractor",response.code()+"" );
                    if (response.code() == 200 && response.body() != null) {
                        SubContractor subContractor = response.body();
                        subContractors.add(subContractor);
                        mAdapter.notifyDataSetChanged();
                        recycler_view.setVisibility(View.VISIBLE);
                        btn_submit_list.setVisibility(View.VISIBLE);
                        rl_add_sub_contractor.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(SelectSubContractorListActivity.this, "Error in inserting sub contractor", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<SubContractor> call, Throwable t) {
                    Toast.makeText(SelectSubContractorListActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
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
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("Invalid Email");
            edt_email.requestFocus();
            isValid = false;
        } else if (!Patterns.PHONE.matcher(mobile).matches()) {
            edt_mobile.setError("Invalid Mobile Number");
            edt_mobile.requestFocus();
            isValid = false;
        } else if (mobile.length() < 10) {
            edt_mobile.setError("Invalid Mobile Number");
            edt_mobile.requestFocus();
            isValid = false;
        } else if (mobile.isEmpty() || mobile.equalsIgnoreCase("")) {
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

    private void setResults() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(AppKeys.SUB_CONTRACTOR_LIST, selectedSubContractors);
        setResult(AppKeys.SUB_CONTRACTOR_LIST_REQUEST_CODE, intent);
        finish();
    }


    private void loadAllSubContractors() {
        showLoader();
        RetrofitClient.getApiService().getAllSubContractors().enqueue(new Callback<List<SubContractor>>() {
            @Override
            public void onResponse(Call<List<SubContractor>> call, Response<List<SubContractor>> response) {
                subContractors.clear();
                if (response.code() == 200 && response.body() != null) {
                    ArrayList<SubContractor> subContractorsFromUser = (ArrayList<SubContractor>) response.body();
                    for (SubContractor subContractor : subContractorsFromUser) {
                        if (!contains(prevSubContractors, subContractor)) {
                            subContractors.add(subContractor);
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                    if(subContractorsFromUser.size() >0
                    ) {
                        recycler_view.setVisibility(View.VISIBLE);
                        btn_submit_list.setVisibility(View.VISIBLE);
                        rl_add_sub_contractor.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(SelectSubContractorListActivity.this, "There is no sub contractor", Toast.LENGTH_SHORT).show();
                    recycler_view.setVisibility(View.GONE);
                    btn_submit_list.setVisibility(View.GONE);
                    rl_add_sub_contractor.setVisibility(View.VISIBLE);
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<SubContractor>> call, Throwable t) {
                Toast.makeText(SelectSubContractorListActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
                recycler_view.setVisibility(View.GONE);
                btn_submit_list.setVisibility(View.GONE);
                rl_add_sub_contractor.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean contains(ArrayList<SubContractor> list, SubContractor listitem) {
        boolean is_contain = false;
        for (SubContractor subContractor : list) {
            if ((subContractor.id == listitem.id) && (subContractor.fname.equalsIgnoreCase(listitem.fname))
                    /*&& (subContractor.lname.equalsIgnoreCase(listitem.lname))*/
                    && subContractor.mobile.equalsIgnoreCase(listitem.mobile)
                    && subContractor.email.equalsIgnoreCase(listitem.email)) {
                is_contain = true;
            }
        }

        return is_contain;
    }

    @Override
    public void onBackPressed() {
        setResults();
    }
}