package com.example.simu.olms.model;

public class ShowIssueBooksResponse {

    private String sr;
    private String b_name;
    private String b_edition;
    private String author;
    private String issue_date;

    public ShowIssueBooksResponse(String sr, String b_name, String b_edition, String author, String issue_date) {
        this.sr = sr;
        this.b_name = b_name;
        this.b_edition = b_edition;
        this.author = author;
        this.issue_date = issue_date;
    }

    public String getSr() {
        return sr;
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

    public String getIssue_date() {
        return issue_date;
    }
}
