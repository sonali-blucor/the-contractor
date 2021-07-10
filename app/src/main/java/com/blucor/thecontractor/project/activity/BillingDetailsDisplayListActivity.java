package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blucor.thecontractor.R;

public class BillingDetailsDisplayListActivity extends AppCompatActivity {

    ListView simpleList;
    String billingDetailsList[] = {"Work description", "Paid", "Amount", "Balance", "Remarks"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details_display_list);

            simpleList = (ListView)findViewById(R.id.lvbillingDetails);
           // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.billing_details_row, R.id.txt_billing, billingDetailsList);
           // simpleList.setAdapter(arrayAdapter);
        }
    }

