package com.application.action;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

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

    public static void toBack(Button button, GridPane mainGrid, List<Node> node) {
        button.setOnAction(event -> {
            mainGrid.getChildren().remove(node.get(node.size() - 1));
            node.remove(node.size() - 1);
            if (node.size() == 1) {
                button.setVisible(false);
                node.clear();
            } else {
                mainGrid.add(node.get(node.size() - 1), 1, 1);
            }
        });
    }

}
