package com.blucor.thecontractor.project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectActivityModel;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

public class EditProjectActActivity extends AppCompatActivity {
    private RecyclerView mRvView;
    private TableRecyclerAdapter mAdapter;
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project_act);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new TableRecyclerAdapter(EditProjectActActivity.this);
        mRvView.setAdapter(mAdapter);

        mList.clear();
        mList.add(new ProjectActivityModel(true));
        mList.add(new ProjectActivityModel("1", "Column", "12/04/2021", "15/04/2021", "4 Days", "Samit Patil", "8798837899", true));
        mList.add(new ProjectActivityModel("2", "Lining", "09/04/2021", "12/04/2021", "4 Days", "Ravi Patil", "8498878949", true));

        mAdapter.addItems(mList);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
                // write edit code here
                ProjectActivityModel projectActivityModel = (ProjectActivityModel)mList.get(position);
                Bundle bundle =new Bundle();
                bundle.putBoolean(AppKeys.ACTIVITY_DETAIL_TYPE,true);
                ScreenHelper.redirectToClass(EditProjectActActivity.this, AddActivityDetailsActivity.class,bundle);
            }
        });
    }

    public void onClickToCall(View view) {
        Uri u = Uri.parse("tel:" + "8776879387");
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        try {
            startActivity(i);
        } catch (SecurityException s) {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
        }
    }
}