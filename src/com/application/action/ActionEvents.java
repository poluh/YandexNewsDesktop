package com.application.action;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import static com.application.App.*;
import static com.application.brain.data.GetPages.getNews;

public class ActionEvents {

    public static void categoriesButton(Button mainButton, GridPane grid) {
        mainButton.setOnAction(event -> {
            try {
                createCategoriesWindow(mainButton.getId(), grid);
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("An error has occurred.");
                alert.setContentText("Check your Internet connection.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void openNews(GridPane gridPane, String link, GridPane mainGrid) {
        gridPane.setOnMouseClicked(event -> {
            try {
                createNewsWindow(getNews(link), mainGrid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void toBack(Button button) {
        button.setOnAction(event -> {
            try {
                createMainWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // TODO create Action Event for show one news

}
