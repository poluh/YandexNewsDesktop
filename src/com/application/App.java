package com.application;

import com.application.brain.data.auxiliaryClasses.Category;
import com.application.brain.data.auxiliaryClasses.Citation;
import com.application.news.News;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static com.application.action.ActionEvents.*;
import static com.application.brain.data.GetPages.*;

public class App extends Application {

    private static final String PATH_TO_STYLE = "style/standard.css";
    private static final String MAIN_PAGE = "https://news.yandex.ru/";
    private static Stage pPrimaryStage = null;
    private static Button toBack = new Button("Назад");
    private static List<Node> cacheBackWindow = new ArrayList<>();
    private static Map<String, Node> cacheCategoriesWindow = new HashMap<>();
    private static final GridPane mainGrid = new GridPane();
    public static List<News> allNews = new ArrayList<>();

    private static GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        return gridPane;
    }

    private static HBox createHBox(Node node, Pos pos) {
        HBox hBox = new HBox();
        hBox.getChildren().add(node);
        hBox.setAlignment(pos);
        hBox.setMinSize(400, 50);
        hBox.setPadding(new Insets(10));
        return hBox;
    }

    private static void createGridPaneOnVBox(GridPane gridPane) {
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setMinSize(1090, 650);
        scrollPane.setMaxSize(1090, 650);
        VBox vBox = new VBox();
        vBox.getChildren().add(scrollPane);
        cacheBackWindow.add(vBox);
        App.mainGrid.add(vBox, 1, 1);
    }

    private static void cachingCategories(List<Category> categories) {
        for (Category category : categories) {
            try {
                GridPane cacheGrid = createGrid();
                createCategoriesWindow(category.getLink(), cacheGrid);
                cacheCategoriesWindow.put(category.getLink(), cacheGrid.getChildren().get(0));
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    // Ease created horizontal box for the text object
    private static HBox createNewsComponent(Label label, boolean isMainNews) {
        HBox hBox = new HBox();
        hBox.setMaxWidth(isMainNews ? 550 : 270);
        hBox.getChildren().add(label);
        if (Objects.equals(label.getId(), "date")) hBox.setAlignment(Pos.BOTTOM_LEFT);
        return hBox;
    }

    private static Node createElementMainWindow() throws Exception {


        // Pane's for the categories and search, the toBack
        GridPane leftGrid = new GridPane();
        GridPane upGrid = new GridPane();

        leftGrid.getStyleClass().add("left-grid");
        leftGrid.setMinHeight(800);

        Label categoryLabel = new Label("Категории");
        categoryLabel.setId("categories");

        toBack.setVisible(false);
        toBack.setId("back");
        toBack(toBack, mainGrid, cacheBackWindow);

        TextField searchNews = new TextField();
        searchNews.setMinWidth(400);
        searchForNews(searchNews);

        upGrid.add(createHBox(categoryLabel, Pos.CENTER_LEFT), 0, 0);
        upGrid.add(createHBox(searchNews, Pos.CENTER), 1, 0);
        upGrid.add(createHBox(toBack, Pos.CENTER_RIGHT), 2, 0);
        mainGrid.add(upGrid, 0, 0, 4, 1);

        // Append categories on left-pane
        int i = 0;
        for (Category category : getListCategories(MAIN_PAGE)) {

            Button categoryButton = new Button(category.getName());
            categoryButton.setMinSize(140, 50);
            categoryButton.setId(category.getLink());
            categoriesButton(categoryButton, mainGrid, leftGrid);

            leftGrid.add(categoryButton, 0, i);
            i++;
        }

        mainGrid.add(leftGrid, 0, 1);

        return mainGrid;
    }

    private static void createInterestingNews(String link, GridPane defaultGrid, GridPane fakeGrid) throws IOException {
        List<News> interestingNews = getInterestingNews(link);

        if (!interestingNews.isEmpty()) {
            Label titleInteresting = new Label("Вам может быть интересно:");
            titleInteresting.setId("title");

            // Shared pane for all interesting news
            GridPane interestingPane = createGrid();
            interestingPane.add(titleInteresting, 0, 0);

            // Append interesting news on pane
            int i = 1;
            for (News news : interestingNews) {
                GridPane gridPane = createGrid();
                gridPane.setId("news-grid");
                openNews(gridPane, news.getLink());

                Label title = new Label(news.getTitle());
                title.setId("description");
                Label date = new Label(news.getDate());
                date.setId("date");

                // Conclude the news in the pane to apply style
                gridPane.add(createNewsComponent(title, false), 0, 0);
                gridPane.add(createNewsComponent(date, false), 0, 1);
                interestingPane.add(gridPane, 0, i);
                i++;
            }

            // Append block-news on the base pane
            defaultGrid.add(interestingPane, 4, 0, 1, 20);
        }

    }

    public static void createFoundNewsWindow(List<News> newsList) {

        try {
            mainGrid.getChildren().remove(2);
            toBack.setVisible(true);
        } catch (Exception ignored) {
        }

        GridPane gridPane = createGrid();

        int i = 0, j = 0;
        for (News news : newsList) {
            GridPane newsGrid = createGrid();
            newsGrid.setId("news-grid");

            Label title = new Label(news.getTitle());
            title.setId("title");

            Label date = new Label(news.getDate());
            date.setId("date");

            ImageView image = !news.getImgO().isEmpty() ? new ImageView(new Image(news.getImgO(),
                    256, 256, true, false)) : null;

            try {
            newsGrid.add(image, 0, 0);
            } catch (NullPointerException ignored) {
            }
            newsGrid.add(createNewsComponent(title, false), 0,   1);
            newsGrid.add(createNewsComponent(date, false), 0, 2);

            gridPane.add(newsGrid, i, j);

            i++;
            if (i == 3) {
                i = 0;
                j += 3;
            }
        }

        createGridPaneOnVBox(gridPane);
    }

    public static void createNewsWindow(News news) throws IOException {

        // Del before windows and show button toBack
        toBack.setVisible(true);
        App.mainGrid.getChildren().remove(2);

        GridPane gridPane = createGrid();
        createInterestingNews(news.getLink(), gridPane, App.mainGrid);
        List<String> imageRef = news.getImg();
        Label title = new Label(news.getTitle());
        title.setId("title");
        Label description = new Label(news.getDescription());
        description.setId("description");
        Label date = new Label(news.getAgency() + " " + news.getDate());
        date.setId("date");

        gridPane.add(createNewsComponent(title, true), 0, 0, 3, 1);

        // Append images news
        IntStream.range(0, imageRef.size())
                .forEach(i -> gridPane.add(new ImageView(new Image(imageRef.get(i))), i, 1));
        gridPane.add(createNewsComponent(description, true),
                0, 2, 3, 1);
        gridPane.add(createNewsComponent(date, false), 0, 3, 3, 1);

        if (news.getCitation() != null) {
            Citation citation = news.getCitation();
            Label text = new Label(citation.getText());
            text.setId("description");
            Label info = new Label(citation.getInfo());
            info.setId("date");

            // TODO Round the image
            Circle shapeImage = new Circle(50);
            Image image = new Image(citation.getImage());
            shapeImage.setFill(new ImagePattern(image));

            // Pane for the citation
            GridPane miniGrid = createGrid();
            miniGrid.add(shapeImage, 0, 3);
            miniGrid.add(createNewsComponent(text, false), 1, 3);
            miniGrid.add(createNewsComponent(info, false), 1, 4);
            miniGrid.setId("citation-grid");

            gridPane.add(miniGrid, 0, 4, 3, 1);
        }
        Button openDefaultBrowser = new Button("Открыть новость на Яндекс");
        openDefaultBrowser.setId("open-browser");
        openDefaultBrowser(openDefaultBrowser, news.getLink());

        HBox boxODBBtn = new HBox();
        boxODBBtn.getChildren().add(openDefaultBrowser);
        boxODBBtn.setAlignment(Pos.TOP_CENTER);
        gridPane.add(boxODBBtn, 0, 5, 3, 1);

        createGridPaneOnVBox(gridPane);
    }

    // Method for append the category and create her window
    public static void createCategoriesWindow(String link, GridPane mainGrid) throws Exception {

        try {
            mainGrid.getChildren().remove(2);
        } catch (Exception ignored) {
        }

        if (cacheCategoriesWindow.keySet().contains(link)) {
            mainGrid.add(cacheCategoriesWindow.get(link), 1, 1);
        } else {
            GridPane gridPane = createGrid();

            int i = 0, j = 0;
            boolean isMainNews = true;
            for (News news : News.divideAndRule(getListNews(link))) {
                allNews.add(news);

                Label title = new Label(news.getTitle());
                title.setId("title");
                Label date = new Label(news.getDate());
                date.setId("date");
                ImageView image = !news.getImgO().isEmpty() ? new ImageView(new Image(news.getImgO())) : null;

                if (isMainNews) {
                    GridPane miniGrid = createGrid();
                    miniGrid.setId("news-grid");
                    openNews(miniGrid, news.getLink());

                    miniGrid.add(image, i, j, 1, 3);

                    Label description = new Label(news.getDescription());
                    description.setId("description");

                    miniGrid.add(createNewsComponent(title, true), i + 2, j);
                    miniGrid.add(createNewsComponent(description, true), i + 2, j + 1);
                    miniGrid.add(createNewsComponent(date, true), i + 2, j + 2);

                    gridPane.add(miniGrid, 0, 0, 3, 1);
                    isMainNews = false;

                    j += 3;
                } else {
                    GridPane miniGrid = createGrid();
                    if (image != null) miniGrid.add(image, 0, 0);
                    miniGrid.add(createNewsComponent(title, false), 0, 1);
                    miniGrid.add(createNewsComponent(date, false), 0, 2);
                    miniGrid.setId("news-grid");
                    openNews(miniGrid, news.getLink());

                    gridPane.add(miniGrid, i, j);
                    i++;
                    if (i == 3) {
                        i = 0;
                        j += 3;
                    }

                }

            }
            cacheBackWindow.clear();
            createGridPaneOnVBox(gridPane);
        }
    }

    private static void createMainWindow() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cachingCategories(getListCategories(MAIN_PAGE));
                } catch (Exception ignored) {
                }
            }
        });

        thread.start();

        try {
            Scene scene = null;
            scene = new Scene((Parent) createElementMainWindow(), 1230, 700);
            scene.getStylesheets().add(PATH_TO_STYLE);

            pPrimaryStage.setScene(scene);
            pPrimaryStage.show();
        } catch (Exception ignored) {
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        pPrimaryStage = primaryStage;
        pPrimaryStage.setTitle("Yandex News");
        pPrimaryStage.setResizable(false);
        createMainWindow();
    }

}
