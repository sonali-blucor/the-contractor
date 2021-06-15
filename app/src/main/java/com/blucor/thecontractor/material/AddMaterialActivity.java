package com.blucor.thecontractor.material;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.Material;
import com.blucor.thecontractor.models.ProjectActivityModel;
import com.blucor.thecontractor.models.ProjectMaterialModel;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.project.activity.AddActivityDetailsActivity;
import com.blucor.thecontractor.project.activity.AddProjectActActivity;
import com.blucor.thecontractor.project.activity.EditProjectActActivity;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

public class AddMaterialActivity extends BaseAppCompatActivity {
    private RecyclerView mRvView;
    private TableRecyclerAdapter mAdapter;
    private List<Material> mList;
    private ProjectsModel project;
    private List<Material> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);
        }

        getMaterialList();

    }

    private void getMaterialList() {
        showLoader();
        RetrofitClient.getApiService().getMaterialsByProjectId(project.id).enqueue(new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {
                if (response.code() == 200) {
                    mList = response.body();
                    setUpAdapter();
                } else {
                    Toast.makeText(AddMaterialActivity.this, "No Material Found", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                Toast.makeText(AddMaterialActivity.this, "Material Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void setUpAdapter() {
        Material material = new Material();
        adapterList = new ArrayList<>();
        adapterList.add(material);
        adapterList.addAll(mList);
        mAdapter = new TableRecyclerAdapter(AddMaterialActivity.this,adapterList);
        mRvView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {

            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
                Material current_material = adapterList.get(position);
                Bundle bundle =new Bundle();
                bundle.putParcelable(AppKeys.MATERIAL,current_material);
                bundle.putParcelable(AppKeys.PROJECT,project);
                ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class,bundle);
            }
        });
        //mAdapter.addItems(mList);
    }

    public void onSlideViewButtonClick(View view) {
        Bundle bundle =new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE,false);
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class,bundle);
    }
}