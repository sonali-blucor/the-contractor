package com.blucor.thecontractor.rv_adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.ProjectsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_COMPLETED = 2;
    private boolean isLoaderVisible = false;
    private List<ProjectsModel> mList = new ArrayList();

    private Context mContext;
    private RecyclerViewClickListener mListener;

    public CardRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public CardRecyclerAdapter(Context mContext, List<ProjectsModel> mList) {
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
        if (mList.get(position).view_type == VIEW_TYPE_NORMAL) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_COMPLETED;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void changeItems(List<ProjectsModel> list, int position) {
        mList.set(position, list.get(position));
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

        private View viewHolder;
        private ProjectsModel item;
        private TextView item_1;
        private TextView item_2;
        private TextView item_3;
        private TextView item_4;
        private TextView item_5;

        ViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_1 = (TextView) itemView.findViewById(R.id.txt_item_1);
            item_2 = (TextView) itemView.findViewById(R.id.txt_item_2);
            item_3 = (TextView) itemView.findViewById(R.id.txt_item_3);
            item_4 = (TextView) itemView.findViewById(R.id.txt_item_4);
            item_5 = (TextView) itemView.findViewById(R.id.txt_item_5);
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
            item = (ProjectsModel) mList.get(position);
            item_1.setText(item.project_name);
            item_2.setText(item.id);
            item_3.setText(item.client_fname +" "+item.client_lname);
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

        private View viewHolder;
        private ProjectsModel item;
        private TextView item_1;
        private TextView item_2;
        private TextView item_3;
        private TextView item_4;
        private TextView item_5;
        private TextView item_6;

        ViewHolderCompleted(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            item_1 = (TextView) itemView.findViewById(R.id.txt_item_1);
            item_2 = (TextView) itemView.findViewById(R.id.txt_item_2);
            item_3 = (TextView) itemView.findViewById(R.id.txt_item_3);
            item_4 = (TextView) itemView.findViewById(R.id.txt_item_4);
            item_5 = (TextView) itemView.findViewById(R.id.txt_item_5);
            item_6 = (TextView) itemView.findViewById(R.id.txt_item_6);
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
            item = (ProjectsModel) mList.get(position);
            item_1.setText(item.project_name);
            item_2.setText(""+item.id);
            item_3.setText(item.client_fname +" "+item.client_lname);

            item_6.setVisibility(View.VISIBLE);
            //item_6.setText(item.getTotalMaterial());

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
}
