package com.blucor.thecontractor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.account.LoginActivity;
import com.blucor.thecontractor.account.UserProfileActivity;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.material.MaterialMenuActivity;
import com.blucor.thecontractor.models.Contractor;
import com.blucor.thecontractor.models.User;
import com.blucor.thecontractor.network.retrofit.RetrofitClient;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.project.ProjectManagementMenuActivity;
import com.blucor.thecontractor.utility.ScreenHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.multidex.BuildConfig;
import retrofit2.Call;
import retrofit2.Callback;

public class MenuActivity extends BaseAppCompatActivity {

    private RelativeLayout mRlUserProfile;
    private ImageView mImgProfile;
    private ImageView mImgProjectMenu;
    private ImageView mImgMaterialMenu;
    private ImageView mImgAccountMenu;
    private ImageView mImgShareMenu;
    private ImageView mImgLogoutMenu;
    private ProgressBar mProgressBar;
    private LinearLayout mLlUserProfile;
    private TextView mUserName;
    private TextView mUserMobile;
    private User user;
    private int is_client;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mRlUserProfile = findViewById(R.id.rl_user_details);
        mImgProfile = findViewById(R.id.img_logo);
        mImgProjectMenu = findViewById(R.id.img_project_menu);
        mImgMaterialMenu = findViewById(R.id.img_material_menu);
        mImgAccountMenu = findViewById(R.id.img_user_profile_menu);
        mImgShareMenu = findViewById(R.id.img_share_menu);
        mImgLogoutMenu = findViewById(R.id.img_logout_menu);
        mProgressBar = findViewById(R.id.pb_menu_contractor);
        mLlUserProfile = findViewById(R.id.ll_user_details);
        mUserName = findViewById(R.id.tv_user_name);
        mUserMobile = findViewById(R.id.tv_mobile_no);

        int widthU = ScreenHelper.getWidthInPercentage(getApplicationContext(), 70);
        RelativeLayout.LayoutParams paramsU = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, widthU);
        mRlUserProfile.setLayoutParams(paramsU);

       /* int width = ScreenHelper.getWidthInPercentage(getApplicationContext(), 15);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        mImgProjectMenu.setLayoutParams(params);
        mImgMaterialMenu.setLayoutParams(params);
        mImgAccountMenu.setLayoutParams(params);
        mImgShareMenu.setLayoutParams(params);
        mImgLogoutMenu.setLayoutParams(params);*/

        int width1 = ScreenHelper.getWidthInPercentage(getApplicationContext(), 20);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width1, width1);
        params1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params1.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
//        params1.addRule(RelativeLayout.ABOVE, R.id.img_logo);
        mImgProfile.setLayoutParams(params1);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);
        setupProfile();
        getContractorDetails();
    }

    public void onClickToLogout(View view) {
       logoutContractor();
    }

    private void logoutContractor() {
        AlertDialog dialog = new AlertDialog.Builder(MenuActivity.this).create();
        dialog.setMessage("Do you want to logout?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                logoutContractorFromServer();
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

    private void logoutContractorFromServer() {
        DatabaseUtil.on().deleteAll();
        ScreenHelper.redirectToClass(MenuActivity.this, LoginActivity.class);
        finish();
        Toast.makeText(this, "Successfully logout", Toast.LENGTH_SHORT).show();
    }

    public void onClickToShare(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= getString(R.string.share_app_msg);
            shareMessage = shareMessage + getString(R.string.share_app_store_link) + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public void onClickToAccount(View view) {
        ScreenHelper.redirectToClass(MenuActivity.this, UserProfileActivity.class);
    }

    public void onClickToMaterial(View view) {
        ScreenHelper.redirectToClass(MenuActivity.this, MaterialMenuActivity.class);
    }

    public void onClickToProject(View view) {
        ScreenHelper.redirectToClass(MenuActivity.this, ProjectManagementMenuActivity.class);
    }

    public void onClickToSendFeedBack(View view) {
       // ScreenHelper.redirectToClass(MenuActivity.this, ProjectManagementMenuActivity.class);
    }

    public void onClickToAboutUs(View view) {
       // ScreenHelper.redirectToClass(MenuActivity.this, ProjectManagementMenuActivity.class);
    }

    public void onClickToUpgradePremium(View view) {
       // ScreenHelper.redirectToClass(MenuActivity.this, ProjectManagementMenuActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getContractorDetails();
    }

    private void getContractorDetails() {
        showLoader();
        RetrofitClient.getApiService().getContractorDetails(user.server_id).enqueue(new Callback<Contractor>() {
            @Override
            public void onResponse(@NonNull Call<Contractor> call, @NonNull retrofit2.Response<Contractor> response) {
                if (response.body() != null) {
                    Contractor contractor = response.body();
                    user = new User();
                    user.setMobile(contractor.mobile);
                    user.setEmail(contractor.email);
                    user.setPassword(contractor.password);
                    user.setLname(contractor.lname);
                    user.setCompany_name(contractor.company_name);
                    user.setFname(contractor.fname);
                    user.setServer_id(contractor.id);
                    user.setIs_client(is_client);
                    user.setImage_name(contractor.profile_pic);
                    user.setCreated_at(contractor.created_at);

                    DatabaseUtil.on().deleteAll();
                    DatabaseUtil.on().insertUser(user);
                    setupProfile();
                }
                stopLoader();
            }

            @Override
            public void onFailure(@NonNull Call<Contractor> call, @NonNull Throwable t) {
                Toast.makeText(MenuActivity.this, "Error to load Contractor details", Toast.LENGTH_SHORT).show();
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
        ScreenHelper.exitApp(MenuActivity.this);
    }
}