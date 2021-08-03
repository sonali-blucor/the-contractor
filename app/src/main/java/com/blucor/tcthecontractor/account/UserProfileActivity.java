package com.blucor.tcthecontractor.account;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.ClientMenuActivity;
import com.blucor.tcthecontractor.MenuActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.models.Client;
import com.blucor.tcthecontractor.models.Contractor;
import com.blucor.tcthecontractor.models.ServerResponseModel;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.network.utils.Contants;
import com.blucor.tcthecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends BaseAppCompatActivity {
    private User user;
    private int is_client;
    private SharedPreferences sharedPreferences;
    private EditText edt_first_name;
    private EditText edt_last_name;
    private EditText edt_company_name;
    private EditText edt_mobile;
    private EditText edt_email;
    private TextInputLayout til_company_name;
    private Button btn_submit;
    private ImageView img_logo;
    private final int EXTERNAL_STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_company_name = findViewById(R.id.edt_company_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        img_logo = findViewById(R.id.img_logo);
        btn_submit = findViewById(R.id.btn_submit);
        til_company_name = findViewById(R.id.til_company_name);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME,MODE_PRIVATE);
        is_client = sharedPreferences.getInt(Contants.USER_TYPE_KEY,-1);

        user = DatabaseUtil.on().getAllUser().get(0);
        setupProfile();
        if (is_client == Contants.USER_TYPE_CONTRACTOR) {
            til_company_name.setVisibility(View.VISIBLE);
            getContractorDetails();
        } else if (is_client == Contants.USER_TYPE_CLIENT) {
            til_company_name.setVisibility(View.GONE);
            getClientDetails();
        }
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
                Toast.makeText(UserProfileActivity.this, "Error to load Client details", Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    public void onClickGallery(View view){
        /*int readPermission = ActivityCompat.checkSelfPermission(UserProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ActivityCompat.checkSelfPermission(UserProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ( readPermission!= PackageManager.PERMISSION_GRANTED && writePermission!= PackageManager.PERMISSION_GRANTED) {
            openGallary();
        } else {*/
            // Requesting Permission to access External Storage
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_PERMISSION_CODE);
        //}
    }

    private void openGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallary();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                img_logo.setImageBitmap(newProfilePic);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                newProfilePic.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
                // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
                String folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "TheContractor";
                File folder = new File(folder_path);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                String file_name = user.fname+"_"+user.lname+"_temp"+System.currentTimeMillis()+".jpg";

                File file = new File(folder_path + File.separator + file_name);
                try {
                    if(file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.flush();
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file != null && file.exists()) {
                    if (is_client == Contants.USER_TYPE_CONTRACTOR) {
                        saveProfilePicture(file);
                    } else if (is_client == Contants.USER_TYPE_CLIENT) {
                        saveClientProfilePicture(file);
                    }
                }
            }
        }
    }

    private void saveProfilePicture(File file) {
        // Parsing any Media type file
        showLoader();
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        int id = user.server_id;
        RequestBody server_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));

        RetrofitClient.getApiService().saveProfilePicture(fileToUpload,server_id).enqueue(new Callback<ServerResponseModel>() {
            @Override
            public void onResponse(Call<ServerResponseModel> call, Response<ServerResponseModel> response) {
                ServerResponseModel model = response.body();
                if (model.success) {
                    String image_name = model.message;
                    ScreenHelper.setThumbImageUriInView(img_logo,image_name);
                    user.setImage_name(image_name);
                    DatabaseUtil.on().updateImageName(image_name,id);
                } else {
                    Toast.makeText(UserProfileActivity.this, ""+model.message, Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG",model.message);
                stopLoader();
            }

            @Override
            public void onFailure(Call<ServerResponseModel> call, Throwable t) {
                Log.e("xg",""+t.getMessage());
                stopLoader();
            }
        });
    }

    private void saveClientProfilePicture(File file) {
        // Parsing any Media type file
        showLoader();
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        int id = user.server_id;
        RequestBody server_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));

        RetrofitClient.getApiService().saveClientProfilePicture(fileToUpload,server_id).enqueue(new Callback<ServerResponseModel>() {
            @Override
            public void onResponse(Call<ServerResponseModel> call, Response<ServerResponseModel> response) {
                ServerResponseModel model = response.body();
                if (model.success) {
                    String image_name = model.message;
                    ScreenHelper.setThumbImageUriInView(img_logo,image_name);
                    user.setImage_name(image_name);
                    DatabaseUtil.on().updateImageName(image_name,id);
                } else {
                    Toast.makeText(UserProfileActivity.this, ""+model.message, Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG",model.message);
                stopLoader();
            }

            @Override
            public void onFailure(Call<ServerResponseModel> call, Throwable t) {
                Log.e("xg",""+t.getMessage());
                stopLoader();
            }
        });
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
                Toast.makeText(UserProfileActivity.this, "Error to register Contractor", Toast.LENGTH_SHORT).show();
                stopLoader();
            }
        });
    }

    private void setupProfile() {
        edt_first_name.setText(user.fname);
        edt_last_name.setText(user.lname);
        edt_company_name.setText(user.company_name);
        edt_email.setText(user.email);
        edt_mobile.setText(user.mobile);
        ScreenHelper.setThumbImageUriInView(img_logo,user.image_name);
    }

    public void onClickToRegister(View view){
        if (is_valid_data()) {
            if (is_client == Contants.USER_TYPE_CLIENT) {
                updateClientUserProfile();
            } else if (is_client == Contants.USER_TYPE_CONTRACTOR) {
                updateContractorUserProfile();
            }
        }
    }

    private boolean is_valid_data() {
        boolean isValid = false;
        String fanme = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String company_name = edt_company_name.getText().toString();
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
        } else if(is_client == Contants.USER_TYPE_CONTRACTOR && (company_name.isEmpty() || company_name.equalsIgnoreCase(""))) {
            edt_company_name.setError(error);
            edt_company_name.requestFocus();
            isValid = false;
        } else {
            edt_first_name.setError(null);
            edt_last_name.setError(null);
            edt_email.setError(null);
            edt_mobile.setError(null);
            edt_company_name.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private void updateContractorUserProfile() {
        showLoader();
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String company_name = edt_company_name.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String email = edt_email.getText().toString();
        RetrofitClient.getApiService().updateContractor(user.server_id,fname,lname,company_name,mobile,email).enqueue(new Callback<Contractor>() {
            @Override
            public void onResponse(Call<Contractor> call, Response<Contractor> response) {
                if (response.code() == 200 && response.body() != null) {
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
                } else {
                    Toast.makeText(UserProfileActivity.this, "Unable to update profile", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
                setEditTextEnabled(false);
            }

            @Override
            public void onFailure(Call<Contractor> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Error in update profile", Toast.LENGTH_SHORT).show();
                stopLoader();
                setEditTextEnabled(false);
            }
        });
    }

    private void updateClientUserProfile() {
        showLoader();
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String email = edt_email.getText().toString();
        RetrofitClient.getApiService().updateClient(fname,lname,mobile,email).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
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
                setEditTextEnabled(false);
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                stopLoader();
                setEditTextEnabled(false);
            }
        });
    }

    public void onClickEditUserProfile(View view) {
        setEditTextEnabled(true);
    }

    public void setEditTextEnabled(boolean enabled) {
        edt_first_name.setEnabled(enabled);
        edt_last_name.setEnabled(enabled);
        edt_company_name.setEnabled(enabled);
        //edt_mobile.setEnabled(enabled);
        edt_email.setEnabled(enabled);
        if (enabled) {
            btn_submit.setVisibility(View.VISIBLE);
        } else {
            btn_submit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (is_client == Contants.USER_TYPE_CLIENT) {
            Intent intent = new Intent(this, ClientMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}