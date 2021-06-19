package com.blucor.thecontractor.project.material;

import android.os.Bundle;
import android.view.View;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.project.ProjectDetailsActivity;
import com.blucor.thecontractor.rv_adapters.CardRecyclerAdapter;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MaterialMenuActivity extends AppCompatActivity {

    private RecyclerView mRvView;
    private CardRecyclerAdapter mAdapter;
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_menu);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new CardRecyclerAdapter(MaterialMenuActivity.this,mList);
        mRvView.setAdapter(mAdapter);

        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                Bundle bundle =new Bundle();
                bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,true);
                ScreenHelper.redirectToClass(MaterialMenuActivity.this, ProjectDetailsActivity.class,bundle);
            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
            }
        });
    }

    public void onClickToMaterials(View view) {
        ScreenHelper.redirectToClass(MaterialMenuActivity.this,EditMaterialActivity.class);
    }

    public void onClickToAddMaterial(View view) {
        ScreenHelper.redirectToClass(MaterialMenuActivity.this,AddMaterialActivity.class);
    }
}