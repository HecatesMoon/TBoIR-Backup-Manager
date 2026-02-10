package com.isaac.service;

public enum Action {
    BACKUP("backup"),
    RESTORE("restore"),
    TOGGLE_STEAMCLOUD("toggle steam cloud");

    private final String name;

    Action (String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
