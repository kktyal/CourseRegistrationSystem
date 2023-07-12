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

public class IdManagementActivity extends AppCompatActivity {


    private ListView listView;
    private IdUserListAdapter adapter;
    private List<IdUser> userList;
    private List<IdUser> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_management);
        Intent intent = getIntent();

        listView = (ListView)findViewById(R.id.listView);
        userList = new ArrayList<IdUser>();
        saveList = new ArrayList<IdUser>();
        adapter = new IdUserListAdapter(getApplicationContext(),userList,this,saveList);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, userPassword, userName, userMajor;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userPassword = object.getString("userPassword");
                userName = object.getString("userName");
                userMajor = object.getString("userMajor");
                IdUser user = new IdUser(userID,userPassword,userName,userMajor);
                if(!userID.equals("admin")){
                    userList.add(user);
                    saveList.add(user);
                }
                count++;
            }

        }
        catch(Exception e){
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
        userList.clear();
        for(int i=0;i<saveList.size();i++){
            if(saveList.get(i).getUserID().contains(search)){
                userList.add(saveList.get(i));
            }

        }
        adapter.notifyDataSetChanged();
    }



}