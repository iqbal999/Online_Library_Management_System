package com.example.iqbal.olms.model;

public class StudentInfo {

    String id, name, dob, phone, email;

    public StudentInfo(String id, String name, String dob, String phone, String email) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
