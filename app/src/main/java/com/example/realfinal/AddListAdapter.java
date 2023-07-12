package com.example.realfinal;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AddListAdapter extends BaseAdapter {
    private Context context;
    private List<Add> addList;
    private List<Add> saveList;
    private Activity parentActivity;
    private Schedule schedule = new Schedule();
    private List<String> courseIDList;
    private String userID = MainActivity.userID;

    public AddListAdapter(Context context, List<Add> addList,Activity parentActivity,List<Add> saveList){
        this.context = context;
        this.addList = addList;
        this.parentActivity = parentActivity;
        this.saveList = saveList;
        schedule = new Schedule();
        courseIDList = new ArrayList<String>();
        new BackgroundTask().execute();
    }
    @Override
    public int getCount() {
        return addList.size();
    }
    @Override
    public Object getItem(int i) {
        return addList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView( int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.add,null);  //한명의 사용자에 대한 뷰
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        final TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        final TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        final TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);

        courseTitle.setText(addList.get(i).getCourseTitle());
        courseProfessor.setText(addList.get(i).getCourseProfessor());
        courseTime.setText(addList.get(i).getCourseTime());
        courseRoom.setText(addList.get(i).getCourseRoom());
        v.setTag(addList.get(i).getCourseTitle());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validate = false;
                validate = schedule.validate(addList.get(i).getCourseTime());

                if (!alreadyIn(courseIDList, addList.get(i).getCourseTitle()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("다시 시도",null)
                            .create();
                    dialog.show();
                }
                else if(validate == false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                    AlertDialog dialog = builder.setMessage("시간표가 중복되었습니다.")
                            .setPositiveButton("다시시도",null)
                            .create();
                    dialog.show();
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                    AlertDialog dialog = builder.setMessage("강의수강에 성공했습니다.")
                                            .setPositiveButton("확인",null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(addList.get(i).getCourseTitle());
                                    schedule.addSchedule(addList.get(i).getCourseTitle());

                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                    builder.setMessage("강의수강에 실패했습니다.")
                                            .setNegativeButton("다시 시도",null)
                                            .create()
                                            .show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    AddRequest addRequest = new AddRequest(userID,addList.get(i).getCourseTitle(),responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parentActivity);
                    queue.add(addRequest);
                }


            }
        });
        return v;
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;
        String userID = MainActivity.userID;
        @Override
        protected void onPreExecute(){
            try
            {target = "https://kktyal.cafe24.com/FStaticsCourseList.php?userID=" +URLEncoder.encode(MainActivity.userID,"UTF-8");
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
                while(count<jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseTitle = object.getString("courseTitle");
                    courseTime = object.getString("courseTime");
                    courseProfessor = object.getString("courseProfessor");
                    System.out.println(courseTitle);
                    System.out.println(courseTime+" 123");
                    System.out.println(courseProfessor);

                    courseIDList.add(courseTitle);
                    schedule.addSchedule(courseTime);

                    count++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public boolean alreadyIn(List<String> courseIDList, String item){
        for(int i=0; i<courseIDList.size();i++){
            if(courseIDList.get(i)==item){
                return false;
            }
        }
        return true;
    }
}
