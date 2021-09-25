package com.blucor.tcthecontractor.project;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.utility.ScreenHelper;

public class ProjectManagementMenuActivity extends AppCompatActivity {

    private int highlightProjects = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_management_menu);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECTS)) {
                highlightProjects = bundle.getInt(AppKeys.PROJECTS);
                Log.e("he", highlightProjects + "");
                if (highlightProjects == 1) {
                    LinearLayout linearLayout = findViewById(R.id.llv_projects);
                    Drawable[] colorDrawables = {getResources().getDrawable(R.drawable.select_menu_press),
                            getResources().getDrawable(R.drawable.selector_menu)};
                    TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
                    linearLayout.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(2000);
                }
            }
        } catch (Exception e) {

        }
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
        //ScreenHelper.redirectToClass(this, WorkOrderBillingProjectListActivity.class);
    }
}