package com.blucor.tcthecontractor.project;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.project.activity.AddProjectActActivity;
import com.blucor.tcthecontractor.project.material.AddMaterialActivity;
import com.blucor.tcthecontractor.project.sub_contractor.SubContractorMgtMenuActivity;
import com.blucor.tcthecontractor.utility.ScreenHelper;

public class ProjectMenuActivity extends AppCompatActivity {
    private ProjectsModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);
        String toolBarTitle ="Projects";
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
                toolBarTitle = project.project_name+"-"+project.id;
            }
        } catch (Exception e) {

        }

        //Start of dynamic title code---------------------
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle(toolBarTitle);
        }
        //End of dynamic title code----------------------
    }

    public void onClickToActivityManagement(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, AddProjectActActivity.class,bundle);
    }

    public void onClickToSubContractorManagement(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, SubContractorMgtMenuActivity.class,bundle);
    }

    public void onClickToMaterialManagement(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, AddMaterialActivity.class,bundle);
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

    @Override
    public void onBackPressed() {
        ScreenHelper.redirectToClass(this, ProjectListActivity.class);
        finish();
    }
}