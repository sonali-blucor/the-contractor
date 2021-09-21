package com.blucor.tcthecontractor.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdulhakeem.seemoretextview.SeeMoreTextView;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.ClientProjectActivityModel;
import com.blucor.tcthecontractor.models.Material;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.models.WorkOrderModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkOrderRecyclerAdapter extends BaseAdapter {
    private List mList;
    private final Context mContext;
    private ViewHolder viewHolder;
    private RecyclerViewClickListener mListener;

    public WorkOrderRecyclerAdapter(Context mContext, List mList) {
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
    public WorkOrderModel getItem(int position) {
        return (WorkOrderModel) mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.work_order_adapter_item_new,null);
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

   /* public class ViewHolder {

        private final View viewHolder;
        private WorkOrderModel item;
        private TextView tv_no;
        private TextView tv_item_name;
        private TextView tv_unit;
        private TextView tv_qty;
        private TextView tv_rate;
        private TextView tv_amount;
        private ImageView img_edit;

        ViewHolder(final View itemView) {
            viewHolder = itemView;
            tv_no = itemView.findViewById(R.id.tv_no_work_order_list_item);
            tv_item_name = itemView.findViewById(R.id.tv_item_name_work_order_list_item);
            tv_unit = itemView.findViewById(R.id.tv_unit_work_order_list_item);
            tv_qty = itemView.findViewById(R.id.tv_qty_work_order_list_item);
            tv_rate = itemView.findViewById(R.id.tv_rate_work_order_list_item);
            tv_amount = itemView.findViewById(R.id.tv_amount_work_order_list_item);
            img_edit = itemView.findViewById(R.id.img_edit_work_order_list_item);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int ten_percent_screen_w = (int) (dpWidth * 40) / 100;
            int ten_percent_screen_h = (int) (dpWidth * 30) / 100;

            tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_item_name.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_unit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_qty.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_rate.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
        }

        protected void clear() {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBind(int position) {
            item = (WorkOrderModel) mList.get(position);

            if (position == 0) {
                tv_no.setText("No.");
                tv_item_name.setText("Item");
                tv_unit.setText("Unit");
                tv_qty.setText("Quantity");
                tv_rate.setText("Rate");
                tv_amount.setText("Amount");

                tv_no.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);;
                tv_item_name.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_unit.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_qty.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_rate.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                tv_amount.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
                img_edit.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);

            } else {
                String no = String.valueOf(position);
                tv_no.setText("" + no);
                tv_item_name.setText("" + item.work_description);
                tv_unit.setText("" + item.unit);
                tv_qty.setText("" + item.quantity);
                tv_rate.setText("" + item.rate);
                tv_amount.setText("" + item.amount);
                img_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.editViewListClicked(viewHolder, position);
                        }
                    }
                });
            }
        }
    }*/

    public class ViewHolder {

        private final View viewHolder;
        private WorkOrderModel item;
        private TextView tv_work_order_no;
        private ImageView img_work_order_edit;
        private TextView tv_work_order_description;
        //private TextView tv_work_order_read_more;
        private TextView tv_work_order_total;
        private TextView tv_work_order_unit;
        private TextView tv_work_order_quantity;
        private TextView tv_work_order_rate;

        ViewHolder(final View itemView) {
            viewHolder = itemView;
            tv_work_order_no = itemView.findViewById(R.id.tv_work_order_no);
            img_work_order_edit = itemView.findViewById(R.id.img_work_order_edit);
            tv_work_order_description = itemView.findViewById(R.id.tv_work_order_description);
//            tv_work_order_read_more = itemView.findViewById(R.id.tv_work_order_read_more);
            tv_work_order_total = itemView.findViewById(R.id.tv_work_order_total);
            tv_work_order_unit = itemView.findViewById(R.id.tv_work_order_unit);
            tv_work_order_quantity = itemView.findViewById(R.id.tv_work_order_quantity);
            tv_work_order_rate = itemView.findViewById(R.id.tv_work_order_rate);

           /* DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int ten_percent_screen_w = (int) (dpWidth * 40) / 100;
            int ten_percent_screen_h = (int) (dpWidth * 30) / 100;

            tv_work_order_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            //img_work_order_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_work_order_description.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            //tv_work_order_read_more.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_work_order_total.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_work_order_unit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_work_order_quantity.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));
            tv_work_order_rate.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_w, ten_percent_screen_h));*/
        }

        protected void clear() {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBind(int position) {
            item = (WorkOrderModel) mList.get(position);
            String no = String.valueOf(position + 1);
            tv_work_order_no.setText("" + no);

            String inputText = "" + item.work_description;
            if(inputText.length()>50)
            {
                String text=inputText.substring(0,50)+"...";
                final String fulltext=inputText;

                final SpannableString ss = new SpannableString(text+"View More");

                ClickableSpan span1 = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        // do some thing
                        SpannableString ss1 = new SpannableString(fulltext+" Show Less");
                        ClickableSpan span2 = new ClickableSpan() {
                            @Override
                            public void onClick(View textView) {
                                // do some thing
                                tv_work_order_description.setText(ss);
                                tv_work_order_description.setMovementMethod(LinkMovementMethod.getInstance());

                            }
                        };
                        ss1.setSpan(span2, fulltext.length(), ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ss1.setSpan(new ForegroundColorSpan(Color.BLUE), fulltext.length(), ss1.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                        tv_work_order_description.setText(ss1);
                        tv_work_order_description.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                };
                ss.setSpan(span1, 53, 62, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(Color.BLUE), 53,62,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                tv_work_order_description.setText(ss);
                tv_work_order_description.setMovementMethod(LinkMovementMethod.getInstance());
            }
            else
            {
                tv_work_order_description.setText(inputText);
            }

            tv_work_order_unit.setText("" + item.unit);
            tv_work_order_quantity.setText("" + item.quantity);
            tv_work_order_rate.setText("" + item.rate);
            tv_work_order_total.setText("" + item.amount);
            img_work_order_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.editViewListClicked(viewHolder, position);
                    }
                }
            });
        }
    }

}
