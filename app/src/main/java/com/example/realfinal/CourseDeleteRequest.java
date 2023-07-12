package com.example.realfinal;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseDeleteRequest extends StringRequest {
    final static private String URL = "https://kktyal.cafe24.com/FFDelete.php";
    private Map<String, String> parameters;

    public CourseDeleteRequest(String courseTitle, Response.Listener<String>listener){
        super(Request.Method.POST,URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("courseTitle",courseTitle);
    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
