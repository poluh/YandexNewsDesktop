package com.application.brain.data;

import com.application.news.News;
import javafx.scene.image.Image;
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

    private static List<String> getTitleDescriptionImage(String html) {
        List<String> answer = new ArrayList<>();

        String storyTag = html.contains("story__title") ? "story__title" : "story__topic";

        String rowTitle = html.substring(html.indexOf(storyTag),
                html.indexOf("/a></h2>"));
        answer.add((rowTitle.substring(rowTitle.lastIndexOf(">") + 1, rowTitle.lastIndexOf("<"))));

        answer.add(html.substring(html.indexOf("story__text\">") + 14,
                html.indexOf("</div>\n" + "      </div>")).trim());
        if (html.contains("image")) {
            answer.add(html.substring(html.indexOf("src=\"") + 5,
                    html.indexOf("\" alt=")));
        }

        return answer;
    }

    public static List<News> getListNews(String link) throws IOException {
        List<News> listNews = new ArrayList<>();

        String elements = Jsoup.connect(link).get().select(".page-content__fixed").toString();
        String rowMainNews = elements.substring(elements.indexOf("stories-set__main-item"), elements.indexOf("table"));
        String rowTableNews = elements.substring(elements.indexOf("<tbody>"), elements.indexOf("</tbody>"));

        News mainNews = new News();
        List<String> getInfo = getTitleDescriptionImage(rowMainNews);

        mainNews.setTitle(getInfo.get(0));
        mainNews.setDescription(getInfo.get(1));
        mainNews.setDate(rowMainNews.substring(rowMainNews.indexOf("story__date\">") + 14,
                rowMainNews.indexOf("</div>\n       <span")).trim().replace("&nbsp;", " "));
        //mainNews.setImg(new Image(getInfo.get(2)));

        listNews.add(mainNews);
        System.out.println(rowMainNews);

        for (String news : rowTableNews.split("<td class=\"stories-set__item\"")) {

            try {

                List<String> newGetInfo = getTitleDescriptionImage(news);
                News fixedNews = new News();

                fixedNews.setTitle(newGetInfo.get(0));
                fixedNews.setDate(rowTableNews.substring(rowTableNews.indexOf("story__date") + 14,
                        rowTableNews.indexOf("</div>\n        <span")).replace("&nbsp;", " "));

                //if (newGetInfo.size() == 3) {
                //    fixedNews.setImg(new Image(getInfo.get(2)));
                //}

                listNews.add(fixedNews);

            } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ignored) {
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
