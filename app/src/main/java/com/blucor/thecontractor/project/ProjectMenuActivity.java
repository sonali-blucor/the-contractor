package com.blucor.thecontractor.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.project.material.MaterialMenuActivity;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.project.activity.AddActivityDetailsActivity;
import com.blucor.thecontractor.utility.ScreenHelper;

public class ProjectMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);
    }

    public void onClickToActivityManagement(View view) {
        ScreenHelper.redirectToClass(this, AddActivityDetailsActivity.class);
    }

    public void onClickToSubContractorManagement(View view) {
        ScreenHelper.redirectToClass(this, SubContractor.class);
    }

    public void onClickToMaterialManagement(View view) {
        ScreenHelper.redirectToClass(this, MaterialMenuActivity.class);
    }

    public void onClickToProjectDetail(View view) {
        ScreenHelper.redirectToClass(this, ProjectDetailsActivity.class);
    }
}