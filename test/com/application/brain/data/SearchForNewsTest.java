package com.application.brain.data;

import org.junit.jupiter.api.Test;
import com.application.brain.data.SearchForNews.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchForNewsTest {
    @Test
    void search() {
    }

    @Test
    void endDelete() {
        assertEquals("навальн", SearchForNews.endDelete("навальный"));
        assertEquals("главн", SearchForNews.endDelete("главное"));
        assertEquals("астраханск", SearchForNews.endDelete("астраханский"));
        assertEquals("санкт-петербург", SearchForNews.endDelete("санкт-петербурге"));
    }

}