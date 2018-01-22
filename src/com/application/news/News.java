package com.application.news;

import javafx.scene.image.Image;

import java.util.List;

public class News {

    private String title, description, agency, date;
    private List<Image> img;

    public News(String title, String description, String agency, String date, List<Image> img) {
        this.title = title;
        this.description = description;
        this.agency = agency;
        this.date = date;
        this.img = img;
    }

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public String setTitle(String title) {
        return this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Image> getImg() {
        return img;
    }

    public void setImg(List<Image> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + agency + " : " + date;
    }
}