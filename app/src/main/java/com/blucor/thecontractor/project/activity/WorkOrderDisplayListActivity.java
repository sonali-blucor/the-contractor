package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blucor.thecontractor.R;

public class WorkOrderDisplayListActivity extends AppCompatActivity {

    ListView simpleList;
    String workorderList[] = {"Percentage", "Unit", "Quantity", "Rate", "Amount"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_display_list);

        simpleList = (ListView)findViewById(R.id.lvbillingDetails);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.workorder_row, R.id.txt_billing, workorderList);
        simpleList.setAdapter(arrayAdapter);
    }
}