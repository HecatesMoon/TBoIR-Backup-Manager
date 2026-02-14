package com.isaac.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.isaac.Config;

public class RestoreManager {

    private Config config;

    public RestoreManager (Config config){
        this.config = config;
    }

    public OperationResult restore(GameVersion version){

        Path finalOriginPath = config.getOriginPath().resolve(config.resolveProtonPath(version));

        Path finalBackupPath = config.getBackupPath().resolve(version.getFolderName());

        //Validates if game folder inside the backup folder exists
        if (!Files.exists(finalBackupPath)){
            return new OperationResult(false, "Backup folder not found: " + finalBackupPath.toString());
        }
        //Validates if origin folder exists
        if (!Files.exists(finalOriginPath)){
            System.out.println("Origin directory couldn't be found");
            System.out.println("Creating directory...");
            try {
                Files.createDirectories(finalOriginPath);
                System.out.println("Directory created: " + finalOriginPath);
            } catch (IOException e) {
                return new OperationResult(false, "Failed trying to create inexistent origin folder: " + e.getMessage());
            }
        }
        //Copies each file from backup folder to origin folder
        try (Stream<Path> saveFiles = Files.walk(finalBackupPath,2)) {
            List<Path> filesToCopy = saveFiles.filter(IOUtils::isTBoIFile).toList();

            if (!IOUtils.hasTBoIFiles(filesToCopy)){
                return new OperationResult(false, "This folder doesn't have any " + version.getName() + " savefiles: " + finalBackupPath);
            }

            System.out.println("Restoring from backup...");
            List<String> failedFiles = new ArrayList<>();
            for (Path file : filesToCopy) {
                try {
                    if (Files.isDirectory(file)){
                        Path folderInOrigin = finalOriginPath.resolve(file.getFileName());
                        Files.createDirectories(folderInOrigin); //todo: create folder method?
                    } else if (Files.isRegularFile(file)){
                        IOUtils.copyFile(finalBackupPath, file, finalOriginPath);
                    }
                } catch (IOException e) {
                    failedFiles.add(file.toString() + " : " + e.getMessage());
                }
            }
                     
            if (failedFiles.size() < 1){
                return new OperationResult(true, "Restore finished succesfully");
            } else {
                return new OperationResult(false, "Restore finished with some problems", failedFiles);
            }
        } catch (IOException e) {
            return new OperationResult(false, "Failed trying to copy files from backup: " + e.getMessage());
        }
    }

    public OperationResult isOverwriteDanger(GameVersion version){

        Path finalOriginPath = config.getOriginPath().resolve(config.resolveProtonPath(version));
        Path finalBackupPath = config.getBackupPath().resolve(version.getFolderName());
        List<Path> backupList;
        List<Path> originList;

        if (!Files.exists(finalOriginPath)){
            return new OperationResult(true, "there are no files to check in the origin folder");
        }
        if (!Files.exists(finalBackupPath)){
            return new OperationResult(false, "backup folder not found: " + finalBackupPath.toString());
        }

        try (
            Stream<Path> backupStream = Files.walk(finalBackupPath).filter(IOUtils::isTBoIFile);
            Stream<Path> originStream = Files.walk(finalOriginPath).filter(IOUtils::isTBoIFile);
        ) {
            backupList = backupStream.toList();
            originList = originStream.toList();
        } catch (IOException e) {
            return new OperationResult(false, "couldn't access files to check if there are conflicting files");
        }

        if (IOUtils.wouldCauseOverwrite(originList, backupList, finalOriginPath, finalBackupPath)){
            Predicate<Path> fileConflict = p -> IOUtils.fileWouldOverwrite(finalBackupPath, p, finalOriginPath);
            List<String> overwriteRiskFiles = originList.stream().filter(fileConflict).map(Path::toString).toList();
            
            return new OperationResult(false, "there are conflicting files", overwriteRiskFiles);
        } else {
            return new OperationResult(true, "there are no conflicting files");
        }

    }

}
