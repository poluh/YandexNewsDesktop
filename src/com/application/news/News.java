package com.application.news;

import javafx.scene.image.Image;

import java.util.List;

public class News {

    private String title, description, agency, date;
    private List<Image> img;
    private Image imgO;

    public News(String title, String description, String agency, String date, List<Image> img) {
        this.title = title;
        this.description = description;
        this.agency = agency;
        this.date = date;
        this.img = img;
    }

    public News(String title, String description, String agency, String date, Image imgO) {
        this.title = title;
        this.description = description;
        this.agency = agency;
        this.date = date;
        this.imgO = imgO;
    }

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setImg(Image imgO) {
        this.imgO = imgO;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + agency + " : " + date;
    }
}
