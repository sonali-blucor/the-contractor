package com.blucor.tcthecontractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.tcthecontractor.account.LoginActivity;
import com.blucor.tcthecontractor.account.UserProfileActivity;
import com.blucor.tcthecontractor.client.ClientProjectsActivity;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.models.Client;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.network.utils.Contants;
import com.blucor.tcthecontractor.project.activity.TodaysActivitiesActivity;
import com.blucor.tcthecontractor.utility.ScreenHelper;

public class ClientMenuActivity extends BaseAppCompatActivity {
    private RelativeLayout mRlUserProfile;
    private ImageView mImgProfile;
    private TextView mUserName;
    private TextView mUserMobile;
    private User user;
    private int is_client;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        mRlUserProfile = findViewById(R.id.rl_user_details);
        mImgProfile = findViewById(R.id.img_logo);
        mUserName = findViewById(R.id.tv_user_name);
        mUserMobile = findViewById(R.id.tv_mobile_no);

        int widthU = ScreenHelper.getWidthInPercentage(getApplicationContext(), 70);
        RelativeLayout.LayoutParams paramsU = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, widthU);
        mRlUserProfile.setLayoutParams(paramsU);

        int width1 = ScreenHelper.getWidthInPercentage(getApplicationContext(), 20);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width1, width1);
        params1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params1.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        mImgProfile.setLayoutParams(params1);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);
        setupProfile();
        getClientDetails();
    }

    public void onClickToLogout(View view) {
        Client();
    }

    private void Client() {
        AlertDialog dialog = new AlertDialog.Builder(ClientMenuActivity.this).create();
        dialog.setMessage("Do you want to logout?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ClientFromServer();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void ClientFromServer() {
        DatabaseUtil.on().deleteAll();
        ScreenHelper.redirectToClass(ClientMenuActivity.this, LoginActivity.class);
        finish();
        Toast.makeText(this, "Successfully logout", Toast.LENGTH_SHORT).show();
    }


    public void onClickToAccount(View view) {
        ScreenHelper.redirectToClass(ClientMenuActivity.this, UserProfileActivity.class);
    }

    public void onClickToTodayActivity(View view) {
        ScreenHelper.redirectToClass(ClientMenuActivity.this, TodaysActivitiesActivity.class);
    }

    public void onClickToProject(View view) {
       /* Intent intent = new Intent(ClientMenuActivity.this, ProjectManagementMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
        ScreenHelper.redirectToClass(ClientMenuActivity.this, ClientProjectsActivity.class);
    }


    private void getClientDetails() {
        showLoader();
        RetrofitClient.getApiService().getClientDetails(user.server_id).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(@NonNull Call<Client> call, @NonNull retrofit2.Response<Client> response) {
                if (response.body() != null) {
                    Client client = response.body();
                    user = new User();
                    user.setMobile(client.mobile);
                    user.setEmail(client.email);
                    user.setPassword(client.password);
                    user.setLname(client.lname);
                    user.setFname(client.fname);
                    user.setServer_id(client.id);
                    user.setIs_client(is_client);
                    user.setImage_name(client.profile_pic);
                    user.setCreated_at(client.created_at);

                    DatabaseUtil.on().deleteAll();
                    DatabaseUtil.on().insertUser(user);
                    setupProfile();
                }
                stopLoader();
            }

            @Override
            public void onFailure(@NonNull Call<Client> call, @NonNull Throwable t) {
                Toast.makeText(ClientMenuActivity.this, "Error to load Client details", Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void setupProfile() {
        user = DatabaseUtil.on().getAllUser().get(0);
        mUserName.setText(user.fname+" "+user.lname);
        mUserMobile.setText(user.mobile);

        ScreenHelper.setThumbImageUriInView(mImgProfile,user.image_name);
    }

    @Override
    public void onBackPressed() {
        ScreenHelper.exitApp(ClientMenuActivity.this);
    }
}