package com.blucor.tcthecontractor.project.sub_contractor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.SubContractor;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.sub_contractor.fragments.SubCBillingFragment;
import com.blucor.tcthecontractor.project.sub_contractor.fragments.SubCWorkOrderFragment;
import com.blucor.tcthecontractor.project.workorderbilling.WorkOrderBillingProjectListActivity;
import com.blucor.tcthecontractor.project.workorderbilling.fragments.BillingFragment;
import com.blucor.tcthecontractor.project.workorderbilling.fragments.WorkOrderFragment;
import com.blucor.tcthecontractor.rv_adapters.ProjectSpinnerAdapter;
import com.blucor.tcthecontractor.rv_adapters.SubContractorSpinnerAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubContractorWorkOrderActivity extends BaseAppCompatActivity {
    private ArrayList<SubContractor> mList;
    private TabLayout tabLayout_fragment;
    private FrameLayout frameLayout;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Spinner spr_sub_contractor_list;
    private SubContractorSpinnerAdapter mAdapter;
    private SubContractor selected_sub_contractor;
    private ArrayList<WorkOrderModel> workOrders;
    private ArrayList<BilliModel> bills;
    private long total_work_order;

    private ProjectsModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_contractor_work_order);

        tabLayout_fragment = findViewById(R.id.tabLayout_frgament);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout_fragemnt);
        spr_sub_contractor_list = findViewById(R.id.spr_sub_contractor_list);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
                //toolBarTitle = project.project_name+"-"+project.id;
                setUpFragemnt();
                getProjectList();

            }
        } catch (Exception e) {

        }
    }



    private void getProjectList() {
        User user = DatabaseUtil.on().getAllUser().get(0);
        int contractor_id = user.server_id;

        showLoader();
        try {
            RetrofitClient.getApiService().getAllSubContractorType(contractor_id,project.id).enqueue(new Callback<List<SubContractor>>() {
                @Override
                public void onResponse(Call<List<SubContractor>> call, Response<List<SubContractor>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        mList = new ArrayList<>();
                        mList.addAll(response.body());
                        setupAdapter();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<SubContractor>> call, Throwable t) {
                    stopLoader();
                }
            });
        } catch(Exception e) {
            Log.e("view project",e.getMessage());
            stopLoader();
        }
    }

    private void setupAdapter() {
        mAdapter = new SubContractorSpinnerAdapter(mList, SubContractorWorkOrderActivity.this);
        spr_sub_contractor_list.setAdapter(mAdapter);
        spr_sub_contractor_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_sub_contractor = mList.get(position);
                  if(tabLayout_fragment.getSelectedTabPosition() == 0) {
                        setUpWorkOrder();
                    } else {
                        setUpBillOnProjectChange();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpBillOnProjectChange() {
        if (selected_sub_contractor != null) {
            showLoader();
            RetrofitClient.getApiService().getAllWorkOrderBySubContractorId(project.id,selected_sub_contractor.id).enqueue(new Callback<List<WorkOrderModel>>() {
                @Override
                public void onResponse(Call<List<WorkOrderModel>> call, Response<List<WorkOrderModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        tabLayout_fragment.setVisibility(View.VISIBLE);
                        workOrders = new ArrayList<>();
                        assert response.body() != null;
                        workOrders.addAll(response.body());
                        total_work_order = getTotalWorkOrder();
                        setUpBill();
                    } else {
                        stopLoader();
                    }
                }

                @Override
                public void onFailure(Call<List<WorkOrderModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } else {
            Toast.makeText(this, "Please select project", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpWorkOrder() {

        if (selected_sub_contractor != null) {
            showLoader();
            RetrofitClient.getApiService().getAllWorkOrderBySubContractorId(project.id,selected_sub_contractor.id).enqueue(new Callback<List<WorkOrderModel>>() {
                @Override
                public void onResponse(Call<List<WorkOrderModel>> call, Response<List<WorkOrderModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        tabLayout_fragment.setVisibility(View.VISIBLE);
                        workOrders = new ArrayList<>();
                        assert response.body() != null;
                        workOrders.addAll(response.body());
                        total_work_order = getTotalWorkOrder();
                        fragment = new SubCWorkOrderFragment(workOrders,project,selected_sub_contractor);
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frameLayout_fragemnt, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<WorkOrderModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } else {
            Toast.makeText(this, "Please select project", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpBill() {
        if (selected_sub_contractor != null) {
            total_work_order = getTotalWorkOrder();
            showLoader();
            RetrofitClient.getApiService().getAllBillBySubContractorId(project.id,selected_sub_contractor.id).enqueue(new Callback<List<BilliModel>>() {
                @Override
                public void onResponse(Call<List<BilliModel>> call, Response<List<BilliModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        tabLayout_fragment.setVisibility(View.VISIBLE);
                        bills = new ArrayList<>();
                        assert response.body() != null;
                        bills.addAll(response.body());
                        fragment = new SubCBillingFragment(total_work_order,bills,project,selected_sub_contractor);
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frameLayout_fragemnt, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<BilliModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } else {
            Toast.makeText(this, "Please select project", Toast.LENGTH_SHORT).show();
        }
    }

    private long getTotalWorkOrder() {
        long tot_amount = 0;
        for (int i = 0; i < workOrders.size(); i++) {
            WorkOrderModel model = (WorkOrderModel) workOrders.get(i);
            tot_amount = tot_amount + model.amount;
        }
        return tot_amount;
    }

    private void setUpFragemnt() {

        tabLayout_fragment.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        setUpWorkOrder();
                        break;
                    case 1:
                        setUpBill();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this, SubContractorMgtMenuActivity.class,bundle);
    }
}