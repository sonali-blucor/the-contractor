package com.blucor.tcthecontractor.project.material;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.MaterialPurchase;
import com.blucor.tcthecontractor.models.MaterialsModal;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.project.ProjectMenuActivity;
import com.blucor.tcthecontractor.rv_adapters.MaterialRecyclerAdapter;
import com.blucor.tcthecontractor.rv_adapters.MaterialsAdapter;
import com.blucor.tcthecontractor.rv_adapters.RecyclerViewClickListener;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterialActivity extends BaseAppCompatActivity {
    private TextView tv_material_quantity;
    private TextInputEditText edt_select_material;
    private RecyclerView mRvView;
    private MaterialRecyclerAdapter mAdapter;
    private List<MaterialPurchase> mList = new ArrayList<>();
    private ProjectsModel project;
    private List<MaterialPurchase> adapterList = new ArrayList<>();

    private boolean fabExpanded = false;
    private FloatingActionButton fabAdd;
    private LinearLayout layoutFabAddSupplier;
    private LinearLayout layoutFabAddMaterial;
    private LinearLayout layoutFabMaterial;

    List<MaterialsModal> materialsModals;
    private int materials_id = 0;
    private String unit = "";

    private Dialog dialog;

    private TextView tv_payment_total_amt;
    private TextView tv_payment_balance_amt;
    private EditText edt_payment_paid_to;
    private EditText edt_payment_amount;
    private EditText edt_payment_type;
    private Button btn_payment_dialog_close;
    private Button btn_payment_dialog_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        tv_material_quantity = findViewById(R.id.tv_material_quantity);
        edt_select_material = findViewById(R.id.edt_select_material);

        mRvView = findViewById(R.id.recycler_view_list);
        mRvView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(layoutManager);

        getMaterials();

        Intent intent = getIntent();
        if (intent.hasExtra(AppKeys.PROJECT)) {
            project = intent.getParcelableExtra(AppKeys.PROJECT);

        }
        setUpAdapter();

        fabAdd = (FloatingActionButton) this.findViewById(R.id.fabAdd);

        layoutFabAddSupplier = (LinearLayout) this.findViewById(R.id.layoutFabAddSupplier);
        layoutFabAddMaterial = (LinearLayout) this.findViewById(R.id.layoutFabAddMaterial);
        layoutFabMaterial = (LinearLayout) this.findViewById(R.id.layoutFAbMaterial);
        //layoutFabSettings = (LinearLayout) this.findViewById(R.id.layoutFabSettings);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true) {
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();

    }

    //closes FAB submenus
    private void closeSubMenusFab() {
        layoutFabAddMaterial.setVisibility(View.INVISIBLE);
        layoutFabAddSupplier.setVisibility(View.INVISIBLE);
        layoutFabMaterial.setVisibility(View.INVISIBLE);
        fabAdd.setImageResource(R.drawable.ic_add);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        layoutFabAddMaterial.setVisibility(View.VISIBLE);
        layoutFabAddSupplier.setVisibility(View.VISIBLE);
        layoutFabMaterial.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabAdd.setImageResource(R.drawable.ic_close_black);
        fabExpanded = true;
    }

    private void getMaterialList() {

        showLoader();

        RetrofitClient.getApiService().getMaterialsByProjectId(project.id/*, materials_id*/).enqueue(new Callback<List<MaterialPurchase>>() {
            @Override
            public void onResponse(Call<List<MaterialPurchase>> call, Response<List<MaterialPurchase>> response) {
                if (response.code() == 200) {
                    mList = response.body();
                    setUpAdapter();
                } else {
                    Toast.makeText(AddMaterialActivity.this, "No MaterialPurchase Found", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<List<MaterialPurchase>> call, Throwable t) {
                Toast.makeText(AddMaterialActivity.this, "MaterialPurchase Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void setUpAdapter() {
        MaterialPurchase materialPurchase = new MaterialPurchase();
        adapterList = new ArrayList<>();
//        adapterList.add(materialPurchase);
        adapterList.addAll(mList);

//        adapterList.add(new MaterialPurchase());
//        adapterList.add(new MaterialPurchase());
//        adapterList.add(new MaterialPurchase());


        mAdapter = new MaterialRecyclerAdapter(AddMaterialActivity.this, adapterList);
        mRvView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {

            }

            @Override
            public void addViewListClicked(View v, int position) {
                MaterialPurchase current_materialPurchase = adapterList.get(position);
                showPopupViewForPaymentAdd(v, current_materialPurchase);
            }

            @Override
            public void editViewListClicked(View v, int position) {
                MaterialPurchase current_materialPurchase = adapterList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppKeys.MATERIAL, current_materialPurchase);
                bundle.putParcelable(AppKeys.PROJECT, project);
                ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class, bundle);
            }
        });
        //mAdapter.addItems(mList);
        if (unit != "") {
            double qty = 0;
            for (MaterialPurchase materialp : mList) {
                qty = qty + Double.parseDouble(materialp.quantity);
            }
            tv_material_quantity.setVisibility(View.VISIBLE);
            tv_material_quantity.setText(qty + unit);
        } else {
            tv_material_quantity.setVisibility(View.GONE);
        }
    }

    public void onSlideViewButtonClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class, bundle);
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(this, ProjectMenuActivity.class, bundle);
    }

    public void onClickAddMaterial(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialsActivity.class, bundle);
    }

    public void onClickAddSupplier(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddSupplierActivity.class, bundle);
    }

    public void onClickMaterial(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppKeys.MATERIAL_DETAIL_TYPE, false);
        bundle.putParcelable(AppKeys.PROJECT, project);
        ScreenHelper.redirectToClass(AddMaterialActivity.this, AddMaterialDetailsActivity.class, bundle);
    }


    private void getMaterials() {
        showLoader();
        RetrofitClient.getApiService().getMaterials().enqueue(new Callback<List<MaterialsModal>>() {
            @Override
            public void onResponse(Call<List<MaterialsModal>> call, Response<List<MaterialsModal>> response) {
                if (response.code() == 200) {
                    List<MaterialsModal> server_materials = response.body();
                    if (server_materials != null && server_materials.size() > 0) {
                        AddMaterialActivity.this.materialsModals = server_materials;
                    } else {
                        AddMaterialActivity.this.materialsModals = new ArrayList<>();
                    }
                }
                stopLoader();
                getMaterialList();
            }

            @Override
            public void onFailure(Call<List<MaterialsModal>> call, Throwable t) {
                Log.e("get matrials", "" + t.getMessage());
                AddMaterialActivity.this.materialsModals = new ArrayList<>();
                stopLoader();
                getMaterialList();
            }
        });
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
                    materials_id = materialsModals.get(position).material_id;
                    unit = materialsModals.get(position).unit;
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

    public void showPopupViewForPaymentAdd(View v, MaterialPurchase materialPurchase) {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_material_payament, null);

            tv_payment_total_amt = view.findViewById(R.id.tv_payment_total_amt);
            tv_payment_balance_amt = view.findViewById(R.id.tv_payment_balance_amt);
            edt_payment_paid_to = view.findViewById(R.id.edt_payment_paid_to);
            edt_payment_amount = view.findViewById(R.id.edt_payment_amount);
            edt_payment_type = view.findViewById(R.id.edt_payment_type);
            btn_payment_dialog_close = view.findViewById(R.id.btn_payment_dialog_close);
            btn_payment_dialog_save = view.findViewById(R.id.btn_payment_dialog_save);

            tv_payment_total_amt.setText(materialPurchase.total_amt);
            tv_payment_balance_amt.setText(materialPurchase.balance_amt);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            edt_payment_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btn_payment_dialog_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            btn_payment_dialog_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } catch (Exception e) {
            Log.e("AddProjectActivity", "" + e.getMessage());
        }
    }


    private void validateAddress() {
   /*     address1 = edt_payment_paid_to.getText().toString().trim();
        address2 = edt_payment_amount.getText().toString().trim();
        city = edt_payment_type.getText().toString().trim();

        if (address1.equals("")) {
            isFieldEmpty = true;
            etAddress1.setBackgroundResource(R.drawable.edittext_error);
            etAddress1.setError("Empty Field");
            etAddress1.requestFocus();
        } else {
            isFieldEmpty = false;
            etAddress1.setBackgroundResource(R.drawable.edittext_round);
        }
        if (city.equals("")) {
            isFieldEmpty = true;
            etCity.setBackgroundResource(R.drawable.edittext_error);
            etCity.setError("Empty Field");
            etCity.requestFocus();
        } else {
            isFieldEmpty = false;
            etCity.setBackgroundResource(R.drawable.edittext_round);
        }
        if (zip.equals("")) {
            isFieldEmpty = true;
            etZip.setBackgroundResource(R.drawable.edittext_error);
            etZip.setError("Empty Field");
            etZip.requestFocus();
        } else if(zip.length() != 6) {
            isFieldEmpty = true;
            etZip.setBackgroundResource(R.drawable.edittext_error);
            etZip.setError("Zip Code cannot be less than 6");
            etZip.requestFocus();
        } else {
            isFieldEmpty = false;
            etZip.setBackgroundResource(R.drawable.edittext_round);
        }
        if (state.equals("")) {
            isFieldEmpty = true;
            etState.setError("Empty Field");
            etState.requestFocus();
            etState.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etState.setBackgroundResource(R.drawable.edittext_round);
        }
        if (country.equals("")) {
            isFieldEmpty = true;
            etCountry.setError("Empty Field");
            etCountry.requestFocus();
            etCountry.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etCountry.setBackgroundResource(R.drawable.edittext_round);
        }*/
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}