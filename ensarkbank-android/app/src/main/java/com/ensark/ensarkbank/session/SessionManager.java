package com.ensark.ensarkbank.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.LoginResponse;
import com.google.gson.Gson;

public class SessionManager {

    private static final String PREF_NAME = "courier_pref";

    private static final String TOKEN = "token";
    private static final String USER = "user";
    private static final String CUSTOMER = "customer";

    private SharedPreferences preferences;

    private Gson gson = new Gson();

    public SessionManager(Context context){

        preferences =
                context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

    }

    //=========================
    // TOKEN
    //=========================

    public void saveToken(String token){

        preferences.edit().putString(TOKEN,token).apply();

    }

    public String getToken(){

        return preferences.getString(TOKEN,null);

    }

    //=========================
    // USER
    //=========================

    public void saveUser(LoginResponse user){

        preferences.edit()
                .putString(USER,gson.toJson(user))
                .apply();

    }

    public LoginResponse getUser(){

        String json=preferences.getString(USER,null);

        if(json==null)
            return null;

        return gson.fromJson(json,LoginResponse.class);

    }

    //=========================
    // CUSTOMER
    //=========================

    public void saveCustomer(CustomerResponse customer){

        preferences.edit()
                .putString(CUSTOMER,gson.toJson(customer))
                .apply();

    }

    public CustomerResponse getCustomer(){

        String json=preferences.getString(CUSTOMER,null);

        if(json==null)
            return null;

        return gson.fromJson(json,CustomerResponse.class);

    }

    public boolean isLoggedIn(){

        return getToken()!=null;

    }

    public void logout(){

        preferences.edit().clear().apply();

    }
}
