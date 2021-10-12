package com.blucor.tcthecontractor.project.material;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.Material;
import com.blucor.tcthecontractor.models.ProjectMaterialModel;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

public class AddSupplierActivity extends BaseAppCompatActivity {


    private TextInputEditText edt_material_supplier_name;
    private TextInputEditText edt_supplier_contact_no;
    private TextInputEditText edt_supplier_email;
    private TextInputEditText edt_supplier_address;
    private Button btn_submit;

    private ProjectsModel project;
    private Material material;
    private boolean isAddOrEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_suppiler);

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

            if (getIntent().hasExtra(AppKeys.MATERIAL)) {
                material = bundle.getParcelable(AppKeys.MATERIAL);
                isAddOrEdit = true;
                setUpData();
            }
        } catch (Exception e) {
            isAddOrEdit = false;
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edt_material_supplier_name.getText().toString();
                String cnt=edt_supplier_contact_no.getText().toString();
                String email=edt_supplier_email.getText().toString();
                String adr=edt_supplier_address.getText().toString();

                //make function for validation and pass all parameters

                 validateinfo(name,cnt,email,adr);

            }


        });



    }

    private Boolean validateinfo(String name, String cnt, String email, String adr) {
        if (name.length() == 0) {
            edt_material_supplier_name.requestFocus();
            edt_material_supplier_name.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!name.matches("[a-zA-z]+")) {
            edt_material_supplier_name.requestFocus();
            edt_material_supplier_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (cnt.length() == 0) {
            edt_supplier_contact_no.requestFocus();
            edt_supplier_contact_no.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!cnt.matches("^[+][0-9]{10,13}$")) {
            edt_supplier_contact_no.requestFocus();
            edt_supplier_contact_no.setError("Correct format: +91xxxxxxxxxx");
            return false;
        } else if (email.length() == 0) {
            edt_supplier_email.requestFocus();
            edt_supplier_email.setError("FIELD CANNOT BE EMPTY");
            return false;
        }else {
            return true;
        }
    }



    private void setUpData() {
        if (material != null) {
            edt_material_supplier_name.setText(material.supplier_name);
            edt_supplier_contact_no.setText(material.supplier_contact);
//            edt_supplier_email.setText(material.supplier_contact);
//            edt_supplier_address.setText(material.supplier_contact);
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
        } else  {
            return false;
        }
    }

    public void onClickToSubmitMaterial(View view) {
        if (is_valid_data()) {
            String name = edt_material_supplier_name.getText().toString();
            String cno = edt_supplier_contact_no.getText().toString();
            String email = edt_supplier_email.getText().toString();
            String adr = edt_supplier_address.getText().toString();


            int material_id = 0;
            if (material != null) {
                isAddOrEdit = true;
                material_id = material.material_id;
            }

        /*    showLoader();
            RetrofitClient.getApiService().storeMaterial(name, cno, email, adr, bname, des, unit, quantity, project.id, isAddOrEdit, material_id).enqueue(new Callback<ProjectMaterialModel>() {
                @Override
                public void onResponse(Call<ProjectMaterialModel> call, Response<ProjectMaterialModel> response) {
                    if (response.code() == 200) {
                        Material material = response.body().material;
                        Toast.makeText(AddSupplierActivity.this, "Material added sucessfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(AppKeys.PROJECT, project);
                        ScreenHelper.redirectToClass(AddSupplierActivity.this, AddMaterialActivity.class, bundle);
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