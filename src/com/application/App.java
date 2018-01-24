package com.application;

import com.application.brain.data.Category;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.application.action.ActionEvents.categoriesButton;
import static com.application.brain.data.GetPages.getListCategories;
import static com.application.brain.data.GetPages.getListNews;

public class App extends Application {

    private static final String PATH_TO_STYLE = "style/standard.css";
    private static final String MAIN_PAGE = "https://news.yandex.ru/";
    private static Stage pPrimaryStage = null;

    private static GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        return gridPane;
    }

    private static Node createElementMainWindow() throws Exception {

        GridPane leftGrid = new GridPane();
        GridPane mainGrid = new GridPane();

        leftGrid.getStyleClass().add("left-grid");
        leftGrid.setMinHeight(800);

        Label categoryLabel = new Label("Категории");
        categoryLabel.setId("categories");

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(25));
        topBox.getChildren().add(categoryLabel);
        topBox.setMinSize(Double.MAX_VALUE, 40);

        mainGrid.add(topBox, 0, 0, 2, 1);

        int i = 0;
        for (Category category : getListCategories(MAIN_PAGE)) {
            Button categoryButton = new Button(category.getName());
            categoryButton.setMinSize(140, 50);
            categoryButton.setId(category.getLink());
            categoriesButton(categoryButton, mainGrid);

            leftGrid.add(categoryButton, 0, i);
            i++;
        }

        mainGrid.add(leftGrid, 0, 1);

        return mainGrid;
    }

    public static void createNewsWindow(News news) {
    }

    private static HBox createNewsComponent(Label label, boolean isMainNews) {
        HBox hBox = new HBox();
        hBox.setMaxWidth(isMainNews ? 500 : 300);
        hBox.getChildren().add(label);
        if (Objects.equals(label.getId(), "date")) hBox.setAlignment(Pos.BOTTOM_LEFT);
        return hBox;
    }

    public static void createCategoriesWindow(String link, GridPane mainGrid) throws IOException {

        GridPane gridPane = createGrid();

        int i = 0, j = 0;
        boolean isMainNews = true;
        for (News news : News.divideAndRule(getListNews(link))) {
            Label title = new Label(news.getTitle());
            title.setId("title");
            Label date = new Label(news.getDate());
            date.setId("date");
            ImageView image = !news.getImgO().isEmpty() ? new ImageView( new Image(news.getImgO())) : null;

            if (isMainNews) {
                GridPane miniGrid = createGrid();

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
                if (image != null) gridPane.add(image, i, j);
                gridPane.add(createNewsComponent(title, false), i, j + 1);
                gridPane.add(createNewsComponent(date, false), i, j + 2);
                i++;
                if (i == 3) {
                    i = 0;
                    j += 3;
                }

            }

        }
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setMaxSize(1200, 660);
        VBox vBox = new VBox();
        vBox.getChildren().add(scrollPane);
        mainGrid.add(vBox, 1, 1);
    }

    public static void createMainWindow() throws Exception {

        Scene scene = new Scene((Parent) createElementMainWindow(), 1200, 700);
        scene.getStylesheets().add(PATH_TO_STYLE);

        pPrimaryStage.setScene(scene);
        pPrimaryStage.show();
    }


    public void start(Stage primaryStage) throws Exception {
        pPrimaryStage = primaryStage;
        pPrimaryStage.setTitle("Yandex News");
        createMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
