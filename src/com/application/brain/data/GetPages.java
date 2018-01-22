package com.application.brain.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPages {

    private static Document mainPage = null;

    private static Document getPage(String link) throws IOException {
        if (mainPage == null) {
            mainPage = Jsoup.connect(link).get();
        }
        return mainPage;
    }

    public static List<Element> getListCategories(String link) throws IOException {
        Document mainPage = getPage(link);
        Elements rowElements = mainPage.select("a");
        return rowElements.subList(0, rowElements.size());
    }

    public static List<Element> getListNews(String link) throws IOException {
        Document mainPage = getPage(link);
        Elements links = mainPage.select("a[href]");
        List<Element> list = new ArrayList<>();

        for (Element element : links) {
            if (element.toString().contains("link link_theme_black link_ajax i-be") && !list.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    public static List<Element> getNews(String link) throws IOException {

        List<Element> answer = new ArrayList<>();

        Document newsPage = getPage(link);
        Elements rowElementsImg = newsPage.select("img");
        answer.addAll(rowElementsImg);
        Element rowTitle = newsPage.selectFirst("h1.story__head");
        Element rowDescription = newsPage.selectFirst("div.doc__text");
        Element rowAgency = newsPage.selectFirst("span.doc__agency");
        Element rowDate = newsPage.selectFirst("span.doc__time");
        answer.add(rowTitle);
        answer.add(rowDescription);
        answer.add(rowAgency);
        answer.add(rowDate);

        return answer;
    }

    public static void main(String[] args) throws IOException {
        getNews("https://news.yandex.ru/yandsearch?lr=2&cl4url=http%3A%2F%2Fwww.interfax.ru%2Fworld%2F595944&lang=ru&stid=RqtUyglu2IXQnLdjo9x8&rubric=index&from=index");
    }
}
