package com.blucor.thecontractor.client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ClientProjectActivityModel;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientProjectsActivity extends AppCompatActivity {

    private RecyclerView mRvView;
    private CardProjectsRecyclerAdapter mAdapter;
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_projects);

        mRvView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new CardProjectsRecyclerAdapter(ClientProjectsActivity.this);
        mRvView.setAdapter(mAdapter);

        mList.clear();
       // mList.add(new ProjectsModel("PSD1", "Project 1", "Contractor 1", "879887899", false));
       // mList.add(new ProjectsModel("PSD2", "Project2", "Contractor 2", "849887899", false));

        mAdapter.addItems(mList);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                Bundle bundle =new Bundle();
                bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
                ScreenHelper.redirectToClass(ClientProjectsActivity.this, ProjectActActivity.class,bundle);
            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
                ClientProjectActivityModel clientProjectActivityModel  = (ClientProjectActivityModel) mList.get(position);
                Uri u = Uri.parse("tel:" + clientProjectActivityModel.getWorkerMobileNo());
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(ClientProjectsActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}