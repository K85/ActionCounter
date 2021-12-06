package com.sakurawald.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sakurawald.log.LoggerManager;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
public class ConfigFile<ConfigDataType extends ConfigData> {
    /* Shared Gson */
    private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    /* Variables */
    private ConfigDataType configDataClassInstance = null;
    private final File file;
    private final Class<? extends ConfigData> configDataClass;

    public ConfigFile(String filePath, String fileName, Class<? extends ConfigData> configDataClass) {
        this.file = new File(filePath + fileName);
        this.configDataClass = configDataClass;
        this.init();
    }

    @SuppressWarnings("unchecked")
    protected void newConfigDataInstance() {
        log.info("FileSystem: Use Reflect to Create the instance of Data Class >> " + configDataClass.getSimpleName());
        try {
            this.configDataClassInstance = (ConfigDataType) this.configDataClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LoggerManager.reportException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void createFile() {
        File file = this.getFile();
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            LoggerManager.reportException(e);
        }
    }

    public Class<? extends ConfigData> getConfigDataClass() {
        return configDataClass;
    }

    public ConfigDataType getConfigDataInstance() {
        if (this.configDataClassInstance == null) {
            this.newConfigDataInstance();
        }
        return this.configDataClassInstance;
    }

    public File getFile() {
        return file;
    }

    protected void init() {
        if (!isExistOnDisk()) {
            createFile();
            newConfigDataInstance();
            writeDefaultToDisk();
        }
        log.info("FileSystem: Load Local File to Memory >> " + this.getFile().getName());
        loadFromDisk();
    }

    public boolean isExistOnDisk() {
        return this.getFile().exists();
    }

    @SuppressWarnings("unchecked")
    public void loadFromDisk() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(this.getFile()));
            this.configDataClassInstance = (ConfigDataType) new Gson().fromJson(reader,
                    this.configDataClass);
        } catch (FileNotFoundException e) {
            LoggerManager.reportException(e);
        } finally {
            try {
                Objects.requireNonNull(reader).close();
            } catch (IOException e) {
                LoggerManager.reportException(e);
            }
        }
    }

    public void reloadFromDisk() {
        log.info("FileSystem: Start to Reload Config: " + this.getFile().getName());
        loadFromDisk();
    }

    public void saveToDisk() {
        log.info("FileSystem: Save Memory Data to Local File >> " + this.getFile().getName());
        try {
            Files.write(this.getFile().toPath(), gson.toJson(this.configDataClassInstance).getBytes());
        } catch (IOException e) {
            LoggerManager.reportException(e);
        }
    }

    public void writeDefaultToDisk() {
        log.info("FileSystem: Start to Write Default ConfigFile Data >> " + this.getFile().getName());
        newConfigDataInstance();
        saveToDisk();
    }
}
