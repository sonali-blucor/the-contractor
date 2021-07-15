package com.blucor.thecontractor.rv_adapters;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import android.app.Activity;

import com.blucor.thecontractor.project.material.AddMaterialActivity;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.project.ScheduleActivity;
import com.blucor.thecontractor.utility.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_COMPLETED = 2;
    private final boolean isLoaderVisible = false;
    private List<ProjectsModel> mList = new ArrayList();
    private List<ProjectsModel> allJournals = new ArrayList();
    private final Activity mContext;
    private RecyclerViewClickListener mListener;

    public CardRecyclerAdapter(Activity context) {
        this.mContext = context;
    }

    public CardRecyclerAdapter(Activity mContext, List<ProjectsModel> mList) {
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
            case VIEW_TYPE_COMPLETED:
                return new ViewHolderCompleted(
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
        if (allJournals.get(position).getView_type() == VIEW_TYPE_NORMAL) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_COMPLETED;
        }
    }

    @Override
    public int getItemCount() {
        return allJournals == null ? 0 : allJournals.size();
    }

    public void changeItems(List<ProjectsModel> list, int position) {
        allJournals.set(position, list.get(position));
        notifyDataSetChanged();
    }

    /*public void addItems(List list) {
        allJournals.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        allJournals.clear();
        notifyDataSetChanged();
    }*/


    public class ViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private ProjectsModel item;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_5;
        private final TextView item_6;
        private final TextView item_schedule;
        private final CardView cardview;


        ViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_5 = itemView.findViewById(R.id.txt_item_5);
            item_6 = itemView.findViewById(R.id.txt_item_6);
            item_schedule = itemView.findViewById(R.id.txt_schedule);
            cardview = itemView.findViewById(R.id.cardview);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            item = allJournals.get(position);
            item_1.setText(item.project_name);
            item_2.setText(""+item.id);
            item_3.setText(item.client_fname +" "+item.client_lname);

            item_schedule.setText("Schedule Project");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(cardview, getAdapterPosition());
                    }
                }
            });

            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(cardview, getAdapterPosition());
                    }
                }
            });
            /*item_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProjectsModel model = allJournals.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppKeys.PROJECT,model);
                    bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
                    ScreenHelper.redirectToClass(mContext, ScheduleActivity.class,bundle);
                }
            });

            item_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProjectsModel model = allJournals.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppKeys.PROJECT,model);
                    bundle.putBoolean(AppKeys.PROJECT_DETAIL_TYPE,false);
                    ScreenHelper.redirectToClass(mContext, AddMaterialActivity.class,bundle);
                }
            });

            item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(cardview, getAdapterPosition());
                    }
                }
            });*/

            if (!item.is_material) {
                item_4.setVisibility(View.VISIBLE);
                item_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.editViewListClicked(item_4, getAdapterPosition());
                        }
                    }
                });
            } else {
                item_4.setVisibility(View.INVISIBLE);
            }
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }


    public class ViewHolderCompleted extends BaseViewHolder {

        private final View viewHolder;
        private ProjectsModel item;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_5;
        private final TextView item_6;
        private final TextView item_schedule;


        ViewHolderCompleted(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_5 = itemView.findViewById(R.id.txt_item_5);
            item_6 = itemView.findViewById(R.id.txt_item_6);
            item_schedule = itemView.findViewById(R.id.txt_schedule);
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
            item_1.setText(item.project_name);
            item_2.setText(""+item.id);
            item_3.setText(item.client_fname +" "+item.client_lname);

            item_6.setVisibility(View.VISIBLE);
            item_schedule.setVisibility(View.GONE);

            item_5.setVisibility(View.VISIBLE);
            item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        mListener.recyclerViewListClicked(item_5,position);
                    }
                }
            });
            item_4.setVisibility(View.VISIBLE);
            item_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.editViewListClicked(item_4, getAdapterPosition());
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allJournals = mList;
                } else {
                    List<ProjectsModel> filteredList = new ArrayList<>();
                    for (ProjectsModel row : mList) {

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
                allJournals = (ArrayList<ProjectsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
