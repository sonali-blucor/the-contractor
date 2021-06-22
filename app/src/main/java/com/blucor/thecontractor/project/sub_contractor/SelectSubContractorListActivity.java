package com.blucor.thecontractor.project.sub_contractor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.client.ClientAddAndSearchActivity;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.project.AddProjectActivity;
import com.blucor.thecontractor.rv_adapters.SubContractorListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectSubContractorListActivity extends BaseAppCompatActivity {
    private EditText edt_search_work_form;
    private RecyclerView recycler_view;
    private Button btn_submit_list;
    public ArrayList<SubContractor> selectedSubContractors;
    public List<SubContractor> subContractors;
    private SubContractorListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sub_contractor_list);

        edt_search_work_form = findViewById(R.id.edt_search_sub_contractor_work_form);
        recycler_view = findViewById(R.id.recycler_view_sub_contractor);
        btn_submit_list = findViewById(R.id.btn_submit_sub_contractor_list);
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

        loadAllSubContractors();
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
                if (response.code() == 200 && response.body() != null) {
                    subContractors.addAll(response.body());
                    mAdapter.notifyDataSetChanged(); /*= new SubContractorListAdapter(SelectSubContractorListActivity.this,subContractors);
                    recycler_view.setAdapter(mAdapter);*/
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
}