package com.blucor.thecontractor.project;

import android.os.Bundle;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.rv_adapters.CardRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedProjectsActivity extends AppCompatActivity {

    private RecyclerView mRvView;
    private CardRecyclerAdapter mAdapter;
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_projects);

        mRvView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new CardRecyclerAdapter(CompletedProjectsActivity.this);
        mRvView.setAdapter(mAdapter);

        /*mList.clear();
        mList.add(new CompletedProjectsModel("PSD1", "Project 1", "Samit Patil", "879887899", "material"));
        mList.add(new CompletedProjectsModel("PSD2", "Project2", "Ravi Patil", "849887899", "material"));

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
                Uri u = Uri.parse("tel:" + "8776879387");
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(CompletedProjectsActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }
}