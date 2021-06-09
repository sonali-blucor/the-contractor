package com.blucor.thecontractor.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.ClientProjectActivityModel;
import com.blucor.thecontractor.models.ProjectActivityModel;
import com.blucor.thecontractor.models.ProjectMaterialModel;

import java.util.ArrayList;
import java.util.List;

public class TableRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_NORMAL_ACTIVITY = 1;
    private static final int VIEW_TYPE_NORMAL_MATERIAL = 2;
    private static final int VIEW_TYPE_NORMAL_CLIENT_PROJECT = 3;
    private final boolean isLoaderVisible = false;
    private List mList = new ArrayList();

    private final Context mContext;
    private RecyclerViewClickListener mListener;

    public TableRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public TableRecyclerAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnRecyclerViewClickListener(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("View Type", "" + viewType);
        switch (viewType) {
            case VIEW_TYPE_NORMAL_ACTIVITY:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_row, parent, false));
            case VIEW_TYPE_NORMAL_MATERIAL:
                return new MaterialViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_row, parent, false));
            case VIEW_TYPE_NORMAL_CLIENT_PROJECT:
                return new ClientViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_row, parent, false));
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
        if (mList.get(position) instanceof ProjectActivityModel) {
            return VIEW_TYPE_NORMAL_ACTIVITY;
        }
        if (mList.get(position) instanceof ProjectMaterialModel) {
            return VIEW_TYPE_NORMAL_MATERIAL;
        }
        if (mList.get(position) instanceof ClientProjectActivityModel) {
            return VIEW_TYPE_NORMAL_CLIENT_PROJECT;
        }
        return 0;
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
        private ProjectActivityModel item;
        private final LinearLayout item_table;
        private final ImageView item_img_edit;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_5;
        private final TextView item_6;

        ViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_table = itemView.findViewById(R.id.llh_item_table);
            item_img_edit = itemView.findViewById(R.id.img_edit_item);
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_5 = itemView.findViewById(R.id.txt_item_5);
            item_6 = itemView.findViewById(R.id.txt_item_6);
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(viewHolder, getAdapterPosition());
                    }
                }
            });
        }

        protected void clear() {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBind(int position) {
            super.onBind(position);
            item = (ProjectActivityModel) mList.get(position);
            item_5.setVisibility(View.VISIBLE);
            if (position == 0) {
                item_table.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                setViewToHeader(item_1, "No.");
                setViewToHeader(item_2, "Activity Name");
                setViewToHeader(item_3, "Start and End Date");
                setViewToHeader(item_4, "Duration");
                setViewToHeader(item_5, "Contact");
                if (item.isEditFlag()) {
                    item_img_edit.setVisibility(View.VISIBLE);
                }
            } else {
                item_1.setText(String.valueOf(position));
                item_2.setText(item.getActivityName());
                item_3.setText(item.getStartDate() + " to " + item.getEndDate());
                item_4.setText(item.getDuration());
                item_5.setText(item.getWorkerName() + "\n Mobile No. : " + item.getWorkerMobileNo());
                if (item.isEditFlag()) {
                    item_img_edit.setVisibility(View.VISIBLE);
                     item_img_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.editViewListClicked(viewHolder, getAdapterPosition());
                            }
                        }
                    });
                }
            }
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }

    public class MaterialViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private ProjectMaterialModel item;
        private final LinearLayout item_table;
        private final ImageView item_img_edit;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_6;
        private final TextView item_7;

        MaterialViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_table = itemView.findViewById(R.id.llh_item_table);
            item_img_edit = itemView.findViewById(R.id.img_edit_item);
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_6 = itemView.findViewById(R.id.txt_item_6);
            item_7 = itemView.findViewById(R.id.txt_item_7);
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(viewHolder, getAdapterPosition());
                    }
                }
            });
        }

        protected void clear() {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBind(int position) {
            super.onBind(position);
            item = (ProjectMaterialModel) mList.get(position);
            item_6.setVisibility(View.VISIBLE);
            if (position == 0) {
                item_table.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                setViewToHeader(item_1, "No.");
                setViewToHeader(item_2, "Material Name");
                setViewToHeader(item_3, "Date");
                setViewToHeader(item_4, "Type");
                setViewToHeader(item_6, "Quantity");
                if (item.isEditFlag()) {
                    item_img_edit.setVisibility(View.VISIBLE);
                }
            } else {
                item_1.setText(String.valueOf(position));
                item_2.setText(item.getMaterialName());
                item_3.setText(item.getMaterialDate());
                item_4.setText(item.getMaterialType());
                item_6.setText(item.getMaterialQuantity());
                if (item.isEditFlag()) {
                    item_img_edit.setVisibility(View.VISIBLE);
                    item_img_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.editViewListClicked(viewHolder, getAdapterPosition());
                            }
                        }
                    });
                }
            }
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }

    public class ClientViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private ClientProjectActivityModel item;
        private final LinearLayout item_table;
        private final ImageView item_img_edit;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_5;
        private final TextView item_6;

        ClientViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_table = itemView.findViewById(R.id.llh_item_table);
            item_img_edit = itemView.findViewById(R.id.img_edit_item);
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_5 = itemView.findViewById(R.id.txt_item_5);
            item_6 = itemView.findViewById(R.id.txt_item_6);
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.recyclerViewListClicked(viewHolder, getAdapterPosition());
                    }
                }
            });
        }

        protected void clear() {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBind(int position) {
            super.onBind(position);
            item = (ClientProjectActivityModel) mList.get(position);
            item_5.setVisibility(View.VISIBLE);
            if (position == 0) {
                item_table.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                setViewToHeader(item_1, "No.");
                setViewToHeader(item_2, "Activity Name");
                setViewToHeader(item_3, "Start and End Date");
                setViewToHeader(item_4, "Duration");
                setViewToHeader(item_5, "Contact");
                if (item.isEditFlag()) {
                    item_img_edit.setVisibility(View.VISIBLE);
                    item_img_edit.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_call_yellow));
                }
            } else {
                item_1.setText(String.valueOf(position));
                item_2.setText(item.getActivityName());
                item_3.setText(item.getStartDate() + " to " + item.getEndDate());
                item_4.setText(item.getDuration());
                item_5.setText(item.getWorkerName() + "\n Mobile No. : " + item.getWorkerMobileNo());
                if (item.isEditFlag()) {
                    item_img_edit.setVisibility(View.VISIBLE);
                    item_img_edit.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_call_yellow));
                    item_img_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.editViewListClicked(viewHolder, getAdapterPosition());
                            }
                        }
                    });
                }
            }
        }

        private void setViewToHeader(TextView textView, String text) {
            textView.setText(text);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }


    private class ViewInit {
        private final LinearLayout item_table;
        private final ImageView item_img_edit;
        private final TextView item_1;
        private final TextView item_2;
        private final TextView item_3;
        private final TextView item_4;
        private final TextView item_5;
        private final TextView item_6;
        private final TextView item_7;
        private final TextView item_8;
        private final TextView item_9;

        ViewInit(View itemView) {
            item_table = itemView.findViewById(R.id.llh_item_table);
            item_img_edit = itemView.findViewById(R.id.img_edit_item);
            item_1 = itemView.findViewById(R.id.txt_item_1);
            item_2 = itemView.findViewById(R.id.txt_item_2);
            item_3 = itemView.findViewById(R.id.txt_item_3);
            item_4 = itemView.findViewById(R.id.txt_item_4);
            item_5 = itemView.findViewById(R.id.txt_item_5);
            item_6 = itemView.findViewById(R.id.txt_item_6);
            item_7 = itemView.findViewById(R.id.txt_item_7);
            item_8 = itemView.findViewById(R.id.txt_item_8);
            item_9 = itemView.findViewById(R.id.txt_item_9);
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
