package com.isaac.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.isaac.Config;

public class RestoreManager {
    public static void restore(){
        
        try (Stream<Path> saveFiles = Files.walk(Config.getBackupPath(),1)) {
            saveFiles.filter(Files::isRegularFile)
                     .forEach(file -> copyFile(file, Config.getOriginPath()));
        } catch (IOException e) {
            System.err.println("Failed trying to access backup files: " + e.getMessage());
        }
    }

    private static void copyFile(Path sourceFile, Path destinationPath){
        try {
            Path filePath = destinationPath.resolve(sourceFile.getFileName());
            Files.copy(sourceFile, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed trying to copy a file: " + e.getMessage());
        }
    }
}
