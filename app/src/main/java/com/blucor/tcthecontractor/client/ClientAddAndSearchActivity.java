package com.blucor.tcthecontractor.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.database.DatabaseUtil;
import com.blucor.tcthecontractor.models.Client;
import com.blucor.tcthecontractor.models.ClientAddSearchModel;
import com.blucor.tcthecontractor.models.User;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.AutocompleteCustomArrayAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientAddAndSearchActivity extends BaseAppCompatActivity {
    public EditText myAutoComplete;
    public ListView lst_search_client;
    public AutocompleteCustomArrayAdapter adapter;
    public ArrayList<Client> clients;
    private String client_id;
    private TextInputEditText edt_first_name;
    private TextInputEditText edt_last_name;
    private TextInputEditText edt_client_id;
    private TextInputEditText edt_mobile;
    private TextInputEditText edt_email;
    private TextInputEditText edt_password;
    private TextInputEditText edt_cpassword;
    public RelativeLayout rl_client;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_and_search);

        myAutoComplete = findViewById(R.id.myautocomplete);
        lst_search_client = findViewById(R.id.lst_search_client);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_client_id = findViewById(R.id.edt_client_id);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_cpassword = findViewById(R.id.edt_cpassword);
        btn_register = findViewById(R.id.btn_register);
        rl_client = findViewById(R.id.rl_add_client);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClient();
            }
        });

        loadAllClients();

        // add the listener so it will tries to suggest while the user type
    }

    private void registerClient() {
        String fname = edt_first_name.getText().toString();
        String lname = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String client_id = edt_client_id.getText().toString();
        String password = edt_password.getText().toString();
        User user = DatabaseUtil.on().getAllUser().get(0);
        int id = user.server_id;

        if(validateData()) {
            int pos = isAlreadyPresentClient();
            if (pos != -1) {
                Client client = clients.get(pos);
                Intent intent = new Intent();
                intent.putExtra("client", client);
                setResult(120, intent);
                finish();

            } else {
                showLoader();
                RetrofitClient.getApiService().storeClientWithId(id, fname, lname, mobile, email, client_id, password).enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(@NonNull Call<Client> call, @NonNull retrofit2.Response<Client> response) {
                        Client client = response.body();
                        if (response.code() == 201) {
                            if (client != null) {
                                if (client.client_id == null && client_id != null) {
                                    client.client_id = client_id;
                                }
                                Intent intent = new Intent();
                                intent.putExtra("client", client);
                                setResult(120, intent);
                                finish();
                                Toast.makeText(ClientAddAndSearchActivity.this, "Successfully register", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ClientAddAndSearchActivity.this, "Client is already present", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ClientAddAndSearchActivity.this, "Client is already present", Toast.LENGTH_SHORT).show();
                        }
                        stopLoader();
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        Toast.makeText(ClientAddAndSearchActivity.this, "Error in registration : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        stopLoader();
                    }
                });
            }
        }
    }

    private int isAlreadyPresentClient() {
        int isPresent = -1;

        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            //if (client.fname.equalsIgnoreCase(edt_first_name.getText().toString()) && client.lname.equalsIgnoreCase(edt_last_name.getText().toString()) && client.mobile.equalsIgnoreCase(edt_mobile.getText().toString())) {
            if (client.mobile.equalsIgnoreCase(edt_mobile.getText().toString())) {
                isPresent = i;
            }
        }

        return isPresent;
    }

    private boolean validateData() {
        edt_first_name.setError(null);
        edt_last_name.setError(null);
        edt_email.setError(null);
        edt_mobile.setError(null);
        edt_password.setError(null);
        edt_cpassword.setError(null);

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

    public void loadAllClients() {
       showLoader();

        RetrofitClient.getApiService().getAllClients().enqueue(new Callback<ClientAddSearchModel>() {
            @Override
            public void onResponse(Call<ClientAddSearchModel> call, Response<ClientAddSearchModel> response) {
                if(response.code() == 200 && response.body() != null) {
                    ClientAddSearchModel model = response.body();
                    clients = model.clients;
                    client_id = model.client_id;

                    edt_client_id.setText(""+client_id);
                    //setListViewAdapter(clients);

                } else {
                    Toast.makeText(ClientAddAndSearchActivity.this, "Unable to get clients", Toast.LENGTH_SHORT).show();
                }
                stopLoader();
            }

            @Override
            public void onFailure(Call<ClientAddSearchModel> call, Throwable t) {
                Toast.makeText(ClientAddAndSearchActivity.this, "Error in loading client : "+t.getMessage(), Toast.LENGTH_LONG).show();
                stopLoader();
            }
        });
    }

    public void setListViewAdapter(ArrayList<Client> clients) {
        if (clients.size() <= 0) {
            rl_client.setVisibility(View.VISIBLE);
            lst_search_client.setVisibility(View.GONE);
        } else {
            rl_client.setVisibility(View.GONE);
            lst_search_client.setVisibility(View.VISIBLE);
        }
       // myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(ClientAddAndSearchActivity.this));

        // set the custom ArrayAdapter
        adapter = new AutocompleteCustomArrayAdapter(ClientAddAndSearchActivity.this, R.layout.list_view_row, clients);
        try {
            lst_search_client.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("TAG",e.getMessage());
        }
        /*lst_search_client.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = clients.get(position);
                myAutoComplete.setText("" + client.fname + " " + client.lname);
                Intent intent = new Intent();
                intent.putExtra("client", client);
                setResult(120, intent);
                finish();
            }
        });*/
    }

    @Override
    public void onBackPressed() {

    }
}