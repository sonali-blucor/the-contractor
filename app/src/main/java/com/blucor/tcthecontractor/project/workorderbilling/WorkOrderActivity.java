package com.blucor.tcthecontractor.project.workorderbilling;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.blucor.tcthecontractor.rv_adapters.WorkOrderRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkOrderActivity extends BaseAppCompatActivity {

    EditText et_workdesc, et_unit, et_qty, et_rate, et_amount;
    Button btnsubmit;
    List<UnitModal> units;
    private boolean is_edit = false;
    private int edit_position;
    private int unit_id;
    //private ProjectsModel project;
    private ListView rv_work_order;

    //private Client client;
    private ArrayList<WorkOrderModel> workOrders;
    private WorkOrderRecyclerAdapter mAdapter;
    private TextView tv_footer_total;
    private View footer_view;
    private TextView tv_view;
    private LinearLayout ll_title;
    private TextView tv_no;
    private TextView tv_item;
    private TextView tv_unit;
    private TextView tv_qty;
    private TextView tv_rate;
    private TextView tv_amount;
    private ImageView img_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        et_workdesc = findViewById(R.id.et_workdesc);
        et_unit = findViewById(R.id.et_unit);
        et_qty = findViewById(R.id.et_qty);
        et_rate = findViewById(R.id.et_rate);
        et_amount = findViewById(R.id.et_amount);
        tv_view = findViewById(R.id.tv_view);
        ll_title = findViewById(R.id.ll_title);
        tv_no = findViewById(R.id.tv_no);
        tv_item = findViewById(R.id.tv_item);
        tv_unit = findViewById(R.id.tv_unit);
        tv_qty = findViewById(R.id.tv_qty);
        tv_rate = findViewById(R.id.tv_rate);
        tv_amount = findViewById(R.id.tv_amount);
        img_edit  = findViewById(R.id.img_edit);

        et_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    float rate = Float.parseFloat("" + s);
                    float qty = Float.parseFloat(et_qty.getText().toString());
                    float amt = rate * qty;
                    et_amount.setText("" + amt);
                }catch (Exception exception) {
                    Log.e("TextWatcher",""+exception.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    float qty = Float.parseFloat("" + s);
                    float rate = Float.parseFloat(et_rate.getText().toString());
                    float amt = rate * qty;
                    et_amount.setText("" + amt);
                }catch (Exception exception) {
                    Log.e("TextWatcher",""+exception.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnsubmit = findViewById(R.id.btnsubmit);
        rv_work_order = findViewById(R.id.rv_work_order);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckAllFields()) {
                    setIntentData();
                }
            }
        });

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int ten_percent_screen = (int) (dpWidth * 27) / 100;

        tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_item.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_unit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_qty.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_rate.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen-5, ten_percent_screen));

        getUnits();
        workOrders = new ArrayList<>();
    }

    private void setUpRecyclerAdapter() {
        mAdapter = new WorkOrderRecyclerAdapter(WorkOrderActivity.this,workOrders);
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

        rv_work_order.setAdapter(mAdapter);
        if (tv_footer_total == null) {
            footer_view = getFooterViewForTotalAmount();
            rv_work_order.addFooterView(footer_view);
        } else {
            float tot_amount = getTotalAmount();
            tv_footer_total.setText(""+tot_amount);
        }
    }

    private View getFooterViewForTotalAmount() {
        View footer_view = LayoutInflater.from(this).inflate(R.layout.work_order_list_total_item,null);
        tv_footer_total = footer_view.findViewById(R.id.tv_work_order_list_total_item);
        float tot_amount = getTotalAmount();
        tv_footer_total.setText(""+tot_amount);
        return footer_view;
    }

    private float getTotalAmount() {
        float tot_amount = 0;
        for (int i = 0; i < workOrders.size(); i++) {
            WorkOrderModel model = (WorkOrderModel) workOrders.get(i);
            tot_amount = tot_amount + model.amount;
        }
        return tot_amount;
    }

    private void setupEditMode(int position) {
        edit_position = position;
        WorkOrderModel work_order = workOrders.get(position);
        is_edit = true;

        et_workdesc.setText(""+work_order.work_description);
        et_amount.setText(""+work_order.amount);
        et_qty.setText(""+work_order.quantity);
        et_rate.setText(""+work_order.rate);
        et_unit.setText(""+work_order.unit);
        unit_id = work_order.unit_id;
        et_qty.requestFocus();

        et_workdesc.setEnabled(false);
        et_unit.setEnabled(false);
    }

    private void setIntentData() {
        tv_view.setVisibility(View.VISIBLE);
        ll_title.setVisibility(View.VISIBLE);
        rv_work_order.setVisibility(View.VISIBLE);

        if (is_edit) {
            workOrders.get(edit_position).setAmount(Float.parseFloat(et_amount.getText().toString()));
            workOrders.get(edit_position).setQuantity(Integer.parseInt(et_qty.getText().toString()));
            workOrders.get(edit_position).setRate(Float.parseFloat(et_rate.getText().toString()));
            workOrders.get(edit_position).setUnit_id(unit_id);
            workOrders.get(edit_position).setUnit(et_unit.getText().toString());
            workOrders.get(edit_position).setWork_description(et_workdesc.getText().toString());
            setUpRecyclerAdapter();
            clearEdit();
            Toast.makeText(WorkOrderActivity.this, "Successfully edited work order", Toast.LENGTH_SHORT).show();
        } else {
            WorkOrderModel workOrder = new WorkOrderModel();
            workOrder.setAmount(Float.parseFloat(et_amount.getText().toString()));
            workOrder.setQuantity(Integer.parseInt(et_qty.getText().toString()));
            workOrder.setRate(Float.parseFloat(et_rate.getText().toString()));
            workOrder.setUnit_id(unit_id);
            workOrder.setUnit(et_unit.getText().toString());
            workOrder.setWork_description(et_workdesc.getText().toString());

            workOrders.add(workOrder);
            setUpRecyclerAdapter();
            clearEdit();
            Toast.makeText(WorkOrderActivity.this, "Successfully added work order", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearEdit() {
        edit_position = -1;
        is_edit = false;
        et_workdesc.setText("");
        et_qty.setText("");
        et_unit.setText("");
        et_rate.setText("");
        et_amount.setText("");

        et_workdesc.setError(null);
        et_qty.setError(null);
        et_amount.setError(null);
        et_unit.setError(null);
        et_rate.setError(null);

        et_workdesc.setEnabled(true);
        et_unit.setEnabled(true);
    }

    private void getUnits() {
        RetrofitClient.getApiService().getUnits().enqueue(new Callback<List<UnitModal>>() {
            @Override
            public void onResponse(Call<List<UnitModal>> call, Response<List<UnitModal>> response) {
                if (response.code() == 200) {
                    List<UnitModal> server_units = response.body();
                    if(server_units != null && server_units.size() > 0) {
                        WorkOrderActivity.this.units = server_units;
                    } else {
                        WorkOrderActivity.this.units = new ArrayList<>();
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units",""+t.getMessage());
                WorkOrderActivity.this.units = new ArrayList<>();
                stopLoader();
            }
        });
    }

    /*private void getWorkOrder() {
        RetrofitClient.getApiService().getWorkOrderByProjectId(client.id,project.id).enqueue(new Callback<List<WorkOrderModel>>() {
            @Override
            public void onResponse(Call<List<WorkOrderModel>> call, Response<List<WorkOrderModel>> response) {

            }

            @Override
            public void onFailure(Call<List<WorkOrderModel>> call, Throwable t) {

            }
        });
    }*/

    public void showPopupViewForUnits(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(WorkOrderActivity.this);
            builder.setTitle("Choose Units: ");
            AlertDialog alert = builder.create();
            UnitAdapter adapter = new UnitAdapter(WorkOrderActivity.this, units);
            ListView lst_project_type = new ListView(WorkOrderActivity.this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    et_unit.setText(units.get(position).unit);
                    unit_id = units.get(position).id;
                    alert.dismiss();
                }
            });
            builder.setCancelable(false)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            alert.show();
        } catch (Exception e) {
            Log.e("AddProjectActivity",""+e.getMessage());
        }
    }


    private boolean CheckAllFields() {

        if (et_workdesc.getText().toString().length() == 0) {
            et_workdesc.setError("All Fields are mendatory");
            et_workdesc.requestFocus();
            return false;
        }

        if (et_unit.getText().toString().length() == 0) {
            et_unit.setError("All Fields are mendatory");
            et_unit.requestFocus();
            return false;
        }

        if (et_qty.getText().toString().length() == 0) {
            et_qty.setError("All Fields are mendatory");
            et_qty.requestFocus();
            return false;
        }

        if (et_rate.getText().toString().length() == 0) {
            et_rate.setError("All Fields are mendatory");
            et_rate.requestFocus();
            return false;
        }

        if (et_amount.getText().toString().length() == 0) {
            et_amount.setError("All Fields are mendatory");
            et_amount.requestFocus();
            return false;
        }

        et_workdesc.setError(null);
        et_unit.setError(null);
        et_qty.setError(null);
        et_rate.setError(null);
        et_amount.setError(null);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(AppKeys.WORK_ORDER, workOrders);
        setResult(121, intent);
        finish();
    }
}

