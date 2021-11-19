package com.blucor.tcthecontractor.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.ServerResponseModel;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.network.utils.NetworkHelper;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UnitView extends LinearLayout {
    // internal components


    private Context context;

    private TextInputLayout til_unit;
    private TextInputEditText edt_unit;
    private View inc_unit;
    private View inc_add_unit;

    private ImageView img_add_unit;
    private ImageView img_search_unit;
    private ImageView img_close;
    private LinearLayout llv_unit_item;
    private TextView tv_unit_item;
    private ListView lst_unit;

    private TextInputLayout til_add_unit;
    private TextInputEditText edt_add_unit;
    private Button btn_submit;

    private BottomSheetDialog bottomSheetDialog;

    private String selectedUnit = "";
    private int selectedUnitId = 0;
    private boolean enable = true;
    private String unitError = "";

    List<UnitModal> units;

    private AlertDialog dialog;

    public UnitView(Context context) {
        super(context);
        this.context = context;
        initControl(context);
    }

    public UnitView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);

    }

    /**
     * Load component XML layout
     */
    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.control_unit, this);


        til_unit = findViewById(R.id.til_unit);
        edt_unit = findViewById(R.id.edt_unit);
        inc_unit = findViewById(R.id.inc_unit);
        inc_add_unit = findViewById(R.id.inc_add_unit);

        if (isEnable()) {
            til_unit.setEndIconOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomSheetUnit();
                }
            });
        }

        getUnits();
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_add_close:
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.img_close:
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.img_add_unit:
                    bottomSheetDialog.dismiss();
                    showBottomSheetAddUnit();
                    break;
                case R.id.img_search_unit:
                    break;
                case R.id.btn_submit:
                    addUnits();
                    break;
            }
        }
    };

    private void showBottomSheetAddUnit() {
        inc_unit.setVisibility(VISIBLE);
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_add_unit);
        img_close = bottomSheetDialog.findViewById(R.id.img_add_close);
        til_add_unit = bottomSheetDialog.findViewById(R.id.til_add_unit);
        edt_add_unit = bottomSheetDialog.findViewById(R.id.edt_add_unit);
        btn_submit = bottomSheetDialog.findViewById(R.id.btn_submit);

        img_close.setOnClickListener(onClickListener);
        btn_submit.setOnClickListener(onClickListener);

        bottomSheetDialog.show();
    }

    private void showBottomSheetUnit() {
        inc_add_unit.setVisibility(VISIBLE);
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_unit);
        img_add_unit = bottomSheetDialog.findViewById(R.id.img_add_unit);
        img_search_unit = bottomSheetDialog.findViewById(R.id.img_search_unit);
        img_close = bottomSheetDialog.findViewById(R.id.img_close);
        llv_unit_item = bottomSheetDialog.findViewById(R.id.llv_unit_item);
        tv_unit_item = bottomSheetDialog.findViewById(R.id.tv_unit_item);
        lst_unit = bottomSheetDialog.findViewById(R.id.lst_unit);

        if (getSelectedUnit().equals("")) {
            llv_unit_item.setVisibility(GONE);
        } else {
            llv_unit_item.setVisibility(VISIBLE);
            tv_unit_item.setText(getSelectedUnit());
        }

        img_add_unit.setOnClickListener(onClickListener);
        img_search_unit.setOnClickListener(onClickListener);
        img_close.setOnClickListener(onClickListener);

        UnitAdapter adapter = new UnitAdapter(context, units);
        lst_unit.setAdapter(adapter);
        lst_unit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUnit = units.get(position).unit;
                edt_unit.setText(selectedUnit);
                tv_unit_item.setText(selectedUnit);
                setSelectedUnitId(units.get(position).id);
//                bottomSheetDialog.dismiss();

                if (getSelectedUnit().equals("")) {
                    llv_unit_item.setVisibility(GONE);
                } else {
                    llv_unit_item.setVisibility(VISIBLE);
                    tv_unit_item.setText(getSelectedUnit());
                }
            }
        });

        bottomSheetDialog.show();
    }


    public String getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(String selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnableL(boolean enable) {
        this.enable = enable;
        til_unit.setEnabled(enable);
    }

    public int getSelectedUnitId() {
        return selectedUnitId;
    }

    public void setSelectedUnitId(int selectedUnitId) {
        this.selectedUnitId = selectedUnitId;
    }

    public String getUnitError() {

        return unitError;
    }

    public void setUnitError(String unitError) {
        this.unitError = unitError;
        edt_unit.setError( this.unitError );
    }

    private void getUnits() {
        showLoader();
        RetrofitClient.getApiService().getUnits().enqueue(new Callback<List<UnitModal>>() {
            @Override
            public void onResponse(Call<List<UnitModal>> call, Response<List<UnitModal>> response) {
                if (response.code() == 200) {
                    List<UnitModal> server_units = response.body();
                    if (server_units != null && server_units.size() > 0) {
                        units = server_units;
                    } else {
                        units = new ArrayList<>();
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                units = new ArrayList<>();
                stopLoader();
            }
        });
    }

    private void addUnits() {
        showLoader();
        String unit = edt_add_unit.getText().toString();
        RetrofitClient.getApiService().storeUnit(unit).enqueue(new Callback<ServerResponseModel>() {
            @Override
            public void onResponse(Call<ServerResponseModel> call, Response<ServerResponseModel> response) {
                if (response != null && response.code() == 200) {
                    if (response.body() != null) {
                        UnitModal unitModal = response.body().unitModal;
                        units.add(unitModal);
                        bottomSheetDialog.dismiss();
                        Toast.makeText(context, "Unit store successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Unable to store supplierModal", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(context, "Internal Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Unit is already exists", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<ServerResponseModel> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                stopLoader();
            }
        });
    }


    public void stopLoader() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void showLoader() {
        if (NetworkHelper.hasNetworkAccess(context)) {
            dialog = new AlertDialog.Builder(context).create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ProgressBar pb = new ProgressBar(context);
            dialog.setView(pb);
            dialog.show();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
