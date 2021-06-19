package com.blucor.thecontractor.project.material;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditMaterialActivity extends AppCompatActivity {
    private RecyclerView mRvView;
    private TableRecyclerAdapter mAdapter;
    private List mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_material);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new TableRecyclerAdapter(EditMaterialActivity.this);
        mRvView.setAdapter(mAdapter);


    }
}