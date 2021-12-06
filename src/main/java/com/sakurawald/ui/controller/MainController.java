package com.sakurawald.ui.controller;

import com.sakurawald.config.ConfigManager;
import com.sakurawald.log.LoggerManager;
import com.sakurawald.ui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.extern.java.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainController extends ApplicationController {

    @FXML
    private Button button_configuration;

    @FXML
    void button_configuration_onAction(ActionEvent event) {
        File file = ConfigManager.getSingleton().applicationConfigFile.getFile();
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            LoggerManager.reportException(e);
        }
    }

}
