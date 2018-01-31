package com.application.brain.data;

import com.application.brain.data.auxiliaryClasses.Category;
import com.application.brain.data.auxiliaryClasses.Citation;
import com.application.news.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetPages {


    private static final String YANDEX = "https://news.yandex.ru";
    private static Document CATEGORY_PAGE = null;

    private static Document getCategoryPage(String link)  {
        if (CATEGORY_PAGE == null) {
            CATEGORY_PAGE = getPage(link);
        }
        return CATEGORY_PAGE;
    }

    private static Document getPage(String path) {
        try {
            Document answer;
            if (path.contains("https")) {
                answer = Jsoup.connect(path).userAgent("Safari").get();
            } else {
                File inputHrml = new File(path);
                answer = Jsoup.parse(inputHrml, "UTF-8");
            }
            return answer;
        } catch (IllegalArgumentException | IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static List<Category> getListCategories(String path) {
        List<Category> listCategories = new ArrayList<>();
        Elements categories = getCategoryPage(path).select("li");

        for (Element element : categories) {
            if (element.select("a").text() != null &&
                    element.select("a").attr("href") != null) {
                listCategories.add(new Category(element.attr("data-name"),
                        "https://news.yandex.ru" + element.select("a").attr("href")));
            }
        }
        return listCategories.subList(1, listCategories.size());
    }


    public static List<News> getListNews(String path) {
        List<News> listNews = new ArrayList<>();

        Elements elements = getPage(path).select(".page-content .page-content__cell");
        Elements mainNewsElm = elements.select(".stories-set__main-item");

        boolean isMainNews = true;

        for (Element blockNews : elements) {
            Elements elementsAtBlock = blockNews.select(".stories-set__item");
            if (isMainNews) {
                isMainNews = false;
                elementsAtBlock = mainNewsElm;
            }
            for (Element rowNews : elementsAtBlock) {

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

    public static News getNews(String path) {
        News news = new News();

        Element rowNews = getPage(path).selectFirst(".story__annot");

        try {
            List<String> media = new ArrayList<>();
            for (Element element : rowNews.select(".story-media__item")) {
                media.add(element.selectFirst(".image").attr("src"));
            }
            news.setImg(media);
        } catch (NullPointerException ignored) {
        }
        news.setTitle(rowNews.selectFirst(".story__head").text());
        news.setDescription(rowNews.selectFirst(".story__main .doc__text").text());
        news.setAgency(rowNews.selectFirst(".story__main .doc__content a .doc__info .doc__agency").text());
        news.setDate(rowNews.selectFirst(".story__main .doc__content a .doc__info .doc__time").text());
        news.setOriginalLink(rowNews.selectFirst(".doc__content a").attr("href"));

        news.setLink(path);

        if (rowNews.selectFirst(".citation") != null) {
            news.setCitation(getCitation(rowNews.selectFirst(".citation")));
        }

        return news;
    }

    public static List<News> getInterestingNews(String path) {
        List<News> answer = new ArrayList<>();
        Elements rowNews = getPage(path).select(".widget__items .widget__item");
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

    public static void main(String[] args) {
    }
}
