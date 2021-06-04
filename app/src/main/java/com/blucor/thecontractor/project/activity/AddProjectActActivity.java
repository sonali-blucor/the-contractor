package com.blucor.thecontractor.project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.ProjectActivityModel;
import com.blucor.thecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.thecontractor.rv_adapters.TableRecyclerAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddProjectActActivity extends AppCompatActivity {

    private RecyclerView mRvView;
    private TableRecyclerAdapter mAdapter;
    private List mList;

    private FloatingActionButton mActivityAdd;
    private LinearLayout mLlvAddActivity;

    private boolean isUp;

    public AddProjectActActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project_act);
        isUp = false;
        mActivityAdd = (FloatingActionButton) findViewById(R.id.fab_activity_add);
        mLlvAddActivity = (LinearLayout) findViewById(R.id.llv_add_activity);

        mRvView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        mList = new ArrayList();
        mAdapter = new TableRecyclerAdapter(AddProjectActActivity.this);
        mRvView.setAdapter(mAdapter);

        mList.clear();
        mList.add(new ProjectActivityModel(false));
        mList.add(new ProjectActivityModel("1", "Column", "12/04/2021", "15/04/2021", "4 Days", "Samit Patil", "8798878959", false));
        mList.add(new ProjectActivityModel("2", "Lining", "09/04/2021", "12/04/2021", "4 Days", "Ravi Patil", "8498878949", false));

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

            }
        });
    }

    public void onClickToCall(View view) {
        Uri u = Uri.parse("tel:" + "8776879387");
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        try {
            startActivity(i);
        } catch (SecurityException s) {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
        }
    }


    /******************* un used code **************************/

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void onSlideViewButtonClick(View view) {
        Bundle bundle =new Bundle();
        bundle.putBoolean(AppKeys.ACTIVITY_DETAIL_TYPE,false);
        ScreenHelper.redirectToClass(AddProjectActActivity.this,AddActivityDetailsActivity.class,bundle);
       /* if (isUp) {
            slideDown(mLlvAddActivity);
        } else {
            slideUp(mLlvAddActivity);
        }
        isUp = !isUp;*/
    }
}