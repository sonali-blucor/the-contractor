package com.blucor.thecontractor.project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.blucor.thecontractor.models.ProjectActivityModel;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.project.ProjectDetailsActivity;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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
                }

                stopLoader();
            }

            @Override
            public void onFailure(Call<List<ActivityResponseModel>> call, Throwable t) {
                stopLoader();
            }
        });
    }


    public void onSlideViewButtonClick(View view) {
        if (project != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppKeys.PROJECT, project);
            ScreenHelper.redirectToClass(AddProjectActActivity.this, AddActivityDetailsActivity.class,bundle);
        }
    }
}