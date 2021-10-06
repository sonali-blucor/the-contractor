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

public class BillPaymentRecyclerAdapter extends BaseAdapter {
    private List mList;
    private final Context mContext;
    private ViewHolder viewHolder;
    private RecyclerViewClickListener mListener;

    public BillPaymentRecyclerAdapter(Context mContext, List mList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.billing_payment_list_item,null);
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
//        private TextView tv_balance;
//        private TextView tv_paid;
//        private TextView tv_payment_date;
        private ImageView img_edit;
        private ImageView img_delete;

        ViewHolder(final View itemView) {
            viewHolder = itemView;
            tv_no = itemView.findViewById(R.id.tv_no_billing_list_item);
            tv_percentage = itemView.findViewById(R.id.tv_percentage_billing_list_item);
            tv_amount = itemView.findViewById(R.id.tv_amount_billing_list_item);
            tv_remark = itemView.findViewById(R.id.tv_remark_billing_list_item);
            tv_billing_date = itemView.findViewById(R.id.tv_billing_date_billing_list_item);
//            tv_balance = itemView.findViewById(R.id.tv_balance);
//            tv_paid = itemView.findViewById(R.id.tv_paid);
//            tv_payment_date = itemView.findViewById(R.id.tv_payment_date);
            img_edit = itemView.findViewById(R.id.img_edit_billing_list_item);
            img_delete = itemView.findViewById(R.id.img_delete_billing_list_item);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int ten_percent_screen_w = (int) (dpWidth * 40) / 100;
            int ten_percent_screen_h = (int) (dpWidth * 30) / 100;

//            tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_percentage.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_remark.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_billing_date.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_balance.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_paid.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            tv_payment_date.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
//            img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w,ten_percent_screen_h));
        }

        protected void clear() {
        }

        @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
        public void onBind(int position) {
            item = (BilliModel) mList.get(position);

         /*   if (position == 0) {

                tv_no.setText("No.");
                tv_percentage.setText("%");
                tv_amount.setText("Amount");
                tv_remark.setText("Remark");
                tv_billing_date.setText("Bill Date");
//                tv_balance.setText("Balance");
//                tv_paid.setText("Paid");
//                tv_payment_date.setText("Pay Date");


                tv_no.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_percentage.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_amount.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_remark.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_billing_date.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
//                tv_balance.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
//                tv_paid.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
//                tv_payment_date.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                img_edit.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);

            } else {

            // set value to view
            }*/

            String no = String.valueOf(position+1);
            tv_no.setText("" + no);
            tv_percentage.setText("" + item.getPercentage());
            tv_amount.setText("" + item.getAmount());
            tv_remark.setText("" + item.getRemark());
            tv_billing_date.setText("" + item.getBilling_date());
            tv_amount.setText("" + item.amount);
//                tv_paid.setText(""+item.paid);
//                tv_balance.setText(""+item.balance);
//                tv_payment_date.setText(""+item.payment_date);
            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.editViewListClicked(viewHolder, position/* - 1*/);
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
