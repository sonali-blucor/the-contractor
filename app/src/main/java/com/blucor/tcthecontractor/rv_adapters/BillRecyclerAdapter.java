package com.blucor.tcthecontractor.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.BilliModel;

import java.util.List;

public class BillRecyclerAdapter extends BaseAdapter {
    private List mList;
    private final Context mContext;
    private ViewHolder viewHolder;
    private RecyclerViewClickListener mListener;

    public BillRecyclerAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnRecyclerViewClickListener(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public BilliModel getItem(int position) {
        return (BilliModel) mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.billing_list_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.onBind(position);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder {

        private final View viewHolder;
        private BilliModel item;
        private TextView tv_no;
        private TextView tv_percentage;
        private TextView tv_remark;
        private TextView tv_billing_date;
        private TextView tv_amount;
        private ImageView img_edit;
        private ImageView img_delete;

        ViewHolder(final View itemView) {
            viewHolder = itemView;
            tv_no = itemView.findViewById(R.id.tv_no_billing_list_item);
            tv_percentage = itemView.findViewById(R.id.tv_percentage_billing_list_item);
            tv_amount = itemView.findViewById(R.id.tv_amount_billing_list_item);
            tv_remark = itemView.findViewById(R.id.tv_remark_billing_list_item);
            tv_billing_date = itemView.findViewById(R.id.tv_billing_date_billing_list_item);
            img_edit = itemView.findViewById(R.id.img_edit_billing_list_item);
            img_delete = itemView.findViewById(R.id.img_delete_billing_list_item);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int ten_percent_screen = (int) (dpWidth * 30) / 100;

//            tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//            tv_percentage.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//            tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//            tv_remark.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//            tv_billing_date.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//            img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
        }

        protected void clear() {
        }

        @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
        public void onBind(int position) {
            item = (BilliModel) mList.get(position);

            String no = String.valueOf(position + 1);
            tv_no.setText("" + no);
            tv_percentage.setText(""+item.getPercentage());
            tv_amount.setText(""+item.getAmount());
            tv_remark.setText(""+item.getRemark());
            tv_billing_date.setText(""+item.getBilling_date());
            tv_amount.setText("" + item.amount);
            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.editViewListClicked(viewHolder, position);
                    }
                }
            });
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.addViewListClicked(viewHolder, position);
                    }
                }
            });
        }
    }

}
