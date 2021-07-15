package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blucor.thecontractor.R;

public class WorkOrderActivity extends AppCompatActivity {

    TextView et_workdesc, et_unit, et_qty, et_rate, et_amount ,btnsubmit;

    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        et_workdesc = findViewById(R.id.et_workdesc);
        et_unit = findViewById(R.id.et_unit);
        et_qty = findViewById(R.id.et_qty);
        et_rate = findViewById(R.id.et_rate);
        et_amount = findViewById(R.id.et_amount);
        btnsubmit = findViewById(R.id.btnsubmit);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    /*Intent intent = new Intent(WorkOrderActivity.this, AddProjectActActivity.class);
                    startActivity(intent);*/
                }
            }
        });
    }

    private boolean CheckAllFields() {

        if (et_workdesc.getText().toString().length() == 0) {
            et_workdesc.setError("All Fields are mendatory");
            et_workdesc.requestFocus();
            return false;
        }

        if (et_unit.getText().toString().length() == 0) {
            et_unit.setError("All Fields are mendatory");
            et_unit.requestFocus();
            return false;
        }

        if (et_qty.getText().toString().length() == 0) {
            et_qty.setError("All Fields are mendatory");
            et_qty.requestFocus();
            return false;
        }

        if (et_rate.getText().toString().length() == 0) {
            et_rate.setError("All Fields are mendatory");
            et_rate.requestFocus();
            return false;
        }

        if (et_amount.getText().toString().length() == 0) {
            et_amount.setError("All Fields are mendatory");
            et_amount.requestFocus();
            return false;
        }

        et_workdesc.setError(null);
        et_unit.setError(null);
        et_qty.setError(null);
        et_rate.setError(null);
        et_amount.setError(null);
        return true;
    }
}

