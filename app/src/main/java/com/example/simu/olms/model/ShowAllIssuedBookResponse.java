package com.example.simu.olms.model;

public class ShowAllIssuedBookResponse {
    private String b_name;
    private String b_edition;
    private String author;
    private String issue_date;
    private String return_date;

    public ShowAllIssuedBookResponse(String b_name, String b_edition, String author, String issue_date, String return_date) {
        this.b_name = b_name;
        this.b_edition = b_edition;
        this.author = author;
        this.issue_date = issue_date;
        this.return_date = return_date;
    }

    public String getB_name() {
        return b_name;
    }

    public String getB_edition() {
        return b_edition;
    }

    public String getAuthorName() {
        return author;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public String getReturn_date() {
        return return_date;
    }
}
