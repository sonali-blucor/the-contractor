package com.blucor.thecontractor.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.Project_Type;

import java.util.List;

public class ProjectTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Project_Type> project_types;

    public ProjectTypeAdapter(Context mContext, List<Project_Type> project_types) {
        this.mContext = mContext;
        this.project_types = project_types;
    }

    @Override
    public int getCount() {
        return project_types.size();
    }

    @Override
    public Project_Type getItem(int position) {
        return project_types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.project_type_list_item,null);
        }
        Project_Type project_type = getItem(position);
        TextView textView = convertView.findViewById(R.id.tv_project_type_list_item);
        textView.setText(project_type.project_type);
        return convertView;
    }
}
