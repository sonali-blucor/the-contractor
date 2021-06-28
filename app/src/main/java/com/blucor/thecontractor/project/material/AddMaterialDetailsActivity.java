package com.blucor.thecontractor.project.material;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.Material;
import com.blucor.thecontractor.models.ProjectMaterialModel;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AddMaterialDetailsActivity extends BaseAppCompatActivity {

    private boolean isAddOrEdit = false;

    private TextInputEditText edt_material_name;
    private TextInputEditText edt_material_brand;
    private TextInputEditText edt_material_date;
    private ImageView img_date;
    private TextInputEditText edt_material_type;
    private TextInputEditText edt_material_supplier_name;
    private TextInputEditText edt_material_supplier_contact_number;
    private TextInputEditText edt_material_unit;
    private TextInputEditText edt_material_quantity;
    private TextInputEditText edt_material_rate;
    private TextInputEditText edt_material_amount;
    private Button btn_submit;
    private ProjectsModel project;
    private Material material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_details);

        edt_material_name = findViewById(R.id.edt_material_name);
        edt_material_brand = findViewById(R.id.edt_material_brand);
        edt_material_date = findViewById(R.id.edt_material_date);
        img_date = findViewById(R.id.img_date);
        edt_material_type = findViewById(R.id.edt_material_type);
        edt_material_supplier_name = findViewById(R.id.edt_material_supplier_name);
        edt_material_supplier_contact_number = findViewById(R.id.edt_material_supplier_contact_number);
        edt_material_unit = findViewById(R.id.edt_material_unit);
        edt_material_quantity = findViewById(R.id.edt_material_quantity);
        edt_material_rate = findViewById(R.id.edt_material_rate);
        edt_material_amount = findViewById(R.id.edt_material_amount);
        btn_submit = findViewById(R.id.btn_submit);

        try {
            Bundle bundle = getIntent().getExtras();
            /*if (getIntent().hasExtra(AppKeys.ACTIVITY_DETAIL_TYPE)) {
                isAddOrEdit = bundle.getBoolean(AppKeys.ACTIVITY_DETAIL_TYPE);
            }*/
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
    }

    private void setUpData() {
        if (material != null) {
           edt_material_name.setText(material.material_name);
           edt_material_brand.setText(material.material_brand);
           edt_material_date.setText(material.material_date);
           edt_material_type.setText(material.material_type);
           edt_material_supplier_name.setText(material.supplier_name);
           edt_material_supplier_contact_number.setText(material.supplier_contact);
           edt_material_unit.setText(material.unit);
           edt_material_quantity.setText(material.quantity);
           edt_material_rate.setText(material.rate);
           edt_material_amount.setText(material.amount);
        }
    }

    public void onClickDate(View view){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMaterialDetailsActivity.this,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year,monthOfYear,dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat(AppKeys.DATE_FORMAT);
                        String date = sdf.format(cal.getTimeInMillis());
                        edt_material_date.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private boolean is_valid_data() {
        String error = "Empty Field";
        if(edt_material_name.getText().toString().isEmpty() || edt_material_name.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_name.setError(error);
            edt_material_name.requestFocus();
            return false;
        } else if(edt_material_brand.getText().toString().isEmpty() || edt_material_brand.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_brand.setError(error);
            edt_material_brand.requestFocus();
            return false;
        } else if(edt_material_date.getText().toString().isEmpty() || edt_material_date.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_date.setError(error);
            edt_material_date.requestFocus();
            return false;
        } else if(edt_material_type.getText().toString().isEmpty() || edt_material_type.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_type.setError(error);
            edt_material_type.requestFocus();
            return false;
        } else if(edt_material_supplier_name.getText().toString().isEmpty() || edt_material_supplier_name.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_supplier_name.setError(error);
            edt_material_supplier_name.requestFocus();
            return false;
        } else if(edt_material_supplier_contact_number.getText().toString().isEmpty() || edt_material_supplier_contact_number.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_supplier_contact_number.setError(error);
            edt_material_supplier_contact_number.requestFocus();
            return false;
        } else if(edt_material_unit.getText().toString().isEmpty() || edt_material_unit.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_unit.setError(error);
            edt_material_unit.requestFocus();
            return false;
        } else if(edt_material_quantity.getText().toString().isEmpty() || edt_material_quantity.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_quantity.setError(error);
            edt_material_quantity.requestFocus();
            return false;
        } else if(edt_material_rate.getText().toString().isEmpty() || edt_material_rate.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_rate.setError(error);
            edt_material_rate.requestFocus();
            return false;
        } else if(edt_material_amount.getText().toString().isEmpty() || edt_material_amount.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_amount.setError(error);
            edt_material_amount.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public void onClickToSubmitMaterial(View view) {
        if (is_valid_data()) {
            String name = edt_material_name.getText().toString();
            String brand = edt_material_brand.getText().toString();
            String date = edt_material_date.getText().toString();
            String type = edt_material_type.getText().toString();
            String supplier_name = edt_material_supplier_name.getText().toString();
            String supplier_contact_number = edt_material_supplier_contact_number.getText().toString();
            String unit = edt_material_unit.getText().toString();
            String quantity = edt_material_quantity.getText().toString();
            String rate = edt_material_rate.getText().toString();
            String amount = edt_material_amount.getText().toString();
            int material_id = 0;
            if (material != null) {
                isAddOrEdit = true;
                material_id = material.material_id;
            }

            showLoader();
            RetrofitClient.getApiService().storeMaterial(name,brand,date,type,supplier_name,supplier_contact_number,unit,quantity,rate,amount,project.id,isAddOrEdit,material_id).enqueue(new Callback<ProjectMaterialModel>() {
                @Override
                public void onResponse(Call<ProjectMaterialModel> call, Response<ProjectMaterialModel> response) {
                    if (response.code() == 200) {
                        Material material = response.body().material;
                        Toast.makeText(AddMaterialDetailsActivity.this, "Material added sucessfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle =new Bundle();
                        bundle.putParcelable(AppKeys.PROJECT,project);
                        ScreenHelper.redirectToClass(AddMaterialDetailsActivity.this,AddMaterialActivity.class,bundle);
                        finish();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<ProjectMaterialModel> call, Throwable t) {
                    stopLoader();
                    Log.e("TAG","Error : "+t.getMessage());
                }
            });
        }
    }
}