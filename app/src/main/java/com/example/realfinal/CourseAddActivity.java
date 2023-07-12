package com.example.realfinal;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CourseAddActivity extends AppCompatActivity {
    private String courseTitle;
    private String courseProfessor;
    private String courseTime;
    private String courseRoom;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);

        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText professorText = (EditText) findViewById(R.id.professorText);
        final EditText timeText = (EditText) findViewById(R.id.timeText);
        final EditText roomText = (EditText) findViewById(R.id.roomText);

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String courseTitle = titleText.getText().toString();
                if(validate){
                    return;
                }
                if(courseTitle.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseAddActivity.this);
                    dialog = builder.setMessage("강의 제목은 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(CourseAddActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 강의 제목입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                titleText.setEnabled(false);
                                validate = true;
                                titleText.setBackgroundColor(getResources().getColor((R.color.colorgGray)));
                                validateButton.setBackgroundColor(getResources().getColor((R.color.colorgGray)));
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CourseAddActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 강의제목입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                CourseValidateRequest courseValidateRequest = new CourseValidateRequest(courseTitle, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CourseAddActivity.this);
                queue.add(courseValidateRequest);
            }
        });
        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = titleText.getText().toString();
                String courseProfessor = professorText.getText().toString();
                String courseTime = timeText.getText().toString();
                String courseRoom = roomText.toString();
                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseAddActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(CourseAddActivity.this);
                                dialog = builder.setMessage("강의 등록이 완료되었습니다. " +
                                                "메인화면으로 돌아가시려면 뒤로 버튼을 눌러주세요")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CourseAddActivity.this);
                                dialog = builder.setMessage("강의 등록에 실패했습니다. ")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                CourseAddRequest registerRequest = new CourseAddRequest(courseTitle,courseProfessor,courseTime,courseRoom,responseListener);
                RequestQueue queue = Volley.newRequestQueue(CourseAddActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}