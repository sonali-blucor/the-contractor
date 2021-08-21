package com.blucor.tcthecontractor.project.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Element;
import android.view.View;

import com.blucor.tcthecontractor.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        String toolBarTitle ="About us";

        //toolBarTitle = project.project_name+"-"+project.id;

        //Start of dynamic title code---------------------
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle(toolBarTitle);
        }
        //End of dynamic title code----------------------

    }
}