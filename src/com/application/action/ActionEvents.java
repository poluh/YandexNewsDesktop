package com.application.action;

import com.application.App;
import javafx.scene.control.Button;

import java.io.IOException;

public class ActionEvents {

    public static void mainButton(Button mainButton) {
        mainButton.setOnAction(event -> {
            switch (mainButton.getId()) {
                case "hot":
                    try {
                        App.createCategoriesWindow(App.MAIN_PAGE);
                    } catch (IOException ignored) {
                    }
                default:
                    try {
                        App.createCategoriesWindow(mainButton.getId());
                    } catch (IOException ignored) {
                    }
            }
        });
    }

}
