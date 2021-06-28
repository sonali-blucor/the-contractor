package com.blucor.thecontractor.rv_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.ActivityResponseModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    List<ActivityResponseModel> activityList;

    public SliderPagerAdapter(Activity activity, List<ActivityResponseModel> activityList) {
        this.activity = activity;
        this.activityList = activityList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        TextView slider_activity_name = view.findViewById(R.id.slider_activity_name);
        TextView slider_start_date = view.findViewById(R.id.slider_start_date);
        TextView slider_end_date = view.findViewById(R.id.slider_end_date);
        RecyclerView slider_recycler_view = view.findViewById(R.id.slider_recycler_view);

        ActivityResponseModel activityResponse = activityList.get(position);

        slider_activity_name.setText(activityResponse.activity.activity_name);
        slider_start_date.setText(activityResponse.activity.start_date);
        slider_end_date.setText(activityResponse.activity.end_date);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        slider_recycler_view.setLayoutManager(layoutManager);

        SubActivityRecyclerAdapter adapter = new SubActivityRecyclerAdapter(activity,activityResponse.subActivities);
        slider_recycler_view.setAdapter(adapter);

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
