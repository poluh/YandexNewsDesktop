package com.application.action;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

import static com.application.App.createCategoriesWindow;
import static com.application.App.createNewsWindow;
import static com.application.brain.data.GetPages.getNews;

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

    public static void openNews(GridPane gridPane, String link, GridPane mainGrid) {
        gridPane.setOnMouseClicked(event -> {
            try {
                createNewsWindow(getNews(link), mainGrid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new IllegalArgumentException("Error = " + e.getMessage());
            }
        });
    }

    public static void toBack(Button button, GridPane mainGrid, List<Node> node) {
        button.setOnAction(event -> {
            mainGrid.getChildren().remove(node.get(node.size() - 1));
            node.remove(node.size() - 1);
            if (node.size() == 1) {
                mainGrid.add(node.get(node.size() - 1), 1, 1);
                button.setVisible(false);
            } else {
                try {
                    mainGrid.add(node.get(node.size() - 1), 1, 1);
                } catch (IllegalArgumentException ignored) {
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


}
