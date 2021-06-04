package com.blucor.thecontractor.material;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectMaterialModel;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;

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

        mRvView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new TableRecyclerAdapter(EditMaterialActivity.this);
        mRvView.setAdapter(mAdapter);

        mList.clear();
        mList.add(new ProjectMaterialModel(true));
        mList.add(new ProjectMaterialModel("1", "Cement", "12/04/2021", "Bag", "25", true));
        mList.add(new ProjectMaterialModel("2", "Sand", "09/04/2021", "Small", "4", true));

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
                ProjectMaterialModel projectMaterialModel = (ProjectMaterialModel)mList.get(position);
                Bundle bundle =new Bundle();
                bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE,true);
                ScreenHelper.redirectToClass(EditMaterialActivity.this, AddMaterialDetailsActivity.class,bundle);
            }
        });
    }
}