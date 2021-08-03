package com.blucor.tcthecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;

public class BillingDetailsActivity extends AppCompatActivity {

    EditText et_percentage,et_paid,et_amount,et_balance,et_remark;
    Button btnsubmit;
    private ProjectsModel project;

    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_order);

        et_percentage=findViewById(R.id.et_percentage);
        et_paid=findViewById(R.id.et_paid);
        et_amount=findViewById(R.id.et_amount);
        et_balance=findViewById(R.id.et_balance);
        et_remark=findViewById(R.id.et_remark);
        btnsubmit=findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    /*Intent intent = new Intent(BillingDetailsActivity.this, AddProjectActActivity.class);
                    startActivity(intent);*/
                }
            }
        });

        getAllWorkOrder();

    }

    private void getAllWorkOrder() {

    }

    private boolean CheckAllFields() {

        if (et_percentage.getText().toString().length() == 0) {
            et_percentage.setError("All Fields are mendatory");
            et_percentage.requestFocus();
            return false;
        }

        if (et_paid.getText().toString().length() == 0) {
            et_paid.setError("All Fields are mendatory");
            et_paid.requestFocus();
            return false;
        }

        if (et_balance.getText().toString().length() == 0) {
            et_balance.setError("All Fields are mendatory");
            et_balance.requestFocus();
            return false;
        }

        if (et_amount.getText().toString().length() == 0) {
            et_amount.setError("All Fields are mendatory");
            et_amount.requestFocus();
            return false;
        }

        if (et_remark.getText().toString().length() == 0) {
            et_remark.setError("All Fields are mendatory");
            et_remark.requestFocus();
            return false;
        }

        et_percentage.setError(null);
        et_paid.setError(null);
        et_balance.setError(null);
        et_amount.setError(null);
        et_remark.setError(null);

        return true;
    }

    public void showPopupViewForWorkOrder(View view) {

    }
}