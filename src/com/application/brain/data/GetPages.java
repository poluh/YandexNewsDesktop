package com.application.brain.data;

import com.application.brain.data.auxiliaryClasses.Category;
import com.application.brain.data.auxiliaryClasses.Citation;
import com.application.news.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPages {


    private static final String YANDEX = "https://news.yandex.ru";
    private static Document CATEGORY_PAGE = null;

    private static Document getCategoryPage(String link) throws IOException {
        if (CATEGORY_PAGE == null) {
            CATEGORY_PAGE = getPage(link);
        }
        return CATEGORY_PAGE;
    }

    private static Document getPage(String link) throws IOException {
        try {
            return Jsoup.connect(link).userAgent("Safari").get();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static List<Category> getListCategories(String link) throws Exception {
        List<Category> listCategories = new ArrayList<>();
        Elements categories = getCategoryPage(link).select("li");

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

        Elements elements = getPage(link).select(".page-content .page-content__cell");

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

        Element rowNews = getPage(link).selectFirst(".story__annot");

        List<String> media = new ArrayList<>();
        for (Element element : rowNews.select(".story-media__item")) {
            media.add(element.selectFirst(".image").attr("src"));
        }
        news.setImg(media);
        news.setTitle(rowNews.selectFirst(".story__head").text());
        news.setDescription(rowNews.selectFirst(".story__main .doc__text").text());
        news.setAgency(rowNews.selectFirst(".story__main .doc__content a .doc__info .doc__agency").text());
        news.setDate(rowNews.selectFirst(".story__main .doc__content a .doc__info .doc__time").text());
        news.setLink(link);

        if (rowNews.selectFirst(".citation") != null) {
            news.setCitation(getCitation(rowNews.selectFirst(".citation")));
        }

        return news;
    }

    public static List<News> getInterestingNews(String link) throws IOException {
        List<News> answer = new ArrayList<>();
        Elements rowNews = getPage(link).select(".widget__items .widget__item");
        try {
            for (Element element : rowNews) {
                News news = new News();
                news.setTitle(element.selectFirst(".story__title").text());
                news.setDate(element.selectFirst(".story__date").text().replace("&nbsp;", " "));
                news.setLink(YANDEX + element.selectFirst("a").attr("href"));
                answer.add(news);
            }
        } catch (NullPointerException ignored) {
        }
        return answer;
    }

    public static void main(String[] args) throws Exception {
    }
}
