package com.blucor.tcthecontractor.project;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.project.fragments.BillingFragment;
import com.blucor.tcthecontractor.project.fragments.SplitViewFragment;
import com.blucor.tcthecontractor.project.fragments.WorkOrderFragment;
import com.google.android.material.tabs.TabLayout;

public class WorkOrderBillingProjectListActivity extends BaseAppCompatActivity {
   /* private List<ProjectsModel> mList;
    private User user;*/
    TabLayout tabLayout;
    TabLayout tabLayout_fragment;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_billing_project_list);

       // mList = new ArrayList<>();
        /*user = DatabaseUtil.on().getAllUser().get(0);
        getProjectList();*/
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout_fragment = findViewById(R.id.tabLayout_frgament);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);

        tabLayout_fragment.setVisibility(View.VISIBLE);
        fragment = new WorkOrderFragment();
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
                        tabLayout_fragment.setVisibility(View.VISIBLE);
                        fragment = new WorkOrderFragment();
                        break;
                    case 1:
                        tabLayout_fragment.setVisibility(View.GONE);
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

        tabLayout_fragment.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                tabLayout_fragment.setVisibility(View.VISIBLE);
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new WorkOrderFragment();
                        break;
                    case 1:
                        fragment = new BillingFragment();
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

    /*private void getProjectList() {
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
    }*/
}