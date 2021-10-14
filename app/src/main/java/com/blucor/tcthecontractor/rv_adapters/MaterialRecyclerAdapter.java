package com.blucor.tcthecontractor.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_NORMAL_MATERIAL = 2;
    private final boolean isLoaderVisible = false;
    private List mList = new ArrayList();

    private final Context mContext;
    private RecyclerViewClickListener mListener;

    public MaterialRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public MaterialRecyclerAdapter(Context mContext, List mList) {
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
            case VIEW_TYPE_NORMAL_MATERIAL:
                return new MaterialViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_list, parent, false));
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
        if (mList.get(position) instanceof Material) {
            return VIEW_TYPE_NORMAL_MATERIAL;
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

    public class MaterialViewHolder extends BaseViewHolder {

        private final View viewHolder;
        private Material item;
        private TextView tv_material_date;
        private TextView tv_material_supplier;
        private TextView tv_material_type;
        private TextView tv_material_quantity;
        private TextView tv_material_rate;
        private TextView tv_material_amount;
        private TextView tv_material_gst;
        private TextView tv_material_gst_amount;
        private TextView tv_material_total_amount;
        private TextView tv_material_paid_to;
        private TextView tv_material_payment_type;
        private TextView btn_material_add_payment;

        MaterialViewHolder(final View itemView) {
            super(itemView);
            viewHolder = itemView;
            tv_material_date = (TextView) itemView.findViewById(R.id.tv_material_date);
            tv_material_supplier = (TextView) itemView.findViewById(R.id.tv_material_supplier);
            tv_material_type = (TextView) itemView.findViewById(R.id.tv_material_type);
            tv_material_quantity = (TextView) itemView.findViewById(R.id.tv_material_quantity);
            tv_material_rate = (TextView) itemView.findViewById(R.id.tv_material_rate);
            tv_material_amount = (TextView) itemView.findViewById(R.id.tv_material_amount);
            tv_material_gst = (TextView) itemView.findViewById(R.id.tv_material_gst);
            tv_material_gst_amount = (TextView) itemView.findViewById(R.id.tv_material_gst_amount);
            tv_material_total_amount = (TextView) itemView.findViewById(R.id.tv_material_total_amount);
            tv_material_paid_to = (TextView) itemView.findViewById(R.id.tv_material_paid_to);
            tv_material_payment_type = (TextView) itemView.findViewById(R.id.tv_material_payment_type);
            btn_material_add_payment = (TextView) itemView.findViewById(R.id.btn_material_add_payment);
            btn_material_add_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.addViewListClicked(viewHolder, getAdapterPosition());
                    }
                }
            });
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
            item = (Material) mList.get(position);

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