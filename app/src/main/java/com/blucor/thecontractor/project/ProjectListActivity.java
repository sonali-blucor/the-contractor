package com.blucor.thecontractor.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.rv_adapters.CardRecyclerAdapter;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectListActivity extends BaseAppCompatActivity {

    private RecyclerView mRvView;
    private CardRecyclerAdapter mAdapter;
    private List<ProjectsModel> mList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        user = DatabaseUtil.on().getAllUser().get(0);

        getProjectList();
    }

    private void getProjectList() {
        int contractor_id = user.server_id;

        showLoader();
        try {
            RetrofitClient.getApiService().getAllProjectContractorType(contractor_id).enqueue(new Callback<List<ProjectsModel>>() {
                @Override
                public void onResponse(Call<List<ProjectsModel>> call, Response<List<ProjectsModel>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        mList = response.body();
                        setadapter();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<ProjectsModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } catch(Exception e) {
            Log.e("view project",e.getMessage());
        }
    }

    private void setadapter() {
        mAdapter = new CardRecyclerAdapter(ProjectListActivity.this);
        mRvView.setAdapter(mAdapter);

        mAdapter.addItems(mList);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                ProjectsModel model = mList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppKeys.PROJECT,model);
                bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
                ScreenHelper.redirectToClass(ProjectListActivity.this, ProjectDetailsActivity.class,bundle);
            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
                ProjectsModel model = mList.get(position);
                String client_mobile_number = model.mobile;
                Uri u = Uri.parse("tel:" + client_mobile_number);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(ProjectListActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}