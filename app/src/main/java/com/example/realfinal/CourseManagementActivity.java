package com.example.realfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseManagementActivity extends AppCompatActivity {
    private ListView listView;
    private CourseListAdapter adapter;
    private List<Course> courseList;
    private List<Course> saveList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_management);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        courseList = new ArrayList<Course>();
        saveList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getApplicationContext(),courseList, this,saveList);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("courseList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String courseTitle, courseProfessor, courseTime, courseRoom;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                courseTitle = object.getString("courseTitle");
                courseProfessor = object.getString("courseProfessor");
                courseTime = object.getString("courseTime");
                courseRoom = object.getString("courseRoom");
                Course course = new Course(courseTitle,courseProfessor,courseTime,courseRoom);

                courseList.add(course);
                saveList.add(course);
                count++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override // 텍스트가 바뀔때 마다
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    public void searchUser(String search){
        courseList.clear();
        for(int i=0;i<saveList.size();i++){
            if(saveList.get(i).getCourseTitle().contains(search)){
                courseList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}