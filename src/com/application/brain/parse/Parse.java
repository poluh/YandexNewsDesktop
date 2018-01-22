package com.application.brain.parse;

import com.application.brain.data.GetPages;
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

    public static 

    public static void main(String[] args) throws IOException {
    }

}
