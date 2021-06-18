package com.blucor.thecontractor.client;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.ClientProjectActivityModel;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProjectActActivity extends AppCompatActivity {

    private RecyclerView mRvView;
    //private TableRecyclerAdapter mAdapter;
    private List mList;
    private ClientProjectActivityModel project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_act);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
       // mAdapter = new TableRecyclerAdapter(ProjectActActivity.this);
       // mRvView.setAdapter(mAdapter);

        mList.clear();
      //  mList.add(new ClientProjectActivityModel(true));
      //  mList.add(new ClientProjectActivityModel("1", "Column", "12/04/2021", "15/04/2021", "4 Days", "Samit Patil", "8798878995", true));
      //  mList.add(new ClientProjectActivityModel("2", "Lining", "09/04/2021", "12/04/2021", "4 Days", "Ravi Patil", "8498878993", true));

        //mAdapter.addItems(mList);
       /* mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {

            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
              //  String contact = ((ClientProjectActivityModel)mList.get(position)).getWorkerMobileNo();
              //  ScreenHelper.goToDialPad(ProjectActActivity.this,contact);
            }
        });*/
    }
}