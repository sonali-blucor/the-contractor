package com.blucor.tcthecontractor.project.material;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.Material;
import com.blucor.tcthecontractor.models.ProjectMaterialModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterialDetailsActivity extends BaseAppCompatActivity {

    private boolean isAddOrEdit = false;

    private TextInputEditText et_material_qty;
    private TextInputEditText et_material_gst;
    private TextInputEditText et_material_gst_amount;
    private TextInputEditText et_material_rate;
    private TextView tv_material_total_amount;
    private Button btn_submit;
    private ProjectsModel project;
    private Material material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_details);


        et_material_qty = findViewById(R.id.et_material_qty);
        et_material_gst = findViewById(R.id.et_material_gst);
        et_material_gst_amount = findViewById(R.id.et_material_gst_amount);
        et_material_rate = findViewById(R.id.et_material_rate);
        tv_material_total_amount = findViewById(R.id.tv_material_total_amount);
        btn_submit = findViewById(R.id.btn_submit);

        try {
            Bundle bundle = getIntent().getExtras();
            if (getIntent().hasExtra(AppKeys.ACTIVITY_DETAIL_TYPE)) {
                isAddOrEdit = bundle.getBoolean(AppKeys.ACTIVITY_DETAIL_TYPE);
            }
            if (getIntent().hasExtra(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
            }

            if (getIntent().hasExtra(AppKeys.MATERIAL)) {
                material = bundle.getParcelable(AppKeys.MATERIAL);
                isAddOrEdit = true;
                setUpData();
            }
        } catch (Exception e) {
            isAddOrEdit = false;
        }

        et_material_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String rate = et_material_rate.getText().toString();
                if (rate.length() != 0) {
                    et_material_gst.setEnabled(true);
                    setTotalAmount();
                } else {
                    et_material_gst.setEnabled(false);
                }
            }
        });
        et_material_gst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int gstper = Integer.parseInt(et_material_gst.getText().toString());
                    if (gstper < 0 || gstper > 100) {
                        et_material_gst.requestFocus();
                        et_material_gst.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        et_material_gst.setTextColor(getResources().getColor(R.color.black));
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                setTotalAmount();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qty = et_material_qty.getText().toString();

                String rate = et_material_rate.getText().toString();
                String gst = et_material_gst.getText().toString();
                String gstAmount = et_material_gst_amount.getText().toString();

                //Make all Function for validation and email parameter

                boolean check = validateinfo(qty, rate, gst);

                if (check == true) {
                    Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Check valid information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setTotalAmount() {
        int gstper = 0;
        if (et_material_gst.getText().toString().length() == 0) {
            gstper = 0;
        } else {
            gstper = Integer.parseInt(et_material_gst.getText().toString());
        }
        double rate = Double.parseDouble(et_material_rate.getText().toString());
/*
GST Amount = (Value of supply x GST%)/100
Price to be charged = Value of supply + GST Amount
*/
        double gstAmount = (rate * gstper) / 100;
        et_material_gst_amount.setText(String.valueOf(gstAmount));

        tv_material_total_amount.setText("Total Amount:" + String.valueOf(gstAmount + rate));
    }

    private Boolean validateinfo(String qty, String rate, String gst) {
        if (qty.length() == 0) {
            et_material_qty.requestFocus();
            et_material_qty.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (rate.length() == 0) {
            et_material_rate.requestFocus();
            et_material_rate.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (gst.length() == 0) {
            et_material_gst.requestFocus();
            et_material_gst.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else {
            return true;
        }
    }


    private void setUpData() {
        if (material != null) {
            et_material_qty.setText(material.quantity);
            //edt_material_rate.setText(material.rate);
            //edt_material_amount.setText(material.amount);
        }
    }

    private boolean is_valid_data() {
        String error = "Empty Field";
        if (et_material_qty.getText().toString().isEmpty() || et_material_qty.getText().toString().trim().equalsIgnoreCase("")) {
            et_material_qty.setError(error);
            et_material_qty.requestFocus();
            return false;
        } else {
            return false;
        }
    }

    public void onClickToSubmitMaterial(View view) {
        if (is_valid_data()) {

            String quantity = et_material_qty.getText().toString();

            int material_id = 0;
            if (material != null) {
                isAddOrEdit = true;
                material_id = material.material_id;
            }

          /*  showLoader();
            RetrofitClient.getApiService().storeMaterial(name, cno, email, adr, bname, des, unit, quantity, project.id, isAddOrEdit, material_id).enqueue(new Callback<ProjectMaterialModel>() {
                @Override
                public void onResponse(Call<ProjectMaterialModel> call, Response<ProjectMaterialModel> response) {
                    if (response.code() == 200) {
                        Material material = response.body().material;
                        Toast.makeText(AddMaterialDetailsActivity.this, "Material added sucessfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(AppKeys.PROJECT, project);
                        ScreenHelper.redirectToClass(AddMaterialDetailsActivity.this, AddMaterialActivity.class, bundle);
                        finish();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<ProjectMaterialModel> call, Throwable t) {
                    stopLoader();
                    Log.e("TAG", "Error : " + t.getMessage());
                }
            });*/
        }
    }
}