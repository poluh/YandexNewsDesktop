package com.application.news;

import javafx.scene.image.Image;

import java.util.List;

public class News {

    private String title, description, agency, date, link;
    private List<String> img;
    private String imgO;

    public News(String title, String description, String agency, String date, String link, List<String> img) {
        this.title = title;
        this.description = description;
        this.agency = agency;
        this.date = date;
        this.link = link;
        this.img = img;
    }

    public News(String title, String description, String agency, String date, String link, String imgO) {
        this.title = title;
        this.description = description;
        this.agency = agency;
        this.date = date;
        this.link = link;
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

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getImgO() {
        return imgO;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImg(String imgO) {
        this.imgO = imgO;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + agency + " : " + date;
    }
}
