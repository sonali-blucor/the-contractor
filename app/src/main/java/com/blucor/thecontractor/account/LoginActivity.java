package com.blucor.thecontractor.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.MenuActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.client.ClientProjectsActivity;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.models.Client;
import com.blucor.thecontractor.models.Contractor;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseAppCompatActivity {

    private TextInputEditText mEdtUserName;
    private TextInputEditText mEdtPassword;
    private TextView tv_error;
    private SharedPreferences sharedPreferences;
    private int is_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtUserName = (TextInputEditText)findViewById(R.id.edt_user_name);
        mEdtPassword = (TextInputEditText)findViewById(R.id.edt_password);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);
        tv_error = findViewById(R.id.tv_txt_error);

        ImageView mImgLogo = (ImageView)findViewById(R.id.img_logo);

        int width = ScreenHelper.getWidthInPercentage(getApplicationContext(), 40);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mImgLogo.setLayoutParams(params);

    }

    public void onClickToRegister(View view) {
        ScreenHelper.redirectToClass(LoginActivity.this,RegisterActivity.class);
    }

    public void onClickToLogin(View view) {
        switch (is_client) {
            case Contants.USER_TYPE_CLIENT:
                checkClientLogin();
                break;
            case Contants.USER_TYPE_CONTRACTOR:
                checkContractorLogin();
                break;
            default:
                Toast.makeText(LoginActivity.this, "Please select client or contractor", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void checkContractorLogin() {
        String mobile = mEdtUserName.getText().toString();
        String password = mEdtPassword.getText().toString();

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().checkContractorLogin(mobile, password).enqueue(new Callback<List<Contractor>>() {
                @Override
                public void onResponse(Call<List<Contractor>> call, Response<List<Contractor>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        tv_error.setVisibility(View.GONE);
                        List<Contractor> contractorList = response.body();
                        if(contractorList.size() > 0) {
                            Contractor contractor = contractorList.get(0);
                            User user = new User();
                            user.setMobile(contractor.mobile);
                            user.setEmail(contractor.email);
                            user.setPassword(contractor.password);
                            user.setLname(contractor.lname);
                            user.setCompany_name(contractor.company_name);
                            user.setFname(contractor.fname);
                            user.setServer_id(contractor.id);
                            user.setIs_client(is_client);
                            user.setCreated_at(contractor.created_at);
                            user.setImage_name(contractor.profile_pic);

                            DatabaseUtil.on().insertUser(user);

                            Toast.makeText(LoginActivity.this, "Sucessfully logged in", Toast.LENGTH_SHORT).show();
                            ScreenHelper.redirectToClass(LoginActivity.this, MenuActivity.class);
                            stopLoader();
                        } else {
                            tv_error.setVisibility(View.VISIBLE);
                            tv_error.setText(getString(R.string.login_error));
                            stopLoader();
                        }
                    } else {
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText(getString(R.string.login_error));
                        stopLoader();
                    }
                }

                @Override
                public void onFailure(Call<List<Contractor>> call, Throwable t) {
                    Log.e("Login Error",""+t.getMessage());
                    stopLoader();
                }
            });
        }
    }

    private boolean validateData() {
        if(mEdtUserName.getText().toString().isEmpty() || mEdtUserName.getText().toString().equalsIgnoreCase("")) {
            mEdtUserName.setError("Empty Field");
            mEdtUserName.requestFocus();
            return false;
        } else if(mEdtPassword.getText().toString().isEmpty() || mEdtPassword.getText().toString().equalsIgnoreCase("")) {
            mEdtPassword.setError("Empty Field");
            mEdtPassword.requestFocus();
            return false;
        } else {
            mEdtUserName.setError("");
            mEdtPassword.setError("");
            return true;
        }
    }

    private void checkClientLogin() {
        String mobile = mEdtUserName.getText().toString();
        String password = mEdtPassword.getText().toString();

        if(validateData()) {
            showLoader();
            RetrofitClient.getApiService().checkClientLogin(mobile, password).enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(@NonNull Call<List<Client>> call, @NonNull retrofit2.Response<List<Client>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        tv_error.setVisibility(View.GONE);
                        List<Client> clientList = response.body();
                        if(clientList.size() > 0) {
                            Client client = clientList.get(0);
                            User user = new User();
                            user.setMobile(client.mobile);
                            user.setEmail(client.email);
                            user.setPassword(client.password);
                            user.setLname(client.lname);
                            user.setFname(client.fname);
                            user.setServer_id(client.id);
                            user.setIs_client(is_client);
                            user.setCreated_at(client.created_at);
                            user.setImage_name(client.profile_pic);

                            DatabaseUtil.on().insertUser(user);

                            Toast.makeText(LoginActivity.this, "Sucessfully logged in", Toast.LENGTH_SHORT).show();
                            ScreenHelper.redirectToClass(LoginActivity.this, ClientProjectsActivity.class);
                        } else {
                            tv_error.setVisibility(View.VISIBLE);
                            tv_error.setText(getString(R.string.login_error));
                        }

                        stopLoader();
                    } else {
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText(getString(R.string.login_error));
                        stopLoader();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Client>> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error to login client", Toast.LENGTH_SHORT).show();
                    stopLoader();
                }
            });
        }
    }

    public void onClickToForgotPassword(View view) {
        if(is_client == -1) {
            Toast.makeText(this, "Please select client or contractor", Toast.LENGTH_SHORT).show();
        } else {
            ScreenHelper.redirectToClass(LoginActivity.this, ForgotPasswordActivity.class);
            finish();
        }
    }
}