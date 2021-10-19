package com.blucor.tcthecontractor.rv_adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.SubContractor;

import java.util.ArrayList;

public class SubContractorSpinnerAdapter implements SpinnerAdapter {
    private ArrayList<SubContractor> subContractors;
    private Context mContext;

    public SubContractorSpinnerAdapter(ArrayList<SubContractor> subContractors, Context mContext) {
        this.subContractors = subContractors;
        this.mContext = mContext;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_list_item,null);
        }

        SubContractor subContractor = getItem(position);
        TextView tv_spinner_list_item = convertView.findViewById(R.id.tv_spinner_list_item);
        tv_spinner_list_item.setText(subContractor.fname);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return subContractors.size();
    }

    @Override
    public SubContractor getItem(int position) {
        return subContractors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_list_item,null);
        }

        SubContractor subContractor = getItem(position);
        TextView tv_spinner_list_item = convertView.findViewById(R.id.tv_spinner_list_item);
        tv_spinner_list_item.setText(subContractor.fname);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
