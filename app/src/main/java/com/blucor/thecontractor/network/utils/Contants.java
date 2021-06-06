package com.blucor.thecontractor.network.utils;

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
    public static final String SHOW_CLIENT_BY_CONTRACTOR_ID = "show_client_by_contractor_id";
    public static final String STORE_CLIENT_WITH_ID = "store_client_by_id";
    public static final String SAVE_PROJECT = "save_project";
    public static final String VIEW_ALL_PROJECTS_BY_CONTRACTOR_ID = "view_all_projects_by_contractor_id";
    public static final String STORE_OR_UPDATE_SCHEDULE = "store_or_update_schedule";
    public static final String GET_SCHEDULE_BY_PROJECT_ID = "get_schedule_by_project_id";
}
