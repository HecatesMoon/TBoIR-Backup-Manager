package com.isaac;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    private static Properties configs = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    //todo: add os compatibilty
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String BASE_DIR = System.getProperty("user.dir");

    private static final Path DEFAULT_ORIGIN_PATH = Path.of(USER_HOME, "Documents", "My Games", "Binding of Isaac Repentance+");
    private static final Path DEFAULT_BACKUP_PATH = Path.of(BASE_DIR, "Isaac-Backups");

    public static Path ORIGIN_PATH;
    public static Path BACKUP_PATH;

    static{

        if (!Files.exists(Path.of(CONFIG_FILE))){
            configs.setProperty("ORIGIN_PATH", DEFAULT_ORIGIN_PATH.toString());
            configs.setProperty("BACKUP_PATH", DEFAULT_BACKUP_PATH.toString());
            storeProperties();
            loadProperties();
        } else {
            loadProperties();
        }

        ORIGIN_PATH = Path.of(configs.getProperty("ORIGIN_PATH", DEFAULT_ORIGIN_PATH.toString()));
        BACKUP_PATH = Path.of(configs.getProperty("BACKUP_PATH", DEFAULT_BACKUP_PATH.toString()));
    }

    private static void loadProperties (){
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)){
            configs.load(fis);
        } catch (IOException e){
            System.err.println("Failed trying to read config.properties: " + e.getMessage());
        }
    }
    private static void storeProperties(){
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            configs.store(fos, "Default path configs for Isaac Backup");
        } catch (IOException e) {
            System.err.println("Failed trying to store config.properties: " + e.getMessage());
        }
    }
}
