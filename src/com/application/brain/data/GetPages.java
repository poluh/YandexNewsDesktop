package com.application.brain.data;

import com.application.news.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPages {

    private static Document MAIN_PAGE = null;

    private static Document getPage(String link) throws IOException {
        if (MAIN_PAGE == null) {
            MAIN_PAGE = Jsoup.connect(link).get();
        }
        return MAIN_PAGE;
    }

    public static List<String> getListCategories(String link) throws IOException {
        List<String> listCategories = new ArrayList<>();
        Elements categories = getPage(link).select("ul");

        for (String element : categories.toString().split("\n"))
            if (element.contains("tabs-menu__tab tabs-menu__tab_pos_")) {
                String catName = element.substring(element.indexOf("data-name=") + 10, element.indexOf("><"));
                String catLink = "https://news.yandex.ru" +
                        element.substring(element.indexOf("href=") + 6, element.indexOf("\" data-counter"));
                listCategories.add((catName + "::" + catLink).replace("\"", ""));
            }

        return listCategories;
    }

    public static List<News> getListNews(String link) throws IOException {
        List<News> listNews = new ArrayList<>();

        String elements = Jsoup.connect(link).get().select(".page-content__fixed").toString();
        String rowMainNews = elements.substring(elements.indexOf("stories-set__main-item"), elements.indexOf("table"));
        String rowTableNews = elements.substring(elements.indexOf("<tbody>"), elements.indexOf("</tbody>"));

        News mainNews = new News();
        String rowTitle = rowMainNews.substring(rowMainNews.indexOf("story__title"),
                rowMainNews.indexOf("/a></h2>"));

        mainNews.setTitle(rowTitle.substring(rowTitle.lastIndexOf(">") + 1, rowTitle.lastIndexOf("<")));
        mainNews.setDescription(rowMainNews.substring(rowMainNews.indexOf("story__text\">") + 14,
                rowMainNews.indexOf("</div>\n" + "      </div>")).trim());
        mainNews.setDate(rowMainNews.substring(rowMainNews.indexOf("story__date\">") + 14,
                rowMainNews.indexOf("</div>\n       <span")).trim());
        /*mainNews.setImg(new Image(rowMainNews.substring(rowMainNews.indexOf("src=\"") + 5,
                rowMainNews.indexOf("\" alt="))));*/

        listNews.add(mainNews);

        for (String news : rowTableNews.split("<td class=\"stories-set__item\"")) {

            try {

                News fixedNews = new News();

                // TODO

                listNews.add(fixedNews);

            } catch (ArrayIndexOutOfBoundsException ignored) {
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
        getListNews("https://news.yandex.ru/society.html?from=rubric");
    }
}
