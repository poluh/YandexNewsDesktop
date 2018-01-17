package com.applicaton.brain.parse;

import com.applicaton.brain.data.GetPages;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseCategories {

    public static List<String> parseCategories() throws IOException {

        List<String> answer = new ArrayList<String>();

        for (Element element : GetPages.getListCategories()) {
            String strElement = element.toString();
            try {
                String title = strElement.substring(strElement.indexOf("</div>"),
                        strElement.indexOf("</a>")).replace("</div>", "");
                String link = "https://news.yandex.ru/" + strElement.substring(strElement.indexOf("alias="),
                        strElement.indexOf(",-parentPos")).replace("alias=", "")
                        + ".html";
                answer.add(title + "::" + link);

            } catch (StringIndexOutOfBoundsException ignored) {
            }
        }
        return answer;
    }

}
