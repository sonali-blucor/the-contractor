package com.blucor.tcthecontractor.project.sub_contractor;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class SelectSubContractorListActivity extends BaseAppCompatActivity {
    private TextInputEditText edt_first_name;
    private TextInputEditText edt_last_name;
    private TextInputEditText edt_mobile;
    private TextInputEditText edt_email;
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
        String toolBarTitle ="Sub Contractor";

        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        rl_add_sub_contractor = findViewById(R.id.rl_add_sub_contractor);
        edt_search_work_form = findViewById(R.id.edt_search_sub_contractor_work_form);
        recycler_view = findViewById(R.id.recycler_view_sub_contractor);
        btn_submit_list = findViewById(R.id.btn_submit_sub_contractor_list);
        btn_register = findViewById(R.id.btn_register);


        //Start of dynamic title code---------------------
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
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

        mAdapter = new SubContractorListAdapter(SelectSubContractorListActivity.this,subContractors);
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
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        User user = DatabaseUtil.on().getAllUser().get(0);
        int id = user.server_id;

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().storeSubContractor(fname,lname,id,mobile,email,"NULL").enqueue(new Callback<SubContractor>() {
                @Override
                public void onResponse(Call<SubContractor> call, Response<SubContractor> response) {
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
                    Toast.makeText(SelectSubContractorListActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    private boolean validateData() {
        boolean isValid = false;
        String fanme = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String error = "Empty Feild";

        if(fanme.isEmpty() || fanme.equalsIgnoreCase("")) {
            edt_first_name.setError(error);
            edt_first_name.requestFocus();
            isValid = false;
        } else if(lname.isEmpty() || lname.equalsIgnoreCase("")) {
            edt_last_name.setError(error);
            edt_last_name.requestFocus();
            isValid = false;
        } else if(email.isEmpty() || email.equalsIgnoreCase("")) {
            edt_email.setError(error);
            edt_email.requestFocus();
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
            edt_first_name.setError(null);
            edt_last_name.setError(null);
            edt_email.setError(null);
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
                        if (!contains(prevSubContractors,subContractor)) {
                            subContractors.add(subContractor);
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SelectSubContractorListActivity.this, "There is no sub contractor", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<SubContractor>> call, Throwable t) {
                Toast.makeText(SelectSubContractorListActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private boolean contains(ArrayList<SubContractor> list,SubContractor listitem) {
        boolean is_contain = false;
        for (SubContractor subContractor : list) {
            if ((subContractor.id == listitem.id) && (subContractor.fname.equalsIgnoreCase(listitem.fname))
                    && (subContractor.lname.equalsIgnoreCase(listitem.lname))
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