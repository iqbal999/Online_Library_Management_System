package com.example.simu.olms.model;

public class StudentLoginResponse {

    private boolean error;
    private String message;
    private StudentInfo user;

    public StudentLoginResponse(boolean error, String message, StudentInfo user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public StudentInfo getUser() {
        return user;
    }
}
