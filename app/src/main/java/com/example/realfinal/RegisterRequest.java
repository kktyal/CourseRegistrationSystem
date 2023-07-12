package com.example.realfinal;



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "https://kktyal.cafe24.com/FRegister.php";
    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userMajor,
                           Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userName",userName);
        parameters.put("userMajor",userMajor);

    }
    @Override
    public Map<String ,String> getParams(){
        return parameters;
    }

}
