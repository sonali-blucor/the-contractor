package com.blucor.tcthecontractor.rv_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.SubContractor;
import com.blucor.tcthecontractor.project.activity.AddSubContractorActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class AutocompleteSubContractorCustomArrayAdapter extends ArrayAdapter<SubContractor> {
    final String TAG = "AutocompleteCustomArrayAdapter.java";

    AddSubContractorActivity mContext;
    int layoutResourceId;
    ArrayList<SubContractor> data = null;
    ArrayList<SubContractor> allJournals = null;

    public AutocompleteSubContractorCustomArrayAdapter(AddSubContractorActivity mContext, int layoutResourceId, ArrayList<SubContractor> data) {
        super(mContext, layoutResourceId, data);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.allJournals = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{

            /*
             * The convertView argument is essentially a "ScrapView" as described is Lucas post
             * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
             * It will have a non-null value when ListView is asking you recycle the row layout.
             * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
             */
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = mContext.getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            // object item based on the position
            SubContractor objectItem = getItem(position);

            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = convertView.findViewById(R.id.textViewItem);
            textViewItem.setText(objectItem.fname+" "+objectItem.lname);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    result.values = allJournals;
                    result.count = allJournals.size();
                }else{
                    ArrayList<SubContractor> filteredList = new ArrayList<SubContractor>();
                    for(SubContractor j: allJournals){
                        if(j.fname.toLowerCase().contains(constraint.toString().toLowerCase()))
                            filteredList.add(j);
                        else if(j.lname.toLowerCase().contains(constraint.toString().toLowerCase()))
                            filteredList.add(j);
                    }
                    result.values = filteredList;
                    result.count = filteredList.size();
                }

                return result;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0) {
                    notifyDataSetInvalidated();
                    mContext.rl_add_sub_contractor.setVisibility(View.VISIBLE);
                    mContext.lst_search_sub_contractor.setVisibility(View.GONE);
                    mContext.rl_search.setVisibility(View.GONE);
                } else {
                    allJournals = (ArrayList<SubContractor>) results.values;
                    mContext.rl_add_sub_contractor.setVisibility(View.GONE);
                    mContext.lst_search_sub_contractor.setVisibility(View.VISIBLE);
                    mContext.rl_search.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void notifyDataSetChanged() {
        mContext.setListViewAdapter(allJournals);
    }
}
