package com.example.realfinal;



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends StringRequest {

    final static private String URL = "https://kktyal.cafe24.com/FCourseAdd.php";
    private Map<String,String> parameters;

    public AddRequest(String userID, String courseTitle, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("courseTitle",courseTitle);
        System.out.println(userID + " "+ courseTitle);
    }
    @Override
    public Map<String ,String> getParams(){
        return parameters;
    }

}
