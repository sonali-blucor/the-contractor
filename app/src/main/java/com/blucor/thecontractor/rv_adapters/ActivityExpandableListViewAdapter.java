package com.blucor.thecontractor.rv_adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.Activity;
import com.blucor.thecontractor.models.ActivityResponseModel;
import com.blucor.thecontractor.models.SubActivityModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ActivityResponseModel> activityResponses;

    public ActivityExpandableListViewAdapter(Context context, List<ActivityResponseModel> activityResponses) {
        this.context = context;
        this.activityResponses = activityResponses;
    }

    @Override
    public int getGroupCount() {
        return activityResponses.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return activityResponses.get(groupPosition).subActivities.size();
    }

    @Override
    public ActivityResponseModel getGroup(int groupPosition) {
        return activityResponses.get(groupPosition);
    }

    @Override
    public SubActivityModel getChild(int groupPosition, int childPosition) {
        return activityResponses.get(groupPosition).subActivities.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exp_list_activity_group_item,null);
        }

        TextView tv_activity = convertView.findViewById(R.id.tv_exp_list_activity_group_item);
        ActivityResponseModel activityResponse = getGroup(groupPosition);
        tv_activity.setText(activityResponse.activity.activity_name);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.sub_activity_list_item,null);
        }

        TextView name = convertView.findViewById(R.id.sub_activity_name_item);
        TextView start_end_date = convertView.findViewById(R.id.sub_activity_start_end_date_item);
        TextView sub_contractor_name = convertView.findViewById(R.id.sub_activity_sub_contractor_name_item);
        ImageView img_duration = convertView.findViewById(R.id.sub_activity_img_duration_item);
        TextView duration_name = convertView.findViewById(R.id.sub_activity_duration_name_item);

        Drawable ic_date = context.getResources().getDrawable(R.drawable.ic_date);
        img_duration.setImageDrawable(applyThemeToDrawable(ic_date));

        SubActivityModel subActivity = getChild(groupPosition,childPosition);
        name.setText(" "+subActivity.sub_activity_name);
        start_end_date.setText(" "+subActivity.start_date+" - "+subActivity.end_date);
        sub_contractor_name.setText(" "+subActivity.sub_contractor_first_name+" "+subActivity.sub_contractor_last_name);
        duration_name.setText(" "+subActivity.duration+" Days");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public Drawable applyThemeToDrawable(Drawable image) {
        if (image != null) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            image.setColorFilter(porterDuffColorFilter);
        }
        return image;
    }
}
