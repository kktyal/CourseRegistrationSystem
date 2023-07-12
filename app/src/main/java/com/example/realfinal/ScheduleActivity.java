package com.example.realfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ScheduleActivity extends AppCompatActivity {


    private TextView monday[] = new TextView[10];
    private TextView tuesday[] = new TextView[10];
    private TextView wednesday[] = new TextView[10];
    private TextView thursday[] = new TextView[10];
    private TextView friday[] = new TextView[10];
    private Schedule schedule = new Schedule();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        monday[1] = (TextView) findViewById(R.id.monday1);
        monday[2] = (TextView) findViewById(R.id.monday2);
        monday[3] = (TextView) findViewById(R.id.monday3);
        monday[4] = (TextView) findViewById(R.id.monday4);
        monday[5] = (TextView) findViewById(R.id.monday5);
        monday[6] = (TextView) findViewById(R.id.monday6);
        monday[7] = (TextView) findViewById(R.id.monday7);
        monday[8] = (TextView) findViewById(R.id.monday8);
        monday[9] = (TextView) findViewById(R.id.monday9);



        tuesday[1] = (TextView) findViewById(R.id.tuesday1);
        tuesday[2] = (TextView) findViewById(R.id.tuesday2);
        tuesday[3] = (TextView) findViewById(R.id.tuesday3);
        tuesday[4] = (TextView) findViewById(R.id.tuesday4);
        tuesday[5] = (TextView) findViewById(R.id.tuesday5);
        tuesday[6] = (TextView) findViewById(R.id.tuesday6);
        tuesday[7] = (TextView) findViewById(R.id.tuesday7);
        tuesday[8] = (TextView) findViewById(R.id.tuesday8);
        tuesday[9] = (TextView) findViewById(R.id.tuesday9);



        wednesday[1] = (TextView) findViewById(R.id.wednesday1);
        wednesday[2] = (TextView) findViewById(R.id.wednesday2);
        wednesday[3] = (TextView) findViewById(R.id.wednesday3);
        wednesday[4] = (TextView) findViewById(R.id.wednesday4);
        wednesday[5] = (TextView) findViewById(R.id.wednesday5);
        wednesday[6] = (TextView) findViewById(R.id.wednesday6);
        wednesday[7] = (TextView) findViewById(R.id.wednesday7);
        wednesday[8] = (TextView) findViewById(R.id.wednesday8);
        wednesday[9] = (TextView) findViewById(R.id.wednesday9);


        thursday[1] = (TextView) findViewById(R.id.thursday1);
        thursday[2] = (TextView) findViewById(R.id.thursday2);
        thursday[3] = (TextView) findViewById(R.id.thursday3);
        thursday[4] = (TextView) findViewById(R.id.thursday4);
        thursday[5] = (TextView) findViewById(R.id.thursday5);
        thursday[6] = (TextView) findViewById(R.id.thursday6);
        thursday[7] = (TextView) findViewById(R.id.thursday7);
        thursday[8] = (TextView) findViewById(R.id.thursday8);
        thursday[9] = (TextView) findViewById(R.id.thursday9);



        friday[1] = (TextView) findViewById(R.id.friday1);
        friday[2] = (TextView) findViewById(R.id.friday2);
        friday[3] = (TextView) findViewById(R.id.friday3);
        friday[4] = (TextView) findViewById(R.id.friday4);
        friday[5] = (TextView) findViewById(R.id.friday5);
        friday[6] = (TextView) findViewById(R.id.friday6);
        friday[7] = (TextView) findViewById(R.id.friday7);
        friday[8] = (TextView) findViewById(R.id.friday8);
        friday[9] = (TextView) findViewById(R.id.friday9);

        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;
        String userID = MainActivity.userID;
        @Override
        protected void onPreExecute(){
            try
            {target = "https://kktyal.cafe24.com/FStaticsCourseList.php?userID=" + URLEncoder.encode(MainActivity.userID,"UTF-8");
                //target = "https://kktyal.cafe24.com/FScheduleList.php?userID = " + URLEncoder.encode(userID,"UTF-8");
            }catch (Exception e){
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
                while((temp = bufferedReader.readLine())!=null){
                    stringBuilder.append(temp+"\n");
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
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){

            try{

                JSONObject jsonObject=  new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                String courseTitle;
                String courseTime;
                String courseProfessor;
                String courseRoom;
                while(count<jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseTitle = object.getString("courseTitle");
                    courseTime = object.getString("courseTime");
                    courseProfessor = object.getString("courseProfessor");
                    courseRoom = object.getString("courseRoom");
                    System.out.println(courseTitle);
                    System.out.println(courseTime+" 123");
                    System.out.println(courseProfessor);

                    schedule.addSchedule(courseTime,courseTitle,courseProfessor);

                    count++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            schedule.setting(monday, tuesday, wednesday, thursday, friday, getBaseContext());
        }
    }
}