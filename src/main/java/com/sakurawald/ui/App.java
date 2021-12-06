package com.sakurawald.ui;

import com.sakurawald.log.LoggerManager;
import com.sakurawald.ui.bean.JavaFxInstance;
import com.sakurawald.ui.view.View;
import com.sakurawald.util.JavaFxUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Optional;

@Slf4j
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        /* Set Default Exception Handler */
        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            LoggerManager.reportException(e);
        });

        /* Load FXML */
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("MainView.fxml"));
        JavaFxUtil.configAndShowStage(primaryStage, System.getProperty("application.name"), new Scene(fxmlLoader.load()));

        // Add Event: CloseRequestEvent
        primaryStage.setOnCloseRequest(event -> {
            Alert askAlert = JavaFxUtil.ofAlert(Alert.AlertType.CONFIRMATION,
                    "Exit", "Sure to exit the program ?");
            Optional<ButtonType> result = askAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                log.info("Shutdown >> Start");
                beforeExit();
                log.info("Shutdown >> End");
                log.info("End Application...");
                System.exit(0);
            } else event.consume();
        });

        JavaFxInstance.manageJavaFxInstance(fxmlLoader, primaryStage);
    }

    private void beforeExit() {
        // do nothing.
    }

    /**
     * @return the default configs path.
     */
    public static String getApplicationConfigsPath() {
        String result;
        result = App.getJvmRunPath();
        result = result + File.separator + System.getProperty("application.name") + File.separator + "Configs" + File.separator;
        return result;
    }

    public static String getJvmRunPath() {
        return new File("").getAbsolutePath();
    }
}
