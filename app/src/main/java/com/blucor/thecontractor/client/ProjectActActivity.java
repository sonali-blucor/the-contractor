package com.blucor.thecontractor.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ActivityResponseModel;
import com.blucor.thecontractor.models.ClientProjectActivityModel;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.SubActivityModel;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.project.activity.AddProjectActActivity;
import com.blucor.thecontractor.rv_adapters.ActivityExpandableListViewAdapter;
import com.blucor.thecontractor.rv_adapters.ActivityExpandableListViewClientAdapter;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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
                SubActivityModel subActivity = activityResponses.get(groupPosition).subActivities.get(childPosition);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppKeys.SUB_ACTIVITY,subActivity);
               // ScreenHelper.redirectToClass(ProjectActActivity.this,"class",bundle);
                return true;
            }
        });
    }
}