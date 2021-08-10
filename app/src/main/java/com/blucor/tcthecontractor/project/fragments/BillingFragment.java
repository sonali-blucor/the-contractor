package com.blucor.tcthecontractor.project.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.project.activity.BillingDetailsActivity;
import com.blucor.tcthecontractor.rv_adapters.BillRecyclerAdapter;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BillingFragment extends Fragment {

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
    private float total_work_order_amount;
    private TextView tv_total_work_order;
    private TextView tv_no;
    private TextView tv_percentage;
    private TextView tv_remark;
    private TextView tv_billing_date;
    private TextView tv_amount;
    private ImageView img_edit;
    private View fragment_view;
    private BaseAppCompatActivity mActivity;
    private FloatingActionButton fab_add_billing;
    private View dialog_view;
    private BilliModel edit_bill;
    private AlertDialog dialog;

    public BillingFragment() {
        // Required empty public constructor
    }

    public BillingFragment(float total_work_order_amount,ArrayList<BilliModel> bills) {
        this.total_work_order_amount = total_work_order_amount;
        this.bills = bills;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragment_view = inflater.inflate(R.layout.fragment_billing, container, false);

       /* et_percentage=fragment_view.findViewById(R.id.et_percentage);
        et_amount=fragment_view.findViewById(R.id.et_amount);
        et_remark=fragment_view.findViewById(R.id.et_remark);
        et_billing_date=fragment_view.findViewById(R.id.et_billing_date);*/
        lst_billing =fragment_view.findViewById(R.id.lst_billing);
        tv_view =fragment_view.findViewById(R.id.tv_view);
        ll_title =fragment_view.findViewById(R.id.ll_title);
        tv_total_work_order = fragment_view.findViewById(R.id.tv_total_work_order);
        tv_no = fragment_view.findViewById(R.id.tv_no);
        tv_percentage = fragment_view.findViewById(R.id.tv_percentage);
        tv_amount = fragment_view.findViewById(R.id.tv_amount);
        tv_remark = fragment_view.findViewById(R.id.tv_remark);
        tv_billing_date = fragment_view.findViewById(R.id.tv_billing_date);
        img_edit = fragment_view.findViewById(R.id.img_edit);
        /*btnsubmit = fragment_view.findViewById(R.id.btn_submit);*/
        fab_add_billing = fragment_view.findViewById(R.id.fab_billing_add);
        fab_add_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBillDialog();
            }
        });
        mActivity = (BaseAppCompatActivity)getActivity();

        tv_total_work_order.setText(""+total_work_order_amount);

       /* et_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calcuate amount according to percentage using total_work_order_amount
                try {
                    float percent = Float.parseFloat(""+s);
                    float amount = (total_work_order_amount * percent) / 100;
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
        });*/
        if (bills == null) {
            bills = new ArrayList<>();
        } else if (bills.size() <= 0) {
            bills = new ArrayList<>();
        } else {
            setUpRecyclerAdapter();
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int ten_percent_screen = (int) (dpWidth * 30) / 100;

        tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
        tv_percentage.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
        tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
        tv_remark.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
        tv_billing_date.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));
        img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen,ten_percent_screen));

        return fragment_view;
    }

    private void showAddBillDialog() {
        dialog = new AlertDialog.Builder(mActivity).create();
        getDialogView();
        if (is_edit) {
            dialog.setTitle("Edit Billing Stage");
        } else {
            dialog.setTitle("Add Billing Stage");
        }
        dialog.setView(dialog_view);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getDialogView() {
        dialog_view = LayoutInflater.from(mActivity).inflate(R.layout.add_bill_dialog_box,null);
        et_percentage = dialog_view.findViewById(R.id.et_percentage);
        et_amount = dialog_view.findViewById(R.id.et_amount);
        et_remark = dialog_view.findViewById(R.id.et_remark);
        et_billing_date = dialog_view.findViewById(R.id.et_billing_date);
        btnsubmit = dialog_view.findViewById(R.id.btn_submit);

        if (is_edit) {
            et_percentage.setText(""+edit_bill.getPercentage());
            et_amount.setText(""+edit_bill.getAmount());
            et_remark.setText(""+edit_bill.getRemark());
            et_billing_date.setText(""+edit_bill.getBilling_date());
        }

        et_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calcuate amount according to percentage using total_work_order_amount
                try {
                    float percent = Float.parseFloat(""+s);
                    float amount = (total_work_order_amount * percent) / 100;
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


    public void showPopupForDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
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
            //edit_bill = bills.get(edit_position);
            bills.get(edit_position).setAmount(Float.parseFloat(et_amount.getText().toString()));
            bills.get(edit_position).setBilling_date(billing_date);
            bills.get(edit_position).setPercentage(Float.parseFloat(et_percentage.getText().toString()));
            bills.get(edit_position).setRemark(et_remark.getText().toString());
            setUpRecyclerAdapter();
            clearEdit();
            Toast.makeText(mActivity, "Successfully edited bill", Toast.LENGTH_SHORT).show();
        } else {
            BilliModel bill = new BilliModel();
            bill.setAmount(Float.parseFloat(et_amount.getText().toString()));
            bill.setBilling_date(billing_date);
            bill.setPercentage(Float.parseFloat(et_percentage.getText().toString()));
            bill.setRemark(et_remark.getText().toString());

            bills.add(bill);
            setUpRecyclerAdapter();
            clearEdit();
            Toast.makeText(mActivity, "Successfully added bill", Toast.LENGTH_SHORT).show();
        }

        dialog.dismiss();
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
        mAdapter = new BillRecyclerAdapter(mActivity,bills);
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
            footer_view = getFooterViewForTotalAmount();
            lst_billing.addFooterView(footer_view);

        } else {
            float tot_amount = getTotalAmount();
            tv_footer_total.setText(""+tot_amount);

            float tot_percentage = getRemaining();
            tv_footer_total_percentage.setText(""+tot_percentage);
        }
    }

    private View getFooterViewForTotalAmount() {
        View footer_view = LayoutInflater.from(mActivity).inflate(R.layout.bill_list_total_item,null);
        tv_footer_total = footer_view.findViewById(R.id.tv_bill_list_total_item);
        tv_footer_total_percentage = footer_view.findViewById(R.id.tv_percentage_bill_list_total_item);
        float tot_amount = getTotalAmount();
        tv_footer_total.setText(""+tot_amount);
        float tot_percentage = getRemaining();
        tv_footer_total_percentage.setText(""+tot_percentage);
        return footer_view;
    }

    private float getTotalAmount() {
        float tot_amount = 0;
        for (int i = 0; i < bills.size(); i++) {
            BilliModel model = (BilliModel) bills.get(i);
            tot_amount = tot_amount + model.amount;
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
        edit_bill = bills.get(position);
        is_edit = true;

        showAddBillDialog();
    }
}