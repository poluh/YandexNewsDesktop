package com.application;

import com.application.brain.data.Category;
import com.application.brain.data.Citation;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.application.action.ActionEvents.categoriesButton;
import static com.application.action.ActionEvents.openNews;
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

    public static void createNewsWindow(News news, GridPane mainGrid) {
        mainGrid.getChildren().remove(1, 1);

        GridPane gridPane = createGrid();
        List<String> imageRef = news.getImg();
        Label title = new Label(news.getTitle());
        title.setId("title");
        Label description = new Label(news.getDescription());
        description.setId("description");
        Label date = new Label(news.getDate());
        date.setId("date");

        gridPane.add(createNewsComponent(title, true), 0, 0, 3, 1);
        IntStream.range(0, imageRef.size())
                .forEach(i -> gridPane.add(new ImageView(new Image(imageRef.get(i))), i, 1));
        gridPane.add(createNewsComponent(description, true),
                0, 2, 3, 1);
        if (news.getCitation() != null) {
            Citation citation = news.getCitation();
            Label text = new Label(citation.getText());
            text.setId("description");
            Label info = new Label(citation.getInfo());
            info.setId("date");

            StackPane image = new StackPane(new ImageView(citation.getImage()));
            image.setId("stack-pane");

            GridPane miniGrid = createGrid();
            miniGrid.add(image, 0, 3);
            miniGrid.add(createNewsComponent(text, false), 1, 3);
            miniGrid.add(createNewsComponent(info, false), 1, 4);
            miniGrid.setId("citation-grid");

            gridPane.add(miniGrid, 0, 3, 3, 1);
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setMinSize(1200, 660);
        VBox vBox = new VBox();
        vBox.getChildren().add(scrollPane);
        mainGrid.add(vBox, 1, 1);
    }

    private static HBox createNewsComponent(Label label, boolean isMainNews) {
        HBox hBox = new HBox();
        hBox.setMaxWidth(isMainNews ? 550 : 270);
        hBox.getChildren().add(label);
        if (Objects.equals(label.getId(), "date")) hBox.setAlignment(Pos.BOTTOM_LEFT);
        return hBox;
    }

    public static void createCategoriesWindow(String link, GridPane mainGrid) throws Exception {

        GridPane gridPane = createGrid();

        int i = 0, j = 0;
        boolean isMainNews = true;
        for (News news : News.divideAndRule(getListNews(link))) {
            Label title = new Label(news.getTitle());
            title.setId("title");
            Label date = new Label(news.getDate());
            date.setId("date");
            ImageView image = !news.getImgO().isEmpty() ? new ImageView(new Image(news.getImgO())) : null;

            if (isMainNews) {
                GridPane miniGrid = createGrid();
                miniGrid.setId("news-grid");
                openNews(miniGrid, news.getLink(), mainGrid);

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
                openNews(miniGrid, news.getLink(), mainGrid);

                gridPane.add(miniGrid, i, j);
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
