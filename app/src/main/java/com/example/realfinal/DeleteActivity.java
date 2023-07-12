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

public class DeleteActivity extends AppCompatActivity {

    private ListView courselistView;
    private DeleteCourseListAdapter adapter;
    private List<Course> courseList;
    private List<Course> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Intent intent = getIntent();

        courselistView = (ListView) findViewById(R.id.courselistView);
        courseList = new ArrayList<Course>();
        saveList = new ArrayList<Course>();
        adapter = new DeleteCourseListAdapter(getApplicationContext(),courseList,this,saveList);
        courselistView.setAdapter(adapter);
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
                System.out.println(courseTitle+" "+courseProfessor+ " "+courseTime + " "+  courseRoom);
                courseList.add(course);
                saveList.add(course);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override // 텍스트가 바뀔때 마다
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
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