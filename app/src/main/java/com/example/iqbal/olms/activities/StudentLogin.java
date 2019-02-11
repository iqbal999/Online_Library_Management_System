package com.example.iqbal.olms.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iqbal.olms.R;
import com.example.iqbal.olms.api.RetrofitClient;
import com.example.iqbal.olms.model.DefaultResponse;
import com.example.iqbal.olms.model.StudentLoginResponse;
import com.example.iqbal.olms.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLogin extends AppCompatActivity implements View.OnClickListener{

    EditText editText_id, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        editText_id = findViewById(R.id.edit_text_id);
        editText_password = findViewById(R.id.edit_text_password);

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                stuLogin();
                break;

            case R.id.textViewRegister:
                startActivity(new Intent(this,StudentSignup.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,StudentProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    private void stuLogin() {
        String stu_id = editText_id.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (stu_id.isEmpty()) {
            editText_id.setError("Id is required");
            editText_id.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            editText_password.setError("Password is required");
            editText_password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editText_password.setError("Password should be atleast 6 character long");
            editText_password.requestFocus();
            return;
        }

        /* Do user login using API call*/
        Call<StudentLoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .stuLogin(stu_id,password);

        call.enqueue(new Callback<StudentLoginResponse>() {
            @Override
            public void onResponse(Call<StudentLoginResponse> call, Response<StudentLoginResponse> response) {
                StudentLoginResponse studentLoginResponse = response.body();

                if(!studentLoginResponse.isError()){
                    // save user
                    // open profile
                    //Toast.makeText(StudentLogin.this, studentLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    SharedPrefManager.getInstance(StudentLogin.this)
                            .saveUser(studentLoginResponse.getUser());
                    Intent intent = new Intent(StudentLogin.this,StudentProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(StudentLogin.this, studentLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StudentLoginResponse> call, Throwable t) {

            }
        });
    }
}
