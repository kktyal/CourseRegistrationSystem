package com.example.realfinal;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "https://kktyal.cafe24.com/FLogin.php";
    private Map<String,String> parameters;

    public LoginRequest(String userID, String userPassword,  Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
    }
    @Override
    public Map<String ,String> getParams(){
        return parameters;
    }

}