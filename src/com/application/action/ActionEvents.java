package com.application.action;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

import static com.application.App.createCategoriesWindow;
import static com.application.App.createFoundNewsWindow;
import static com.application.App.createNewsWindow;
import static com.application.brain.data.GetPages.getNews;
import static com.application.brain.data.SearchForNews.search;

public class ActionEvents {

    public static void categoriesButton(Button mainButton, GridPane grid, GridPane leftGrid) {
        mainButton.setOnAction(event -> {
            try {
                createCategoriesWindow(mainButton.getId(), grid);
                mainButton.setDisable(true);
                for (Node node : leftGrid.getChildren()) {
                    if (node != mainButton) {
                        node.setDisable(false);
                    }
                }
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("An error has occurred.");
                alert.setContentText("Check your Internet connection.");
                alert.showAndWait();
            } catch (Exception e) {
                throw new IllegalArgumentException("Error = " + e.getMessage());
            }
        });
    }

    public static void openNews(GridPane gridPane, String link) {
        gridPane.setOnMouseClicked(event -> {
            try {
                createNewsWindow(getNews(link));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new IllegalArgumentException("Error = " + e.getMessage());
            }
        });
    }

    public static void toBack(Button button, GridPane mainGrid, List<Node> nodes) {
        button.setOnAction(event -> {
            mainGrid.getChildren().remove(nodes.get(nodes.size() - 1));
            nodes.remove(nodes.size() - 1);
            if (nodes.size() == 1) {
                mainGrid.add(nodes.get(nodes.size() - 1), 1, 1);
                button.setVisible(false);
            } else {
                try {
                    mainGrid.add(nodes.get(nodes.size() - 1), 1, 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    for (Node node : nodes) {
                        mainGrid.getChildren().remove(node);
                    }
                    nodes.clear();
                    button.setVisible(false);
                }
            }
        });
    }

    public static void openDefaultBrowser(Button button, String url) {

        button.setOnAction(event -> {
            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();

            try {
                if (os.contains("win")) {

                    rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                } else if (os.contains("mac")) {
                    rt.exec("open " + url);
                } else {
                    if (os.contains("nix") || os.contains("nux")) {

                        String[] browsers = {"epiphany", "firefox", "mozilla",
                                "konqueror", "netscape", "opera", "links", "lynx", "safari"};

                        // "browser0 "URI" || browser1 "URI" ||..."
                        StringBuilder cmd = new StringBuilder();
                        for (int i = 0; i < browsers.length; i++)
                            cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                        rt.exec(new String[]{"sh", "-c", cmd.toString()});
                    }
                }
            } catch (Exception ignored) {
            }
        });
    }


    public static void searchForNews(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                createFoundNewsWindow(search(textField.getText()));
            }
        });
    }

}
