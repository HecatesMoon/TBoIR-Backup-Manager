package com.hecatesmoon.isaacbackupmanager.service;

import java.util.ArrayList;
import java.util.List;

public class OperationResult {
    private final boolean success;
    private final String message;
    private final List<String> failedFiles;

    public OperationResult (boolean success, String message){
        this.success = success;
        this.message = message;
        this.failedFiles = new ArrayList<>();
    }

    public OperationResult (boolean success, String message, List<String> failedFiles){
        this.success = success;
        this.message = message;
        this.failedFiles = failedFiles;
    }

    public boolean getSuccess(){
        return this.success;
    }

    public String getMessage(){
        return this.message;
    }

    public List<String> getFailedFiles(){
        return failedFiles;
    }
}
