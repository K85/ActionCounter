package com.sakurawald.util;

import com.sakurawald.ui.App;
import com.sakurawald.ui.bean.JavaFxInstance;
import com.sakurawald.ui.controller.MainController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class JavaFxUtil {

    public static void configAndShowStage(Stage stage, String title, Scene scene) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(
                Objects.requireNonNull(App.class.getResourceAsStream("icon.png"))));
        stage.show();
    }


    public static Alert ofAlert(Alert.AlertType alertType, String title, String contentText, ButtonType... buttonTypes) {
        Alert a = new Alert(alertType, contentText, buttonTypes);
        a.setTitle(title);
        ((Stage) a.getDialogPane().getScene().getWindow()).getIcons().addAll(
                JavaFxInstance.getSingletonInstance(MainController.class).getStage().getIcons());
        return a;
    }
}
