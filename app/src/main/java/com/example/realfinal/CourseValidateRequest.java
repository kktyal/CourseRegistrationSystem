package com.example.realfinal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseValidateRequest extends StringRequest {

    final static private String URL = "https://kktyal.cafe24.com/FCourseValidate.php";
    private Map<String, String> parameters;

    public CourseValidateRequest(String courseTitle, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("courseTitle",courseTitle);


    }

    @Override
    public Map<String, String>getParams(){
        return parameters;
    }
}
