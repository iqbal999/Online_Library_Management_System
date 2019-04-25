package com.example.simu.olms.model;

public class ShowAllRequest {
    private String req_id;
    private String book_id;
    private String b_name;
    private String b_edition;
    private String author;
    private String req_date;
    private String req_type;
    private String approve_status;

    public ShowAllRequest(String req_id, String book_id, String b_name, String b_edition, String author, String req_date, String req_type,
                          String approve_status) {
        this.req_id = req_id;
        this.book_id = book_id;
        this.b_name = b_name;
        this.b_edition = b_edition;
        this.author = author;
        this.req_date = req_date;
        this.req_type = req_type;
        this.approve_status = approve_status;
    }

    public String getReq_id() {
        return req_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getB_name() {
        return b_name;
    }

    public String getB_edition() {
        return b_edition;
    }

    public String getAuthor() {
        return author;
    }

    public String getReq_date() {
        return req_date;
    }

    public String getReq_type() {
        return req_type;
    }

    public String getApprove_status() {
        return approve_status;
    }
}
