package com.blucor.tcthecontractor.project.workorderbilling;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.workorderbilling.fragments.BillingFragment;
import com.blucor.tcthecontractor.project.workorderbilling.fragments.SplitViewFragment;
import com.blucor.tcthecontractor.project.workorderbilling.fragments.WorkOrderFragment;
import com.blucor.tcthecontractor.rv_adapters.ProjectSpinnerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderBillingProjectListActivity extends BaseAppCompatActivity {
    private ArrayList<ProjectsModel> mList;
    private TabLayout tabLayout;
    private TabLayout tabLayout_fragment;
    private FrameLayout frameLayout;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Spinner spr_project_list;
    private ProjectSpinnerAdapter mAdapter;
    private ProjectsModel selected_project;
    private ArrayList<WorkOrderModel> workOrders;
    private ArrayList<BilliModel> bills;
    private float total_work_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_billing_project_list);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout_fragment = findViewById(R.id.tabLayout_frgament);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout_fragemnt);
        spr_project_list =findViewById(R.id.spr_project_list);

        setUpFragemnt();
        getProjectList();
    }

    private void getProjectList() {
        User user = DatabaseUtil.on().getAllUser().get(0);
        int contractor_id = user.server_id;

        showLoader();
        try {
            RetrofitClient.getApiService().getAllProjectContractorType(contractor_id).enqueue(new Callback<List<ProjectsModel>>() {
                @Override
                public void onResponse(Call<List<ProjectsModel>> call, Response<List<ProjectsModel>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        mList = new ArrayList<>();
                        mList.addAll(response.body());
                        setupAdapter();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<ProjectsModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } catch(Exception e) {
            Log.e("view project",e.getMessage());
            stopLoader();
        }
    }

    private void setupAdapter() {
        mAdapter = new ProjectSpinnerAdapter(mList,WorkOrderBillingProjectListActivity.this);
        spr_project_list.setAdapter(mAdapter);
        spr_project_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_project = mList.get(position);
                if (tabLayout.getSelectedTabPosition() == 0) {
                    if(tabLayout_fragment.getSelectedTabPosition() == 0) {
                        setUpWorkOrder();
                    } else {
                        setUpBillOnProjectChange();
                    }
                } else if(tabLayout.getSelectedTabPosition() == 1) {
                    setUpSplitView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpBillOnProjectChange() {
        if (selected_project != null) {
            showLoader();
            RetrofitClient.getApiService().getAllWorkOrderByProjectId(selected_project.id).enqueue(new Callback<List<WorkOrderModel>>() {
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

        if (selected_project != null) {
            showLoader();
            RetrofitClient.getApiService().getAllWorkOrderByProjectId(selected_project.id).enqueue(new Callback<List<WorkOrderModel>>() {
                @Override
                public void onResponse(Call<List<WorkOrderModel>> call, Response<List<WorkOrderModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        tabLayout_fragment.setVisibility(View.VISIBLE);
                        workOrders = new ArrayList<>();
                        assert response.body() != null;
                        workOrders.addAll(response.body());
                        total_work_order = getTotalWorkOrder();
                        fragment = new WorkOrderFragment(workOrders,selected_project);
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
        if (selected_project != null) {
            total_work_order = getTotalWorkOrder();
            showLoader();
            RetrofitClient.getApiService().getAllBillByProjectId(selected_project.id).enqueue(new Callback<List<BilliModel>>() {
                @Override
                public void onResponse(Call<List<BilliModel>> call, Response<List<BilliModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        tabLayout_fragment.setVisibility(View.VISIBLE);
                        bills = new ArrayList<>();
                        assert response.body() != null;
                        bills.addAll(response.body());
                        fragment = new BillingFragment(total_work_order,bills);
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

    private float getTotalWorkOrder() {
        float tot_amount = 0;
        for (int i = 0; i < workOrders.size(); i++) {
            WorkOrderModel model = (WorkOrderModel) workOrders.get(i);
            tot_amount = tot_amount + model.amount;
        }
        return tot_amount;
    }

    private void setUpFragemnt() {


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        setUpWorkOrder();
                        break;
                    case 1:
                        setUpSplitView();
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

    private void setUpSplitView() {
        tabLayout_fragment.setVisibility(View.GONE);
        fragment = new SplitViewFragment(selected_project);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout_fragemnt, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}