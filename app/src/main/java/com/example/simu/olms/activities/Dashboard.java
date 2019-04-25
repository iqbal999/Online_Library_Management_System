package com.example.simu.olms.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.simu.olms.R;
import com.example.simu.olms.storage.SharedPrefManager;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView cv_profile, cv_logout, cv_issue_book, cv_return_book, cv_history,
                    cv_all_request, cv_all_return_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        cv_profile = findViewById(R.id.cardView_profile);
        cv_logout = findViewById(R.id.cardView_logout);
        cv_issue_book = findViewById(R.id.cardView_issue_book);
        cv_return_book = findViewById(R.id.cardView_return_book);
        cv_history = findViewById(R.id.cardView_history);
        cv_all_request = findViewById(R.id.cardView_all_request);
        cv_all_return_request = findViewById(R.id.cardView_return_request);

        cv_profile.setOnClickListener(this);
        cv_logout.setOnClickListener(this);
        cv_issue_book.setOnClickListener(this);
        cv_return_book.setOnClickListener(this);
        cv_history.setOnClickListener(this);
        cv_all_request.setOnClickListener(this);
        cv_all_return_request.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_profile:
                startActivity(new Intent(this, StudentProfile.class));
                break;
            case R.id.cardView_logout:
                logout();
                break;
            case R.id.cardView_issue_book:
                startActivity(new Intent(this, SearchBook.class));
                break;
            case R.id.cardView_return_book:
                startActivity(new Intent(this, ShowIssuedBooks.class));
                break;
            case R.id.cardView_history:
                startActivity(new Intent(this, ShowAllIssuedBooksOfUser.class));
                break;
            case R.id.cardView_all_request:
                startActivity(new Intent(this, Request.class));
                break;
            case R.id.cardView_return_request:
                startActivity(new Intent(this, AllReturnRequest.class));
                break;
        }
    }

    private void logout() {
        SharedPrefManager.getInstance(getApplicationContext()).clear();
        startActivity(new Intent(this,Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
