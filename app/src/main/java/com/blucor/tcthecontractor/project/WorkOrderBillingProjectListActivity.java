package com.blucor.tcthecontractor.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.fragments.SplitViewFragment;
import com.blucor.tcthecontractor.project.fragments.TabViewFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderBillingProjectListActivity extends BaseAppCompatActivity {
    private List<ProjectsModel> mList;
    private User user;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_billing_project_list);

        mList = new ArrayList<>();
        user = DatabaseUtil.on().getAllUser().get(0);
        getProjectList();
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);

        fragment = new TabViewFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new TabViewFragment();
                        break;
                    case 1:
                        fragment = new SplitViewFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void getProjectList() {
        int contractor_id = user.server_id;

        showLoader();
        try {
            RetrofitClient.getApiService().getAllProjectContractorType(contractor_id).enqueue(new Callback<List<ProjectsModel>>() {
                @Override
                public void onResponse(Call<List<ProjectsModel>> call, Response<List<ProjectsModel>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        mList.addAll(response.body());
                        //mAdapter.notifyDataSetChanged();
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
        }
    }
}