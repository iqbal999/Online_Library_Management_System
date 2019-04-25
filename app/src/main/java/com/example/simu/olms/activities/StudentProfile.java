package com.example.simu.olms.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.simu.olms.R;
import com.example.simu.olms.model.StudentInfo;
import com.example.simu.olms.storage.SharedPrefManager;

public class StudentProfile extends AppCompatActivity implements View.OnClickListener{

    TextView tv_name, tv_dob, tv_phone, tv_email;
    Button btn_logout;
    SharedPreferences prefs;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        prefs = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, MODE_PRIVATE);
        userType = prefs.getString("user",null);

        tv_name = findViewById(R.id.tv_name);
        tv_dob = findViewById(R.id.tv_dob);
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);

        btn_logout = findViewById(R.id.btn_logout);

        StudentInfo stu_info = SharedPrefManager.getInstance(this).getStudentInfo();
        tv_name.setText("Name: "+ stu_info.getName());
        tv_dob.setText("Birth Date: "+stu_info.getDob());
        tv_phone.setText("Phone: "+stu_info.getPhone());
        tv_email.setText("Email: "+stu_info.getEmail());

        btn_logout.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,StudentSignup.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPrefManager.getInstance(getApplicationContext()).clear();
        startActivity(new Intent(StudentProfile.this,Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
