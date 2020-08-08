package com.example.project.User;

public class User {
    private String id;
    private String fname;
    private String lname;
    private String dateofbirth;
    private String gender;
    private int mobile;
    private String email;
    private int usertype; //1-manager 2-admin 3-user

    public User(String id) {
        this.id = id;
    }

    public User(String id, String fname, String lname, String dateofbirth, String gender, int mobile, String email, int usertype) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.usertype = usertype;
    }

    public String getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public int getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public int getUsertype() {
        return usertype;
    }
}
