package com.blucor.thecontractor.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.Client;
import com.blucor.thecontractor.models.Contractor;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseAppCompatActivity {
     private ImageView img_logo;
     private TextView tv_txt_reg;
     private TextView tv_txt_regs;
     private LinearLayout llh_user_type;
     private TextInputEditText edt_first_name;
     private TextInputEditText edt_last_name;
     private TextInputEditText edt_company_name;
     private TextInputEditText edt_mobile;
     private TextInputEditText edt_email;
     private TextInputEditText edt_password;
     private TextInputEditText edt_cpassword;
     private TextView btn_register;
     private LinearLayout llh_bottom;
     private TextView tv_txt_sign_ups;
     private TextView tv_txt_sign_up;
     private int is_client = -1;
     private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         img_logo = findViewById(R.id.img_logo);
         tv_txt_reg = findViewById(R.id.tv_txt_reg);
         tv_txt_regs = findViewById(R.id.tv_txt_regs);
         llh_user_type = findViewById(R.id.llh_user_type);
         edt_first_name = findViewById(R.id.edt_first_name);
         edt_last_name = findViewById(R.id.edt_last_name);
         edt_company_name = findViewById(R.id.edt_company_name);
         edt_mobile = findViewById(R.id.edt_mobile);
         edt_email = findViewById(R.id.edt_email);
         edt_password = findViewById(R.id.edt_password);
         edt_cpassword = findViewById(R.id.edt_cpassword);
         btn_register = findViewById(R.id.btn_register);
         llh_bottom = findViewById(R.id.llh_bottom);
         tv_txt_sign_ups = findViewById(R.id.tv_txt_sign_ups);
         tv_txt_sign_up = findViewById(R.id.tv_txt_sign_up);
         sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
         is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);
         if(is_client == Contants.USER_TYPE_CLIENT) {
             edt_company_name.setVisibility(View.GONE);
         } else {
             edt_company_name.setVisibility(View.VISIBLE);
         }
    }

    private void registerContractor() {
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String company_name = edt_company_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String password = edt_password.getText().toString();

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().storeContractor(fname, lname,company_name, mobile, email, password).enqueue(new Callback<Contractor>() {
                @Override
                public void onResponse(Call<Contractor> call, Response<Contractor> response) {
                    Contractor contractor = response.body();
                    if (response.code() == 201) {
                        if (contractor != null) {
                            ScreenHelper.redirectToClass(RegisterActivity.this,LoginActivity.class);
                            finish();
                            Toast.makeText(RegisterActivity.this, "Successfully register", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Contractor is already present", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Contractor is already present", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<Contractor> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error in registration : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    private void registerClient() {
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String password = edt_password.getText().toString();

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().storeClient(fname, lname, mobile, email, password).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(@NonNull Call<Client> call, @NonNull retrofit2.Response<Client> response) {
                    Client client = response.body();
                    if (response.code() == 201) {
                        if (client != null) {
                            ScreenHelper.redirectToClass(RegisterActivity.this,LoginActivity.class);
                            finish();
                            Toast.makeText(RegisterActivity.this, "Successfully register", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Client is already present", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Client is already present", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error in registration : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    public void onClickToLogin(View view) {
        ScreenHelper.redirectToClass(RegisterActivity.this,LoginActivity.class);
        finish();
    }

    public void onClickToRegister(View view) {
        switch (is_client) {
            case Contants.USER_TYPE_CLIENT:
                registerClient();
                break;
            case Contants.USER_TYPE_CONTRACTOR:
                registerContractor();
                break;
            default:
                Toast.makeText(RegisterActivity.this, "Please select client or contractor", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean validateData() {
        boolean isValid = false;
        String fanme = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String password = edt_password.getText().toString();
        String cpassword = edt_cpassword.getText().toString();
        String error = "Empty Feild";

        if(fanme.isEmpty() || fanme.equalsIgnoreCase("")) {
            edt_first_name.setError(error);
            edt_first_name.requestFocus();
            isValid = false;
        } else if(lname.isEmpty() || lname.equalsIgnoreCase("")) {
            edt_last_name.setError(error);
            edt_last_name.requestFocus();
            isValid = false;
        } else if(email.isEmpty() || email.equalsIgnoreCase("")) {
            edt_email.setError(error);
            edt_email.requestFocus();
            isValid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("Invalid Email");
            edt_email.requestFocus();
            isValid = false;
        }else if(!Patterns.PHONE.matcher(mobile).matches()) {
            edt_mobile.setError("Invalid Mobile Number");
            edt_mobile.requestFocus();
            isValid = false;
        } else if(mobile.length() < 10) {
            edt_mobile.setError("Invalid Mobile Number");
            edt_mobile.requestFocus();
            isValid = false;
        } else if(mobile.isEmpty() || mobile.equalsIgnoreCase("")) {
            edt_mobile.setError(error);
            edt_mobile.requestFocus();
            isValid = false;
        } else if(password.isEmpty() || password.equalsIgnoreCase("")) {
            edt_password.setError(error);
            edt_password.requestFocus();
            isValid = false;
        } else if(cpassword.isEmpty() || cpassword.equalsIgnoreCase("")) {
            edt_cpassword.setError(error);
            edt_cpassword.requestFocus();
            isValid = false;
        } else if(!password.equals(cpassword)) {
            edt_password.setError("Password not match");
            edt_password.requestFocus();
            isValid = false;
        } else {
            edt_first_name.setError("");
            edt_last_name.setError("");
            edt_email.setError("");
            edt_mobile.setError("");
            edt_password.setError("");
            edt_company_name.setError("");
            edt_cpassword.setError("");
            isValid = true;
        }

        return isValid;
    }
}