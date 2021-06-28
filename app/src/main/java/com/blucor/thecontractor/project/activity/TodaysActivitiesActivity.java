package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.ClientMenuActivity;
import com.blucor.thecontractor.MenuActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.models.ActivityResponseModel;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.rv_adapters.ActivityExpandableListViewAdapter;
import com.blucor.thecontractor.rv_adapters.SliderPagerAdapter;
import com.blucor.thecontractor.rv_adapters.TodaysActivityExpandableListViewAdapter;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TodaysActivitiesActivity extends BaseAppCompatActivity {
    private User user;
    private int is_client;
    private SharedPreferences sharedPreferences;
    private List<ActivityResponseModel> activityResponses;
    private List<ActivityResponseModel> topThreeActivities;
    private SliderPagerAdapter sliderPagerAdapter;
    private TextView[] dots;
    int page_position = 0;
    private LinearLayout ll_dots;
    private ViewPager vp_slider;
    private ExpandableListView exp_lst_main_activity;
    private TodaysActivityExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_activities);

        vp_slider = findViewById(R.id.vp_slider);
        exp_lst_main_activity = findViewById(R.id.exp_lst_main_activity);
        ll_dots = findViewById(R.id.ll_dots);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);

        user = DatabaseUtil.on().getAllUser().get(0);

        if (is_client == Contants.USER_TYPE_CLIENT) {
            getAllTodayClientActivities();
        } else if (is_client == Contants.USER_TYPE_CONTRACTOR) {
            getAllTodayContractorActivities();
        }
    }

    private void getAllTodayClientActivities() {
        showLoader();
        int client_id = user.server_id;
        RetrofitClient.getApiService().getClientTodaysActivities(client_id).enqueue(new Callback<List<ActivityResponseModel>>() {
            @Override
            public void onResponse(Call<List<ActivityResponseModel>> call, Response<List<ActivityResponseModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    activityResponses = response.body();
                    setupAdapter();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<ActivityResponseModel>> call, Throwable t) {
                stopLoader();
                Toast.makeText(TodaysActivitiesActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTopActivities() {
        topThreeActivities = new ArrayList<>();
        if (activityResponses.size() >= 3) {
            topThreeActivities.add(activityResponses.get(0));
            topThreeActivities.add(activityResponses.get(1));
            topThreeActivities.add(activityResponses.get(2));
        } else if (activityResponses.size() >= 2) {
            topThreeActivities.add(activityResponses.get(0));
            topThreeActivities.add(activityResponses.get(1));
        } else if (activityResponses.size() >= 1) {
            topThreeActivities.add(activityResponses.get(0));
        }
    }

    private void setupAdapter() {

        adapter = new TodaysActivityExpandableListViewAdapter(TodaysActivitiesActivity.this,activityResponses);
        exp_lst_main_activity.setAdapter(adapter);

        addTopActivities();

        sliderPagerAdapter = new SliderPagerAdapter(TodaysActivitiesActivity.this, topThreeActivities);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        addBottomDots(0);

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == activityResponses.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 200, 5000);
    }

    private void getAllTodayContractorActivities() {
        showLoader();
        int contractor_id = user.server_id;
        RetrofitClient.getApiService().getContractorTodaysActivities(contractor_id).enqueue(new Callback<List<ActivityResponseModel>>() {
            @Override
            public void onResponse(Call<List<ActivityResponseModel>> call, Response<List<ActivityResponseModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    activityResponses = response.body();
                    setupAdapter();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<ActivityResponseModel>> call, Throwable t) {
                stopLoader();
                Toast.makeText(TodaysActivitiesActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[activityResponses.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onBackPressed() {
        if (is_client == Contants.USER_TYPE_CLIENT) {
            ScreenHelper.redirectToClass(TodaysActivitiesActivity.this, ClientMenuActivity.class);
        } else {
            ScreenHelper.redirectToClass(TodaysActivitiesActivity.this, MenuActivity.class);
        }
    }
}