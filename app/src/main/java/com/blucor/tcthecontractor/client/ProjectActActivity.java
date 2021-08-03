package com.blucor.tcthecontractor.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ActivityResponseModel;
import com.blucor.tcthecontractor.models.ClientProjectActivityModel;
import com.blucor.tcthecontractor.models.SubActivityModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.ActivityExpandableListViewClientAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;

import java.util.List;

public class ProjectActActivity extends BaseAppCompatActivity {

    private ExpandableListView exp_lst_main_activity;
    private List<ActivityResponseModel> activityResponses;
    private ActivityExpandableListViewClientAdapter adapter;
    private ClientProjectActivityModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_act);

        exp_lst_main_activity = findViewById(R.id.exp_lst_main_activity);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        getProjectActivities();
    }

    private void getProjectActivities() {
        showLoader();
        RetrofitClient.getApiService().getActivityList(project.id).enqueue(new Callback<List<ActivityResponseModel>>() {
            @Override
            public void onResponse(Call<List<ActivityResponseModel>> call, Response<List<ActivityResponseModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    activityResponses = response.body();
                    setupAdapter();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<ActivityResponseModel>> call, Throwable t) {
                stopLoader();
            }
        });
    }

    private void setupAdapter() {
        adapter = new ActivityExpandableListViewClientAdapter(ProjectActActivity.this,activityResponses,project);
        exp_lst_main_activity.setAdapter(adapter);
        exp_lst_main_activity.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SubActivityModel subActivity = adapter.getChild(groupPosition,childPosition);
                if (subActivity != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppKeys.SUB_ACTIVITY, subActivity);
                    ScreenHelper.redirectToClass(ProjectActActivity.this, ClientProjectSubActDetailsActivity.class, bundle);
                }
                return true;
            }
        });
    }
}