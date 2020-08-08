package com.example.project.Books;

public class Book {
    private String id;
    private String imageUrl;
    private String BookTitle;
    private String BookAuthor;
    private String YearPublished;
    private double Price;
    private String Quantity;
    private double rating;
    private String Category;
    private String BookDescription;
    private String userid;
    private String bothid;

    public Book(String bookTitle, double price) {
        BookTitle = bookTitle;
        Price = price;
    }

    public Book(String id, String userid, String bothid, String bookTitle, double price, String quantity) {
        this.id = id;
        BookTitle = bookTitle;
        Price = price;
        Quantity = quantity;
        this.userid=userid;
        this.bothid=bothid;
    }

    public Book(String id, String imageUrl, String bookTitle, String bookAuthor, String yearPublished, double price, String quantity, double rating, String category, String bookDescription) {
        this.id = id;
        this.imageUrl = imageUrl;
        BookTitle = bookTitle;
        BookAuthor = bookAuthor;
        YearPublished = yearPublished;
        Price = price;
        Quantity = quantity;
        this.rating = rating;
        Category = category;
        BookDescription = bookDescription;
    }

    public String getBothid() {
        return bothid;
    }

    public String getUserid() {
        return userid;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public String getYearPublished() {
        return YearPublished;
    }

    public double getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public double getRating() {
        return rating;
    }

    public String getCategory() {
        return Category;
    }

    public String getBookDescription() {
        return BookDescription;
    }
}
