package com.hecatesmoon.isaacbackupmanager;

import com.hecatesmoon.isaacbackupmanager.service.OptionsManager;
import com.hecatesmoon.isaacbackupmanager.service.RestoreManager;
import com.hecatesmoon.isaacbackupmanager.service.SaveManager;
import com.hecatesmoon.isaacbackupmanager.ui.CLI;

public class App {
    public static void main(String[] args) {

        Config config = new Config();
        
        SaveManager saveManager = new SaveManager(config);
        RestoreManager restoreManager = new RestoreManager(config);
        OptionsManager optionsManager = new OptionsManager(config);

        CLI cli = new CLI(config, saveManager, restoreManager, optionsManager);
        cli.cliLoop();
    }
}
