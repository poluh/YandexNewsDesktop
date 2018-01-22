package com.application.brain.parse;

import com.application.brain.data.GetPages;
import com.application.news.News;
import javafx.scene.image.Image;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parse {

    public static List<String> parseCategories(String link) throws IOException {

        List<String> answer = new ArrayList<>();

        for (Element element : GetPages.getListCategories(link)) {
            String strElement = element.toString();
            try {
                String title = strElement.substring(strElement.indexOf("</div>"),
                        strElement.indexOf("</a>")).replace("</div>", "");
                String links = "https://news.yandex.ru/" + strElement.substring(strElement.indexOf("alias="),
                        strElement.indexOf(",-parentPos")).replace("alias=", "")
                        + ".html";
                answer.add(title + "::" + links);

            } catch (StringIndexOutOfBoundsException ignored) {
            }
        }
        return answer;
    }

    public static List<String> parseListNews(String link) throws IOException {

        List<String> answer = new ArrayList<>();

        for (Element element : GetPages.getListNews(link)) {
                String strElement = element.toString();
                String title = strElement.substring(strElement.lastIndexOf("\">") + 2, strElement.lastIndexOf("</a>"));
                String links = "https://news.yandex.ru" + strElement.substring(strElement.indexOf("href=") + 6,
                        strElement.indexOf("\" data-counter=")).replace("amp;", "");
                answer.add(title + "::" + links);
            }
        return answer;
    }

    public static News parseNews(String link) throws IOException {
        News news = new News();

        List<Image> imgList = new ArrayList<>();
        for (Element element : GetPages.getNews(link)) {
            String elemStr;
            if (element != null) {
                elemStr = element.toString();
            } else elemStr = "";
            if (elemStr.contains("https://") && elemStr.contains("width")) {
                System.out.println(elemStr.substring(elemStr.indexOf("src=\"") + 5, elemStr.indexOf("\" alt")));
                imgList.add(new Image(elemStr.substring(elemStr.indexOf("src=\"") + 5, elemStr.indexOf("\" alt"))));
            } else if (elemStr.contains("story__head")) {
                news.setTitle(elemStr.substring(elemStr.indexOf(">") + 1, elemStr.lastIndexOf("<")));
            } else if (elemStr.contains("doc__text")) {
                news.setDescription(elemStr.substring(elemStr.indexOf(">") + 1, elemStr.lastIndexOf("<")));
            } else if (elemStr.contains("doc__agency")) {
                news.setAgency(elemStr.substring(elemStr.indexOf(">") + 1, elemStr.lastIndexOf("<")));
            } else if (elemStr.contains("doc__time")) {
                news.setDate(elemStr.substring(elemStr.indexOf(">") + 1, elemStr.lastIndexOf("<")));
            }
        }
        news.setImg(imgList);

        System.out.println(news);

        return news;
    }

    public static void main(String[] args) throws IOException {
//        parseNews("https://news.yandex.ru/yandsearch?lr=68&cl4url=http%3A%2F%2Ftass.ru%2Fmezhdunarodnaya-panorama%2F4890594&lang=ru&stid=r_6hpt2zg7Ovq8g_wk_J&rubric=index&from=index");
    }

}
