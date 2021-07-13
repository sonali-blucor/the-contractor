package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blucor.thecontractor.R;

public class BillingDetailsActivity extends AppCompatActivity {

    EditText et_percentage,et_paid,et_amount,et_balance,et_remark;
    TextView btnsubmit;

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
        btnsubmit=findViewById(R.id.btnsubmit);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    Intent intent = new Intent(BillingDetailsActivity.this, AddProjectActActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean CheckAllFields() {

        if (et_percentage.length() == 0) {
            et_percentage.setError("All Fields are mendatory");
            return false;
        }

        if (et_paid.length() == 0) {
            et_paid.setError("All Fields are mendatory");
            return false;
        }

        if (et_balance.length() == 0) {
            et_balance.setError("All Fields are mendatory");
            return false;
        }

        if (et_amount.length() == 0) {
            et_amount.setError("All Fields are mendatory");
            return false;
        }

        if (et_remark.length() == 0) {
            et_remark.setError("All Fields are mendatory");
            return false;
        }

        return true;
    }
}