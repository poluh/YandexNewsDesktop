package com.application.brain.data;

import com.application.news.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPages {

    private static Document MAIN_PAGE = null;
    private static final String YANDEX = "https://news.yandex.ru";
    private static Button toBack = new Button("Назад");

    private static Document getPage(String link) throws IOException {
        if (MAIN_PAGE == null) {
            MAIN_PAGE = Jsoup.connect(link).userAgent("Safari").get();
        }
        return MAIN_PAGE;
    }

    public static List<Category> getListCategories(String link) throws Exception {
        List<Category> listCategories = new ArrayList<>();
        Elements categories = getPage(link).select("li");

        for (Element element : categories) {
            if (element.select("a").text() != null &&
                    element.select("a").attr("href") != null) {
                listCategories.add(new Category(element.attr("data-name"),
                        "https://news.yandex.ru" + element.select("a").attr("href")));
            }
        }
        return listCategories.subList(1, listCategories.size());
    }


    public static List<News> getListNews(String link) throws Exception {
        List<News> listNews = new ArrayList<>();

        Elements elements = Jsoup
                .connect(link)
                .userAgent("Safari")
                .get()
                .select(".page-content .page-content__cell");

        Element mainNewsElm = elements.select(".stories-set__main-item").first();
        News mainNews = new News();
        mainNews.setTitle(mainNewsElm.selectFirst("h2 a").text());
        mainNews.setDescription(mainNewsElm.selectFirst(".story__text").text());
        mainNews.setImg(mainNewsElm.selectFirst(".image").attr("src"));
        mainNews.setDate(mainNewsElm.selectFirst(".story__date").text());
        mainNews.setLink(YANDEX + mainNewsElm.selectFirst(".story__topic h2 a").attr("href"));
        listNews.add(mainNews);

        for (Element blockNews : elements) {
            for (Element rowNews : blockNews.select(".stories-set__item")) {
                News news = new News();
                news.setTitle(rowNews.selectFirst("h2 a").text());
                news.setDate(rowNews.selectFirst(".story__date").text());
                news.setLink(YANDEX + rowNews.selectFirst(".story__topic h2 a").attr("href"));
                news.setDescription("");

                String imgSrc = "";
                try {
                    imgSrc = rowNews.selectFirst(".image").attr("src");
                } catch (NullPointerException ignored) {
                }

                news.setImg(imgSrc);
                listNews.add(news);
            }
        }

        return listNews;
    }

    private static Citation getCitation(Element citationElements) {
        Citation citation = new Citation();
        citation.setImage("https:" + citationElements.selectFirst(".citation__left .image").attr("src"));
        citation.setText(citationElements.selectFirst(".citation__right .citation__content").text());
        citation.setInfo(citationElements.select(".citation__right .citation__info a").text());
        return citation;
    }

    public static News getNews(String link) throws IOException {
        News news = new News();

        Element rowNews = Jsoup
                .connect(link)
                .userAgent("Safari")
                .get()
                .selectFirst(".story__annot");

        List<String> media = new ArrayList<>();
        for (Element element : rowNews.select(".story-media__item")) {
            media.add(element.selectFirst(".image").attr("src"));
        }
        news.setImg(media);
        news.setTitle(rowNews.selectFirst(".story__head").text());
        news.setDescription(rowNews.selectFirst(".story__main .doc__text").text());
        news.setAgency(rowNews.selectFirst(".story__main .doc__content a .doc__info .doc__agency").text());
        news.setDate(rowNews.selectFirst(".story__main .doc__content a .doc__info .doc__time").text());

        if (rowNews.selectFirst(".citation") != null) {
            news.setCitation(getCitation(rowNews.selectFirst(".citation")));
        }

        return news;
    }

    public static void main(String[] args) throws Exception {
        getListNews("https://news.yandex.ru");
    }
}
