package com.example.simu.olms.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simu.olms.R;
import com.example.simu.olms.api.RetrofitClient;
import com.example.simu.olms.model.SearchBooksResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



        btn_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                showBookList();
        }
    }

    private void showBookList() {
        String book_name = editTextSearch.getText().toString().trim();

        if(book_name.isEmpty()){
            editTextSearch.setError("Book name is required");
            editTextSearch.requestFocus();
            return;
        }

        /* Do api call for show book list */

        Call<List<SearchBooksResponse>> call = RetrofitClient
                .getInstance()
                .getApi()
                .searchBookByName(book_name);

        call.enqueue(new Callback<List<SearchBooksResponse>>() {
            @Override
            public void onResponse(Call<List<SearchBooksResponse>> call, Response<List<SearchBooksResponse>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<SearchBooksResponse>> call, Throwable t) {
                Toast.makeText(SearchBook.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    class CustomAdapter extends BaseAdapter{

        private List<SearchBooksResponse> searchBooksResponses;
        private Context context;

        public CustomAdapter(Context context, List<SearchBooksResponse> searchBooksResponses) {
            this.searchBooksResponses = searchBooksResponses;
            this.context = context;
        }


        @Override
        public int getCount() {
            return searchBooksResponses.size();
        }

        @Override
        public Object getItem(int position) {
            return searchBooksResponses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_book_title, tv_author_name, tv_book_edition;
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item,null);
            }

            tv_book_title = convertView.findViewById(R.id.tv_book_title);
            tv_author_name = convertView.findViewById(R.id.tv_author_name);
            tv_book_edition = convertView.findViewById(R.id.tv_book_edition);

            final SearchBooksResponse searchBooks = searchBooksResponses.get(position);

            tv_book_title.setText(searchBooks.getBook_name());
            tv_author_name.setText(searchBooks.getAuthor_name());
            tv_book_edition.setText(searchBooks.getEdition());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] all_info_of_books =  {

                            String.valueOf(searchBooks.getId()),
                            searchBooks.getBook_name(),
                            searchBooks.getEdition(),
                            searchBooks.getAuthor_name(),
                            String.valueOf(searchBooks.getAvailable_copies()),
                            searchBooks.getShelf(),
                            searchBooks.getPosition(),
                            searchBooks.getPdf_file_name()
                    };

                    openDetailsActivity(all_info_of_books);

                }
            });



            return convertView;
        }

        private void openDetailsActivity(String[] data) {
            Intent intent = new Intent(SearchBook.this, BookDetails.class);
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

    }



    private ListView listView;
    private CustomAdapter adapter;

    private void populateListView (List<SearchBooksResponse> booksList){
        listView = findViewById(R.id.mListView);
        adapter = new CustomAdapter(this, booksList);
        listView.setAdapter(adapter);

    }


}
