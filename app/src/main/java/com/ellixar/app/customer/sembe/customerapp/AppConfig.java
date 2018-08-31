package com.ellixar.app.customer.sembe.customerapp;

/**
 * Created by Munyingi Ian on 5/1/2018.
 */

public class AppConfig {

    //static String baseUrl = "192.168.43.31";
    private static String baseUrl = "http://198.58.110.50";

    public static String URL_LOGIN = ""+baseUrl+"/api/auth/login";

    // Server user register url
    //public static String URL_REGISTER = "http://v1/index.php.aimdevelopers.com/register";
    public static String URL_REGISTER = ""+baseUrl+"/api/auth/register";

    public static String URL_REGISTER_EMPLOYEES = ""+baseUrl+"/pos/api/v1/index.php/register_employees";

    public static String URL_ADD_SALES = ""+baseUrl+"/pos/api/v1/index.php/add_sales";

    public static String URL_ADD_CUSTOMER = ""+baseUrl+"/pos/api/v1/index.php/add_customers";

    public static String URL_ADD_PRODUCTS = ""+baseUrl+"/pos/api/v1/index.php/add_products";


}
