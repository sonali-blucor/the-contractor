package com.blucor.thecontractor.project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.UnitModal;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.rv_adapters.UnitAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderActivity extends BaseAppCompatActivity {

    EditText et_workdesc, et_unit, et_qty, et_rate, et_amount;
    Button btnsubmit;
    List<UnitModal> units;

    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        et_workdesc = findViewById(R.id.et_workdesc);
        et_unit = findViewById(R.id.et_unit);
        et_qty = findViewById(R.id.et_qty);
        et_rate = findViewById(R.id.et_rate);
        et_amount = findViewById(R.id.et_amount);
        btnsubmit = findViewById(R.id.btnsubmit);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    /*Intent intent = new Intent(WorkOrderActivity.this, AddProjectActActivity.class);
                    startActivity(intent);*/
                }
            }
        });

        getUnits();
    }

    private void getUnits() {
        RetrofitClient.getApiService().getUnits().enqueue(new Callback<List<UnitModal>>() {
            @Override
            public void onResponse(Call<List<UnitModal>> call, Response<List<UnitModal>> response) {
                if (response.code() == 200) {
                    List<UnitModal> server_units = response.body();
                    if(server_units != null && server_units.size() > 0) {
                        WorkOrderActivity.this.units = server_units;
                    } else {
                        WorkOrderActivity.this.units = new ArrayList<>();
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units",""+t.getMessage());
                WorkOrderActivity.this.units = new ArrayList<>();
                stopLoader();
            }
        });
    }

    public void showPopupViewForUnits(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(WorkOrderActivity.this);
            builder.setTitle("Choose Units: ");
            AlertDialog alert = builder.create();
            UnitAdapter adapter = new UnitAdapter(WorkOrderActivity.this, units);
            ListView lst_project_type = new ListView(WorkOrderActivity.this);
            lst_project_type.setAdapter(adapter);
            alert.setView(lst_project_type);
            lst_project_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    et_unit.setText(units.get(position).unit);
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
            Log.e("AddProjectActivity",""+e.getMessage());
        }
    }


    private boolean CheckAllFields() {

        if (et_workdesc.getText().toString().length() == 0) {
            et_workdesc.setError("All Fields are mendatory");
            et_workdesc.requestFocus();
            return false;
        }

        if (et_unit.getText().toString().length() == 0) {
            et_unit.setError("All Fields are mendatory");
            et_unit.requestFocus();
            return false;
        }

        if (et_qty.getText().toString().length() == 0) {
            et_qty.setError("All Fields are mendatory");
            et_qty.requestFocus();
            return false;
        }

        if (et_rate.getText().toString().length() == 0) {
            et_rate.setError("All Fields are mendatory");
            et_rate.requestFocus();
            return false;
        }

        if (et_amount.getText().toString().length() == 0) {
            et_amount.setError("All Fields are mendatory");
            et_amount.requestFocus();
            return false;
        }

        et_workdesc.setError(null);
        et_unit.setError(null);
        et_qty.setError(null);
        et_rate.setError(null);
        et_amount.setError(null);
        return true;
    }
}

