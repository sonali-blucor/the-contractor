package com.blucor.thecontractor.rv_adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.SubContractor;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubContractorListWorkOrderAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private List<SubContractor> mList = new ArrayList();
    private List<SubContractor> allJournals = new ArrayList();

    private final Activity mContext;
    private RecyclerViewClickListener mListener;

    public SubContractorListWorkOrderAdapter(Activity context) {
        this.mContext = context;
    }

    public SubContractorListWorkOrderAdapter(Activity mContext, List<SubContractor> mList) {
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
          return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_sub_contractor_list_item, parent, false));
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
        private final TextView tv_sub_contractor;

        ViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            tv_sub_contractor = itemView.findViewById(R.id.tv_work_order_sub_contractor_list_item);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            item = allJournals.get(position);
            tv_sub_contractor.setText(""+item.fname+" "+item.lname);
            tv_sub_contractor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.recyclerViewListClicked(v,getAdapterPosition());
                }
            });
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
