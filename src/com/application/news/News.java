package com.application.news;

import com.application.brain.data.auxiliaryClasses.Citation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public static List<News> divideAndRule(List<News> newsList) {
        List<News> newsImg = new ArrayList<>();
        List<News> newsNotImg = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (News news : newsList) {
            if (!titles.contains(news.getTitle())) {
                if (news.getImgO().isEmpty()) {
                    newsNotImg.add(news);
                } else newsImg.add(news);
                titles.add(news.getTitle());
            }
        }
        newsImg.addAll(newsNotImg);
        return newsImg;
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

    public void setImg(String imgO) {
        this.imgO = imgO;
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

    public Citation getCitation() {
        return citation;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }

    public int hashCode() {
        return title.hashCode() & date.hashCode() & 1234567890;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + agency + " : " + date;
    }
}
