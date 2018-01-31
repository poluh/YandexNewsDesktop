package com.application.brain.data;

import com.application.brain.data.auxiliaryClasses.Category;
import com.application.news.News;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class GetPagesTest {

    private final static String LINK_TO_NEWS = "https://news.yandex.ru/yandsearch?lr=" +
            "68&cl4url=https%3A%2F%2Fwww.gazeta.ru%2Fscience%2F2018%2F01%2F31_a_11631889." +
            "shtml&lang=ru&stid=YlZwH7mYM_9xFUzBQ6J8&rubric=index&from=index";
    private final static String PATH_TO_NEWS = "test/res/NASANews.html";
    private final static String LINK_TO_MAIN_PAGE = "https://news.yandex.ru";
    private final static String PATH_TO_MAIN_HTML = "test/res/htmlNews.html";


    @Test
    void getListCategories() throws Exception {
        assertEquals(Arrays.asList(
                new Category("Главное", "https://news.yandex.ru/index.html?from=index"),
                new Category("Чита", "https://news.yandex.ru/Chita/index.html?from=index"),
                new Category("Политика", "https://news.yandex.ru/politics.html?from=index"),
                new Category("Общество", "https://news.yandex.ru/society.html?from=index"),
                new Category("Экономика", "https://news.yandex.ru/business.html?from=index"),
                new Category("В мире", "https://news.yandex.ru/world.html?from=index"),
                new Category("Спорт", "https://news.yandex.ru/sport.html?from=index"),
                new Category("Происшествия", "https://news.yandex.ru/incident.html?from=index"),
                new Category("Культура", "https://news.yandex.ru/culture.html?from=index"),
                new Category("Технологии", "https://news.yandex.ru/computers.html?from=index"),
                new Category("Наука", "https://news.yandex.ru/science.html?from=index"),
                new Category("Авто", "https://news.yandex.ru/auto.html?from=index")).toString(),
                GetPages.getListCategories(LINK_TO_MAIN_PAGE).toString());
    }

    @Test
    void getListNews() {
        assertEquals(Collections.singletonList(new News("Эксперты составили Топ-10 самых дешевых иномарок в России",
                        "Обновлено в 06:09")).toString(),
                GetPages.getListNews(PATH_TO_MAIN_HTML).toString());
    }

    @Test
    void getNews() {
        assertEquals(new News("Жители Земли наблюдают суперлуние",
                "В среду, 31 января, жители Земли станут свидетелями редчайшего астрономического явления —" +
                        " полного лунного затмения во время полнолуния и суперлуния.",
                        "RT на русском", "вчера в 21:13").toString(),
                GetPages.getNews(LINK_TO_NEWS).toString());
    }

    @Test
    void getInterestingNews() {
        assertEquals(Collections.emptyList(), GetPages.getInterestingNews(PATH_TO_NEWS));
    }

}