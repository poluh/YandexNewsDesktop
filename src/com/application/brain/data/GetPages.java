package com.applicaton.brain.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class GetPages {

    public static List<Element> getListCategories() throws IOException {
        Document mainPage = Jsoup.connect("https://news.yandex.ru").get();
        Elements rowElements = mainPage.select("a");
        return rowElements.subList(0, rowElements.size());
    }

}
