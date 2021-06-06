package com.blucor.thecontractor.network.reposervices;

import com.blucor.thecontractor.models.Client;
import com.blucor.thecontractor.models.ClientAddSearchModel;
import com.blucor.thecontractor.models.Contract_Type;
import com.blucor.thecontractor.models.Contractor;
import com.blucor.thecontractor.models.ForgotPasswordModel;
import com.blucor.thecontractor.models.Project_Type;
import com.blucor.thecontractor.models.ProjectsModel;
import com.blucor.thecontractor.models.ServerResponseModel;
import com.blucor.thecontractor.network.utils.Contants;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST(Contants.STORE_CLIENT)
    Call<Client> storeClient(@Field("fname") String fname,
                           @Field("lname") String lname,
                           @Field("mobile") String mobile,
                           @Field("email") String email,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST(Contants.UPDATE_CLIENT)
    Call<Client> updateClient(@Field("fname") String fname,
                           @Field("lname") String lname,
                           @Field("mobile") String mobile,
                           @Field("email") String email);

    @FormUrlEncoded
    @POST(Contants.STORE_CONTRACTOR)
    Call<Contractor> storeContractor(@Field("fname") String fname,
                           @Field("lname") String lname,
                           @Field("company_name") String company_name,
                           @Field("mobile") String mobile,
                           @Field("email") String email,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST(Contants.UPDATE_CONTRACTOR)
    Call<Contractor> updateContractor(@Field("id") int id,
                           @Field("fname") String fname,
                           @Field("lname") String lname,
                           @Field("company_name") String company_name,
                           @Field("mobile") String mobile,
                           @Field("email") String email);

    @FormUrlEncoded
    @POST(Contants.CHECK_CLIENT_LOGIN)
    Call<List<Client>> checkClientLogin(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST(Contants.CHECK_CONREACTOR_LOGIN)
    Call<List<Contractor>> checkContractorLogin(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST(Contants.FORGOT_CLIENT_PASS_EMAIL)
    Call<ForgotPasswordModel> forgotClientPassEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST(Contants.FORGOT_CLIENT_PASS_MOBILE)
    Call<ForgotPasswordModel> forgotClientPassMobile(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(Contants.FORGOT_CONTRACTOR_PASS_EMAIL)
    Call<ForgotPasswordModel> forgotContractorPassEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST(Contants.FORGOT_CONTRACTOR_PASS_MOBILE)
    Call<ForgotPasswordModel> forgotContractorPassMobile(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(Contants.UPDATE_CLIENT_PASSWORD)
    Call<Client> updateClientPassword(@Field("password") String password,
                                      @Field("server_id") String server_id);

    @FormUrlEncoded
    @POST(Contants.UPDATE_CONTRACTOR_PASSWORD)
    Call<Contractor> updateContractorPassword(@Field("password") String password,
                                      @Field("server_id") String server_id);

    @FormUrlEncoded
    @POST(Contants.SHOW_CONTRACTOR_BY_ID)
    Call<Contractor> getContractorDetails(@Field("id") int server_id);

    @Multipart
    @POST(Contants.SAVE_CONTRACTOR_PROFILE_PICTURE)
    Call<ServerResponseModel> saveProfilePicture(@Part MultipartBody.Part file,@Part("id") RequestBody server_id);

    @GET(Contants.GET_CONTRACT_TYPE)
    Call<List<Contract_Type>>  getContractType();

    @GET(Contants.GET_PROJECT_TYPE)
    Call<List<Project_Type>>  getProjectType();

    @FormUrlEncoded
    @POST(Contants.SHOW_CLIENT_BY_CONTRACTOR_ID)
    Call<ClientAddSearchModel> getAllClientsByContractor(@Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.STORE_CLIENT_WITH_ID)
    Call<Client> storeClientWithId(@Field("contractor_id") int contractor_id,
                             @Field("fname") String fname,
                             @Field("lname") String lname,
                             @Field("mobile") String mobile,
                             @Field("email") String email,
                             @Field("client_id") String client_id,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST(Contants.SAVE_PROJECT)
    Call<ProjectsModel> saveProject(@Field("project_name") String project_name,
                                      @Field("project_type") String project_type,
                                      @Field("contract_type") String contract_type,
                                      @Field("client_id") int client_id,
                                      @Field("project_location") String project_location,
                                      @Field("start_date") String start_date,
                                      @Field("end_date") String end_date,
                                      @Field("duration") String duration,
                                      @Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.VIEW_ALL_PROJECTS_BY_CONTRACTOR_ID)
    Call<List<ProjectsModel>> getAllProjectContractorType(@Field("contractor_id") int contractor_id);
}

