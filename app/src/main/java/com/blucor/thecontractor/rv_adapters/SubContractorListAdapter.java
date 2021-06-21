package com.blucor.thecontractor.rv_adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.project.ScheduleActivity;
import com.blucor.thecontractor.project.material.AddMaterialActivity;
import com.blucor.thecontractor.project.sub_contractor.SelectSubContractorListActivity;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubContractorListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private List<SubContractor> mList = new ArrayList();
    private List<SubContractor> allJournals = new ArrayList();

    private final SelectSubContractorListActivity mContext;
    private RecyclerViewClickListener mListener;

    public SubContractorListAdapter(SelectSubContractorListActivity context) {
        this.mContext = context;
    }

    public SubContractorListAdapter(SelectSubContractorListActivity mContext, List<SubContractor> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.allJournals = mList;
    }

    public void setOnRecyclerViewClickListener(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("View Type", "" + viewType);
          return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.work_order_sub_contractor_list_item_with_checkbox, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }



    @Override
    public int getItemCount() {
        return allJournals == null ? 0 : allJournals.size();
    }


    public class ViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private SubContractor item;
        private final CheckBox cb_sub_contractor;

        ViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            cb_sub_contractor = itemView.findViewById(R.id.cb_sub_contractor_list);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            item = allJournals.get(position);
            cb_sub_contractor.setTag(""+item.fname+" "+item.lname);
            cb_sub_contractor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mContext.selectedSubContractors.add(item);
                    } else {
                        mContext.selectedSubContractors.remove(item);
                    }
                }
            });
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allJournals = mList;
                } else {
                    List<SubContractor> filteredList = new ArrayList<>();
                    for (SubContractor row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.fname.toLowerCase().contains(charString.toLowerCase()) || row.lname.toLowerCase().contains(charString.toLowerCase()) || row.mobile.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    allJournals = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = allJournals;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allJournals = (ArrayList<SubContractor>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
