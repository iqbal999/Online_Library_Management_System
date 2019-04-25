package com.example.simu.olms.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.simu.olms.model.ShowAllRequest;
import com.example.simu.olms.model.DefaultResponse;
import com.example.simu.olms.model.StudentInfo;
import com.example.simu.olms.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request extends AppCompatActivity {

    SharedPreferences prefs;
    private AlertDialog.Builder builder;
    private StudentInfo studentInfo;
    private Call<List<ShowAllRequest>> call;
    private Call<DefaultResponse> issue_book_call;
    private Call<DefaultResponse> call_return_book;
    private Call<DefaultResponse> call_delete_req;
    String userType, request_type;
    Button btn_return_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        prefs = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, MODE_PRIVATE);
        userType = prefs.getString("user",null);

        studentInfo = SharedPrefManager.getInstance(this).getStudentInfo();
        showAllRequest();
    }

    private void showAllRequest() {
        call = RetrofitClient
                .getInstance()
                .getApi()
                .showAllRequest(studentInfo.getId());

        call.enqueue(new Callback<List<ShowAllRequest>>() {
            @Override
            public void onResponse(Call<List<ShowAllRequest>> call, Response<List<ShowAllRequest>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<ShowAllRequest>> call, Throwable t) {
                Toast.makeText(Request.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CustomAdapterShowRequest extends BaseAdapter {

        private List<ShowAllRequest> showAllRequests;
        private Context context;

        public CustomAdapterShowRequest(List<ShowAllRequest> showAllRequests, Context context) {
            this.showAllRequests = showAllRequests;
            this.context = context;
        }

        @Override
        public int getCount() {
            return showAllRequests.size();
        }

        @Override
        public Object getItem(int position) {
            return showAllRequests.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView tv_book_title, tv_author_name, tv_book_edition, tv_req_date, tv_req_type, tv_req_status;


            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_all_request,null);
            }

            tv_book_title = convertView.findViewById(R.id.tv_b_title);
            tv_book_edition = convertView.findViewById(R.id.tv_b_edition);
            tv_author_name = convertView.findViewById(R.id.tv_a_name);
            tv_req_date = convertView.findViewById(R.id.tv_request_date);
            tv_req_type = convertView.findViewById(R.id.tv_request_type);
            tv_req_status = convertView.findViewById(R.id.tv_request_status);

            btn_return_book = convertView.findViewById(R.id.button_return_book);

            final ShowAllRequest allRequest = showAllRequests.get(position);

            tv_book_title.setText("Book Title : "+allRequest.getB_name());
            tv_book_edition.setText("Edition : "+allRequest.getB_edition());
            tv_author_name.setText("Author : "+allRequest.getAuthor());
            tv_req_date.setText("Request Date : "+allRequest.getReq_date());
            tv_req_type.setText("Request Type : "+allRequest.getReq_type());
            //tv_req_status.setText("Request Status : "+allRequest.getApprove_status());

            if(allRequest.getReq_type().equals("Issue Book")){
                btn_return_book.setText(allRequest.getReq_type());
            }else if(allRequest.getReq_type().equals("Return Book")){
                btn_return_book.setText(allRequest.getReq_type());
            }

            if(allRequest.getApprove_status().equals("No")){
                tv_req_status.setTextColor(Color.parseColor("#FFFF0015"));
                tv_req_status.setText("Approve Status : "+allRequest.getApprove_status());
                btn_return_book.setEnabled(false);
            }else if(allRequest.getApprove_status().equals("Yes")){
                tv_req_status.setTextColor(Color.parseColor("#ffbb00"));
                tv_req_status.setText("Approve Status : "+allRequest.getApprove_status());
            }

            //request_type = btn_return_book.getText().toString();
            btn_return_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        builder = new AlertDialog.Builder(Request.this);
                        builder.setMessage("Do you want to issue this book?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        issueBook(allRequest.getBook_id());
                                        deleteRequest(allRequest.getReq_id());
                                        showAllRequest();
                                        //listView.removeViewAt(position);

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

    private void deleteRequest(String req_id) {

        call_delete_req = RetrofitClient
                            .getInstance()
                            .getApi()
                            .delRequest(req_id);

        call_delete_req.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(Request.this, "Request Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

    private void issueBook(final String book_id) {
        //Log.d("AAA",userType);

        if(userType.equals("Student")){
            issue_book_call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .issueBook(studentInfo.getId(), Integer.parseInt(book_id));
        }else if(userType.equals("Faculty")){
            issue_book_call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .issueBookFaculty(studentInfo.getId(), Integer.parseInt(book_id));
        }

        issue_book_call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                Toast.makeText(Request.this, dr.getMsg(), Toast.LENGTH_SHORT).show();
                btn_return_book.setEnabled(false);
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(Request.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ListView listView;
    private CustomAdapterShowRequest adapter;

    private void populateListView (List<ShowAllRequest> showBooksList){
        listView = findViewById(R.id.list_view_all_request);
        adapter = new CustomAdapterShowRequest(showBooksList,this);
        listView.setAdapter(adapter);

    }
}
