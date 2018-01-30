package com.application.brain.data;

import com.application.brain.data.auxiliaryClasses.Category;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GetPagesTest {
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
                GetPages.getListCategories("https://news.yandex.ru").toString());
    }

    @Test
    void getListNews() {
    }

    @Test
    void getNews() {
    }

    @Test
    void getInterestingNews() {
    }

    @Test
    void main() {
    }

}