package com.blucor.tcthecontractor.project.activity;

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
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.ProjectMenuActivity;
import com.blucor.tcthecontractor.rv_adapters.ActivityExpandableListViewAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * @created by swapna
 *
 * Activity to display main activity list with sub activity
 */
public class AddProjectActActivity extends BaseAppCompatActivity {
    private ProjectsModel project;
    private ExpandableListView exp_lst_main_activity;
    private FloatingActionButton add_activity;
    private List<ActivityResponseModel> activityResponses;
    private ActivityExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project_act);

        exp_lst_main_activity = findViewById(R.id.exp_lst_main_activity);
        add_activity = findViewById(R.id.fab_activity_add);

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
        adapter = new ActivityExpandableListViewAdapter(AddProjectActActivity.this,activityResponses,project);
        exp_lst_main_activity.setAdapter(adapter);
    }


    public void onSlideViewButtonClick(View view) {
        if (project != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppKeys.PROJECT, project);
            ScreenHelper.redirectToClass(AddProjectActActivity.this, AddActivityDetailsActivity.class,bundle);
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this,ProjectMenuActivity.class,bundle);
    }
}