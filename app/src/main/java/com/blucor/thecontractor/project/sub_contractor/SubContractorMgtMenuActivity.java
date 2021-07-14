package com.blucor.thecontractor.project.sub_contractor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.project.ProjectMenuActivity;
import com.blucor.thecontractor.project.activity.AddProjectActActivity;
import com.blucor.thecontractor.project.activity.BillingDetailsDisplayListActivity;
import com.blucor.thecontractor.project.activity.WorkOrderDisplayListActivity;
import com.blucor.thecontractor.utility.ScreenHelper;

public class SubContractorMgtMenuActivity extends AppCompatActivity {
    private ProjectsModel project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_contractor_mgt_menu);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
            }
        } catch (Exception e) {

        }
    }

    public void onClickToWorkOrder(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, WorkOrderDisplayListActivity.class,bundle);
    }

    public void onClickToBilling(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, BillingDetailsDisplayListActivity.class,bundle);
    }

    public void onClickToDownloadReport(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, SubContractorDownloadReportActivity.class,bundle);
    }

    public void onClickToSubContractor(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, AddWorkOrderToProjectActivity.class,bundle);
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this, ProjectMenuActivity.class,bundle);
    }
}