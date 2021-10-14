package com.blucor.tcthecontractor.project.material;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.Supplier;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSupplierActivity extends BaseAppCompatActivity {


    private TextInputEditText edt_material_supplier_name;
    private TextInputEditText edt_supplier_contact_no;
    private TextInputEditText edt_supplier_email;
    private TextInputEditText edt_supplier_address;
    private Button btn_submit;

    private ProjectsModel project;
    private Supplier supplier;
    private boolean isAddOrEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suppiler);

        edt_material_supplier_name = findViewById(R.id.edt_material_supplier_name);
        edt_supplier_contact_no = findViewById(R.id.edt_supplier_contact_no);
        edt_supplier_email = findViewById(R.id.edt_supplier_email);
        edt_supplier_address = findViewById(R.id.edt_supplier_address);
        btn_submit = findViewById(R.id.btn_submit);

        try {
            Bundle bundle = getIntent().getExtras();
            if (getIntent().hasExtra(AppKeys.ACTIVITY_DETAIL_TYPE)) {
                isAddOrEdit = bundle.getBoolean(AppKeys.ACTIVITY_DETAIL_TYPE);
            }
            if (getIntent().hasExtra(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
            }

            if (getIntent().hasExtra(AppKeys.SUPPLIER)) {
                supplier = bundle.getParcelable(AppKeys.SUPPLIER);
                isAddOrEdit = true;
                setUpData();
            }
        } catch (Exception e) {
            isAddOrEdit = false;
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_material_supplier_name.getText().toString();
                String cnt = edt_supplier_contact_no.getText().toString();
                String email = edt_supplier_email.getText().toString();
                String adr = edt_supplier_address.getText().toString();

                //make function for validation and pass all parameters

                if (validateinfo(name, cnt, email, adr)) {
                    onClickToSubmitSupplier();
                }

            }


        });


    }

    private Boolean validateinfo(String name, String cnt, String email, String adr) {
        if (name.length() == 0) {
            edt_material_supplier_name.requestFocus();
            edt_material_supplier_name.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!name.matches("[a-zA-z ]+")) {
            edt_material_supplier_name.requestFocus();
            edt_material_supplier_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (cnt.length() == 0) {
            edt_supplier_contact_no.requestFocus();
            edt_supplier_contact_no.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!cnt.matches("^[0-9]{10,13}$")) {
            edt_supplier_contact_no.requestFocus();
            edt_supplier_contact_no.setError("Correct format: xxxxxxxxxx");
            return false;
        } else if (email.length() == 0) {
            edt_supplier_email.requestFocus();
            edt_supplier_email.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else {
            return true;
        }
    }


    private void setUpData() {
        if (supplier != null) {
            edt_material_supplier_name.setText(supplier.supplierName);
            edt_supplier_contact_no.setText(supplier.supplierContact);
            edt_supplier_email.setText(supplier.supplierEmail);
            edt_supplier_address.setText(supplier.supplierAddress);
        }
    }


    private boolean is_valid_data() {
        String error = "Empty Field";
        if (edt_material_supplier_name.getText().toString().isEmpty() || edt_material_supplier_name.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_supplier_name.setError(error);
            edt_material_supplier_name.requestFocus();
            return false;
        } else if (edt_supplier_contact_no.getText().toString().isEmpty() || edt_supplier_contact_no.getText().toString().trim().equalsIgnoreCase("")) {
            edt_supplier_contact_no.setError(error);
            edt_supplier_contact_no.requestFocus();
            return false;
        } else if (edt_supplier_email.getText().toString().isEmpty() || edt_supplier_email.getText().toString().trim().equalsIgnoreCase("")) {
            edt_supplier_email.setError(error);
            edt_supplier_email.requestFocus();
            return false;
        } else if (edt_supplier_address.getText().toString().isEmpty() || edt_supplier_address.getText().toString().trim().equalsIgnoreCase("")) {
            edt_supplier_address.setError(error);
            edt_supplier_address.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void onClickToSubmitSupplier() {
        if (is_valid_data()) {
            String name = edt_material_supplier_name.getText().toString();
            String cno = edt_supplier_contact_no.getText().toString();
            String email = edt_supplier_email.getText().toString();
            String adr = edt_supplier_address.getText().toString();


            String supplier_id = "";
            if (supplier != null) {
                isAddOrEdit = true;
                supplier_id = supplier.supplierId;
            }

            showLoader();
            RetrofitClient.getApiService().storeSupplier(name, cno, email, adr).enqueue(new Callback<Supplier>() {
                @Override
                public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                    Log.e("response", response.toString());
                    if (response != null && response.code() == 200) {
                        if (response.body() != null) {
                            Supplier supplier = response.body();
                            Toast.makeText(AddSupplierActivity.this, "Supplier store successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddSupplierActivity.this, "Unable to store supplier", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(AddSupplierActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddSupplierActivity.this, "Supplier is already exists", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<Supplier> call, Throwable t) {
                    stopLoader();
                    Log.e("TAG", "Error : " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(AddSupplierActivity.this, "Error validation", Toast.LENGTH_SHORT).show();
        }
    }

}