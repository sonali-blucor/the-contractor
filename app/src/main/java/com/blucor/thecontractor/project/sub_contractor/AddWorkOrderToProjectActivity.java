package com.blucor.thecontractor.project.sub_contractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ArrayListSubContractor;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.ServerResponseModel;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.project.ProjectListActivity;
import com.blucor.thecontractor.rv_adapters.SubContractorListWorkOrderAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddWorkOrderToProjectActivity extends BaseAppCompatActivity {
    private ArrayList<SubContractor> subContractors;
    private ArrayList<SubContractor> prevSubContractors;
    private RecyclerView rv_sub_contractor_work_order;
    private FloatingActionButton btn_add_sub_contractor;
    private SubContractorListWorkOrderAdapter mAdapter;
    private ProjectsModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_order_to_project);

        rv_sub_contractor_work_order = findViewById(R.id.rv_sub_contractor_work_order);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_sub_contractor_work_order.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        btn_add_sub_contractor = findViewById(R.id.btn_add_sub_contractor);
        btn_add_sub_contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent();
            }
        });

        subContractors = new ArrayList<>();
        mAdapter = new SubContractorListWorkOrderAdapter(AddWorkOrderToProjectActivity.this,subContractors);
        rv_sub_contractor_work_order.setAdapter(mAdapter);
        getSubContractors();
    }

    private void startIntent(){
        Intent intent = new Intent(AddWorkOrderToProjectActivity.this, SelectSubContractorListActivity.class);
        intent.putExtra(AppKeys.PREV_SUBCONTRACTORS,prevSubContractors);
        intent.putExtra(AppKeys.PROJECT,project);
        startActivityForResult(intent, AppKeys.SUB_CONTRACTOR_LIST_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppKeys.SUB_CONTRACTOR_LIST_REQUEST_CODE) {
            try {
                if (data.hasExtra(AppKeys.SUB_CONTRACTOR_LIST)) {
                    //subContractors.clear();
                    ArrayList<SubContractor> subContractorsFromUser = data.getParcelableArrayListExtra(AppKeys.SUB_CONTRACTOR_LIST);
                    for (SubContractor subContractor : subContractorsFromUser) {
                        if (!contains(prevSubContractors,subContractor)) {
                            if(!contains(subContractors,subContractor)) {
                                subContractors.add(subContractor);
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.work_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_menu) {
            saveList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSubContractors() {
        showLoader();
        int id = project.id;
        RetrofitClient.getApiService().getAllWorkOrderSubContractors(id).enqueue(new Callback<List<SubContractor>>() {
            @Override
            public void onResponse(Call<List<SubContractor>> call, Response<List<SubContractor>> response) {
                if (response.code() == 200 && response.body() != null) {
                    prevSubContractors = (ArrayList<SubContractor>) response.body();
                    subContractors.addAll(prevSubContractors);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddWorkOrderToProjectActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<SubContractor>> call, Throwable t) {
                Toast.makeText(AddWorkOrderToProjectActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void saveList() {
        showLoader();
        int id = project.id;
        Gson gson = new Gson();
        String sub_contractor = gson.toJson(subContractors);
        RetrofitClient.getApiService().storeWorkOrder(id,sub_contractor).enqueue(new Callback<ServerResponseModel>() {
            @Override
            public void onResponse(Call<ServerResponseModel> call, Response<ServerResponseModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(AddWorkOrderToProjectActivity.this, "Sucessfully Saved", Toast.LENGTH_SHORT).show();
                    ScreenHelper.redirectToClass(AddWorkOrderToProjectActivity.this,ProjectListActivity.class);
                } else {
                    Toast.makeText(AddWorkOrderToProjectActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                }

                stopLoader();
            }

            @Override
            public void onFailure(Call<ServerResponseModel> call, Throwable t) {
                Toast.makeText(AddWorkOrderToProjectActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
}