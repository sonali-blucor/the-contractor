package com.blucor.tcthecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BillingDetailsDisplayListActivity extends AppCompatActivity {

    double Work_description, Paid, Amount, Balance, Remarks,Total;
    private FloatingActionButton btn_add_billing;
    private ProjectsModel project;

    ListView simpleList;
    String billingDetailsList[] = {"Work description", "Paid", "Amount", "Balance", "Remarks"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details_display_list);

        btn_add_billing = findViewById(R.id.btn_add_billing);

        btn_add_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBilling();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }


        Total = Work_description+Paid+Amount+Balance;
        TextView tv_total = findViewById(R.id.tv_total);
        tv_total.setText("Total :    "+Total);

        simpleList = (ListView)findViewById(R.id.lvbillingDetails);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.billing_details_row, R.id.txt_billing, billingDetailsList);
        // simpleList.setAdapter(arrayAdapter);
    }

    private void addBilling() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this, BillingDetailsActivity.class,bundle);
    }
}

