package com.blucor.tcthecontractor.project;

import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.helper.FontsOverride;

import androidx.multidex.MultiDexApplication;

public class ContractorApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseUtil.init(getApplicationContext());
        FontsOverride.setDefaultFont(this);
    }
}
