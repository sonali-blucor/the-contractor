package com.blucor.tcthecontractor.project.workorderbilling.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.BillPaymentRecyclerAdapter;
import com.blucor.tcthecontractor.rv_adapters.BillRecyclerAdapter;
import com.blucor.tcthecontractor.rv_adapters.WorkOrderRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SplitViewFragment extends Fragment {
    private ArrayList<WorkOrderModel> workOrders;
    private ArrayList<BilliModel> bills;
    private View fragement_view;
    private BaseAppCompatActivity mActivity;
    private ProjectsModel selected_project;
    private ListView rv_work_order;
    private ListView lst_billing;
    private WorkOrderRecyclerAdapter mWorkAdapter;
    private BillPaymentRecyclerAdapter mBillAdapter;

    private TextView tv_no;
    private TextView tv_item;
    private TextView tv_unit;
    private TextView tv_qty;
    private TextView tv_rate;
    private TextView tv_amount;
    private TextView tv_no_bill;
    private TextView tv_percentage_bill;
    private TextView tv_amount_bill;
    private TextView tv_remark_bill;
    private TextView tv_billing_date_bill;
    private ImageView img_edit;
    private ImageView img_edit_bill;

    public SplitViewFragment(ProjectsModel selected_project) {
        this.selected_project = selected_project;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragement_view =  inflater.inflate(R.layout.fragment_split_view, container, false);
        rv_work_order = fragement_view.findViewById(R.id.rv_work_order);
        lst_billing = fragement_view.findViewById(R.id.lst_billing);
        tv_no = fragement_view.findViewById(R.id.tv_no);
        tv_item = fragement_view.findViewById(R.id.tv_item);
        tv_unit = fragement_view.findViewById(R.id.tv_unit);
        tv_qty = fragement_view.findViewById(R.id.tv_qty);
        tv_rate = fragement_view.findViewById(R.id.tv_rate);
        tv_amount = fragement_view.findViewById(R.id.tv_amount);
        img_edit = fragement_view.findViewById(R.id.img_edit);
        tv_no_bill = fragement_view.findViewById(R.id.tv_no_bill);
        tv_percentage_bill = fragement_view.findViewById(R.id.tv_percentage);
        tv_amount_bill = fragement_view.findViewById(R.id.tv_amount_bill);
        tv_remark_bill = fragement_view.findViewById(R.id.tv_remark);
        tv_billing_date_bill = fragement_view.findViewById(R.id.tv_billing_date);
        img_edit_bill = fragement_view.findViewById(R.id.img_edit_bill);

        mActivity = (BaseAppCompatActivity)getActivity();

        getWorkOrderAndBilling();

        return fragement_view;
    }

    private void getWorkOrderAndBilling() {
        if (selected_project != null) {
            mActivity.showLoader();
            RetrofitClient.getApiService().getAllWorkOrderByProjectId(selected_project.id).enqueue(new Callback<List<WorkOrderModel>>() {
                @Override
                public void onResponse(Call<List<WorkOrderModel>> call, Response<List<WorkOrderModel>> response) {
                    if (response.code() == 200) {
                        workOrders = new ArrayList<>();
                        assert response.body() != null;
                        workOrders.addAll(response.body());

                        getBill();
                    } else {
                        mActivity.stopLoader();
                    }
                }

                @Override
                public void onFailure(Call<List<WorkOrderModel>> call, Throwable t) {
                    mActivity.stopLoader();
                }
            });
        }
    }

    private void getBill() {
        if (selected_project != null) {
            RetrofitClient.getApiService().getAllBillByProjectId(selected_project.id).enqueue(new Callback<List<BilliModel>>() {
                @Override
                public void onResponse(Call<List<BilliModel>> call, Response<List<BilliModel>> response) {
                    if (response.code() == 200) {
                        bills = new ArrayList<>();
                        assert response.body() != null;
                        bills.addAll(response.body());
                        setUpAdapter();
                    }
                    mActivity.stopLoader();
                }

                @Override
                public void onFailure(Call<List<BilliModel>> call, Throwable t) {
                    mActivity.stopLoader();
                }
            });
        } else {
            mActivity.stopLoader();
        }
    }

    private void setUpAdapter() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        //screen size for work order
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int ten_percent_screen = (int) (dpWidth * 27) / 100;

        tv_no.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_item.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_unit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_qty.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_rate.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        tv_amount.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen, ten_percent_screen));
        img_edit.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen-5, ten_percent_screen));

        float dpWidth_bill = displayMetrics.widthPixels / displayMetrics.density;
        int ten_percent_screen_bill = (int) (dpWidth_bill * 30) / 100;

        tv_no_bill.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_bill,ten_percent_screen_bill));
        tv_percentage_bill.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_bill,ten_percent_screen_bill));
        tv_amount_bill.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_bill,ten_percent_screen_bill));
        tv_remark_bill.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_bill,ten_percent_screen_bill));
        tv_billing_date_bill.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_bill,ten_percent_screen_bill));
        img_edit_bill.setLayoutParams(new LinearLayout.LayoutParams(ten_percent_screen_bill,ten_percent_screen_bill));

        mWorkAdapter = new WorkOrderRecyclerAdapter(mActivity,workOrders);
        mBillAdapter = new BillPaymentRecyclerAdapter(mActivity,bills);
        rv_work_order.setAdapter(mWorkAdapter);
        lst_billing.setAdapter(mBillAdapter);
    }
}