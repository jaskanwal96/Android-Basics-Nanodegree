package com.example.jaska.newsapp;

/**
 * Created by jaska on 22-Dec-17.
 */

public class News {
    private String sectionName;
    private String date;
    private String newsTitle;
    private String url;
    private String author;

    public News(String sectionName, String date, String newsTitle, String url, String author) {
        this.sectionName = sectionName;
        this.date = date;
        this.newsTitle = newsTitle;
        this.url = url;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDate() {
        return date;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getUrl() {
        return url;
    }
}
