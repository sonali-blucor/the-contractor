package com.blucor.thecontractor.project;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.project.activity.AddActivityDetailsActivity;
import com.blucor.thecontractor.project.material.MaterialMenuActivity;
import com.blucor.thecontractor.utility.ScreenHelper;

public class ProjectMenuActivity extends AppCompatActivity {
    private ProjectsModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
            }
        } catch (Exception e) {

        }
    }

    public void onClickToActivityManagement(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, AddActivityDetailsActivity.class,bundle);
    }

    public void onClickToSubContractorManagement(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, SubContractor.class,bundle);
    }

    public void onClickToMaterialManagement(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, MaterialMenuActivity.class,bundle);
    }

    public void onClickToProjectDetail(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, ProjectDetailsActivity.class,bundle);
    }

    public void onClickToProjectSchedule(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, ScheduleActivity.class,bundle);
    }
}