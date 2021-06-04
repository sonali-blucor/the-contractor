package com.blucor.thecontractor.project;

import com.blucor.thecontractor.database.DatabaseUtil;

import androidx.multidex.MultiDexApplication;

public class ContractorApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseUtil.init(getApplicationContext());
    }
}
