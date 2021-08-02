package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkOrderDisplayListActivity extends AppCompatActivity {

    ListView simpleList;
    String workorderList[] = {"Percentage", "Unit", "Quantity", "Rate", "Amount"};
    private FloatingActionButton btn_add_work_order;

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
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.workorder_row, R.id.txt_billing, workorderList);
        //simpleList.setAdapter(arrayAdapter);
    }

    private void addWorkOrder() {
        Bundle bundle = new Bundle();
        /*bundle.putParcelable(AppKeys.PROJECT,project);
        bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);*/
        ScreenHelper.redirectToClass(this, WorkOrderActivity.class,bundle);
    }
}