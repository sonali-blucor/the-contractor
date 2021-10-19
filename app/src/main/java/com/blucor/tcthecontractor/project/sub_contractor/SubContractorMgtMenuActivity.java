package com.blucor.tcthecontractor.project.sub_contractor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.project.ProjectMenuActivity;
import com.blucor.tcthecontractor.utility.ScreenHelper;

public class SubContractorMgtMenuActivity extends AppCompatActivity {
    private ProjectsModel project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_contractor_mgt_menu);
        String toolBarTitle ="Sub Contractor Management";

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
                //toolBarTitle = project.project_name+"-"+project.id;
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

    public void onClickToWorkOrder(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, SubContractorWorkOrderActivity.class,bundle);
    }

    public void onClickToBilling(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, SubContractorBillingActivity.class,bundle);
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
        ScreenHelper.redirectToClass(this, AddSubContractorsListToProjectActivity.class,bundle);
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this, ProjectMenuActivity.class,bundle);
    }
}