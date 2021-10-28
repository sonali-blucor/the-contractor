package com.blucor.tcthecontractor.network.reposervices;

import com.blucor.tcthecontractor.models.ActivityResponseModel;
import com.blucor.tcthecontractor.models.BillResponseModel;
import com.blucor.tcthecontractor.models.BilliModel;
import com.blucor.tcthecontractor.models.Client;
import com.blucor.tcthecontractor.models.ClientAddSearchModel;
import com.blucor.tcthecontractor.models.ClientProjectActivityModel;
import com.blucor.tcthecontractor.models.Contract_Type;
import com.blucor.tcthecontractor.models.Contractor;
import com.blucor.tcthecontractor.models.ForgotPasswordModel;
import com.blucor.tcthecontractor.models.HolidayModel;
import com.blucor.tcthecontractor.models.InsertActivityResponseModel;
import com.blucor.tcthecontractor.models.InsertSubActivityResponseModel;
import com.blucor.tcthecontractor.models.MaterialPurchase;
import com.blucor.tcthecontractor.models.MaterialsModal;
import com.blucor.tcthecontractor.models.ProjectMaterialModel;
import com.blucor.tcthecontractor.models.Project_Type;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.models.ScheduleModel;
import com.blucor.tcthecontractor.models.ServerResponseModel;
import com.blucor.tcthecontractor.models.SubContractor;
import com.blucor.tcthecontractor.models.SubContractorAddSearchModel;
import com.blucor.tcthecontractor.models.SupplierModal;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.models.WorkOrderModel;
import com.blucor.tcthecontractor.models.WorkOrderResponseModel;
import com.blucor.tcthecontractor.network.utils.Contants;

import java.util.ArrayList;
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
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST(Contants.SHOW_CLIENT_BY_ID)
    Call<Client> getClientDetails(@Field("id") int server_id);

    @Multipart
    @POST(Contants.SAVE_CONTRACTOR_PROFILE_PICTURE)
    Call<ServerResponseModel> saveProfilePicture(@Part MultipartBody.Part file, @Part("id") RequestBody server_id);

    @Multipart
    @POST(Contants.SAVE_CLIENT_PROFILE_PICTURE)
    Call<ServerResponseModel> saveClientProfilePicture(@Part MultipartBody.Part file, @Part("id") RequestBody server_id);

    @GET(Contants.GET_CONTRACT_TYPE)
    Call<List<Contract_Type>> getContractType();

    @GET(Contants.GET_PROJECT_TYPE)
    Call<List<Project_Type>> getProjectType();

    @GET(Contants.GET_UNIT)
    Call<List<UnitModal>> getUnits();

    @GET(Contants.SHOW_CLIENT)
    Call<ClientAddSearchModel> getAllClients();

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
                                    @Field("work_order") String work_order,
                                    @Field("billing") String bills,
                                    @Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.VIEW_ALL_PROJECTS_BY_CONTRACTOR_ID)
    Call<List<ProjectsModel>> getAllProjectContractorType(@Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.GET_SCHEDULE_BY_PROJECT_ID)
    Call<List<ScheduleModel>> getScheduleByProjectId(@Field("project_id") int project_id);

    @FormUrlEncoded
    @POST(Contants.STORE_OR_UPDATE_SCHEDULE)
    Call<ScheduleModel> saveOrUpdateSchedule(
            @Field("project_id") int project_id,
            @Field("project_status") String project_status,
            @Field("no_of_days") int no_of_days,
            @Field("week_days") String week_days,
            @Field("project_status_integer") int project_status_integer,
            @Field("rating") float rating,
            @Field("schedule_dates") String schedule_dates);

    @FormUrlEncoded
    @POST(Contants.GET_ACTIVITY_BY_PROJECT_ID)
    Call<List<ActivityResponseModel>> getActivityList(@Field("project_id") int id);

    @FormUrlEncoded
    @POST(Contants.GET_ACTIVITY_BY_PROJECT_ID_ACT_ID)
    Call<ActivityResponseModel> getProjectActivityDetails(@Field("project_id") int id,
                                                          @Field("main_activity_id") int main_activity_id);

    @FormUrlEncoded
    @POST(Contants.STORE_OR_UPDATE_ACTIVITY)
    Call<InsertActivityResponseModel> insertOrUpdateActivity(@Field("project_id") int id,
                                                             @Field("activity_name") String main_activity_name,
                                                             @Field("start_date") String start_date,
                                                             @Field("end_date") String end_date);


    @FormUrlEncoded
    @POST(Contants.STORE_SUB_CONTRACTOR)
    Call<SubContractor> storeSubContractor(@Field("fname") String fname,
                                           @Field("lname") String lname,
                                           @Field("contractor_id") int contractor_id,
                                           @Field("mobile") String mobile,
                                           @Field("email") String email,
                                           @Field("password") String password,
                                           @Field("address") String address,
                                           @Field("pan_cart_no") String pan_cart_no,
                                           @Field("aadhar_cart_no") String aadhar_cart_no,
                                           @Field("gst_no") String gst_no,
                                           @Field("bank_details") String bank_details
    );

    @FormUrlEncoded
    @POST(Contants.STORE_OR_UPDATE_SUB_ACTIVITY)
    Call<InsertSubActivityResponseModel> saveOrUpdateSubActivity(@Field("activity_id") int activity_id,
                                                                 @Field("sub_activity_name") String sub_activity_name,
                                                                 @Field("start_date") String sub_activity_start_date,
                                                                 @Field("end_date") String sub_activity_end_date,
                                                                 @Field("sub_contractor_id") int sub_contractor_id,
                                                                 @Field("duration") String duration);

    @FormUrlEncoded
    @POST(Contants.SHOW_SUB_CONTRACTOR_BY_CONTRACTOR_ID)
    Call<SubContractorAddSearchModel> getAllSubContractorsByContractor(@Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.VIEW_ALL_COMPLETED_PROJECTS_BY_CONTRACTOR_ID)
    Call<List<ProjectsModel>> getAllCompletedProjectContractorType(@Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.STORE_MATERIAL)
    Call<ProjectMaterialModel> storeMaterial(@Field("material_name") String material_name,
                                             @Field("material_brand") String material_brand,
                                             @Field("material_date") String material_date,
                                             @Field("material_type") String material_type,
                                             @Field("supplier_name") String supplier_name,
                                             @Field("supplier_contact") String supplier_contact,
                                             @Field("unit") String unit,
                                             @Field("quantity") String quantity,
                                             @Field("project_id") int project_id,
                                             @Field("is_edit") boolean is_edit,
                                             @Field("material_id") int material_id);

    @FormUrlEncoded
    @POST(Contants.STORE_MATERIAL_PURCHASE)
    Call<ProjectMaterialModel> storeMaterialPurchase(@Field("supplier_id") int suppler_id,
                                                     @Field("material_id") int materials_id,
                                                     @Field("quantity") String quantity,
                                                     @Field("rate") String rate,
                                                     @Field("amount") String amount,
                                                     @Field("gst") String gst,
                                                     @Field("gst_amt") String gstAmount,
                                                     @Field("total_amt") String totalAmount,
                                                     @Field("paid_amt") String paidAmount,
                                                     @Field("balance_amt") String balanceAmount,
                                                     @Field("contractor_id") int contractor_id,
                                                     @Field("project_id") int project_id,
                                                     @Field("is_edit") boolean is_edit,
                                                     @Field("material_purchase_id") int material_id
    );

    // akash lawande

    @FormUrlEncoded
    @POST(Contants.STORE_MATERIAL_PURCHASE_PAYMENT)
    Call<ProjectMaterialModel> storeMaterialPurchasePayment(
            @Field("id") int material_id,
            @Field("paid_to") String paid_to,
            @Field("paid_amt") String amount,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST(Contants.STORE_MATERIALS)
    Call<MaterialsModal> storeMaterials(@Field("material_brand") String material_brand,
                                        @Field("material_type") String material_type,
                                        @Field("material_description") String material_description,
                                        @Field("unit") int unit);

    @FormUrlEncoded
    @POST(Contants.STORE_SUPPLIER)
    Call<SupplierModal> storeSupplier(@Field("supplier_name") String supplier_name,
                                      @Field("contact") String supplier_contact,
                                      @Field("email") String supplier_email,
                                      @Field("address") String supplier_address);

        @FormUrlEncoded
    @POST(Contants.GET_MATERIAL_PURCHASE_BY_PROJECT_ID /*+ "/{project_id}/{material_id}"*/)
    Call<List<MaterialPurchase>> getMaterialsByProjectId(@Field("project_id") int project_id
                                                        , @Field("material_id") int material_id
    );

    @FormUrlEncoded
    @POST(Contants.VIEW_ALL_PROJECTS_BY_CLIENT_ID)
    Call<List<ClientProjectActivityModel>> getAllProjectClientType(@Field("client_id") int client_id);

    @GET(Contants.SHOW_SUB_CONTRACTORS)
    Call<List<SubContractor>> getAllSubContractors();

    @FormUrlEncoded
    @POST(Contants.STORE_PROJECT_SUB_CONTRACTORS)
    Call<ServerResponseModel> storeProjectSubContractor(@Field("project_id") int project_id,
                                                        @Field("sub_contractors") String sub_contractors);

    @FormUrlEncoded
    @POST(Contants.SHOW_PROJECT_SUB_CONTRACTORS)
    Call<List<SubContractor>> getAllProjectSubContractors(@Field("project_id") int project_id);

    @FormUrlEncoded
    @POST(Contants.GET_CLIENT_TODAY_ACTIVITY)
    Call<List<ActivityResponseModel>> getClientTodaysActivities(@Field("client_id") int client_id);

    @FormUrlEncoded
    @POST(Contants.GET_CONTRACTOR_TODAY_ACTIVITY)
    Call<List<ActivityResponseModel>> getContractorTodaysActivities(@Field("contractor_id") int contractor_id);

    @FormUrlEncoded
    @POST(Contants.SHOW_WORK_ORDERS)
    Call<List<WorkOrderModel>> getWorkOrderByProjectId(@Field("client_id") int client_id,
                                                       @Field("project_id") int project_id);

    @FormUrlEncoded
    @POST(Contants.SHOW_WORK_ORDERS_BY_PROJECT_ID)
    Call<List<WorkOrderModel>> getAllWorkOrderByProjectId(@Field("project_id") int project_id);

    @FormUrlEncoded
    @POST(Contants.SHOW_BILL_BY_PROJECT_ID)
    Call<List<BilliModel>> getAllBillByProjectId(@Field("project_id") int project_id);


    // added 19.10.2021
    @FormUrlEncoded
    @POST(Contants.SHOW_WORK_ORDERS_BY_SUB_CONTRACTOR_ID)
    Call<List<WorkOrderModel>> getAllWorkOrderBySubContractorId(@Field("project_id") int project_id, @Field("sub_contractor_id") int sub_contractor_id);

    @FormUrlEncoded
    @POST(Contants.SHOW_BILL_BY_SUB_CONTRACTOR_ID)
    Call<List<BilliModel>> getAllBillBySubContractorId(@Field("project_id") int project_id, @Field("sub_contractor_id") int sub_contractor_id);

    @FormUrlEncoded
    @POST(Contants.VIEW_ALL_SUB_CONTRACTOR_BY_CONTRACTOR_ID)
    Call<List<SubContractor>> getAllSubContractorType(@Field("contractor_id") int contractor_id, @Field("project_id") int project_id);


    @FormUrlEncoded
    @POST(Contants.ADD_WORK_ORDER_BY_PROJECT_ID)
    Call<WorkOrderResponseModel> storeWorkOrderByProjectId(@Field("is_edit") boolean is_edit,
                                                           @Field("work_order_id") int work_order_id,
                                                           @Field("work_description") String work_description,
                                                           @Field("unit_id") int unit_id,
                                                           @Field("quantity") long quantity,
                                                           @Field("rate") long rate,
                                                           @Field("amount") long amount,
                                                           @Field("project_id") int project_id);

    @FormUrlEncoded
    @POST(Contants.ADD_BILL_BY_PROJECT_ID)
    Call<BillResponseModel> storeBillByProjectId(@Field("is_edit") boolean is_edit,
                                                 @Field("bill_id") int bill_id,
                                                 @Field("percentage") float percentage,
                                                 @Field("amount") double amount,
                                                 @Field("remark") String remark,
                                                 @Field("balance") double balance,
                                                 @Field("paid") double paid,
                                                 @Field("payment_date") String payment_date,
                                                 @Field("billing_date") String billing_date,
                                                 @Field("project_id") int project_id);

    @FormUrlEncoded
    @POST(Contants.ADD_CLIENT_BILL_BY_PROJECT_ID)
    Call<BillResponseModel> storeClientBillByProjectId(@Field("is_edit") boolean is_edit,
                                                       @Field("bill_id") int bill_id,
                                                       @Field("percentage") float percentage,
                                                       @Field("amount") double amount,
                                                       @Field("remark") String remark,
                                                       @Field("balance") double balance,
                                                       @Field("paid") double paid,
                                                       @Field("payment_date") String payment_date,
                                                       @Field("billing_date") String billing_date,
                                                       @Field("project_id") int project_id);

    /*@POST(Contants.FETCH_HOLIDAYS)
    Call<ArrayList<HolidayModel>> getHolidays();*/

    @FormUrlEncoded
    @POST(Contants.FETCH_HOLIDAYS_BY_PROJECT)
    Call<ArrayList<HolidayModel>> getHolidaysByProjectId(@Field("project_id") int project_id);


    // added 19.10.20921
    @FormUrlEncoded
    @POST(Contants.ADD_BILL_BY_SUB_CONTRACTOR_PROJECT_ID)
    Call<BillResponseModel> storeBillBySubContractorProjectId(@Field("is_edit") boolean is_edit,
                                                              @Field("bill_id") int bill_id,
                                                              @Field("percentage") float percentage,
                                                              @Field("amount") double amount,
                                                              @Field("remark") String remark,
                                                              @Field("balance") double balance,
                                                              @Field("paid") double paid,
                                                              @Field("payment_date") String payment_date,
                                                              @Field("billing_date") String billing_date,
                                                              @Field("project_id") int project_id,
                                                              @Field("sub_contractor_id") int sub_contractor_id
    );

    @FormUrlEncoded
    @POST(Contants.ADD_WORK_ORDER_BY_SUB_CONTRACTOR_PROJECT_ID)
    Call<WorkOrderResponseModel> storeWorkOrderBySubContractorProjectId(@Field("is_edit") boolean is_edit,
                                                                        @Field("work_order_id") int work_order_id,
                                                                        @Field("work_description") String work_description,
                                                                        @Field("unit_id") int unit_id,
                                                                        @Field("quantity") long quantity,
                                                                        @Field("rate") long rate,
                                                                        @Field("amount") long amount,
                                                                        @Field("project_id") int project_id,
                                                                        @Field("sub_contractor_id") int sub_contractor_id
    );

    @FormUrlEncoded
    @POST(Contants.ADD_CLIENT_BILL_BY_SUB_CONTRACTOR_PROJECT_ID)
    Call<BillResponseModel> storeClientBillBySubContractorProjectId(@Field("is_edit") boolean is_edit,
                                                                    @Field("bill_id") int bill_id,
                                                                    @Field("percentage") float percentage,
                                                                    @Field("amount") double amount,
                                                                    @Field("remark") String remark,
                                                                    @Field("balance") double balance,
                                                                    @Field("paid") double paid,
                                                                    @Field("payment_date") String payment_date,
                                                                    @Field("billing_date") String billing_date,
                                                                    @Field("project_id") int project_id,
                                                                    @Field("sub_contractor_id") int sub_contractor_id);


    @GET(Contants.GET_SUPPLER)
    Call<List<SupplierModal>> getSupplier();

    @GET(Contants.GET_MATERIALS)
    Call<List<MaterialsModal>> getMaterials();

}

