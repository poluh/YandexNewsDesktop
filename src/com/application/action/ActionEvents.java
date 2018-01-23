package com.application.action;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.application.App.*;
import static com.application.brain.data.GetPages.getNews;

public class ActionEvents {

    public static void mainButton(Button mainButton) {
        mainButton.setOnAction(event -> {
            try {
                switch (mainButton.getId()) {
                    case "hot":
                        createCategoriesWindow(MAIN_PAGE);
                    default:
                        createCategoriesWindow(mainButton.getId());
                }
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("An error has occurred.");
                alert.setContentText("Check your Internet connection.");
                alert.showAndWait();
            }
        });
    }

    public static void openNews(Button button, String link) {
        button.setOnAction(event -> {
            try {
                createNewsWindow(getNews(link));
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
