package com.application;

import com.application.action.ActionEvents;
import com.application.brain.data.Category;
import com.application.news.News;
import com.sun.javafx.geom.Rectangle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static com.application.action.ActionEvents.toBack;
import static com.application.brain.data.GetPages.getListCategories;
import static com.application.brain.data.GetPages.getListNews;

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

    private static Node createElementMainWindow(Node newsPanel) throws Exception {

        GridPane leftGrid = new GridPane();
        GridPane mainGrid = new GridPane();
        Node supplementalGrid;

        leftGrid.getStyleClass().add("left-grid");
        leftGrid.setMinHeight(800);

        if (newsPanel != null) {
            supplementalGrid = newsPanel;
        }

        Label categoryLabel = new Label("Категории");
        categoryLabel.setId("title");

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(25));
        topBox.getChildren().add(categoryLabel);
        topBox.setMinSize(Double.MAX_VALUE, 40);
        topBox.getStyleClass().add("hbox");
        topBox.setId("hbox1");
        topBox.getStylesheets().add(PATH_TO_STYLE);

        mainGrid.add(topBox, 0, 0, 2, 1);

        int i = 0;
        for (Category category : getListCategories(MAIN_PAGE)) {
            Button categoryButton = new Button(category.getName());
            categoryButton.setMinSize(140, 35);
            leftGrid.add(categoryButton, 0, i);
            i++;
        }

        mainGrid.add(leftGrid, 0, 1);

        return mainGrid;
    }

    private static Node createElementCategoriesWindow(String link) throws IOException {
        return createGrid();
    }

    private static Node createElementNewsWindow(News news) {
        return createGrid();
    }

    public static void createNewsWindow(News news) {

    }

    public static void createCategoriesWindow(String link) throws IOException {

    }

    public static void createMainWindow() throws Exception {

        Scene scene = new Scene((Parent) createElementMainWindow(null), 800, 500);
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
