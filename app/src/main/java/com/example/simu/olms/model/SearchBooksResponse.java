package com.example.simu.olms.model;

import com.google.gson.annotations.SerializedName;

public class SearchBooksResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("b_name")
    private String book_name;

    @SerializedName("b_edition")
    private String edition;

    @SerializedName("a_name")
    private String author_name;

    @SerializedName("aval_copies")
    private int available_copies;

    private String shelf;

    private String position;

    private String pdf_file_name;

    public SearchBooksResponse(int id, String book_name, String edition, String author_name, int available_copies, String shelf, String position, String pdf_file_name) {
        this.id = id;
        this.book_name = book_name;
        this.edition = edition;
        this.author_name = author_name;
        this.available_copies = available_copies;
        this.shelf = shelf;
        this.position = position;
        this.pdf_file_name = pdf_file_name;
    }

    public int getId() {
        return id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public int getAvailable_copies() {
        return available_copies;
    }

    public String getShelf() {
        return shelf;
    }

    public String getPosition() {
        return position;
    }

    public String getPdf_file_name() {
        return pdf_file_name;
    }

    public String getEdition() {
        return edition;
    }
}
