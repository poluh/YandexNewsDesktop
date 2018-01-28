package com.application.brain.data;

import com.application.App;
import com.application.news.News;

import java.util.ArrayList;
import java.util.List;

public class SearchForNews {

    private static final List<News> allNews = App.allNews;

    public static List<News> search(String string) {
        List<News> answer = new ArrayList<>();
        for (News news : allNews) {
            if (news.getTitle().toLowerCase().contains(string.toLowerCase()) ||
                    news.getDescription().toLowerCase().contains(string.toLowerCase())) {
                answer.add(news);
            }
        }
        return answer;
    }

}
