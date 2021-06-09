package com.blucor.thecontractor.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.BaseAppCompatActivity;
import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.ForgotPasswordModel;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseAppCompatActivity {
    private int is_client = -1;
    private EditText edt_mobile_email;
    private TextView btn_forgot_pass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_mobile_email = findViewById(R.id.edt_mobile_email);
        btn_forgot_pass = findViewById(R.id.btn_submit_forgot_pass);
        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME, MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY, -1);

        btn_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (is_valid_data()) {
                    case 1:
                        //email
                        edt_mobile_email.setError(null);
                        forgotPassUsingEmail();
                        break;
                    case 2:
                        //mobile
                        edt_mobile_email.setError(null);
                        forgotPassUsingMobile();
                        break;
                    case 0:
                        edt_mobile_email.setError(getString(R.string.invalid_data_forgot_password));
                        break;
                    default:
                        if (edt_mobile_email.getText().toString().isEmpty() || edt_mobile_email.getText().toString().equalsIgnoreCase("")) {
                            edt_mobile_email.setError(getString(R.string.invalid_data_forgot_password));
                        }
                        break;
                }
            }
        });
    }


    private void forgotPassUsingMobile() {
        if (is_client == 1) {
            forgotPassUsingMobileClient();
        } else if (is_client == 0) {
            forgotPassUsingMobileContractor();
        }
    }

    private void forgotPassUsingMobileContractor() {
        showLoader();

        String email_mobile = edt_mobile_email.getText().toString();
        RetrofitClient.getApiService().forgotContractorPassMobile(email_mobile).enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                openConfirmPassword(response.body());
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                stopLoader();
            }
        });
    }

    private void forgotPassUsingMobileClient() {
        showLoader();

        String email_mobile = edt_mobile_email.getText().toString();
        RetrofitClient.getApiService().forgotClientPassMobile(email_mobile).enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                openConfirmPassword(response.body());
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                stopLoader();
            }
        });
    }

    private void forgotPassUsingEmail() {
        if (is_client == 1) {
            forgotPassUsingEmailClient();
        } else if (is_client == 0) {
            forgotPassUsingEmailContractor();
        }
    }

    private void forgotPassUsingEmailContractor() {
        showLoader();

        String email_mobile = edt_mobile_email.getText().toString();
        RetrofitClient.getApiService().forgotContractorPassEmail(email_mobile).enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                openConfirmPassword(response.body());
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                stopLoader();
            }
        });
    }

    private void forgotPassUsingEmailClient() {
        showLoader();

        String email_mobile = edt_mobile_email.getText().toString();
        RetrofitClient.getApiService().forgotClientPassEmail(email_mobile).enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                openConfirmPassword(response.body());
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                stopLoader();
            }
        });
    }

    private boolean is_mobile() {
        String mobile = edt_mobile_email.getText().toString();
        if (Patterns.PHONE.matcher(mobile).matches()) {
            if (mobile.length() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean is_email() {
        if (Patterns.EMAIL_ADDRESS.matcher(edt_mobile_email.getText().toString()).matches()) {
            return true;
        } else {
            return false;
        }
    }

    private int is_valid_data() {
        if (is_email()) {
            return 1;
        } else if (is_mobile()) {
            return 2;
        } else {
            return 0;
        }
    }

    private void openConfirmPassword(ForgotPasswordModel forgotPasswordModel) {
        stopLoader();
        if (forgotPasswordModel.otp != null) {
            Intent intent = new Intent(ForgotPasswordActivity.this, ConfirmPasswordActivity.class);
            intent.putExtra("otp", forgotPasswordModel.otp);
            intent.putExtra("server_id", forgotPasswordModel.server_id);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Otp null try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickToLogin(View view) {
        ScreenHelper.redirectToClass(ForgotPasswordActivity.this, LoginActivity.class);
        finish();
    }
}