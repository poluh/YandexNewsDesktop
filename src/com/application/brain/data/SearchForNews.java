package com.application.brain.data;

import com.application.App;
import com.application.news.News;

import java.util.ArrayList;
import java.util.List;

public class SearchForNews {

    private static final List<News> allNews = App.allNews;
    //private static final List<News> allNews = new ArrayList<>();

    public static List<News> search(String string) {
        List<News> answer = new ArrayList<>();
        for (News news : allNews) {
            if (news.getTitle().toLowerCase().contains(endDelete(string.toLowerCase())) ||
                    news.getDescription().toLowerCase().contains(string.toLowerCase())) {
                answer.add(news);
            }
        }
        return answer;
    }

    static String endDelete(String string) {

        final String[] ends = {"ать", "ять", "уть", "ем", "ешь", "ете", "ой", "ёй", "ом", "ем", "яя", "ее", "ое", "ый",
                "ет", "ут", "ют", "ам", "а", "я", "ы", "и", "е", "у", "ю", "о", "ь"};
        if (string.length() > 3) {
            for (String end : ends) {
                if (string.substring(string.length() - 4, string.length()).contains(end)) {
                    return string.substring(0, string.lastIndexOf(end));
                }
            }
        }
        return string;
    }

}

