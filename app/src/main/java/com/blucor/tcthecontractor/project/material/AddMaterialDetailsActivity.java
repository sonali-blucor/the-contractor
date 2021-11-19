package com.blucor.tcthecontractor.project.material;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.MaterialPurchase;
import com.blucor.tcthecontractor.models.MaterialsModal;
import com.blucor.tcthecontractor.models.ProjectMaterialModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.SupplierModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.MaterialsAdapter;
import com.blucor.tcthecontractor.rv_adapters.SupplierAdapter;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterialDetailsActivity extends BaseAppCompatActivity {

    private boolean isAddOrEdit = false;

    private TextInputEditText edt_select_sup;
    private TextInputEditText edt_select_material;
    private TextView tv_material_unit;
    private TextInputEditText et_material_qty;
    private TextInputEditText et_material_gst;
    private TextInputEditText et_material_gst_amount;
    private TextInputEditText et_material_rate;
    private TextInputEditText edt_material_amount;
    private TextView tv_material_total_amount;
    private TextView tv_material_paid_amount;
    private TextView tv_material_balance_amount;
    private Button btn_submit;
    private Button btn_add_payment;
    private LinearLayout llv_material_edit;
    private ProjectsModel project;
    private MaterialPurchase materialPurchase;

    List<SupplierModal> supplierModals;
    List<MaterialsModal> materialsModals;
    private int supplier_id;
    private int materials_id;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_details);

        getSupplier();


        edt_select_sup = findViewById(R.id.edt_select_sup);
        edt_select_material = findViewById(R.id.edt_select_material);
        tv_material_unit = findViewById(R.id.tv_material_unit);
        et_material_qty = findViewById(R.id.et_material_qty);
        et_material_gst = findViewById(R.id.et_material_gst);
        et_material_gst_amount = findViewById(R.id.et_material_gst_amount);
        et_material_rate = findViewById(R.id.et_material_rate);
        edt_material_amount = findViewById(R.id.edt_material_amount);
        tv_material_total_amount = findViewById(R.id.tv_material_total_amount);
        tv_material_paid_amount = findViewById(R.id.tv_material_paid_amount);
        tv_material_balance_amount = findViewById(R.id.tv_material_balance_amount);
        btn_submit = findViewById(R.id.btn_submit);
        btn_add_payment = findViewById(R.id.btn_add_payment);
        llv_material_edit = findViewById(R.id.llv_material_edit);


        try {
            Bundle bundle = getIntent().getExtras();
            if (getIntent().hasExtra(AppKeys.MATERIAL_DETAIL_TYPE)) {
                isAddOrEdit = bundle.getBoolean(AppKeys.MATERIAL_DETAIL_TYPE);
            }
            if (getIntent().hasExtra(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
            }

            if (getIntent().hasExtra(AppKeys.MATERIAL)) {
                materialPurchase = bundle.getParcelable(AppKeys.MATERIAL);
                isAddOrEdit = true;
                llv_material_edit.setVisibility(View.VISIBLE);
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

                String suppler = edt_select_sup.getText().toString();
                String material = edt_select_material.getText().toString();
                String qty = et_material_qty.getText().toString();

                String rate = et_material_rate.getText().toString();
                String gst = et_material_gst.getText().toString();
                String gstAmount = et_material_gst_amount.getText().toString();

                //Make all Function for validation and email parameter

                boolean check = validateinfo(suppler, material, qty, rate, gst);

                if (check == true) {
                    onClickToSubmitMaterial(v);
//                    Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_SHORT).show();
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
        double qty = Double.parseDouble(et_material_qty.getText().toString());

/*
GST Amount = (Value of supply x GST%)/100
Price to be charged = Value of supply + GST Amount
*/
        double amount = (rate * qty);
        double gstAmount = (amount * gstper) / 100;
        et_material_gst_amount.setText(String.valueOf(gstAmount));
        edt_material_amount.setText(String.format("%.2f", amount));

        tv_material_total_amount.setText(String.format("%.2f", gstAmount + amount));
        tv_material_balance_amount.setText(String.format("%.2f", gstAmount + amount));
        tv_material_paid_amount.setText("00.00");
    }

    private Boolean validateinfo(String suppler, String matrial, String qty, String rate, String gst) {
        if (suppler.length() == 0) {
            edt_select_sup.requestFocus();
            edt_select_sup.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (matrial.length() == 0) {
            edt_select_material.requestFocus();
            edt_select_material.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (qty.length() == 0) {
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
        if (materialPurchase != null) {
            et_material_qty.setText(materialPurchase.quantity);
            et_material_rate.setText(materialPurchase.rate);
            edt_material_amount.setText(materialPurchase.amount);
            supplier_id = materialPurchase.supplier_id;
            materials_id = materialPurchase.material_id;
            edt_select_sup.setText(materialPurchase.supplier_name);
            edt_select_material.setText(materialPurchase.material_brand);
            et_material_gst.setText(materialPurchase.gst);
            et_material_gst_amount.setText(materialPurchase.gst_amt);
            tv_material_total_amount.setText(materialPurchase.total_amt);
            tv_material_paid_amount.setText(materialPurchase.paid_amt);
            tv_material_balance_amount.setText(materialPurchase.balance_amt);
            tv_material_unit.setText(materialPurchase.material_unit);
        }
    }

    private boolean is_valid_data() {
        String error = "Empty Field";
        if (edt_select_sup.getText().toString().isEmpty() || edt_select_sup.getText().toString().trim().equalsIgnoreCase("")) {
            edt_select_sup.setError(error);
            edt_select_sup.requestFocus();
            return false;
        } else if (edt_select_material.getText().toString().isEmpty() || edt_select_material.getText().toString().trim().equalsIgnoreCase("")) {
            edt_select_material.setError(error);
            edt_select_material.requestFocus();
            return false;
        } else if (et_material_qty.getText().toString().isEmpty() || et_material_qty.getText().toString().trim().equalsIgnoreCase("")) {
            et_material_qty.setError(error);
            et_material_qty.requestFocus();
            return false;
        } else if (et_material_rate.getText().toString().isEmpty() || et_material_rate.getText().toString().trim().equalsIgnoreCase("")) {
            et_material_rate.setError(error);
            et_material_rate.requestFocus();
            return false;
        } else if (et_material_gst.getText().toString().isEmpty() || et_material_gst.getText().toString().trim().equalsIgnoreCase("")) {
            et_material_gst.setError(error);
            et_material_gst.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public void onClickToSubmitMaterial(View view) {
        if (is_valid_data()) {

            int material_id = 0;
            if (materialPurchase != null) {
                isAddOrEdit = true;
                material_id = materialPurchase.material_purchase_id;
            }

//            String suppler = edt_select_sup.getText().toString();
//            String materialPurchase = edt_select_material.getText().toString();
            String quantity = et_material_qty.getText().toString();
            String rate = et_material_rate.getText().toString();
            String amount = edt_material_amount.getText().toString();
            String gst = et_material_gst.getText().toString();
            String gstAmount = et_material_gst_amount.getText().toString();
            String totalAmount = tv_material_total_amount.getText().toString();
            String paidAmount = tv_material_paid_amount.getText().toString();
            String balanceAmount = tv_material_balance_amount.getText().toString();

            Log.e("param",
                    "supplier_id="+supplier_id+"&"+
                            "material_id="+materials_id+"&"+
                            "quantity="+quantity+"&"+
                            "rate="+rate+"&"+
                            "amount="+amount+"&"+
                            "gst="+gst+"&"+
                            "gst_amt="+gstAmount+"&"+
                            "total_amt="+totalAmount+"&"+
                            "paid_amt="+paidAmount+"&"+
                            "balance_amt="+balanceAmount+"&"+
                            "contractor_id="+project.contractor_id+"&"+
                            "project_id="+project.id+"&"+
                            "is_edit="+isAddOrEdit+"&"+
                            "material_purchase_id="+material_id
            );

            showLoader();
            RetrofitClient.getApiService().storeMaterialPurchase(supplier_id,
                    materials_id,
                    quantity,
                    rate,
                    amount,
                    gst,
                    gstAmount,
                    totalAmount,
                    paidAmount,
                    balanceAmount, project.contractor_id, project.id, isAddOrEdit, material_id).enqueue(new Callback<ProjectMaterialModel>() {
                @Override
                public void onResponse(Call<ProjectMaterialModel> call, Response<ProjectMaterialModel> response) {
                    if (response.code() == 200) {
//                        MaterialPurchase materialPurchase = response.body().materialPurchase;
                        Toast.makeText(AddMaterialDetailsActivity.this, "Material purchase added successfully", Toast.LENGTH_SHORT).show();
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
            });
        }
    }


    public void showPopupViewForSupplier(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Supplier: ");
            AlertDialog alert = builder.create();
            SupplierAdapter adapter = new SupplierAdapter(this, supplierModals);
            ListView lst_project_type = new ListView(this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    edt_select_sup.setText(supplierModals.get(position).supplierName + "," +
                            supplierModals.get(position).supplierContact);
                    supplier_id = supplierModals.get(position).supplierId;
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
            Log.e("AddProjectActivity", "" + e.getMessage());
        }
    }

    public void showPopupViewForMaterial(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Materials: ");
            AlertDialog alert = builder.create();
            MaterialsAdapter adapter = new MaterialsAdapter(this, materialsModals);
            ListView lst_project_type = new ListView(this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    edt_select_material.setText(materialsModals.get(position).material_brand);
                    tv_material_unit.setText(materialsModals.get(position).unit);
                    materials_id = materialsModals.get(position).material_id;
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
            Log.e("AddProjectActivity", "" + e.getMessage());
        }
    }


    private void getSupplier() {
        RetrofitClient.getApiService().getSupplier().enqueue(new Callback<List<SupplierModal>>() {
            @Override
            public void onResponse(Call<List<SupplierModal>> call, Response<List<SupplierModal>> response) {
                if (response.code() == 200) {
                    List<SupplierModal> server_supplier = response.body();
                    if (server_supplier != null && server_supplier.size() > 0) {
                        AddMaterialDetailsActivity.this.supplierModals = server_supplier;
                    } else {
                        AddMaterialDetailsActivity.this.supplierModals = new ArrayList<>();
                    }
                }
                stopLoader();
                getMaterials();
            }

            @Override
            public void onFailure(Call<List<SupplierModal>> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                AddMaterialDetailsActivity.this.supplierModals = new ArrayList<>();
                stopLoader();
                getMaterials();
            }
        });
    }


    private void getMaterials() {
        RetrofitClient.getApiService().getMaterials().enqueue(new Callback<List<MaterialsModal>>() {
            @Override
            public void onResponse(Call<List<MaterialsModal>> call, Response<List<MaterialsModal>> response) {
                if (response.code() == 200) {
                    List<MaterialsModal> server_materials = response.body();
                    if (server_materials != null && server_materials.size() > 0) {
                        AddMaterialDetailsActivity.this.materialsModals = server_materials;
                    } else {
                        AddMaterialDetailsActivity.this.materialsModals = new ArrayList<>();
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<MaterialsModal>> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                AddMaterialDetailsActivity.this.materialsModals = new ArrayList<>();
                stopLoader();
            }
        });
    }

    public void onClickToPaymentAdd(View view) {
    }

    public void showPopupViewForPaymentAdd(View v) {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_material_payament, null);

        /*    etAddress1 = view.findViewById(R.id.et_shipping_address_1);
            etAddress2 = view.findViewById(R.id.et_shipping_address_2);
            etCity = view.findViewById(R.id.et_shipping_city);
            etZip = view.findViewById(R.id.et_shipping_zip);
            etState = view.findViewById(R.id.et_shipping_state);
            etCountry = view.findViewById(R.id.et_shipping_country);
            btnCompleteAddress = view.findViewById(R.id.btn_complete_address_inner);

            etAddress1.setText(address1);
            etAddress2.setText(address2);
            etZip.setText(zip);
            etCity.setText(city);
            etState.setText(state);
            etCountry.setText(country);*/

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

         /*   btnCompleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateAddress();
                    if (!isFieldEmpty) {
                        isAddressInput = true;
                        hideKeyboardFrom(AddProjectActivity.this,view);
                        String Address = address1+","+address2+","+city+","+zip+","+state+","+country;
                        mEdtAddress.setText(Address);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(AddProjectActivity.this, "Field Empty!", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        } catch (Exception e) {
            Log.e("AddProjectActivity", "" + e.getMessage());
        }
    }

}