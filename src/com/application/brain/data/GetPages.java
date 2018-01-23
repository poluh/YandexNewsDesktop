package com.application.brain.data;

import com.application.news.News;
import javafx.scene.image.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPages {

    private static Document MAIN_PAGE = null;

    private static Document getPage(String link) throws IOException {
        if (MAIN_PAGE == null) {
            MAIN_PAGE = Jsoup.connect(link).userAgent("Safari").get();
        }
        return MAIN_PAGE;
    }

    public static List<Category> getListCategories(String link) throws Exception {
        List<Category> listCategories = new ArrayList<>();
        Elements categories = getPage(link).select("ul");

        for (String element : categories.toString().split("\n"))
            if (element.contains("tabs-menu__tab tabs-menu__tab_pos_")) {
                String categoryName = element.substring(element.indexOf("data-name=") + 10, element.indexOf("><")).replace("\"", "");
                String categoryLink = "https://news.yandex.ru" +
                        element.substring(element.indexOf("href=") + 6, element.indexOf("\" data-counter"));
                listCategories.add(new Category(categoryName, categoryLink));
            }

        return listCategories;
    }



    public static List<News> getListNews(String link) throws IOException {
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
        mainNews.setLink(MAIN_PAGE + mainNewsElm.selectFirst("h2 a").attr("href"));
        listNews.add(mainNews);

        for (Element blockNews : elements) {
            for (Element rowNews : blockNews.select(".stories-set__item")) {
                News news = new News();
                news.setTitle(rowNews.selectFirst("h2 a").text());
                news.setDate(rowNews.selectFirst(".story__date").text());
                news.setLink(MAIN_PAGE + rowNews.selectFirst("h2 a").attr("href"));
                news.setDescription("");

                String imgSrc = "";
                try {
                    imgSrc = rowNews.selectFirst(".image").attr("src");
                } catch (NullPointerException ignored) {}

                news.setImg(imgSrc);
                listNews.add(news);
            }
        }

        return listNews;
    }

    public static News getNews(String link) throws IOException {
        News news = new News();

        // TODO

        return news;
    }

    public static void main(String[] args) throws IOException {
        getListNews("https://news.yandex.ru/index.html?from=region");
    }
}
