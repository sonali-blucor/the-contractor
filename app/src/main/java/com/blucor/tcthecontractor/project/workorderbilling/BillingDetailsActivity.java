package com.blucor.tcthecontractor.project.workorderbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.rv_adapters.BillRecyclerAdapter;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BillingDetailsActivity extends AppCompatActivity {

    EditText et_percentage,et_amount,et_remark,et_billing_date;
    Button btnsubmit;
    private boolean is_edit = false;
    private int edit_position;
    private String billing_date;
    private ListView lst_billing;
    private ArrayList<BilliModel> bills;
    private TextView tv_footer_total;
    private TextView tv_footer_total_percentage;
    private View footer_view;
    private TextView tv_view;
    private LinearLayout ll_title;
    private BillRecyclerAdapter mAdapter;
    private long total_work_order_amount = 0;
    private TextView tv_total_work_order;
//    private TextView tv_no;
//    private TextView tv_percentage;
//    private TextView tv_remark;
//    private TextView tv_billing_date;
//    private TextView tv_amount;
//    private ImageView img_edit;

    private TextView tv_bill_percentage;
    private TextView tv_bill_c_percentage;
    private TextView tv_bill_amount;
    private TextView tv_bill_c_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_order);

        et_percentage=findViewById(R.id.et_percentage);
        et_amount=findViewById(R.id.et_amount);
        et_remark=findViewById(R.id.et_remark);
        et_billing_date=findViewById(R.id.et_billing_date);
        lst_billing =findViewById(R.id.lst_billing);
        tv_view =findViewById(R.id.tv_view);
        ll_title =findViewById(R.id.ll_title);
        tv_total_work_order = findViewById(R.id.tv_total_work_order);
//        tv_no = findViewById(R.id.tv_no);
//        tv_percentage = findViewById(R.id.tv_percentage);
//        tv_amount = findViewById(R.id.tv_amount);
//        tv_remark = findViewById(R.id.tv_remark);
//        tv_billing_date = findViewById(R.id.tv_billing_date);
//        img_edit = findViewById(R.id.img_edit);
        btnsubmit = findViewById(R.id.btn_submit);

        tv_bill_percentage = findViewById(R.id.tv_bill_percentage);
        tv_bill_c_percentage = findViewById(R.id.tv_bill_c_percentage);
        tv_bill_amount = findViewById(R.id.tv_bill_amount);
        tv_bill_c_amount = findViewById(R.id.tv_bill_c_amount);

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.TOTAL_WORK_ORDER_AMOUNT)) {
            total_work_order_amount = intent.getLongExtra(AppKeys.TOTAL_WORK_ORDER_AMOUNT,0);
            tv_total_work_order.setText(""+total_work_order_amount);
        }

        et_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calcuate amount according to percentage using total_work_order_amount
                try {
                    long percent = Long.parseLong(""+s);
                    long amount = (total_work_order_amount * percent) / 100;
                    et_amount.setText(""+amount);
                } catch (Exception exception) {
                    Log.e("Error",""+exception.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckAllFields()) {
                    setIntentData();
                }
            }
        });
        bills = new ArrayList<>();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int ten_percent_screen = (int) (dpWidth * 30) / 100;

//        tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//        tv_percentage.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//        tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//        tv_remark.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//        tv_billing_date.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
//        img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));

    }

    private boolean CheckAllFields() {

        if (et_percentage.getText().toString().length() == 0) {
            et_percentage.setError("All Fields are mendatory");
            et_percentage.requestFocus();
            return false;
        }

        if (et_amount.getText().toString().length() == 0) {
            et_amount.setError("All Fields are mendatory");
            et_amount.requestFocus();
            return false;
        }

        if(getTotalPercentage() > 100) {
            et_percentage.setError("Please add proper percentage");
            et_percentage.requestFocus();
            return false;
        }

        et_percentage.setError(null);
        et_amount.setError(null);

        return true;
    }

    public void showPopupViewForWorkOrder(View view) {

    }

    public void showPopupForDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(BillingDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                billing_date = sdf.format(new Date(calendar.getTimeInMillis()));
                et_billing_date.setText(billing_date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void setIntentData() {
        tv_view.setVisibility(View.VISIBLE);
        ll_title.setVisibility(View.VISIBLE);
        lst_billing.setVisibility(View.VISIBLE);

        if (is_edit) {
            bills.get(edit_position).setAmount(Double.parseDouble(et_amount.getText().toString()));
            bills.get(edit_position).setBilling_date(billing_date);
            bills.get(edit_position).setPercentage(Long.parseLong(et_percentage.getText().toString()));
            bills.get(edit_position).setRemark(et_remark.getText().toString());
            setUpRecyclerAdapter();
            clearEdit();
            Toast.makeText(BillingDetailsActivity.this, "Successfully edited bill", Toast.LENGTH_SHORT).show();
        } else {
            BilliModel bill = new BilliModel();
            bill.setAmount(Double.parseDouble(et_amount.getText().toString()));
            bill.setBilling_date(billing_date);
            bill.setPercentage(Long.parseLong(et_percentage.getText().toString()));
            bill.setRemark(et_remark.getText().toString());

            bills.add(bill);
            setUpRecyclerAdapter();
            clearEdit();
            Toast.makeText(BillingDetailsActivity.this, "Successfully added bill", Toast.LENGTH_SHORT).show();
        }
        long amounts=0;
        long percents=0;
        for (BilliModel billiModel :
                bills) {
            amounts +=billiModel.getAmount();
            percents +=billiModel.getPercentage();
        }


        tv_bill_amount.setText(String.valueOf(total_work_order_amount- amounts));
//        tv_bill_c_amount.setText(String.valueOf(amounts));
        tv_bill_percentage.setText(String.valueOf(percents)+"%");
//        tv_bill_c_percentage.setText(String.valueOf(100- percents)+"%");
        tv_bill_c_amount.setText(String.valueOf(total_work_order_amount));
        tv_bill_c_percentage.setText(String.valueOf(100)+"%");

    }

    private void clearEdit() {
        edit_position = -1;
        is_edit = false;

        et_percentage.setText("");
        et_amount.setText("");
        et_remark.setText("");
        et_billing_date.setText("");

        et_percentage.setError(null);
        et_amount.setError(null);
        et_remark.setError(null);
        et_billing_date.setError(null);
    }

    private void setUpRecyclerAdapter() {
        mAdapter = new BillRecyclerAdapter(BillingDetailsActivity.this,bills);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {

            }

            @Override
            public void addViewListClicked(View v, int position) {

            }

            @Override
            public void editViewListClicked(View v, int position) {
                setupEditMode(position);
            }
        });

        lst_billing.setAdapter(mAdapter);
        if (tv_footer_total == null) {
//            footer_view = getFooterViewForTotalAmount();
//            lst_billing.addFooterView(footer_view);

        } else {
            long tot_amount = getTotalAmount();
            tv_footer_total.setText(""+tot_amount);

            float tot_percentage = getRemaining();
            tv_footer_total_percentage.setText(""+tot_percentage);
        }
    }

    private View getFooterViewForTotalAmount() {
        View footer_view = LayoutInflater.from(this).inflate(R.layout.bill_list_total_item,null);
        tv_footer_total = footer_view.findViewById(R.id.tv_bill_list_total_item);
        tv_footer_total_percentage = footer_view.findViewById(R.id.tv_percentage_bill_list_total_item);
        long tot_amount = getTotalAmount();
        tv_footer_total.setText(""+tot_amount);
        float tot_percentage = getRemaining();
        tv_footer_total_percentage.setText(""+tot_percentage);
        return footer_view;
    }

    private long getTotalAmount() {
        long tot_amount = 0;
        for (int i = 0; i < bills.size(); i++) {
            BilliModel model = (BilliModel) bills.get(i);
            tot_amount = tot_amount + Long.parseLong(String.valueOf(model.amount));
        }
        return tot_amount;
    }

    private float getRemaining() {
        float tot_percentage = 100;
        for (int i = 0; i < bills.size(); i++) {
            BilliModel model = bills.get(i);
            tot_percentage = tot_percentage - model.percentage;
        }
        return tot_percentage;
    }

    private float getTotalPercentage() {
        BilliModel model_per = new BilliModel();
        model_per.setPercentage(Float.parseFloat(et_percentage.getText().toString()));
        ArrayList<BilliModel> perList = new ArrayList<>();
        perList.addAll(bills);
        perList.add(model_per);
        float tot_percentage = 0;
        for (int i = 0; i < perList.size(); i++) {
            BilliModel model = perList.get(i);
            tot_percentage = tot_percentage + model.percentage;
        }
        return tot_percentage;
    }

    @SuppressLint("SetTextI18n")
    private void setupEditMode(int position) {
        edit_position = position;
        BilliModel bill = bills.get(position);
        is_edit = true;

        et_percentage.setText(""+bill.getPercentage());
        et_amount.setText(""+bill.getAmount());
        et_remark.setText(""+bill.getRemark());
        et_billing_date.setText(""+bill.getBilling_date());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(AppKeys.BILL, bills);
        setResult(122, intent);
        finish();
    }
}