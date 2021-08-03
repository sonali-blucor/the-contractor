package com.blucor.tcthecontractor.client;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.ClientProjectActivityModel;
import com.blucor.tcthecontractor.rv_adapters.BaseViewHolder;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardProjectsRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private static final int VIEW_TYPE_NORMAL = 1;
    private final boolean isLoaderVisible = false;
    private List<ClientProjectActivityModel> mList = new ArrayList();
    private List<ClientProjectActivityModel> allJournals = new ArrayList();

    private final Context mContext;
    private RecyclerViewClickListener mListener;

   /* public CardProjectsRecyclerAdapter(Context context) {
        this.mContext = context;
    }*/

    public CardProjectsRecyclerAdapter(Context mContext, List mList) {
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
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_list, parent, false));
              default:
                return new NoViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_data, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (allJournals.get(position) instanceof ClientProjectActivityModel) {
            return VIEW_TYPE_NORMAL;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return allJournals == null ? 0 : allJournals.size();
    }

   /* public void changeItems(List<ClientProjectActivityModel> list, int position) {
        mList.set(position, list);
        notifyDataSetChanged();
    }*/

    /*public void addItems(List list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }*/

    /*public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }*/

    @Override
    public Filter getFilter() {
         return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allJournals = mList;
                } else {
                    List<ClientProjectActivityModel> filteredList = new ArrayList<>();
                    for (ClientProjectActivityModel row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.project_name.toLowerCase().contains(charString.toLowerCase())) {
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
                allJournals = (ArrayList<ClientProjectActivityModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private ClientProjectActivityModel item;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_5;
        private final TextView item_schedule;

        ViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_5 = itemView.findViewById(R.id.txt_item_5);
            item_schedule = itemView.findViewById(R.id.txt_schedule);
            item_schedule.setVisibility(View.GONE);
            item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(item_5, getAdapterPosition());
                    }
                }
            });
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            item = allJournals.get(position);
                item_1.setText(""+item.project_name);
                item_2.setText(""+item.id);
                item_3.setText(""+item.fname+" "+item.lname);
               // if (!item.isMaterial()) {
                    item_4.setText("Contractor");
                    item_4.setVisibility(View.VISIBLE);
                    item_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mListener != null) {
                                mListener.editViewListClicked(item_4, getAdapterPosition());
                            }
                        }
                    });
                /*}else{
                    item_4.setVisibility(View.INVISIBLE);
                }*/
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(""+text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }

    public class NoViewHolder extends BaseViewHolder {

        NoViewHolder(final View itemView) {
            super(itemView);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
        }
    }
}
