package com.example.simu.olms.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simu.olms.R;
import com.example.simu.olms.api.RetrofitClient;
import com.example.simu.olms.model.ShowAllIssuedBookResponse;
import com.example.simu.olms.model.ShowIssueBooksResponse;
import com.example.simu.olms.model.StudentInfo;
import com.example.simu.olms.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllIssuedBooksOfUser extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private StudentInfo studentInfo;
    private ShowAllIssuedBookResponse allIssuedBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_issued_books_of_user);

        studentInfo = SharedPrefManager.getInstance(this).getStudentInfo();

        showAllIssuedBooks();
    }

    private void showAllIssuedBooks() {
        Call<List<ShowAllIssuedBookResponse>> call = RetrofitClient
                .getInstance()
                .getApi()
                .showAllIssuedBooks(studentInfo.getId());

        call.enqueue(new Callback<List<ShowAllIssuedBookResponse>>() {
            @Override
            public void onResponse(Call<List<ShowAllIssuedBookResponse>> call, Response<List<ShowAllIssuedBookResponse>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<ShowAllIssuedBookResponse>> call, Throwable t) {

            }
        });
    }

    class AllBooksListAdapter extends BaseAdapter{

        private List<ShowAllIssuedBookResponse> showAllIssuedBookResponses;
        private Context context;

        public AllBooksListAdapter(List<ShowAllIssuedBookResponse> showAllIssuedBookResponses, Context context) {
            this.showAllIssuedBookResponses = showAllIssuedBookResponses;
            this.context = context;
        }

        @Override
        public int getCount() {
            return showAllIssuedBookResponses.size();
        }

        @Override
        public Object getItem(int position) {
            return showAllIssuedBookResponses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_book_title, tv_author_name, tv_book_edition, tv_issue_date, tv_return_date;

            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_all_issued_book,null);
            }

            tv_book_title = convertView.findViewById(R.id.tv_b_title);
            tv_book_edition = convertView.findViewById(R.id.tv_b_edition);
            tv_author_name = convertView.findViewById(R.id.tv_a_name);
            tv_issue_date = convertView.findViewById(R.id.tv_issue_date);
            tv_return_date = convertView.findViewById(R.id.tv_return_date);

            allIssuedBook = showAllIssuedBookResponses.get(position);

            tv_book_title.setText("Book Title: "+ allIssuedBook.getB_name());
            tv_book_edition.setText("Edition: "+ allIssuedBook.getB_edition());
            tv_author_name.setText("Author: " + allIssuedBook.getAuthorName());
            tv_issue_date.setText("Issued Date: "+ allIssuedBook.getIssue_date());

            if(allIssuedBook.getReturn_date().equals("")){
                tv_return_date.setTextColor(Color.parseColor("#FFFF0015"));
                tv_return_date.setText("Retrun Date :Not Returned Yet");
            }else{
                tv_return_date.setTextColor(Color.parseColor("#ffbb00"));
                tv_return_date.setText("Retrun Date :"+ allIssuedBook.getReturn_date());
            }

            return convertView;
        }
    }

    private ListView listView;
    private AllBooksListAdapter adapter;
    private void populateListView(List<ShowAllIssuedBookResponse> allBookList) {
        listView = findViewById(R.id.list_view_all_issue_book);
        adapter = new AllBooksListAdapter(allBookList,this);
        listView.setAdapter(adapter);

    }
}
