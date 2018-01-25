package com.application.news;

import com.application.brain.data.Citation;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class News {

    private String title, description, agency, date, link, imgO;
    private List<String> img;
    private Citation citation;

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

    public static List<News> divideAndRule(List<News> newsList) {
        List<News> newsImg = new ArrayList<>();
        List<News> newsNotImg = new ArrayList<>();
        for (News news : newsList) {
            if (news.getImgO().isEmpty()) {
                newsNotImg.add(news);
            } else newsImg.add(news);
        }
        newsImg.addAll(newsNotImg);
        return newsImg;
    }


    @Override
    public String toString() {
        return title + "\n" + description + "\n" + agency + " : " + date;
    }

    public Citation getCitation() {
        return citation;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }
}
