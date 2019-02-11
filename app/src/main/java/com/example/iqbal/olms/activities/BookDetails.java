package com.example.iqbal.olms.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iqbal.olms.R;

public class BookDetails extends AppCompatActivity {

    TextView tv_book_name, tv_author_name, tv_avail_copies, tv_shelf, tv_position;
    Button pdf_download;
    String base_url = "http://192.168.1.5/library/upload/";
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        tv_book_name = findViewById(R.id.tv_book_name);
        tv_author_name = findViewById(R.id.tv_author_name);
        tv_avail_copies = findViewById(R.id.tv_aval_copies);
        tv_shelf = findViewById(R.id.tv_shelf_no);
        tv_position = findViewById(R.id.tv_book_position);
        pdf_download = findViewById(R.id.pdf_download);


        // Received data from intent

        Intent i = this.getIntent();
        String id = i.getStringExtra("id");
        String book_name = i.getStringExtra("book");
        String author_name = i.getStringExtra("author");
        String avail_copies = i.getStringExtra("ava_cop");
        String shelf = i.getStringExtra("shelf");
        String pos = i.getStringExtra("pos");
        final String pdf = i.getStringExtra("pdf");

        // Show data to text view

        tv_book_name.setText("Book Name: "+book_name);
        tv_author_name.setText("Author Name: "+author_name);
        tv_avail_copies.setText("Available copies: "+avail_copies);
        tv_shelf.setText("Shelf No: "+shelf);
        tv_position.setText("Book Positon: "+pos);
        //tv_pdf.setText("PDF: "+pdf);

        pdf_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                //Uri uri = Uri.parse(base_url+pdf);
                Uri uri = Uri.parse("http://library.rajwebhost.com/upload/Introduction to Automata Theory Languages and Computation.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.allowScanningByMediaScanner();
                request.setMimeType("application/pdf");
                Long reference = downloadManager.enqueue(request);
            }
        });


    }
}


