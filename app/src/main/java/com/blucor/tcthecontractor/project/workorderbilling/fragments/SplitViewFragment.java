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
       /* WorkOrderModel workOrderModel = new WorkOrderModel();
        ArrayList<WorkOrderModel> workOrderList = new ArrayList<>();
        workOrderList.add(workOrderModel);
        workOrderList.addAll(workOrders);*/
        mWorkAdapter = new WorkOrderRecyclerAdapter(mActivity,workOrders);

        BilliModel model = new BilliModel();
        ArrayList<BilliModel> billList = new ArrayList<>();
        billList.add(model);
        billList.addAll(bills);
        mBillAdapter = new BillPaymentRecyclerAdapter(mActivity,billList);
        rv_work_order.setAdapter(mWorkAdapter);
        lst_billing.setAdapter(mBillAdapter);
    }
}