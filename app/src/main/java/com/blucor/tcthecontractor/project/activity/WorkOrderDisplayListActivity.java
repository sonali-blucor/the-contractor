package com.blucor.tcthecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkOrderDisplayListActivity extends AppCompatActivity {

    ListView simpleList;
    String workorderList[] = {"Percentage", "Unit", "Quantity", "Rate", "Amount"};
    private FloatingActionButton btn_add_work_order;
    private ProjectsModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_display_list);

        btn_add_work_order = findViewById(R.id.btn_add_work_order);
        btn_add_work_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkOrder();
            }
        });

        simpleList = (ListView)findViewById(R.id.lvbillingDetails);
        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.workorder_row, R.id.txt_billing, workorderList);
        //simpleList.setAdapter(arrayAdapter);
    }

    private void addWorkOrder() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this, WorkOrderActivity.class,bundle);
    }
}