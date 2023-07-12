package com.example.realfinal;




import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class DeleteCourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;
    private Activity parentActivity;
    private List<Course> saveList;
    private String userID = MainActivity.userID;


    public DeleteCourseListAdapter(Context context, List<Course> courseList, Activity parentActivity,List<Course> saveList){
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
        View v = View.inflate(context, R.layout.delete,null);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        final TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        final TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        final TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseProfessor.setText(courseList.get(i).getCourseProfessor());
        courseTime.setText(courseList.get(i).getCourseTime());
        courseRoom.setText(courseList.get(i).getCourseRoom());

        v.setTag(courseList.get(i).getCourseTitle());

        Button deletButton = (Button) v.findViewById(R.id.RegisteredCoursedeleteButton);
        deletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                AlertDialog dialog = builder.setMessage("수강취소에 성공했습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                courseList.remove(i);
                                for(int j =0;j<saveList.size();j++)
                                {
                                    if(saveList.get(i).getCourseTitle().equals(courseTitle.getText().toString()))
                                    {
                                        saveList.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                AlertDialog dialog = builder.setMessage("수강철회에 실패했습니다.")
                                        .setNegativeButton("다시 시도",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID,courseList.get(i).getCourseTitle(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });
        return v;
    }
}

