package com.blucor.tcthecontractor.project.material;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.custom.UnitView;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.MaterialsModal;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterialsActivity extends BaseAppCompatActivity {

    private TextInputEditText edt_material_bname;
    private TextInputEditText edt_material_des;
    private TextInputEditText edt_material_type;
    private TextInputEditText edt_material_unit;
    private Button btn_submit;


    private MaterialsModal materialPurchase;
    private ProjectsModel project;
    private boolean isAddOrEdit = false;
    List<UnitModal> units;
    private int unit_id;

    private UnitView edt_unit_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materials);

        edt_material_bname = findViewById(R.id.edt_material_bname);
        edt_material_des = findViewById(R.id.edt_material_des);
        edt_material_type = findViewById(R.id.etd_material_type);
        edt_material_unit = findViewById(R.id.edt_material_unit);

        edt_unit_v = findViewById(R.id.edt_unit_v);

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
                materialPurchase = bundle.getParcelable(AppKeys.MATERIAL);
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
                if (validateinfo(name, des, mtype, unt)) {
                    onClickToSubmitMaterials(v);
                }
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
        if (materialPurchase != null) {
            edt_material_bname.setText(materialPurchase.material_brand);
            edt_material_type.setText(materialPurchase.material_type);
//            edt_material_des.setText(materialPurchase.material_type);
//            edt_material_unit.setText(materialPurchase.unit);
            edt_unit_v.setSelectedUnit(materialPurchase.unit);
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
                    edt_material_unit.setText( units.get(position).unit);
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
            return true;
        }
    }

    public void onClickToSubmitMaterials(View view) {

        if (is_valid_data()) {

            String bname = edt_material_bname.getText().toString();
            String mType = edt_material_type.getText().toString();
            String des = edt_material_des.getText().toString();
            unit_id = edt_unit_v.getSelectedUnitId();
            int unit = unit_id;

            int material_id = 0;
            if (materialPurchase != null) {
                isAddOrEdit = true;
                material_id = materialPurchase.material_id;
            }

            showLoader();
            RetrofitClient.getApiService().storeMaterials(bname, mType, des, unit).enqueue(new Callback<MaterialsModal>() {
                @Override
                public void onResponse(Call<MaterialsModal> call, Response<MaterialsModal> response) {
                    Log.e("response", response.toString());
                    if (response != null && response.code() == 200) {
                        if (response.body() != null) {
                            MaterialsModal materialsModal = response.body();
                            Toast.makeText(AddMaterialsActivity.this, "Material store successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddMaterialsActivity.this, "Unable to store material", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(AddMaterialsActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddMaterialsActivity.this, "Material is already exists", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<MaterialsModal> call, Throwable t) {
                    stopLoader();
                    Log.e("TAG", "Error : " + t.getMessage());
                }
            });
        }
    }

}