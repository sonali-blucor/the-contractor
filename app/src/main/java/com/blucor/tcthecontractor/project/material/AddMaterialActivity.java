package com.blucor.tcthecontractor.project.material;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.Material;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.ProjectMenuActivity;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.tcthecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterialActivity extends BaseAppCompatActivity {
    private RecyclerView mRvView;
    private TableRecyclerAdapter mAdapter;
    private List<Material> mList;
    private ProjectsModel project;
    private List<Material> adapterList;

    private boolean fabExpanded = false;
    private FloatingActionButton fabAdd;
    private LinearLayout layoutFabAddSupplier;
    private LinearLayout layoutFabAddMaterial;
    private LinearLayout layoutFabMaterial;

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
            getMaterialList();
        }

        fabAdd = (FloatingActionButton) this.findViewById(R.id.fabAdd);

        layoutFabAddSupplier = (LinearLayout) this.findViewById(R.id.layoutFabAddSupplier);
        layoutFabAddMaterial = (LinearLayout) this.findViewById(R.id.layoutFabAddMaterial);
        layoutFabMaterial = (LinearLayout) this.findViewById(R.id.layoutFAbMaterial);
        //layoutFabSettings = (LinearLayout) this.findViewById(R.id.layoutFabSettings);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true) {
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();

    }

    //closes FAB submenus
    private void closeSubMenusFab() {
        layoutFabAddMaterial.setVisibility(View.INVISIBLE);
        layoutFabAddSupplier.setVisibility(View.INVISIBLE);
        layoutFabMaterial.setVisibility(View.INVISIBLE);
        fabAdd.setImageResource(R.drawable.ic_add);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        layoutFabAddMaterial.setVisibility(View.VISIBLE);
        layoutFabAddSupplier.setVisibility(View.VISIBLE);
        layoutFabMaterial.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabAdd.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
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
                Toast.makeText(AddMaterialActivity.this, "Material Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void setUpAdapter() {
        Material material = new Material();
        adapterList = new ArrayList<>();
        adapterList.add(material);
        adapterList.addAll(mList);
        mAdapter = new TableRecyclerAdapter(AddMaterialActivity.this, adapterList);
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
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppKeys.MATERIAL, current_material);
                bundle.putParcelable(AppKeys.PROJECT, project);
                ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class, bundle);
            }
        });
        //mAdapter.addItems(mList);
    }

    public void onSlideViewButtonClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class, bundle);
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(this, ProjectMenuActivity.class, bundle);
    }

    public void onClickAddMaterial(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialsActivity.class, bundle);
    }

    public void onClickAddSupplier(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddSupplierActivity.class, bundle);
    }

    public void onClickMaterial(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class, bundle);
    }
}