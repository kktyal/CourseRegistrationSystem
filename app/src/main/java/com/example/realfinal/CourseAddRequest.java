package com.example.realfinal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseAddRequest extends StringRequest {

    final static private String URL = "https://kktyal.cafe24.com/FAddCourse.php";
    private Map<String,String> parameters;

    public CourseAddRequest(String courseTtile, String courseProfessor, String courseTime, String courseRoom,
                           Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("courseTitle",courseTtile);
        parameters.put("courseProfessor",courseProfessor);
        parameters.put("courseTime",courseTime);
        parameters.put("courseRoom",courseRoom);

    }
    @Override
    public Map<String ,String> getParams(){
        return parameters;
    }

}
