package com.example.project.Ratings;

public class Ratings {
    private String id;
    private String userid;
    private String bookid;
    private Float rate;
    private String comment;
    private Boolean status;

    public Ratings(String id,String userid, String bookid, Float rate, String comment, Boolean status) {
        this.id=id;
        this.userid = userid;
        this.bookid = bookid;
        this.rate = rate;
        this.comment = comment;
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public String getBookid() {
        return bookid;
    }

    public Float getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
