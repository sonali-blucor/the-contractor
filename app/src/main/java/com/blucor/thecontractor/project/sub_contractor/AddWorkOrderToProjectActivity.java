package com.blucor.thecontractor.project.sub_contractor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.rv_adapters.SubContractorListWorkOrderAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddWorkOrderToProjectActivity extends AppCompatActivity {
    private ArrayList<SubContractor> subContractors;
    private RecyclerView rv_sub_contractor_work_order;
    private FloatingActionButton btn_add_sub_contractor;
    private SubContractorListWorkOrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_order_to_project);

        rv_sub_contractor_work_order = findViewById(R.id.rv_sub_contractor_work_order);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_sub_contractor_work_order.setLayoutManager(mLayoutManager);

        btn_add_sub_contractor = findViewById(R.id.btn_add_sub_contractor);
        btn_add_sub_contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent();
            }
        });

        subContractors = new ArrayList<>();
        mAdapter = new SubContractorListWorkOrderAdapter(AddWorkOrderToProjectActivity.this,subContractors);
        rv_sub_contractor_work_order.setAdapter(mAdapter);
    }

    private void startIntent(){
        Intent intent = new Intent(AddWorkOrderToProjectActivity.this, SelectSubContractorListActivity.class);
        startActivityForResult(intent, AppKeys.SUB_CONTRACTOR_LIST_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppKeys.SUB_CONTRACTOR_LIST_REQUEST_CODE) {
            try {
                if (data.hasExtra(AppKeys.SUB_CONTRACTOR_LIST)) {
                    subContractors.addAll(data.getParcelableArrayListExtra(AppKeys.SUB_CONTRACTOR_LIST));
                    mAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}