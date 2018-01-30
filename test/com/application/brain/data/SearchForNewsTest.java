package com.application.brain.data;

import com.application.news.News;
import org.junit.jupiter.api.Test;
import com.application.brain.data.SearchForNews.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchForNewsTest {

    private static News news = new News("Погода нынче не очень.", "Ожидаются дожди из мемесов",
            "Мемоград", "0.0.0.0", "mem.mem", "");
    private static News news2 = new News("Погодные условия пропадут очень скоро!",
            "Мемологие говорят, что мемы скоро покинут Санкт-Петербург", "Мемоград", "0.0.0.0",
            "weather.mem", "");
    private static News news3 = new News("Ученые доказали! Код компилируется с первого раза, нужно лишь...",
            "ТЫКТЫКТЫКТЫК", "ТвойГрад",
            "0.0.0.0", "vkontikti.cam", "");
    private static News news4 = new News("Неожиданный инцидент в Санкт-Петербурге!",
            "Петербуржец убил программиста, что поддерживал код до него! Поговорка \"пиши код так" +
                    "будто его будет поддерживать психопат, что знает, где ты живешь\" оказалась" +
                    "правдой!", "Фонтанка",
            "0.0.0.0", "mem.mem", "");
    private static List<News> newsList = Arrays.asList(news, news2, news3, news4);


    // for compile a tests replace a line in tested file "List<News> allNews <...>"
    @Test
    void search() {
        assertEquals(2, SearchForNews.search(newsList, "ПоГоДа").size());
        assertEquals(2, SearchForNews.search(newsList, "Петербург").size());
        assertEquals(1, SearchForNews.search(newsList, "УЧЕНЫЙ").size());
    }

    @Test
    void endDelete() {
        assertEquals("навальн", SearchForNews.endDelete("навальный"));
        assertEquals("главн", SearchForNews.endDelete("главное"));
        assertEquals("астраханск", SearchForNews.endDelete("астраханский"));
        assertEquals("санкт-петербург", SearchForNews.endDelete("санкт-петербурге"));
    }

}