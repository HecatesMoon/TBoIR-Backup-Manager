package com.isaac.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.isaac.Config;

public class SaveManager {
    public static void backup(){
        try {
            
            Files.createDirectories(Config.getBackupPath());

            try (Stream<Path> saveFiles = Files.walk(Config.getOriginPath(), 1)) {
                saveFiles.filter(file -> file.getFileName()
                                             .toString()
                                             .contains("persistentgamedata"))
                                             .forEach(file -> copyFile(file, Config.getBackupPath()));
            } catch (IOException e) {
                System.err.println("Failed trying to access folder: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Failed trying to create the backup folder: " + e.getMessage());
        }
    }

    private static void copyFile(Path sourceFile, Path destinationPath){
        try {
            Path filePath = destinationPath.resolve(sourceFile.getFileName());
            Files.copy(sourceFile, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed trying to copy files: " + e.getMessage());
        }
    }
}
