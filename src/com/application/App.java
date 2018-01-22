package com.application;

import com.application.action.ActionEvents;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.IntStream;

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

    public static Node createElementMainWindow() throws IOException {

        GridPane gridPane = createGrid();

        int i = 0, j = 0;
        for (String name : getListCategories(MAIN_PAGE)) {

            Button categories = new Button(name.split("::")[0]);
            if (i == 1 && j == 0) {
                categories.setMinSize(450, 140);
                gridPane.add(categories, i, j, 3, 1);
                i += 2;
            } else {
                categories.setMinSize(140, 140);
                gridPane.add(categories, i, j);
            }
            categories.setId(name.split("::")[1]);
            ActionEvents.mainButton(categories);
            i++;
            if (i == 5) {
                j++;
                i = 0;
            }
        }

        Button more = new Button("Обвновиться");
        more.setMinSize(140, 140);
        more.setId("more");

        // TODO Update button ^

        gridPane.add(more, 4, 2);

        return gridPane;
    }

    private static Node createElementCategoriesWindow(String link) throws IOException {
        GridPane gridPane = createGrid();



        return new ScrollPane(gridPane);
    }

    private static Node createElementNewsWindow(News news) {
        GridPane gridPane = createGrid();
        ScrollPane scrollPane = new ScrollPane(gridPane);

        gridPane.add(new Label(news.getTitle()), 0, 0, 3, 1);
        IntStream.range(0, 3).forEach(i -> gridPane.add(new ImageView(news.getImg().get(i)), i, 1));
        gridPane.add(new Text(news.getDescription()), 0, 2, 3, 1);
        gridPane.add(new Label(news.getAgency() + news.getDate()), 3, 3);

        return scrollPane;
    }

    public static void createNewsWindow(News news) {
        Scene scene = new Scene((Parent) createElementNewsWindow(news), 800, 500);
        scene.getStylesheets().add(PATH_TO_STYLE);

        pPrimaryStage.setScene(scene);
        pPrimaryStage.show();
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
        pPrimaryStage.setTitle("Yandex News");
        createMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
