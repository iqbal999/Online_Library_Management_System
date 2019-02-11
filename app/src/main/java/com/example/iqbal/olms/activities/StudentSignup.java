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
import com.example.iqbal.olms.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentSignup extends AppCompatActivity implements View.OnClickListener {
    EditText editText_id, editText_dob, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        editText_id = findViewById(R.id.edit_text_id);
        editText_dob = findViewById(R.id.edit_text_dob);
        editText_password = findViewById(R.id.edit_text_password);

        findViewById(R.id.button_signup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_signup:
                userSignUp();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this,StudentLogin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    private void userSignUp() {
        String stu_id = editText_id.getText().toString().trim();
        String dob = editText_dob.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (stu_id.isEmpty()) {
            editText_id.setError("Id is required");
            editText_id.requestFocus();
            return;
        }

        if (dob.isEmpty()) {
            editText_dob.setError("Birth date is required");
            editText_dob.requestFocus();
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

        /* Do user registration using API call*/
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(stu_id, dob, password);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                //String s = null;
                DefaultResponse dr = response.body();
                Toast.makeText(StudentSignup.this, dr.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(StudentSignup.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
