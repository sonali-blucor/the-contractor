package com.blucor.tcthecontractor.rv_adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.SubActivityModel;

import java.util.List;

public class SubActivityAdapter extends BaseAdapter {
    private Context mContext;
    private List<SubActivityModel> subActivities;

    public SubActivityAdapter(Context mContext, List<SubActivityModel> subActivities) {
        this.mContext = mContext;
        this.subActivities = subActivities;
    }

    @Override
    public int getCount() {
        return subActivities.size();
    }

    @Override
    public SubActivityModel getItem(int position) {
        return subActivities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sub_activity_list_item,null);
        }

        TextView name = convertView.findViewById(R.id.sub_activity_name_item);
        TextView start_end_date = convertView.findViewById(R.id.sub_activity_start_end_date_item);
        TextView sub_contractor_name = convertView.findViewById(R.id.sub_activity_sub_contractor_name_item);
        ImageView img_duration = convertView.findViewById(R.id.sub_activity_img_duration_item);
        TextView duration_name = convertView.findViewById(R.id.sub_activity_duration_name_item);

        Drawable ic_date = mContext.getResources().getDrawable(R.drawable.ic_date);
        img_duration.setImageDrawable(applyThemeToDrawable(ic_date));

        SubActivityModel subActivity = getItem(position);
        name.setText(" "+subActivity.sub_activity_name);
        start_end_date.setText(" "+subActivity.start_date+" - "+subActivity.end_date);
        sub_contractor_name.setText(" "+subActivity.sub_contractor_first_name+" "+subActivity.sub_contractor_last_name);
        duration_name.setText(" "+subActivity.duration+" Days");

        return convertView;
    }

    public Drawable applyThemeToDrawable(Drawable image) {
        if (image != null) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            image.setColorFilter(porterDuffColorFilter);
        }
        return image;
    }
}
