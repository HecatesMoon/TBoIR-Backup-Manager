package com.isaac;

import com.isaac.service.SaveManager;

public class App {
    public static void main(String[] args) {

        System.out.println("Ruta de origen: " + Config.getOriginPath());
        System.out.println("Ruta de origen: " + Config.getBackupPath());
        SaveManager.backup();
    }
}
