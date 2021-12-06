package com.sakurawald.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.beans.JavaBean;

@Slf4j
public class ConfigManager {

    /* Config Manager */
    @Getter
    private static final ConfigManager singleton = new ConfigManager();

    private ConfigManager() {
        // do nothing.
    }

    /* Config Instances. */
    public final ApplicationConfigFile applicationConfigFile = new ApplicationConfigFile();
}
