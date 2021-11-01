package com.blucor.tcthecontractor.network.utils;

public class Contants {
    public static final String USER_PREFERNCE_NAME = "user_preferance";
    public static final String USER_TYPE_KEY = "user_type";
    public static final int USER_TYPE_CLIENT = 1;
    public static final int USER_TYPE_CONTRACTOR = 0;

    /**
     * Netwok urls
     */
    //public static final String MAIN_URL = "https://192.168.43.234/contractor_api/public/";
    public static final String MAIN_URL = "http://tcthecontractor.in/";
    public static final String IMAGE_URL = "http://tcthecontractor.in/public/";
    public static final String ROOT_URL = MAIN_URL + "api/";
    public static final String STORE_CLIENT = "store_client";
    public static final String STORE_CONTRACTOR = "store_contractor";
    public static final String CHECK_CLIENT_LOGIN = "check_client_login";
    public static final String CHECK_CONREACTOR_LOGIN = "check_contractor_login";
    public static final String FORGOT_CLIENT_PASS_MOBILE = "forgot_client_pass_mobile";
    public static final String FORGOT_CLIENT_PASS_EMAIL = "forgot_client_pass_email";
    public static final String FORGOT_CONTRACTOR_PASS_MOBILE = "forgot_contractor_pass_mobile";
    public static final String FORGOT_CONTRACTOR_PASS_EMAIL = "forgot_contractor_pass_email";
    public static final String UPDATE_CLIENT_PASSWORD = "update_client_password";
    public static final String UPDATE_CONTRACTOR_PASSWORD = "update_contractor_password";
    public static final String SHOW_CONTRACTOR_BY_ID = "show_contractor_by_id";
    public static final String SAVE_CONTRACTOR_PROFILE_PICTURE = "save_contractor_profile_picture";
    public static final String UPDATE_CONTRACTOR = "update_contractor";
    public static final String UPDATE_CLIENT = "update_client";
    public static final String GET_CONTRACT_TYPE = "view_contract_type";
    public static final String GET_PROJECT_TYPE = "view_project_type";
    public static final String SHOW_CLIENT = "show_client";
    public static final String STORE_CLIENT_WITH_ID = "store_client_by_id";
    public static final String SAVE_PROJECT = "save_project";
    public static final String VIEW_ALL_PROJECTS_BY_CONTRACTOR_ID = "view_all_projects_by_contractor_id";
    public static final String STORE_OR_UPDATE_SCHEDULE = "store_or_update_schedule";
    public static final String GET_SCHEDULE_BY_PROJECT_ID = "get_schedule_by_project_id";
    public static final String GET_ACTIVITY_BY_PROJECT_ID = "get_activity_by_project_id";
    public static final String GET_ACTIVITY_BY_PROJECT_ID_ACT_ID = "get_activity_by_project_id_act_id";
    public static final String STORE_OR_UPDATE_ACTIVITY = "store_or_update_activity";
    public static final String STORE_SUB_CONTRACTOR = "store_sub_contractor";
    public static final String STORE_OR_UPDATE_SUB_ACTIVITY = "store_or_update_sub_activity";
    public static final String SHOW_SUB_CONTRACTOR_BY_CONTRACTOR_ID = "show_sub_contractor_by_contractor_id";
    public static final String VIEW_ALL_COMPLETED_PROJECTS_BY_CONTRACTOR_ID = "view_all_completed_projects_by_contractor_id";
    public static final String STORE_MATERIAL = "store_material";
    //akash lawande
    public static final String STORE_SUPPLIER = "create_supplier";
    public static final String STORE_MATERIALS = "create_material";

    public static final String GET_MATERIAL_BY_PROJECT_ID = "get_material_by_project_id";
    public static final String VIEW_ALL_PROJECTS_BY_CLIENT_ID = "view_all_projects_by_client_id";
    public static final String SHOW_SUB_CONTRACTORS = "show_sub_contractors";
    public static final String STORE_PROJECT_SUB_CONTRACTORS = "store_project_sub_contractors";
    public static final String SHOW_PROJECT_SUB_CONTRACTORS = "show_project_sub_contractors";
    public static final String SHOW_CLIENT_BY_ID = "show_client_by_id";
    public static final String GET_CLIENT_TODAY_ACTIVITY = "get_client_today_activity";
    public static final String GET_CONTRACTOR_TODAY_ACTIVITY = "get_contractor_today_activity";
    public static final String SAVE_CLIENT_PROFILE_PICTURE = "save_client_profile_picture";
    public static final String GET_UNIT = "view_unit";
    public static final String SHOW_WORK_ORDERS = "show_work_orders";
    public static final String SHOW_WORK_ORDERS_BY_PROJECT_ID = "show_work_orders_by_project_id";
    public static final String SHOW_BILL_BY_PROJECT_ID = "show_biling_data_by_project_id";
    public static final String ADD_WORK_ORDER_BY_PROJECT_ID = "store_work_order_by_project_id";
    public static final String ADD_BILL_BY_PROJECT_ID = "store_biling_by_project_id";
    public static final String ADD_CLIENT_BILL_BY_PROJECT_ID = "store_client_billing_by_project_id";
    public static final String FETCH_HOLIDAYS = "fetch_holidays";
    public static final String FETCH_HOLIDAYS_BY_PROJECT = "fetch_holidays_by_project";

    // added 19.10.2021
    public static final String SHOW_WORK_ORDERS_BY_SUB_CONTRACTOR_ID = "work_order_sub_contractor";
    public static final String SHOW_BILL_BY_SUB_CONTRACTOR_ID = "select_billing_sub_contratcor";
    public static final String SHOW_CLIENT_BILL_BY_SUB_CONTRACTOR_PROJECT_ID = "select_bill_pay_sub_contratcor";
    public static final String VIEW_ALL_SUB_CONTRACTOR_BY_CONTRACTOR_ID = "view_all_sub_contractor_by_contractor_id";
    public static final String ADD_BILL_BY_SUB_CONTRACTOR_PROJECT_ID = "store_billing_sub_contractcor";
    public static final String ADD_WORK_ORDER_BY_SUB_CONTRACTOR_PROJECT_ID = "store_work_order_sub_contractor";
    public static final String ADD_CLIENT_BILL_BY_SUB_CONTRACTOR_PROJECT_ID = "store_bill_pay_sub_contratcor";
    public static final String STORE_MATERIAL_PURCHASE = "purchase_material";
    public static final String GET_SUPPLER = "All_supplier";
    public static final String GET_MATERIALS = "all_material";

    public static final String GET_MATERIAL_PURCHASE_BY_PROJECT_ID = "select_material";
    public static final String STORE_MATERIAL_PURCHASE_PAYMENT = "payment_update";
}
