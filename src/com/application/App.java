package com.application;

import com.application.brain.data.auxiliaryClasses.Category;
import com.application.brain.data.auxiliaryClasses.Citation;
import com.application.news.News;
import javafx.application.Application;
import javafx.application.Platform;
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
    private static Button toCategory = new Button("< Туды)0)");
    private static List<Node> cacheBackWindow = new ArrayList<>();
    private static Map<String, List<News>> cacheCategories = new HashMap<>();
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
        hBox.setMinSize(200, 50);
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

    // TODO
    private static void cachingCategories(List<Category> categories) {
        for (Category category : categories) {
            try {
                cacheCategories.put(category.getLink(), getListNews(category.getLink()));
            } catch (Exception e) {
                e.printStackTrace();
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

    private static GridPane newsToGrid(Node... nodes) {
        GridPane gridPane = createGrid();
        int i = 0;
        for (Node node : nodes) {
            gridPane.add(node, 0, i);
            i++;
        }
        gridPane.setId("news-grid");
        return gridPane;
    }

    private static Node createElementMainWindow() {

        Platform.runLater(() -> {

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

            toCategory.setVisible(false);
            toCategory.setId("back");
            toCategory(toCategory, mainGrid, cacheBackWindow, toBack);

            TextField searchNews = new TextField();
            searchNews.setMinWidth(500);
            searchForNews(searchNews);
            searchNews.setFocusTraversable(false);
            searchNews.requestFocus();

            upGrid.add(createHBox(categoryLabel, Pos.CENTER_LEFT), 0, 0);
            upGrid.add(createHBox(toCategory, Pos.CENTER_LEFT), 1, 0);
            upGrid.add(createHBox(searchNews, Pos.CENTER), 2, 0);
            upGrid.add(createHBox(toBack, Pos.CENTER_RIGHT), 3, 0);
            mainGrid.add(upGrid, 0, 0, 4, 1);

            // Append categories on left-pane
            int i = 0;
            try {
                for (Category category : getListCategories(MAIN_PAGE)) {

                    Button categoryButton = new Button(category.getName());
                    categoryButton.setMinSize(140, 50);
                    categoryButton.setId(category.getLink());
                    categoriesButton(categoryButton, mainGrid, leftGrid);

                    leftGrid.add(categoryButton, 0, i);
                    i++;
                }
            } catch (Exception ignored) {
            }

            mainGrid.add(leftGrid, 0, 1);

        });
        return mainGrid;
    }

    private static void createInterestingNews(String link, GridPane defaultGrid) throws IOException {
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

                Label title = new Label(news.getTitle());
                title.setId("description");
                Label date = new Label(news.getDate());
                date.setId("date");
                GridPane gridPane = newsToGrid(createNewsComponent(title, false),
                        createNewsComponent(date, false));
                openNews(gridPane, news.getLink());

                // Conclude the news in the pane to apply style
                interestingPane.add(gridPane, 0, i);
                i++;
            }

            // Append block-news on the base pane
            defaultGrid.add(interestingPane, 4, 0, 1, interestingNews.size() + 4);
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

            Label title = new Label(news.getTitle());
            title.setId("title");

            Label date = new Label(news.getDate());
            date.setId("date");

            ImageView image = !news.getImgO().isEmpty() ? new ImageView(new Image(news.getImgO(),
                    256, 256, true, false)) : null;

            GridPane newsGrid;
            try {
                newsGrid = newsToGrid(image, createNewsComponent(title, false),
                        createNewsComponent(date, false));
            } catch (NullPointerException ignored) {
                newsGrid = newsToGrid(createNewsComponent(title, false),
                        createNewsComponent(date, false));
            }
            openNews(newsGrid, news.getLink());
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
        toCategory.setVisible(true);
        App.mainGrid.getChildren().remove(2);

        GridPane gridPane = createGrid();
        createInterestingNews(news.getLink(), gridPane);
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
            cacheBackWindow.clear();
        } catch (Exception ignored) {
        }


        GridPane gridPane = createGrid();

        int i = 0, j = 0;
        boolean isMainNews = true;
        List<News> newsList = cacheCategories.containsKey(link) ? cacheCategories.get(link) : News.divideAndRule(getListNews(link));

        for (News news : newsList) {
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

                GridPane newsGrid;
                try {
                    newsGrid = newsToGrid(image, createNewsComponent(title, false),
                            createNewsComponent(date, true));
                } catch (NullPointerException e) {
                    newsGrid = newsToGrid(createNewsComponent(title, false),
                            createNewsComponent(date, true));
                }
                openNews(newsGrid, news.getLink());

                gridPane.add(newsGrid, i, j);
                i++;
                if (i == 3) {
                    i = 0;
                    j += 3;
                }
            }
        }
        createGridPaneOnVBox(gridPane);
    }

    private static void createMainWindow() {


        Scene scene = new Scene((Parent) createElementMainWindow(), 1230, 700);
        scene.getStylesheets().add(PATH_TO_STYLE);

        pPrimaryStage.setScene(scene);
        pPrimaryStage.show();

    }

    public void start(Stage primaryStage) throws Exception {
        pPrimaryStage = primaryStage;
        pPrimaryStage.setTitle("Yandex News");
        pPrimaryStage.setResizable(false);
        createMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
