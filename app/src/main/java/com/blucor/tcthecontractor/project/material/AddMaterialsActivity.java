package com.blucor.tcthecontractor.project.material;

import androidx.appcompat.app.AlertDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.Material;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddMaterialsActivity extends BaseAppCompatActivity {

    private TextInputEditText edt_material_bname;
    private TextInputEditText edt_material_des;
    private TextInputEditText edt_material_type;
    private TextInputEditText edt_material_unit;
    private Button btn_submit;


    private Material material;
    private ProjectsModel project;
    private boolean isAddOrEdit = false;
    List<UnitModal> units;
    private int unit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materials);

        edt_material_bname = findViewById(R.id.edt_material_bname);
        edt_material_des = findViewById(R.id.edt_material_des);
        edt_material_type = findViewById(R.id.etd_material_type);
        edt_material_unit = findViewById(R.id.edt_material_unit);
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
                String name = edt_material_bname.getText().toString();
                String des = edt_material_des.getText().toString();
                String mtype = edt_material_type.getText().toString();
                String unt = edt_material_unit.getText().toString();

                //make function for validation and pass all parameters
                validateinfo(name, des, mtype, unt);
            }
        });

        getUnits();
    }

    private Boolean validateinfo(String name, String des, String mtype, String unt) {
        if (name.length() == 0) {
            edt_material_bname.requestFocus();
            edt_material_bname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!name.matches("[a-zA-z]+")) {
            edt_material_bname.requestFocus();
            edt_material_bname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (des.length() == 0) {
            edt_material_des.requestFocus();
            edt_material_des.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!des.matches("^[+][0-9]{10,13}$")) {
            edt_material_des.requestFocus();
            edt_material_des.setError("Correct format: +91xxxxxxxxxx");
            return false;
        } else if (mtype.length() == 0) {
            edt_material_type.requestFocus();
            edt_material_type.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (unt.length() == 0) {
            edt_material_unit.requestFocus();
            edt_material_unit.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else {
            return true;
        }
    }


    private void setUpData() {
        if (material != null) {
            edt_material_bname.setText(material.material_brand);
            edt_material_type.setText(material.material_type);
//            edt_material_des.setText(material.material_type);        
            edt_material_unit.setText(material.unit);
        }
    }

    private void getUnits() {
        RetrofitClient.getApiService().getUnits().enqueue(new Callback<List<UnitModal>>() {
            @Override
            public void onResponse(Call<List<UnitModal>> call, Response<List<UnitModal>> response) {
                if (response.code() == 200) {
                    List<UnitModal> server_units = response.body();
                    if (server_units != null && server_units.size() > 0) {
                        AddMaterialsActivity.this.units = server_units;
                    } else {
                        AddMaterialsActivity.this.units = new ArrayList<>();
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                AddMaterialsActivity.this.units = new ArrayList<>();
                stopLoader();
            }
        });
    }

    public void showPopupViewForUnits(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddMaterialsActivity.this);
            builder.setTitle("Choose Units: ");
            AlertDialog alert = builder.create();
            UnitAdapter adapter = new UnitAdapter(AddMaterialsActivity.this, units);
            ListView lst_project_type = new ListView(AddMaterialsActivity.this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    edt_material_unit.setText(units.get(position).unit);
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
        if (edt_material_bname.getText().toString().isEmpty() || edt_material_bname.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_bname.setError(error);
            edt_material_bname.requestFocus();
            return false;
        } else if (edt_material_des.getText().toString().isEmpty() || edt_material_des.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_des.setError(error);
            edt_material_des.requestFocus();
            return false;

        } else if (edt_material_type.getText().toString().isEmpty() || edt_material_type.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_type.setError(error);
            edt_material_type.requestFocus();
            return false;

        } else if (edt_material_unit.getText().toString().isEmpty() || edt_material_unit.getText().toString().trim().equalsIgnoreCase("")) {
            edt_material_unit.setError(error);
            edt_material_unit.requestFocus();
            return false;
        } else {
            return false;
        }
    }

    public void onClickToSubmitMaterial(View view) {
        if (is_valid_data()) {
            String bname = edt_material_bname.getText().toString();
            String mType = edt_material_type.getText().toString();
            String des = edt_material_des.getText().toString();
            String unit = edt_material_unit.getText().toString();

            int material_id = 0;
            if (material != null) {
                isAddOrEdit = true;
                material_id = material.material_id;
            }

           /* showLoader();
            RetrofitClient.getApiService().storeMaterial(name, cno, email, adr, bname, des, unit, quantity, project.id, isAddOrEdit, material_id).enqueue(new Callback<ProjectMaterialModel>() {
                @Override
                public void onResponse(Call<ProjectMaterialModel> call, Response<ProjectMaterialModel> response) {
                    if (response.code() == 200) {
                        Material material = response.body().material;
                        Toast.makeText(AddMaterialsActivity.this, "Material added sucessfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(AppKeys.PROJECT, project);
                        ScreenHelper.redirectToClass(AddMaterialsActivity.this, AddMaterialActivity.class, bundle);
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