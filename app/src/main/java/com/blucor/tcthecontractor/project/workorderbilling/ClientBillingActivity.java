package com.blucor.tcthecontractor.project.workorderbilling;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.BillResponseModel;
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.ClientBillPaymentRecyclerAdapter;
import com.blucor.tcthecontractor.rv_adapters.ProjectSpinnerAdapter;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientBillingActivity extends BaseAppCompatActivity {
    private ArrayList<ProjectsModel> mList;
    private ProjectSpinnerAdapter mAdapter;
    private ProjectsModel selected_project;

    private Spinner spr_project_list;
    private ListView lst_billing;

    private TextView tv_total_work_order;
    private TextView tv_bill_payable_percentage;
    private TextView tv_bill_paid_percentage;
    private TextView tv_bill_balance_percentage;
    private TextView tv_bill_payable_amount;
    private TextView tv_bill_paid_amount;
    private TextView tv_bill_balance_amount;

    private ArrayList<BilliModel> bills;
    private ArrayList<WorkOrderModel> workOrders;
    private long total_work_order;

    private ClientBillPaymentRecyclerAdapter mAdapterBill;

    private View dialog_view;
    private BilliModel edit_bill;
    private AlertDialog dialog;
    EditText et_percentage, et_amount, et_remark, et_billing_date, et_balance, et_paid, et_payment_date;
    Button btnsubmit;
    private boolean is_edit = false;
    private int edit_position;
    private String billing_date;
    private String payment_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_billing);

        spr_project_list = findViewById(R.id.spr_project_list);
        lst_billing = findViewById(R.id.lst_billing);
        tv_total_work_order = findViewById(R.id.tv_total_work_order);
        tv_bill_payable_percentage = findViewById(R.id.tv_bill_payable_percentage);
        tv_bill_paid_percentage = findViewById(R.id.tv_bill_paid_percentage);
        tv_bill_balance_percentage = findViewById(R.id.tv_bill_balance_percentage);
        tv_bill_payable_amount = findViewById(R.id.tv_bill_payable_amount);
        tv_bill_paid_amount = findViewById(R.id.tv_bill_paid_amount);
        tv_bill_balance_amount = findViewById(R.id.tv_bill_balance_amount);

        bills = new ArrayList<>();
        getProjectList();
    }

    private void getProjectList() {
        User user = DatabaseUtil.on().getAllUser().get(0);
        int contractor_id = user.server_id;

        showLoader();
        try {
            RetrofitClient.getApiService().getAllProjectContractorType(contractor_id).enqueue(new Callback<List<ProjectsModel>>() {
                @Override
                public void onResponse(Call<List<ProjectsModel>> call, Response<List<ProjectsModel>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        mList = new ArrayList<>();
                        mList.addAll(response.body());
                        setupAdapter();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<ProjectsModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } catch (Exception e) {
            Log.e("view project", e.getMessage());
            stopLoader();
        }
    }

    private void setupAdapter() {
        mAdapter = new ProjectSpinnerAdapter(mList, ClientBillingActivity.this);
        spr_project_list.setAdapter(mAdapter);
        spr_project_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_project = mList.get(position);
                setUpBillOnProjectChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpBillOnProjectChange() {
        if (selected_project != null) {
            showLoader();
            RetrofitClient.getApiService().getAllWorkOrderByProjectId(selected_project.id).enqueue(new Callback<List<WorkOrderModel>>() {
                @Override
                public void onResponse(Call<List<WorkOrderModel>> call, Response<List<WorkOrderModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        workOrders = new ArrayList<>();
                        assert response.body() != null;
                        workOrders.addAll(response.body());
                        total_work_order = getTotalWorkOrder();
                        tv_total_work_order.setText("" + total_work_order);
                        setUpBill();
                    } else {
                        stopLoader();
                    }
                }

                @Override
                public void onFailure(Call<List<WorkOrderModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } else {
            Toast.makeText(this, "Please select project", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpBill() {
        if (selected_project != null) {
            showLoader();
            RetrofitClient.getApiService().getAllBillByProjectId(selected_project.id).enqueue(new Callback<List<BilliModel>>() {
                @Override
                public void onResponse(Call<List<BilliModel>> call, Response<List<BilliModel>> response) {
                    if (response.code() == 200) {
                        stopLoader();
                        lst_billing.setVisibility(View.VISIBLE);
                        bills = new ArrayList<>();
                        assert response.body() != null;
                        bills.addAll(response.body());
                        lst_billing.setVisibility(View.VISIBLE);
                        setUpRecyclerAdapter();
                        setGrandTotal();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<List<BilliModel>> call, Throwable t) {
                    stopLoader();
                }
            });
        } else {
            Toast.makeText(this, "Please select project", Toast.LENGTH_SHORT).show();
        }
    }


    private void setUpRecyclerAdapter() {
        BilliModel model = new BilliModel();
        ArrayList<BilliModel> billList = new ArrayList<>();
//        billList.add(model);
        billList.addAll(bills);
        mAdapterBill = new ClientBillPaymentRecyclerAdapter(this, billList);
        mAdapterBill.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
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

        lst_billing.setAdapter(mAdapterBill);
    }

    private void showAddBillDialog() {
        dialog = new AlertDialog.Builder(this).create();
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
        dialog_view = LayoutInflater.from(this).inflate(R.layout.add_bill_dialog_box, null);
        et_percentage = dialog_view.findViewById(R.id.et_percentage);
        et_amount = dialog_view.findViewById(R.id.et_amount);
        et_remark = dialog_view.findViewById(R.id.et_remark);
        et_billing_date = dialog_view.findViewById(R.id.et_billing_date);
        et_percentage.setEnabled(false);
        et_amount.setEnabled(false);
        et_remark.setEnabled(false);
        et_billing_date.setEnabled(false);
        et_balance = dialog_view.findViewById(R.id.et_balance);
        et_paid = dialog_view.findViewById(R.id.et_paid);
        et_payment_date = dialog_view.findViewById(R.id.et_payment_date);
        btnsubmit = dialog_view.findViewById(R.id.btn_submit);

        if (is_edit) {
            /*tv_percentage.setText("" + edit_bill.getPercentage());
            tv_amount.setText("" + edit_bill.getAmount());
            tv_remark.setText("" + edit_bill.getRemark());
            tv_billing_date.setText("" + edit_bill.getBilling_date());*/
            billing_date = edit_bill.getBilling_date();
            /*tv_amount.setText("" + edit_bill.amount);
            tv_paid.setText(""+edit_bill.paid);
            tv_balance.setText(""+edit_bill.balance);
            tv_payment_date.setText(""+edit_bill.payment_date);*/
        }
        et_billing_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForDate(v);
            }
        });

        et_payment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForPayDate(v);
            }
        });

        if (is_edit) {
            et_percentage.setText("" + edit_bill.getPercentage());
            et_amount.setText("" + edit_bill.getAmount());
            et_remark.setText("" + edit_bill.getRemark());
            et_billing_date.setText("" + edit_bill.getBilling_date());
            et_balance.setText("" + edit_bill.getBalance());
            et_paid.setText("" + edit_bill.getPaid());
            et_payment_date.setText("" + edit_bill.getPayment_date());
        }

        et_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calcuate amount according to percentage using total_work_order_amount
                try {
                    float percent = Float.parseFloat("" + s);
                    float amount = (total_work_order * percent) / 100;
                    et_amount.setText("" + amount);
                } catch (Exception exception) {
                    Log.e("Error", "" + exception.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_paid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calcuate amount according to percentage using total_work_order_amount
                try {
                    float paid_amount = Float.parseFloat("" + s);
                    float bill_amount = Float.parseFloat("" + et_amount.getText().toString());
                    if (bill_amount >= paid_amount) {
                        float balance_amount = bill_amount - paid_amount;
                        et_balance.setText("" + balance_amount);
                    } else {
                        Toast.makeText(ClientBillingActivity.this, "Please add proper amount", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exception) {
                    Log.e("Error", "" + exception.getMessage());
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

        if (et_balance.getText().toString().length() == 0) {
            et_balance.setError("All Fields are mendatory");
            et_balance.requestFocus();
            return false;
        }

        if (et_paid.getText().toString().length() == 0) {
            et_paid.setError("All Fields are mendatory");
            et_paid.requestFocus();
            return false;
        }

        if (et_payment_date.getText().toString().length() == 0) {
            et_payment_date.setError("All Fields are mendatory");
            et_payment_date.requestFocus();
            return false;
        }

        if (et_billing_date.getText().toString().length() == 0) {
            et_billing_date.setError("All Fields are mendatory");
            et_billing_date.requestFocus();
            return false;
        }

        if (!is_edit && getTotalPercentage() > 100) {
            et_percentage.setError("Please add proper percentage");
            et_percentage.requestFocus();
            return false;
        }

        et_percentage.setError(null);
        et_amount.setError(null);

        return true;
    }


    public void showPopupForDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                billing_date = sdf.format(new Date(calendar.getTimeInMillis()));
                et_billing_date.setText(billing_date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    public void showPopupForPayDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                payment_date = sdf.format(new Date(calendar.getTimeInMillis()));
                et_payment_date.setText(payment_date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void setIntentData() {
       /* tv_view.setVisibility(View.VISIBLE);
        ll_title.setVisibility(View.VISIBLE);
        lst_billing.setVisibility(View.VISIBLE);*/

        BilliModel edit_bill = bills.get(edit_position);
        edit_bill.setAmount(Double.parseDouble(et_amount.getText().toString()));
        edit_bill.setBilling_date(billing_date);
        edit_bill.setPercentage(Float.parseFloat(et_percentage.getText().toString()));
        edit_bill.setRemark(et_remark.getText().toString());
        edit_bill.setBalance(Double.parseDouble(et_balance.getText().toString()));
        edit_bill.setPaid(Double.parseDouble(et_paid.getText().toString()));
        edit_bill.setPayment_date(et_payment_date.getText().toString());

        editBill(edit_bill);


        dialog.dismiss();
    }

    private void editBill(BilliModel bill) {
        showLoader();
        RetrofitClient.getApiService().storeClientBillByProjectId(true, bill.id, bill.percentage, bill.amount, bill.remark, bill.balance, bill.paid, bill.payment_date, bill.billing_date, selected_project.id).enqueue(new Callback<BillResponseModel>() {
            @Override
            public void onResponse(Call<BillResponseModel> call, Response<BillResponseModel> response) {
                if (response.code() == 200) {
                    Log.e("response",response.body().getMessage());
                    bills.set(edit_position, bill);
                    setUpRecyclerAdapter();
                    setGrandTotal();
                    clearEdit();
                    Toast.makeText(ClientBillingActivity.this, "Successfully edited bill", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClientBillingActivity.this, "Error to edit bill", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<BillResponseModel> call, Throwable t) {
                Toast.makeText(ClientBillingActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });

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

    private long getTotalWorkOrder() {
        long tot_amount = 0;
        for (int i = 0; i < workOrders.size(); i++) {
            WorkOrderModel model = (WorkOrderModel) workOrders.get(i);
            tot_amount = tot_amount + model.amount;
        }
        return tot_amount;
    }


    private void setGrandTotal() {
        float pPercentage = 0, pdPercentage = 0;
        double pdAmount = 0, pAmount = 0, ppAmount = 0;
        for (BilliModel billiModel :
                bills) {
            pPercentage += billiModel.getPercentage();
            pAmount += billiModel.getAmount();

            ppAmount += (billiModel.getPaid() / billiModel.getAmount());
            pdAmount += billiModel.getPaid();
        }
        pdPercentage = Float.parseFloat(""+pdAmount / pAmount) * pPercentage;

//        Log.e(pdAmount +"/"+ pAmount,pPercentage+"="+pdPercentage);
        tv_bill_payable_percentage.setText(String.format("%.2f", pPercentage) + "%");
        tv_bill_payable_amount.setText(String.valueOf(pAmount) + " Rs.");

        tv_bill_paid_percentage.setText(String.format("%.2f", pdPercentage) + "%");
        tv_bill_paid_amount.setText(String.valueOf(pdAmount) + " Rs.");

        tv_bill_balance_percentage.setText(String.format("%.2f", pPercentage - pdPercentage) + "%");
        tv_bill_balance_amount.setText(String.valueOf(pAmount - pdAmount) + " Rs.");
    }


}