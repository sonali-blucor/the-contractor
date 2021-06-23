package com.blucor.thecontractor.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedProjectsActivity extends BaseAppCompatActivity {

    private RecyclerView mRvView;
    private CardRecyclerAdapter mAdapter;
    private List<ProjectsModel> mList;
    private User user;
    private EditText mEdtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_projects);

        mRvView = findViewById(R.id.recycler_view_list);
        mEdtSearch = findViewById(R.id.edt_completed_project_search);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        user = DatabaseUtil.on().getAllUser().get(0);

        mList = new ArrayList<>();
        mAdapter = new CardRecyclerAdapter(CompletedProjectsActivity.this,mList);
        mRvView.setAdapter(mAdapter);
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getCompletedProjectList();
    }

    private void getCompletedProjectList() {
        int contractor_id = user.server_id;

        showLoader();
        try {
            RetrofitClient.getApiService().getAllCompletedProjectContractorType(contractor_id).enqueue(new Callback<List<ProjectsModel>>() {
                @Override
                public void onResponse(Call<List<ProjectsModel>> call, Response<List<ProjectsModel>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        mList.addAll(response.body());
                        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onBackPressed() {
        ScreenHelper.redirectToClass(this,ProjectManagementMenuActivity.class);
        finish();
    }
}