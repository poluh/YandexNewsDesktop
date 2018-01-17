package com.application;

import com.application.brain.parse.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static final String PATH_TO_STYLE = "css/style/StandardStyle.css";

    public static Scene createElementMainWindow() throws IOException {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25));
        gridPane.setVgap(15);
        gridPane.setHgap(15);

        int i = 0, j = 0;
        for (String name : ParseCategories.parseCategories()) {
            Button categories = new Button(name.split("::")[0]);
            categories.setMinSize(140, 140);

            gridPane.add(categories, i, j);
            i++;
            if (i == 5) {
                j++;
                i = 0;
            }
        }

        Scene scene = new Scene(gridPane, 800, 500);
        scene.getStylesheets().add(PATH_TO_STYLE);
        return scene;
    }

    private static void createMainWindow(Stage primaryStage) throws IOException {
            primaryStage.setScene(createElementMainWindow());
            primaryStage.show();
    }


    public void start(Stage primaryStage) throws Exception {
        createMainWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
