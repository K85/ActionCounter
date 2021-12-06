package com.sakurawald.ui.bean;

import com.sakurawald.ui.controller.ApplicationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Getter
public class JavaFxInstance<T extends ApplicationController> {

    /* Static Variables */
    private static final HashMap<String, ArrayList<JavaFxInstance<?>>> instances = new HashMap<>();

    /* Instance Variables */
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private T controller;

    private JavaFxInstance(FXMLLoader fxmlLoader, Stage stage) {
        this.fxmlLoader = fxmlLoader;
        this.stage = stage;
    }

    private void register() {
        log.debug("Registering {} {}", controller.getClass().getSimpleName(), controller);
        String key = controller.getClass().getSimpleName();
        instances.putIfAbsent(key, new ArrayList<>());
        instances.get(key).add(this);
    }

    private void unregister() {
        log.debug("Unregistering {} {}", controller.getClass().getSimpleName(), controller);
        String key = controller.getClass().getSimpleName();
        instances.get(key).remove(this);
    }

    private void init() {
        log.debug("Initializing: fxmlLoader {}, stage {}", fxmlLoader, stage);

        // Update instance variables
        this.controller = fxmlLoader.getController();

        // Add to Controllers
        register();

        // Register event (use WINDOW_HIDING event to replace WINDOW_CLOSED event)
        this.stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> {
            if (event.isConsumed()) return;
            unregister();
        });
    }

    /** the fxmlLoad.load() must be called before. */
    public static void manageJavaFxInstance(FXMLLoader fxmlLoader, Stage stage) {
        new JavaFxInstance<>(fxmlLoader, stage).init();
    }

    public static JavaFxInstance<?> getSingletonInstance(Class<? extends ApplicationController> clazz) {
        return getInstances(clazz).get(0);
    }

    public static ArrayList<JavaFxInstance<?>> getInstances(Class<? extends ApplicationController> clazz) {
        String key = clazz.getSimpleName();
        return instances.get(key);
    }
}
