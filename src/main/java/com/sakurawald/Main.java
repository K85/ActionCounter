package com.sakurawald;

import com.sakurawald.config.ConfigManager;
import com.sakurawald.hook.KeyboardHook;
import com.sakurawald.hook.MouseHook;
import com.sakurawald.timer.ActionPerMinuteTimer;
import com.sakurawald.timer.TimerManager;
import com.sakurawald.ui.App;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {

        new Thread(() -> {
            new MouseHook().install();
        }).start();

        new Thread(() -> {
            new KeyboardHook().install();
        }).start();

        /* Set system properties */
        System.setProperty("application.name", "ActionCounter");
        System.setProperty("application.jvm_run_path", App.getJvmRunPath());
        System.setProperty("application.data_path", System.getProperty("application.name"));

        /* Init */
        log.info("Start Application...");
        log.info("Init >> Start");

        // Init configs
        log.debug("FileSystem: Init All Configs...");
        ConfigManager.getSingleton();
        log.info("Init >> End");

        /* Hook Actions */


        /* Register Timers  */
        TimerManager.executor.scheduleAtFixedRate(ActionPerMinuteTimer.getInstance(), 0, 1, TimeUnit.SECONDS);

        // Launch JavaFX application
        Application.launch(App.class);
    }

}

