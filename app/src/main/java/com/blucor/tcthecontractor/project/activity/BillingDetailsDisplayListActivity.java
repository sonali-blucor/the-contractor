package com.blucor.tcthecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BillingDetailsDisplayListActivity extends AppCompatActivity {

    double Work_description, Paid, Amount, Balance, Remarks,Total;
    private FloatingActionButton btn_add_billing;

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


            Total = Work_description+Paid+Amount+Balance;
            TextView tv_total = findViewById(R.id.tv_total);
            tv_total.setText("Total :    "+Total);

          simpleList = (ListView)findViewById(R.id.lvbillingDetails);
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.billing_details_row, R.id.txt_billing, billingDetailsList);
           // simpleList.setAdapter(arrayAdapter);
        }

    private void addBilling() {
        Bundle bundle = new Bundle();
        /*bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);*/
        ScreenHelper.redirectToClass(this, BillingDetailsActivity.class,bundle);
    }
}

