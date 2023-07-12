package com.example.realfinal;




import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;
    private Activity parentActivity;
    private List<Course> saveList;



    public CourseListAdapter(Context context, List<Course> courseList, Activity parentActivity,List<Course> saveList){
        this.context = context;
        this.courseList = courseList;
        this.parentActivity = parentActivity;
        this.saveList = saveList;

    }
    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course,null);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        final TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        final TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        final TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseProfessor.setText(courseList.get(i).getCourseProfessor());
        courseTime.setText(courseList.get(i).getCourseTime());
        courseRoom.setText(courseList.get(i).getCourseRoom());

        v.setTag(courseList.get(i).getCourseTitle());

        Button coursedeleteButton = (Button) v.findViewById(R.id.coursedeleteButton);
        coursedeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                courseList.remove(i);
                                for(int i=0;i<saveList.size();i++) {
                                    if(saveList.get(i).getCourseTitle().equals(courseTitle.getText().
                                            toString())){
                                        saveList.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                CourseDeleteRequest coursedeleteRequest = new CourseDeleteRequest(courseTitle.getText().
                        toString(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(coursedeleteRequest);


            }

        });
        return v;
    }
}

