package com.example.simu.olms.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.simu.olms.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String CHANNEL_ID = "olms";
    public static final String CHANNEL_NAME = "olms";
    public static final String CHANNEL_DESC = "online library";

    CardView cardView_login, cardView_signup, cardView_searchBookByTitle, cardView_faculty_signup,
            cardView_search_book_by_author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("test");

        cardView_login= findViewById(R.id.cardView_login);
        cardView_signup= findViewById(R.id.cardView_signup);
        cardView_searchBookByTitle = findViewById(R.id.cardView_search_book_by_title);
        cardView_faculty_signup = findViewById(R.id.cardView_faculty_signup);
        cardView_search_book_by_author = findViewById(R.id.cardView_search_book_by_author);

        cardView_login.setOnClickListener(this);
        cardView_signup.setOnClickListener(this);
        cardView_searchBookByTitle.setOnClickListener(this);
        cardView_faculty_signup.setOnClickListener(this);
        cardView_search_book_by_author.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView_login:
                startActivity(new Intent(MainActivity.this,Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case R.id.cardView_signup:
                startActivity(new Intent(MainActivity.this,StudentSignup.class));
                break;
            case R.id.cardView_search_book_by_title:
                startActivity(new Intent(MainActivity.this,SearchBook.class).putExtra("search_by","title"));
                break;
            case R.id.cardView_faculty_signup:
                startActivity(new Intent(MainActivity.this, FacultySignUp.class));
                break;
            case R.id.cardView_search_book_by_author:
                startActivity(new Intent(MainActivity.this, SearchBook.class).putExtra("search_by","author"));
                break;


        }
    }
}
