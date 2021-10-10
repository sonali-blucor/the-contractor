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

    private TextInputEditText edt_material_supplier_name;
    private TextInputEditText edt_supplier_contact_no;
    private TextInputEditText edt_supplier_email;
    private TextInputEditText edt_supplier_address;
    private TextInputEditText edt_material_bname;
    private TextInputEditText edt_material_des;
    private int unit_id;
    List<UnitModal> units;
    private TextInputEditText et_material_unit;
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

        edt_material_supplier_name = findViewById(R.id.edt_material_supplier_name);
        edt_supplier_contact_no = findViewById(R.id.edt_supplier_contact_no);
        edt_supplier_email = findViewById(R.id.edt_supplier_email);
        edt_supplier_address = findViewById(R.id.edt_supplier_address);
        edt_material_bname = findViewById(R.id.edt_material_bname);
        edt_material_des = findViewById(R.id.edt_material_des);
        et_material_unit = findViewById(R.id.et_material_unit);
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
                String name = edt_material_supplier_name.getText().toString();
                String cno = edt_supplier_contact_no.getText().toString();
                String email = edt_supplier_email.getText().toString();
                String adr = edt_supplier_address.getText().toString();
                String bname = edt_material_bname.getText().toString();
                String des = edt_material_des.getText().toString();

                String unit = et_material_unit.getText().toString();
                String qty = et_material_qty.getText().toString();

                String rate = et_material_rate.getText().toString();
                String gst = et_material_gst.getText().toString();
                String gstAmount = et_material_gst_amount.getText().toString();

                //Make all Function for validation and email parameter

                boolean check = validateinfo(name, cno, email, adr, bname, des, unit, qty, rate, gst);

                if (check == true) {
                    Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Check valid information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getUnits();
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

    private Boolean validateinfo(String name, String cno, String email, String adr, String bname, String des, String unit, String qty, String rate, String gst) {
        if (name.length() == 0) {
            edt_material_supplier_name.requestFocus();
            edt_material_supplier_name.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!name.matches("[a-zA-z]+")) {
            edt_material_supplier_name.requestFocus();
            edt_material_supplier_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (cno.length() == 0) {
            edt_supplier_contact_no.requestFocus();
            edt_supplier_contact_no.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!cno.matches("^[+][0-9]{10,13}$")) {
            edt_supplier_contact_no.requestFocus();
            edt_supplier_contact_no.setError("Correct format: +91xxxxxxxxxx");
            return false;
        } else if (email.length() == 0) {
            edt_supplier_email.requestFocus();
            edt_supplier_email.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            edt_supplier_email.requestFocus();
            edt_supplier_email.setError("ENTER VALID EMAIL ADDRESS");
            return false;
        } else if (adr.length() == 0) {
            edt_supplier_address.requestFocus();
            edt_supplier_address.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!adr.matches("[a-zA-Z0-1]+")) {
            edt_supplier_address.requestFocus();
            edt_supplier_address.setError("ENTER YOUR ADDRESS");
            return false;
        } else if (bname.length() == 0) {
            edt_material_bname.requestFocus();
            edt_material_bname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!bname.matches("[a-zA-Z._-]+")) {
            edt_material_bname.requestFocus();
            edt_material_bname.setError("ENTER VALID BRAND NAME");
            return false;
        } else if (des.length() == 0) {
            edt_material_des.requestFocus();
            edt_material_des.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!des.matches("[a-zA-Z0-9._-]+")) {
            edt_material_des.requestFocus();
            edt_material_des.setError("PLEASE ENTER YOUR DETAILS BELOW");
        } else if (unit.length() == 0) {
            et_material_unit.requestFocus();
            et_material_unit.setError("FIELD CANNOT BE EMPTY");
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
        return null;
    }


    private void setUpData() {
        if (material != null) {
            //edt_material_name.setText(material.material_name);
            edt_material_bname.setText(material.material_brand);
            //edt_material_date.setText(material.material_date);
            //edt_material_type.setText(material.material_type);
            edt_material_supplier_name.setText(material.supplier_name);
            edt_supplier_contact_no.setText(material.supplier_contact);
            et_material_unit.setText(material.unit);
            et_material_qty.setText(material.quantity);
            //edt_material_rate.setText(material.rate);
            //edt_material_amount.setText(material.amount);
        }
    }


    private void getUnits() {
        RetrofitClient.getApiService().getUnits().enqueue(new Callback<List<UnitModal>>() {
            @Override
            public void onResponse(Call<List<UnitModal>> call, Response<List<UnitModal>> response) {
                if (response.code() == 200) {
                    List<UnitModal> server_units = response.body();
                    if (server_units != null && server_units.size() > 0) {
                        AddMaterialDetailsActivity.this.units = server_units;
                    } else {
                        AddMaterialDetailsActivity.this.units = new ArrayList<>();
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                AddMaterialDetailsActivity.this.units = new ArrayList<>();
                stopLoader();
            }
        });
    }

    public void showPopupViewForUnits(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddMaterialDetailsActivity.this);
            builder.setTitle("Choose Units: ");
            AlertDialog alert = builder.create();
            UnitAdapter adapter = new UnitAdapter(AddMaterialDetailsActivity.this, units);
            ListView lst_project_type = new ListView(AddMaterialDetailsActivity.this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    et_material_unit.setText(units.get(position).unit);
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
            Log.e("AddProjectActivity", "" + e.getMessage());
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
        } else if (edt_material_supplier_name.getText().toString().isEmpty() || edt_material_supplier_name.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_supplier_name.setError(error);
            edt_material_supplier_name.requestFocus();
            return false;
        } else if (edt_material_bname.getText().toString().isEmpty() || edt_material_bname.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_bname.setError(error);
            edt_material_bname.requestFocus();
            return false;
        } else if (edt_material_des.getText().toString().isEmpty() || edt_material_des.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_des.setError(error);
            edt_material_des.requestFocus();
            return false;

        } else if (et_material_unit.getText().toString().isEmpty() || et_material_unit.getText().toString().trim().equalsIgnoreCase("")) {
            et_material_unit.setError(error);
            et_material_unit.requestFocus();
            return false;
        } else if (et_material_qty.getText().toString().isEmpty() || et_material_qty.getText().toString().trim().equalsIgnoreCase("")) {
            et_material_qty.setError(error);
            et_material_qty.requestFocus();
            return false;
        } else {
            return false;
        }
    }

    public void onClickToSubmitMaterial(View view) {
        if (is_valid_data()) {
            String name = edt_material_supplier_name.getText().toString();
            String cno = edt_supplier_contact_no.getText().toString();
            String email = edt_supplier_email.getText().toString();
            String adr = edt_supplier_address.getText().toString();
            String bname = edt_material_bname.getText().toString();
            String des = edt_material_des.getText().toString();
            String unit = et_material_unit.getText().toString();
            String quantity = et_material_qty.getText().toString();

            int material_id = 0;
            if (material != null) {
                isAddOrEdit = true;
                material_id = material.material_id;
            }

            showLoader();
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
            });
        }
    }
}