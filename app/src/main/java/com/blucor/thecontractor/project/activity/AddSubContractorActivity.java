package com.blucor.thecontractor.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.client.ClientAddAndSearchActivity;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.models.Client;
import com.blucor.thecontractor.models.SubContractor;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

public class AddSubContractorActivity extends BaseAppCompatActivity {
    private TextInputEditText edt_first_name;
    private TextInputEditText edt_last_name;
    private TextInputEditText edt_mobile;
    private TextInputEditText edt_email;
    private TextInputEditText edt_password;
    private TextInputEditText edt_cpassword;
    private SubContractor subContractor;
    private Button btn_register;
    private EditText edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_contractor);

        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_cpassword = findViewById(R.id.edt_cpassword);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSubContractor();
            }
        });
    }

    private void registerSubContractor() {
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String password = edt_password.getText().toString();
        User user = DatabaseUtil.on().getAllUser().get(0);
        int id = user.server_id;

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().storeSubContractor(fname,lname,id,mobile,email,password).enqueue(new Callback<SubContractor>() {
                @Override
                public void onResponse(Call<SubContractor> call, Response<SubContractor> response) {
                    if (response.code() == 200 && response.body() != null) {
                        subContractor = response.body();
                        Intent intent = new Intent();
                        intent.putExtra(AppKeys.SUB_CONTRACTOR, subContractor);
                        setResult(AppKeys.SUB_CONTRACTOR_RESULT, intent);
                        finish();
                    } else {
                        Toast.makeText(AddSubContractorActivity.this, "Error in inserting sub contractor", Toast.LENGTH_SHORT).show();
                    }
                    stopLoader();
                }

                @Override
                public void onFailure(Call<SubContractor> call, Throwable t) {
                    Toast.makeText(AddSubContractorActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    private void sendIntent(){
        Intent intent = new Intent();
        intent.putExtra(AppKeys.SUB_CONTRACTOR, subContractor);
        setResult(AppKeys.SUB_CONTRACTOR_RESULT, intent);
        finish();
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
        } else if(!password.equals(cpassword) || password.length() != cpassword.length()) {
            edt_password.setError("Password not match");
            edt_password.requestFocus();
            isValid = false;
        }  else {
            edt_first_name.setError(null);
            edt_last_name.setError(null);
            edt_email.setError(null);
            edt_mobile.setError(null);
            edt_password.setError(null);
            edt_cpassword.setError(null);
            isValid = true;
        }

        return isValid;
    }
}