package com.blucor.thecontractor.client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ClientProjectActivityModel;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
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

public class ClientProjectsActivity extends BaseAppCompatActivity {

    private RecyclerView mRvView;
    private CardProjectsRecyclerAdapter mAdapter;
    private List<ClientProjectActivityModel> mList;
    private EditText mEdtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_projects);

        mRvView = findViewById(R.id.recycler_view_list);
        mEdtSearch = findViewById(R.id.edt_client_project_search);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new CardProjectsRecyclerAdapter(ClientProjectsActivity.this,mList);
        mRvView.setAdapter(mAdapter);
        setupAdapter();

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

        loadProjectList();
    }

    private void loadProjectList() {
        showLoader();
        User user = DatabaseUtil.on().getAllUser().get(0);
        int id = user.server_id;
        RetrofitClient.getApiService().getAllProjectClientType(id).enqueue(new Callback<List<ClientProjectActivityModel>>() {
            @Override
            public void onResponse(Call<List<ClientProjectActivityModel>> call, Response<List<ClientProjectActivityModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    mList.clear();
                    mList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<ClientProjectActivityModel>> call, Throwable t) {
                Toast.makeText(ClientProjectsActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void setupAdapter() {
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                ClientProjectActivityModel project = mList.get(position);
                Bundle bundle =new Bundle();
                bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
                bundle.putParcelable(AppKeys.PROJECT,project);
                ScreenHelper.redirectToClass(ClientProjectsActivity.this, ProjectActActivity.class,bundle);
            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
                ClientProjectActivityModel clientProjectActivityModel  = (ClientProjectActivityModel) mList.get(position);
                Uri u = Uri.parse("tel:" + clientProjectActivityModel.mobile);
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