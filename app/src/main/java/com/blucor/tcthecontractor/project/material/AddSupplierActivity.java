package com.blucor.tcthecontractor.project.material;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.SupplierModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSupplierActivity extends BaseAppCompatActivity {


    private TextInputEditText edt_material_supplier_name;
    private TextInputEditText edt_supplier_contact_no;
    private TextInputEditText edt_supplier_email;
    private TextInputEditText edt_supplier_address;
    private TextInputEditText edt_pan_cart_no;
    private TextInputEditText edt_aadhar_cart_no;
    private TextInputEditText edt_bank_details;
    private TextInputLayout til_supplier_contact_no;

    private Button btn_submit;

    private ProjectsModel project;
    private SupplierModal supplierModal;
    private boolean isAddOrEdit = false;


    private final static int PICK_CONTACT = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suppiler);

        edt_material_supplier_name = findViewById(R.id.edt_material_supplier_name);
        edt_supplier_contact_no = findViewById(R.id.edt_supplier_contact_no);
        edt_supplier_email = findViewById(R.id.edt_supplier_email);
        edt_supplier_address = findViewById(R.id.edt_supplier_address);
        edt_pan_cart_no = findViewById(R.id.edt_pan_cart_no);
        edt_aadhar_cart_no = findViewById(R.id.edt_aadhar_cart_no);
        edt_bank_details = findViewById(R.id.edt_bank_details);
        til_supplier_contact_no = findViewById(R.id.til_supplier_contact_no);
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
                supplierModal = bundle.getParcelable(AppKeys.SUPPLIER);
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

        til_supplier_contact_no.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            try {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                edt_supplier_contact_no.setText(cursor.getString(column).replace(" ", "").replace("+91", ""));
                Log.e("phone number", cursor.getString(column));
            } catch (Exception e) {
                Log.e("phone number", e.getMessage());
            }
        }
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
        if (supplierModal != null) {
            edt_material_supplier_name.setText(supplierModal.supplierName);
            edt_supplier_contact_no.setText(supplierModal.supplierContact);
            edt_supplier_email.setText(supplierModal.supplierEmail);
            edt_supplier_address.setText(supplierModal.supplierAddress);
            edt_pan_cart_no.setText(supplierModal.pan_cart_no);
            edt_aadhar_cart_no.setText(supplierModal.aadhar_cart_no);
            edt_bank_details.setText(supplierModal.bank_details);
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
            String pan_cart_no = edt_pan_cart_no.getText().toString();
            String aadhar_cart_no = edt_aadhar_cart_no.getText().toString();
            String bank_details = edt_bank_details.getText().toString();


            int supplier_id = 0;
            if (supplierModal != null) {
                isAddOrEdit = true;
                supplier_id = supplierModal.supplierId;
            }

            showLoader();
            RetrofitClient.getApiService().storeSupplier(name, cno, email, adr, pan_cart_no, aadhar_cart_no, bank_details).enqueue(new Callback<SupplierModal>() {
                @Override
                public void onResponse(Call<SupplierModal> call, Response<SupplierModal> response) {
                    Log.e("response", response.toString());
                    if (response != null && response.code() == 200) {
                        if (response.body() != null) {
                            SupplierModal supplierModal = response.body();
                            Toast.makeText(AddSupplierActivity.this, "SupplierModal store successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddSupplierActivity.this, "Unable to store supplierModal", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(AddSupplierActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddSupplierActivity.this, "SupplierModal is already exists", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<SupplierModal> call, Throwable t) {
                    stopLoader();
                    Log.e("TAG", "Error : " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(AddSupplierActivity.this, "Error validation", Toast.LENGTH_SHORT).show();
        }
    }

}