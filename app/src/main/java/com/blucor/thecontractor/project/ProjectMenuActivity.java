package com.blucor.thecontractor.project;

import android.os.Bundle;
import android.view.View;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.utility.ScreenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);
    }

    public void onClickToCompletedProject(View view) {
        ScreenHelper.redirectToClass(ProjectMenuActivity.this,CompletedProjectsActivity.class);
    }

    public void onClickToProjects(View view) {
        Bundle bundle =new Bundle();
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(ProjectMenuActivity.this,ProjectListActivity.class,bundle);
    }

    public void onClickToAddProject(View view) {
        ScreenHelper.redirectToClass(ProjectMenuActivity.this,AddProjectActivity.class);
    }

    public void onClickToActivityManagement(View view) {

    }

    public void onClickToSubContractorManagement(View view) {

    }

    public void onClickToMaterialManagement(View view) {

    }

    public void onClickToDownloadAndReport(View view) {

    }
}