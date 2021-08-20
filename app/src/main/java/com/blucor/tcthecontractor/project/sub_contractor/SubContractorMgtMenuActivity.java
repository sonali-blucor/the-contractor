package com.blucor.tcthecontractor.project.sub_contractor;

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

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
            }
        } catch (Exception e) {

        }
    }

    public void onClickToWorkOrder(View view) {
       /* Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, WorkOrderBillingProjectListActivity.class,bundle);*/
    }

    public void onClickToBilling(View view) {
        /*Bundle bundle = new Bundle();cv_download_reportd
        bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(this, BillingDetailsDisplayListActivity.class,bundle);*/
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