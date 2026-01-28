package com.isaac;

import com.isaac.service.RestoreManager;
import com.isaac.service.SaveManager;
import com.isaac.ui.CLI;

public class App {
    public static void main(String[] args) {

        CLI cli = new CLI();
        cli.cliLoop();
    }
}
