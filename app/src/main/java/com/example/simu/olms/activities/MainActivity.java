package com.example.simu.olms.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.simu.olms.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView cardView_login, cardView_signup, cardView_searchBookByTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView_login= findViewById(R.id.cardView_login);
        cardView_signup= findViewById(R.id.cardView_signup);
        cardView_searchBookByTitle = findViewById(R.id.cardView_search_book_by_title);

        cardView_login.setOnClickListener(this);
        cardView_signup.setOnClickListener(this);
        cardView_searchBookByTitle.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView_login:
                startActivity(new Intent(MainActivity.this,StudentLogin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case R.id.cardView_signup:
                startActivity(new Intent(MainActivity.this,StudentSignup.class));
                break;
            case R.id.cardView_search_book_by_title:
                startActivity(new Intent(MainActivity.this,SearchBook.class));
                break;

        }
    }
}
