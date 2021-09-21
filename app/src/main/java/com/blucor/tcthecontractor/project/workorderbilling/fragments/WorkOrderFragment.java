package com.blucor.tcthecontractor.project.workorderbilling.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.models.WorkOrderResponseModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.blucor.tcthecontractor.rv_adapters.WorkOrderRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WorkOrderFragment extends Fragment {

    EditText et_workdesc, et_unit, et_qty, et_rate, et_amount;
    Button btnsubmit;
    List<UnitModal> units;
    private boolean is_edit = false;
    private int edit_position;
    private WorkOrderModel edit_work_order;
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
    /*private TextView tv_no;
    private TextView tv_item;
    private TextView tv_unit;
    private TextView tv_qty;
    private TextView tv_rate;
    private TextView tv_amount;
    private ImageView img_edit;*/
    private View fragment_view;
    private BaseAppCompatActivity mActivity;
    private ProjectsModel selected_project;
    private ImageView img_units;
    private FloatingActionButton fab_add_work_order;
    private View dialog_view;
    private AlertDialog dialog;

    public WorkOrderFragment() {
        // Required empty public constructor
    }

    public WorkOrderFragment(ArrayList<WorkOrderModel> workOrders,ProjectsModel selected_project) {
        // Required empty public constructor
        this.workOrders = workOrders;
        this.selected_project = selected_project;
        //setUpRecyclerAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragment_view = inflater.inflate(R.layout.fragment_work_order, container, false);

        /*et_workdesc = fragment_view.findViewById(R.id.et_workdesc);
        et_unit = fragment_view.findViewById(R.id.et_unit);
        img_units = fragment_view.findViewById(R.id.img_units);
        et_qty = fragment_view.findViewById(R.id.et_qty);
        et_rate = fragment_view.findViewById(R.id.et_rate);
        et_amount = fragment_view.findViewById(R.id.et_amount);*/
        tv_view = fragment_view.findViewById(R.id.tv_view);
        ll_title = fragment_view.findViewById(R.id.ll_title);
        /*tv_no = fragment_view.findViewById(R.id.tv_no);
        tv_item = fragment_view.findViewById(R.id.tv_item);
        tv_unit = fragment_view.findViewById(R.id.tv_unit);
        tv_qty = fragment_view.findViewById(R.id.tv_qty);
        tv_rate = fragment_view.findViewById(R.id.tv_rate);
        tv_amount = fragment_view.findViewById(R.id.tv_amount);
        img_edit  = fragment_view.findViewById(R.id.img_edit);*/
        fab_add_work_order  = fragment_view.findViewById(R.id.fab_work_order_add);
        mActivity = (BaseAppCompatActivity)getActivity();

        /*et_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupViewForUnits(v);
            }
        });

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
        });*/
        /*btnsubmit = fragment_view.findViewById(R.id.btnsubmit);*/
        rv_work_order = fragment_view.findViewById(R.id.rv_work_order);

       /* btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckAllFields()) {
                    setIntentData();
                }
            }
        });*/

       /* DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int ten_percent_screen = (int) (dpWidth * 27) / 100;

        tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_item.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_unit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_qty.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_rate.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen-5, ten_percent_screen));*/

        getUnits();
        if (workOrders == null) {
            workOrders = new ArrayList<>();
        }else if (workOrders.size() <= 0) {
            workOrders = new ArrayList<>();
        } else {
            setUpRecyclerAdapter();
        }

        fab_add_work_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddWorkOrderDialog();
            }
        });

        return fragment_view;
    }

    private void showAddWorkOrderDialog() {
        dialog = new AlertDialog.Builder(mActivity).create();
        getDialogView();
        if (is_edit) {
            dialog.setTitle("Edit Work Order");
        } else {
            dialog.setTitle("Add Work Order");
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
        dialog_view = LayoutInflater.from(mActivity).inflate(R.layout.add_work_order_dialog_box,null);
        et_workdesc = dialog_view.findViewById(R.id.et_workdesc);
        et_unit = dialog_view.findViewById(R.id.et_unit);
        img_units = dialog_view.findViewById(R.id.img_units);
        et_qty = dialog_view.findViewById(R.id.et_qty);
        et_rate = dialog_view.findViewById(R.id.et_rate);
        et_amount = dialog_view.findViewById(R.id.et_amount);
        btnsubmit = dialog_view.findViewById(R.id.btnsubmit);
        if (is_edit) {
            et_workdesc.setText(""+edit_work_order.work_description);
            et_amount.setText(""+edit_work_order.amount);
            et_qty.setText(""+edit_work_order.quantity);
            et_rate.setText(""+edit_work_order.rate);
            et_unit.setText(""+edit_work_order.unit);
            unit_id = edit_work_order.unit_id;
            et_qty.requestFocus();

            et_workdesc.setEnabled(false);
            et_unit.setEnabled(false);
        }
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckAllFields()) {
                    setIntentData();
                }
            }
        });
        et_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupViewForUnits(v);
            }
        });

        et_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    long rate = Long.parseLong("" + s);
                    long qty = Long.parseLong(et_qty.getText().toString());
                    long amt = rate * qty;
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
                    long qty =Long.parseLong("" + s);
                    long rate = Long.parseLong(et_rate.getText().toString());
                    long amt = rate * qty;
                    et_amount.setText("" + amt);
                }catch (Exception exception) {
                    Log.e("TextWatcher",""+exception.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setUpRecyclerAdapter() {
        /*WorkOrderModel workOrderModel = new WorkOrderModel();
        ArrayList<WorkOrderModel> workOrderList = new ArrayList<>();
        workOrderList.add(workOrderModel);
        workOrderList.addAll(workOrders);*/

        mAdapter = new WorkOrderRecyclerAdapter(mActivity,workOrders);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {

            }

            @Override
            public void addViewListClicked(View v, int position) {
                setupDeleteMode(position);
            }

            @Override
            public void editViewListClicked(View v, int position) {
                setupEditMode(position);
            }
        });

        rv_work_order.setAdapter(mAdapter);
        if (tv_footer_total == null) {
            footer_view = getFooterViewForTotalAmount();
            rv_work_order.addHeaderView(footer_view);
        } else {
            long tot_amount = getTotalAmount();
            tv_footer_total.setText(""+tot_amount);
        }
    }
    private void setupDeleteMode(int position) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(mActivity);
//        builder.setMessage("Do you wan't to delete").setTitle("Delete Work Order");
        builder.setMessage("Do you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        workOrders.remove(position);
                        setUpRecyclerAdapter();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Delete Work Order");
        alert.show();

    }

    private View getFooterViewForTotalAmount() {
        View footer_view = LayoutInflater.from(mActivity).inflate(R.layout.work_order_list_total_item,null);
        tv_footer_total = footer_view.findViewById(R.id.tv_work_order_list_total_item);
        long tot_amount = getTotalAmount();
        tv_footer_total.setText(""+tot_amount);
        return footer_view;
    }

    private long getTotalAmount() {
        long tot_amount = 0;
        for (int i = 0; i < workOrders.size(); i++) {
            WorkOrderModel model = (WorkOrderModel) workOrders.get(i);
            tot_amount = tot_amount + model.amount;
        }
        return tot_amount;
    }

    private void setupEditMode(int position) {
        edit_position = position;
        edit_work_order = workOrders.get(position);
        is_edit = true;

        showAddWorkOrderDialog();

        /*et_workdesc.setText(""+work_order.work_description);
        et_amount.setText(""+work_order.amount);
        et_qty.setText(""+work_order.quantity);
        et_rate.setText(""+work_order.rate);
        et_unit.setText(""+work_order.unit);
        unit_id = work_order.unit_id;
        et_qty.requestFocus();

        et_workdesc.setEnabled(false);
        et_unit.setEnabled(false);*/
    }

    private void setIntentData() {

        if (is_edit) {
            WorkOrderModel edit_work_order = workOrders.get(edit_position);
            edit_work_order.setAmount(Long.parseLong(et_amount.getText().toString()));
            edit_work_order.setQuantity(Long.parseLong(et_qty.getText().toString()));
            edit_work_order.setRate(Long.parseLong(et_rate.getText().toString()));
            edit_work_order.setUnit_id(unit_id);
            edit_work_order.setUnit(et_unit.getText().toString());
            edit_work_order.setWork_description(et_workdesc.getText().toString());
            editWorkOrder(edit_work_order);
        } else {
            WorkOrderModel workOrder = new WorkOrderModel();
            workOrder.setAmount(Long.parseLong(et_amount.getText().toString()));
            workOrder.setQuantity(Long.parseLong(et_qty.getText().toString()));
            workOrder.setRate(Long.parseLong(et_rate.getText().toString()));
            workOrder.setUnit_id(unit_id);
            workOrder.setUnit(et_unit.getText().toString());
            workOrder.setWork_description(et_workdesc.getText().toString());

            addWorkOrder(workOrder);
        }

        dialog.dismiss();
    }

    private void addWorkOrder(WorkOrderModel workOrder) {
        mActivity.showLoader();
        RetrofitClient.getApiService().storeWorkOrderByProjectId(false,0,workOrder.work_description,workOrder.unit_id,workOrder.quantity,workOrder.rate,workOrder.amount,selected_project.id).enqueue(new Callback<WorkOrderResponseModel>() {
            @Override
            public void onResponse(Call<WorkOrderResponseModel> call, Response<WorkOrderResponseModel> response) {
                if (response.code() == 200) {
                    workOrders.add(workOrder);
                    setUpRecyclerAdapter();
                    clearEdit();
                    Toast.makeText(mActivity, "Successfully added work order", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "Error to add work order", Toast.LENGTH_SHORT).show();
                }
                mActivity.stopLoader();
            }

            @Override
            public void onFailure(Call<WorkOrderResponseModel> call, Throwable t) {
                Toast.makeText(mActivity, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                mActivity.stopLoader();
            }
        });

    }

    private void editWorkOrder(WorkOrderModel workOrder) {
        mActivity.showLoader();
        RetrofitClient.getApiService().storeWorkOrderByProjectId(true,workOrder.id,workOrder.work_description,workOrder.unit_id,workOrder.quantity,workOrder.rate,workOrder.amount,selected_project.id).enqueue(new Callback<WorkOrderResponseModel>() {
            @Override
            public void onResponse(Call<WorkOrderResponseModel> call, Response<WorkOrderResponseModel> response) {
                if (response.code() == 200) {
                    setUpRecyclerAdapter();
                    clearEdit();
                    Toast.makeText(mActivity, "Successfully edited work order", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "Error to edited work order", Toast.LENGTH_SHORT).show();
                }
                mActivity.stopLoader();
            }

            @Override
            public void onFailure(Call<WorkOrderResponseModel> call, Throwable t) {
                Toast.makeText(mActivity, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                mActivity.stopLoader();
            }
        });

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
                        units = server_units;
                    } else {
                        units = new ArrayList<>();
                    }
                }
                mActivity.stopLoader();
                if (workOrders == null) {
                    workOrders = new ArrayList<>();
                }else if (workOrders.size() <= 0) {
                    workOrders = new ArrayList<>();
                } else {
                    setUpRecyclerAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units",""+t.getMessage());
                units = new ArrayList<>();
                mActivity.stopLoader();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Choose Units: ");
            AlertDialog alert = builder.create();
            UnitAdapter adapter = new UnitAdapter(mActivity, units);
            ListView lst_project_type = new ListView(mActivity);
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

}