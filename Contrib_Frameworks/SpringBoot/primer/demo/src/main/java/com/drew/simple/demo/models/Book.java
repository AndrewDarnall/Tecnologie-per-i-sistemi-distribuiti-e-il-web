package com.drew.simple.demo.models;

public class Book {
 
    private long id;
    private String title;
    private String author;
    private String isbn;
    private int rating;
    private int year;
    private double price;

    Book() {}

    public Book(String title, String author, int rating, double price) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.price = price;
    }


    // Command and Query methods
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
// This is what we are modelling