package com.example.realfinal;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button idmanagementButton = (Button) findViewById(R.id.idmanagementButton);
        Button coursemanagementButton = (Button) findViewById(R.id.coursemanagementButton);
        Button faddButton = (Button) findViewById(R.id.addButton);
        Button fdeleteButton = (Button) findViewById(R.id.fdeleteButton);
        Button fcourseaddButton =(Button) findViewById(R.id.fcourseaddButton);
        Button scheduleButton =(Button) findViewById(R.id.scheduleButton);

        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);

        userID = getIntent().getStringExtra("userID");
        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);

        System.out.println(userID);
        if(!userID.equals("admin")) {
            idmanagementButton.setVisibility(View.GONE);
            coursemanagementButton.setVisibility(View.GONE);
            fcourseaddButton.setVisibility(View.GONE);
        }

        new BackgroundTask4().execute();

        coursemanagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask1().execute();
            }
        });
        idmanagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
        faddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask2().execute();
            }
        });
        fdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask3().execute();
            }
        });
        fcourseaddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent courseregisterIntent = new Intent(MainActivity.this,CourseAddActivity.class);
                MainActivity.this.startActivity(courseregisterIntent);
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);

                MainActivity.this.startActivity(intent);
            }
        });


    }


    class BackgroundTask4 extends AsyncTask<Void, Void, String>{

        String target;

        @Override
        protected void onPreExecute(){
            target = "https://kktyal.cafe24.com/FFNoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null){
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){

            try{

                JSONObject jsonObject=  new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                String noticeContent, noticeName, noticeDate;
                while(count<jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent,noticeName);
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    class BackgroundTask3 extends AsyncTask<Void,Void,String> {

        String target;
        @Override
        protected void onPreExecute(){
            try {
                target = "https://kktyal.cafe24.com/FStaticsCourseList.php?userID=" +
                        URLEncoder.encode(MainActivity.userID,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(stringBuilder);
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void ... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){

            Intent intent = new Intent(MainActivity.this,  DeleteActivity.class);
            intent.putExtra("courseList",result);
            MainActivity.this.startActivity(intent);
        }

    }
    class BackgroundTask2 extends AsyncTask<Void,Void,String> {

        String target;
        @Override //초기화
        protected void onPreExecute(){
            target = "https://kktyal.cafe24.com/FFList.php";
        }

        @Override //실질적으로 하는곳
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(stringBuilder);
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void ... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){

            Intent intent = new Intent(MainActivity.this,  AddActivity.class);
            intent.putExtra("addList",result);
            MainActivity.this.startActivity(intent);
        }

    }
    class BackgroundTask1 extends AsyncTask<Void,Void,String> {

        String target;
        @Override
        protected void onPreExecute(){
            target = "https://kktyal.cafe24.com/FFList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(stringBuilder);
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void ... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){

            Intent intent = new Intent(MainActivity.this,  CourseManagementActivity.class);
            intent.putExtra("courseList",result);
            MainActivity.this.startActivity(intent);
        }

    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String target;
        @Override
        protected void onPreExecute(){
            target = "https://kktyal.cafe24.com/FList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(stringBuilder);
                return stringBuilder.toString().trim();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void ... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, IdManagementActivity.class);
            intent.putExtra("userList",result);
            MainActivity.this.startActivity(intent);
        }
    }


}