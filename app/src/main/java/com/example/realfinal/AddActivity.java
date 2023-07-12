package com.example.realfinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {


    private ListView listView;
    private AddListAdapter adapter;
    private List<Add> addList;
    private List<Add> saveList;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        addList = new ArrayList<Add>();
        saveList = new ArrayList<Add>();
        adapter = new AddListAdapter(getApplicationContext(),addList,this,saveList);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("addList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String courseTitle, courseProfessor, courseTime, courseRoom;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                courseTitle = object.getString("courseTitle");
                courseProfessor = object.getString("courseProfessor");
                courseTime = object.getString("courseTime");
                courseRoom = object.getString("courseRoom");
                Add course = new Add(courseTitle,courseProfessor,courseTime,courseRoom);
                System.out.println(courseTitle+" "+courseProfessor+ " "+courseTime + " "+  courseRoom);
                addList.add(course);
                saveList.add(course);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override // 텍스트가 바뀔때 마다
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void searchUser(String search){
        addList.clear();
        for(int i=0;i<saveList.size();i++){
            if(saveList.get(i).getCourseTitle().contains(search)){
                addList.add(saveList.get(i));
            }

        }
        adapter.notifyDataSetChanged();
    }


}