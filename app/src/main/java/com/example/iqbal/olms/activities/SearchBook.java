package com.example.iqbal.olms.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.iqbal.olms.R;

public class SearchBook extends AppCompatActivity implements View.OnClickListener{

    ImageButton btn_search;
    EditText editTextSearch;
    TextView tv_book_name, tv_author_name, tv_aval_copies, tv_shelf_no, tv_book_position, tv_pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        btn_search = findViewById(R.id.btn_search);
        editTextSearch = findViewById(R.id.edit_text_search);

        tv_book_name = findViewById(R.id.tv_book_name);
        tv_author_name = findViewById(R.id.tv_author_name);
        tv_aval_copies = findViewById(R.id.tv_aval_copies);
        tv_shelf_no = findViewById(R.id.tv_shelf_no);
        tv_book_position = findViewById(R.id.tv_book_position);
        tv_pdf = findViewById(R.id.tv_pdf);

        btn_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:

        }
    }
}
