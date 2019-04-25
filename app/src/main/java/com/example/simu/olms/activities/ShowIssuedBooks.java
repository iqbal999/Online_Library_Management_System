package com.example.simu.olms.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simu.olms.R;
import com.example.simu.olms.api.RetrofitClient;
import com.example.simu.olms.model.DefaultResponse;
import com.example.simu.olms.model.SearchBooksResponse;
import com.example.simu.olms.model.ShowIssueBooksResponse;
import com.example.simu.olms.model.StudentInfo;
import com.example.simu.olms.storage.SharedPrefManager;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowIssuedBooks extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private StudentInfo studentInfo;
    private Call<List<ShowIssueBooksResponse>> call;
    //private Call<DefaultResponse> call_return_book;
    private Call<DefaultResponse> call_return_request;

    SharedPreferences prefs;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_issued_books);

        prefs = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, MODE_PRIVATE);
        userType = prefs.getString("user",null);

        studentInfo = SharedPrefManager.getInstance(this).getStudentInfo();
        showIssuedBooks();

    }


    private void showIssuedBooks() {

        if(userType.equals("Student")){
            call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .showIssuedBooks(studentInfo.getId());

        }else if(userType.equals("Faculty")){
            call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .showIssuedBooksFaculty(studentInfo.getId());
        }


        call.enqueue(new Callback<List<ShowIssueBooksResponse>>() {
            @Override
            public void onResponse(Call<List<ShowIssueBooksResponse>> call, Response<List<ShowIssueBooksResponse>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<ShowIssueBooksResponse>> call, Throwable t) {
                Toast.makeText(ShowIssuedBooks.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    class CustomAdapterShowBook extends BaseAdapter {

        private List<ShowIssueBooksResponse> showIssueBooksResponses;
        private Context context;

        public CustomAdapterShowBook(List<ShowIssueBooksResponse> showIssueBooksResponses, Context context) {
            this.showIssueBooksResponses = showIssueBooksResponses;
            this.context = context;
        }


        @Override
        public int getCount() {
            return showIssueBooksResponses.size();
        }

        @Override
        public Object getItem(int position) {
            return showIssueBooksResponses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView tv_book_title, tv_author_name, tv_book_edition, tv_issue_date;
            Button btn_return_book, btn_return_request;

            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_issued_book,null);
            }

            tv_book_title = convertView.findViewById(R.id.tv_b_title);
            tv_book_edition = convertView.findViewById(R.id.tv_b_edition);
            tv_author_name = convertView.findViewById(R.id.tv_a_name);
            tv_issue_date = convertView.findViewById(R.id.tv_issue_date);

            //btn_return_book = convertView.findViewById(R.id.button_return_book);
            btn_return_request = convertView.findViewById(R.id.button_return_request);

            final ShowIssueBooksResponse showIssueBooks = showIssueBooksResponses.get(position);


            tv_book_title.setText("Book Title: "+showIssueBooks.getB_name());
            tv_book_edition.setText("Edition: "+showIssueBooks.getB_edition());
            tv_author_name.setText("Author Name: "+showIssueBooks.getAuthor());
            tv_issue_date.setText("Issued Date: "+showIssueBooks.getIssue_date());

//            btn_return_book.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    builder = new AlertDialog.Builder(ShowIssuedBooks.this);
//                    builder.setMessage("Do you want to return this book?")
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    returnBook(Integer.parseInt(showIssueBooks.getSr()));
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // User cancelled the dialog
//                                }
//                            });
//
//                    AlertDialog ad = builder.create();
//                    ad.show();
//
//                }
//            });

            btn_return_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(ShowIssuedBooks.this);
                    builder.setMessage("Do you want to request for return this book?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d("AAA", ""+showIssueBooks.getSr());
                                    requestForReturn(Integer.parseInt(showIssueBooks.getSr()));
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });

                    AlertDialog ad = builder.create();
                    ad.show();
                }
            });

            return convertView;
        }
    }

    private void requestForReturn(final int sr) {
        call_return_request = RetrofitClient
                .getInstance()
                .getApi()
                .requestForReturn(studentInfo.getId(), sr, "Return Book");

        call_return_request.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                Toast.makeText(ShowIssuedBooks.this, dr.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(ShowIssuedBooks.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private ListView listView;
    private CustomAdapterShowBook adapter;

    private void populateListView (List<ShowIssueBooksResponse> showBooksList){
        listView = findViewById(R.id.list_view_issue_book);
        adapter = new CustomAdapterShowBook(showBooksList,this);
        listView.setAdapter(adapter);

    }
}
