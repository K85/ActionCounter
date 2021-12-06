package com.sakurawald.log;

import com.sakurawald.exception.ApplicationException;
import com.sakurawald.util.JavaFxUtil;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerManager {

    public static void reportException(Throwable throwable) {
        log.error(ApplicationException.getExceptionDetails(throwable));
        showErrorDialog(throwable);
    }

    public static void showErrorDialog(Throwable throwable) {
        Platform.runLater(() -> {
            // Create Expandable Exception.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error occurs...");
            alert.setHeaderText("Something must be wrong.");
            alert.setContentText("Class：" + throwable.getClass() + "\nCause：" + throwable.getCause() + "\nMessage：" + throwable.getMessage());
            Label label = new Label("Trace Stack：");
            TextArea textArea = new TextArea(ApplicationException.getExceptionDetails(throwable));
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
            alert.show();
        });
    }

}
