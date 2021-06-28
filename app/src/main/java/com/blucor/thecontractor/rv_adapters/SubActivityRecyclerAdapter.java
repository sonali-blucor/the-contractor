package com.blucor.thecontractor.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.ActivityResponseModel;
import com.blucor.thecontractor.models.ClientProjectActivityModel;
import com.blucor.thecontractor.models.Material;
import com.blucor.thecontractor.models.ProjectActivityModel;
import com.blucor.thecontractor.models.SubActivityModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubActivityRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List mList = new ArrayList();
    private final Context mContext;

    public SubActivityRecyclerAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_activity_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void changeItems(List list, int position) {
        mList.set(position, list);
        notifyDataSetChanged();
    }

    public void addItems(List list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private TextView name;
        private TextView start_end_date;
        private TextView sub_contractor_name;
        private ImageView img_duration;
        private TextView duration_name;
        private SubActivityModel subActivity;

        ViewHolder(final View convertView) {
            super(convertView);
            viewHolder = convertView;
            name = convertView.findViewById(R.id.sub_activity_name_item);
            start_end_date = convertView.findViewById(R.id.sub_activity_start_end_date_item);
            sub_contractor_name = convertView.findViewById(R.id.sub_activity_sub_contractor_name_item);
            img_duration = convertView.findViewById(R.id.sub_activity_img_duration_item);
            duration_name = convertView.findViewById(R.id.sub_activity_duration_name_item);
        }

        protected void clear() {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBind(int position) {
            super.onBind(position);
            subActivity = (SubActivityModel) mList.get(position);
            Drawable ic_date = mContext.getResources().getDrawable(R.drawable.ic_date);
            img_duration.setImageDrawable(applyThemeToDrawable(ic_date));

            name.setText(" "+subActivity.sub_activity_name);
            start_end_date.setText(" "+subActivity.start_date+" - "+subActivity.end_date);
            sub_contractor_name.setText(" "+subActivity.sub_contractor_first_name+" "+subActivity.sub_contractor_last_name);
            duration_name.setText(" "+subActivity.duration+" Days");
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }

    public Drawable applyThemeToDrawable(Drawable image) {
        if (image != null) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            image.setColorFilter(porterDuffColorFilter);
        }
        return image;
    }


}
