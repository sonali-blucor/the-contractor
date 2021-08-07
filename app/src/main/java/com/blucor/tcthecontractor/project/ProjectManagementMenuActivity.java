package com.blucor.tcthecontractor.project;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.utility.ScreenHelper;

public class ProjectManagementMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_management_menu);
    }

    public void onClickToCompletedProject(View view) {
        ScreenHelper.redirectToClass(ProjectManagementMenuActivity.this, CompletedProjectsActivity.class);
    }

    public void onClickToProjects(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE, false);
        ScreenHelper.redirectToClass(ProjectManagementMenuActivity.this, ProjectListActivity.class, bundle);
    }

    public void onClickToAddProject(View view) {
        ScreenHelper.redirectToClass(ProjectManagementMenuActivity.this, AddProjectActivity.class);
    }

    public void onClickToDownloadAndReport(View view) {

    }

    public void onClickToWorkOrderManagement(View view) {
        ScreenHelper.redirectToClass(this, WorkOrderBillingProjectListActivity.class);
    }
}