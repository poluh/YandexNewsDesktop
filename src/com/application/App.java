package com.application;

import com.application.action.ActionEvents;
import com.application.brain.parse.Parse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static final String PATH_TO_STYLE = "style/standard.css";
    public static final String MAIN_PAGE = "https://news.yandex.ru/";
    private static Stage pPrimaryStage = null;

    private static GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        return gridPane;
    }

    public static Node createElementMainWindow() throws IOException {

        GridPane gridPane = createGrid();

        int i = 0, j = 0;
        for (String name : Parse.parseCategories(MAIN_PAGE)) {
            Button categories = new Button(name.split("::")[0]);
            categories.setMinSize(140, 140);
            categories.setId(name.split("::")[1]);
            ActionEvents.mainButton(categories);

            gridPane.add(categories, i, j);
            i++;
            if (i == 5) {
                j++;
                i = 0;
            }
        }
        Button hotNews = new Button("Горячие новости");
        hotNews.setMinSize(605, 140);
        hotNews.setId("hot");
        ActionEvents.mainButton(hotNews);

        gridPane.add(hotNews, 1, j, 3, 1);

        return gridPane;
    }

    private static Node createElementCategoriesWindow(String link) throws IOException {
        GridPane gridPane = createGrid();

        int i = 0;
        for (String name : Parse.parseListNews(link)) {
            Button hotTitle = new Button(name.split("::")[0]);
            hotTitle.setMinSize(550, 70);

            Button littleMore = new Button("Небольшие подробности");
            littleMore.setMinSize(140, 70);

            gridPane.add(hotTitle, 0, i);
            gridPane.add(littleMore, 1, i);
            i++;
        }

        return new ScrollPane(gridPane);
    }


    public static void createCategoriesWindow(String link) throws IOException {
        Scene scene = new Scene((Parent) createElementCategoriesWindow(link), 800, 500);
        scene.getStylesheets().add(PATH_TO_STYLE);

        pPrimaryStage.setScene(scene);
        pPrimaryStage.show();
    }

    private static void createMainWindow() throws IOException {

        Scene scene = new Scene((Parent) createElementMainWindow(), 800, 500);
        scene.getStylesheets().add(PATH_TO_STYLE);

        pPrimaryStage.setScene(scene);
        pPrimaryStage.show();
    }


    public void start(Stage primaryStage) throws Exception {
        pPrimaryStage = primaryStage;
        createMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
