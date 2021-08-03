package com.blucor.tcthecontractor.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.SubActivityModel;

public class ClientProjectSubActDetailsActivity extends AppCompatActivity {
    private SubActivityModel subActivity;
    private TextView tv_name;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_duration;
    private TextView tv_sub_contractor_name;
    private TextView tv_sub_contractor_email;
    private TextView tv_sub_contractor_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_project_sub_act_details);

        tv_name = findViewById(R.id.tv_sub_activity_name);
        tv_start_date = findViewById(R.id.tv_sub_activity_start_date);
        tv_end_date = findViewById(R.id.tv_sub_activity_end_date);
        tv_duration = findViewById(R.id.tv_sub_activity_duration);
        tv_sub_contractor_name = findViewById(R.id.tv_sub_activity_sub_contractor_name);
        tv_sub_contractor_email = findViewById(R.id.tv_sub_activity_sub_contractor_email);
        tv_sub_contractor_mobile = findViewById(R.id.tv_sub_activity_sub_contractor_mobile);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.SUB_ACTIVITY)) {
            subActivity = intent.getParcelableExtra(AppKeys.SUB_ACTIVITY);

            if (subActivity != null) {
                setUpDetails();
            }
        }
    }

    private void setUpDetails() {
        tv_name.setText(""+subActivity.sub_activity_name);
        tv_start_date.setText(""+subActivity.start_date);
        tv_end_date.setText(""+subActivity.end_date);
        tv_duration.setText(""+subActivity.duration);
        tv_sub_contractor_name.setText(""+subActivity.sub_contractor_first_name+" "+subActivity.sub_contractor_last_name);
        tv_sub_contractor_email.setText(""+subActivity.sub_contractor_email);
        tv_sub_contractor_mobile.setText(""+subActivity.sub_contractor_mobile);
    }
}