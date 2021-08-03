package com.blucor.tcthecontractor.project;

import com.blucor.tcthecontractor.database.DatabaseUtil;

import androidx.multidex.MultiDexApplication;

public class ContractorApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseUtil.init(getApplicationContext());
    }
}
