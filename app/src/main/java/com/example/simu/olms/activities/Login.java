package com.example.simu.olms.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simu.olms.R;
import com.example.simu.olms.api.RetrofitClient;
import com.example.simu.olms.model.StudentLoginResponse;
import com.example.simu.olms.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText editText_id, editText_password;
    private String id, book_name, book_edition, author_name, avail_copies, shelf, pos, pdf, flag;
    private Spinner login_type;
    private String login_type_text;
    Call<StudentLoginResponse> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        editText_id = findViewById(R.id.edit_text_id);
        editText_password = findViewById(R.id.edit_text_password);

        login_type = findViewById(R.id.select_login_type);
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.login_type, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        login_type.setAdapter(adapter);
        login_type.setOnItemSelectedListener(this);

        Intent i = this.getIntent();
        id = i.getStringExtra("id");
        book_name = i.getStringExtra("book");
        book_edition = i.getStringExtra("edition");
        author_name = i.getStringExtra("author");
        avail_copies = i.getStringExtra("ava_cop");
        shelf = i.getStringExtra("shelf");
        pos = i.getStringExtra("pos");
        pdf = i.getStringExtra("pdf");
        flag = i.getStringExtra("flag");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                stuLogin();
                break;

            case R.id.textViewRegister:
                startActivity(new Intent(this, StudentSignup.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, Dashboard.class);
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

        if (!login_type_text.equals("Select Login Type")) {
            if(login_type_text.equals("Student")){

                call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .stuLogin(stu_id, password);

                call.enqueue(new Callback<StudentLoginResponse>() {
                    @Override
                    public void onResponse(Call<StudentLoginResponse> call, Response<StudentLoginResponse> response) {
                        StudentLoginResponse studentLoginResponse = response.body();

                        if (!studentLoginResponse.isError()) {

                            SharedPrefManager.getInstance(Login.this)
                                    .saveUser(studentLoginResponse.getUser());

                            SharedPrefManager.getInstance(Login.this)
                                    .userType(login_type_text);

                            Intent intent = new Intent(Login.this, Dashboard.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        } else {
                            Toast.makeText(Login.this, studentLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentLoginResponse> call, Throwable t) {

                    }
                });

            }else if(login_type_text.equals("Faculty")){
                call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .facLogin(stu_id, password);

                call.enqueue(new Callback<StudentLoginResponse>() {
                    @Override
                    public void onResponse(Call<StudentLoginResponse> call, Response<StudentLoginResponse> response) {
                        StudentLoginResponse studentLoginResponse = response.body();

                        if (!studentLoginResponse.isError()) {

                            SharedPrefManager.getInstance(Login.this)
                                    .saveUser(studentLoginResponse.getUser());

                            SharedPrefManager.getInstance(Login.this)
                                    .userType(login_type_text);


                            Intent intent = new Intent(Login.this, Dashboard.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        } else {
                            Toast.makeText(Login.this, studentLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentLoginResponse> call, Throwable t) {

                    }
                });
            }

        }else{
            Toast.makeText(this, "Please select a login type", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDetailsActivity(String[] data) {
        Intent intent = new Intent(this, BookDetails.class);
        intent.putExtra("id", data[0]);
        intent.putExtra("book", data[1]);
        intent.putExtra("edition", data[2]);
        intent.putExtra("author", data[3]);
        intent.putExtra("ava_cop", data[4]);
        intent.putExtra("shelf", data[5]);
        intent.putExtra("pos", data[6]);
        intent.putExtra("pdf", data[7]);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        login_type_text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
