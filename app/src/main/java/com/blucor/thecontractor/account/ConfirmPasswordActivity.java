package com.blucor.thecontractor.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.Client;
import com.blucor.thecontractor.models.Contractor;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPasswordActivity extends BaseAppCompatActivity {
    private TextInputEditText edt_confirm_password;
    private TextInputEditText edt_confirm_cpassword;
    private TextInputEditText edt_otp;
    private MaterialButton btn_confirm_password;
    private MaterialButton btn_confirm_otp;
    private String otp;
    private String server_id;
    private int is_client = -1;
    private SharedPreferences sharedPreferences;
    private LinearLayout ll_otp;
    private LinearLayout ll_confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        edt_confirm_cpassword = findViewById(R.id.edt_confirm_cpassword);
        edt_otp = findViewById(R.id.edt_otp_confirm_password);
        btn_confirm_password = findViewById(R.id.btn_confirm_password);
        btn_confirm_otp = findViewById(R.id.btn_confirm_otp);
        ll_otp = findViewById(R.id.ll_otp_confirm_password);
        ll_confirm_pass = findViewById(R.id.ll_confirm_password);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);

        Intent intent = getIntent();
        if(intent.hasExtra("otp")) {
            otp = intent.getStringExtra("otp");
        }

        if(intent.hasExtra("server_id")) {
            server_id = intent.getStringExtra("server_id");
        }

        btn_confirm_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_confirm_password.setError(null);
                edt_confirm_cpassword.setError(null);
                if(is_valid()) {
                    requestChangePassword();
                }
            }
        });
        btn_confirm_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_otp = edt_otp.getText().toString();
                if (otp.equalsIgnoreCase(entered_otp)) {
                    setOtpGone();
                } else {
                    Toast.makeText(ConfirmPasswordActivity.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setOtpVisible();
    }

    private void requestChangePassword() {
        if (is_client == 1) {
            requestChangePasswordClient();
        } else if (is_client == 0) {
            requestChangePasswordContractor();
        }
    }

    private void requestChangePasswordContractor() {
        showLoader();
        String password = edt_confirm_password.getText().toString();

        RetrofitClient.getApiService().updateContractorPassword(password,server_id).enqueue(new Callback<Contractor>() {
            @Override
            public void onResponse(Call<Contractor> call, Response<Contractor> response) {
                if(response.body() != null) {
                    Contractor contractor = response.body();
                    if (contractor != null) {
                        Toast.makeText(ConfirmPasswordActivity.this, "Sucessfully Updated Password", Toast.LENGTH_SHORT).show();
                        ScreenHelper.redirectToClass(ConfirmPasswordActivity.this,LoginActivity.class);
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<Contractor> call, Throwable t) {
                Log.e("ConfirmPassword","Contractor Error : "+t.getMessage());
                stopLoader();
            }
        });
    }

    private void requestChangePasswordClient() {
        showLoader();
        String password = edt_confirm_password.getText().toString();

        RetrofitClient.getApiService().updateClientPassword(password,server_id).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.body() != null) {
                    Client client = response.body();
                    if (client != null) {
                        Toast.makeText(ConfirmPasswordActivity.this, "Sucessfully Updated Password", Toast.LENGTH_LONG).show();
                        ScreenHelper.redirectToClass(ConfirmPasswordActivity.this,LoginActivity.class);
                    }
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.e("ConfirmPassword","Client Error : "+t.getMessage());
                stopLoader();
            }
        });
    }

    private boolean is_valid() {
        String password = edt_confirm_password.getText().toString();
        String cpassword = edt_confirm_cpassword.getText().toString();
        if (password.isEmpty() || password.equalsIgnoreCase("")) {
            edt_confirm_password.setError("Empty Field!");
            edt_confirm_password.requestFocus();
            return false;
        } else if (cpassword.isEmpty() || cpassword.equalsIgnoreCase("")) {
            edt_confirm_cpassword.setError("Empty Field!");
            edt_confirm_cpassword.requestFocus();
            return false;
        } else if (!cpassword.equals(password)) {
            edt_confirm_password.setError("Password doesn't match!");
            edt_confirm_password.requestFocus();
            return false;
        } else if (password.length() != cpassword.length()) {
            edt_confirm_password.setError("Password doesn't match!");
            edt_confirm_password.requestFocus();
            return false;
        } else {
            edt_confirm_password.setError(null);
            edt_confirm_cpassword.setError(null);
            return true;
        }
    }

    private void setOtpVisible() {
        ll_otp.setVisibility(View.VISIBLE);
        ll_confirm_pass.setVisibility(View.GONE);
    }

    private void setOtpGone() {
        ll_otp.setVisibility(View.GONE);
        ll_confirm_pass.setVisibility(View.VISIBLE);
    }
}