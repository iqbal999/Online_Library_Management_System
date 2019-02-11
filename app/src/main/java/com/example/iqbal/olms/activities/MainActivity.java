package com.example.iqbal.olms.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iqbal.olms.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_search, btn_download, btn_issue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_search = findViewById(R.id.btn_search_book);
        btn_download = findViewById(R.id.btn_download_book);
        btn_issue = findViewById(R.id.btn_issue_book);

        btn_search.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_issue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search_book:
                startActivity(new Intent(MainActivity.this,SearchBook.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case R.id.btn_download_book:
                // do something
                break;
            case R.id.btn_issue_book:
                startActivity(new Intent(MainActivity.this,StudentLogin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
        }
    }
}
