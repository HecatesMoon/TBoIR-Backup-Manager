package com.isaac.ui;

import java.util.Scanner;

import com.isaac.Config;
import com.isaac.service.RestoreManager;
import com.isaac.service.SaveManager;

public class CLI {

    private Boolean isRunning;
    private Scanner scanner;

    public CLI (){
        this.isRunning = true;
        this.scanner = new Scanner(System.in);
    }

    public void cliLoop(){
        
        System.out.println("Welcome to TBoIR Backup Maker");
        printMenu();
        
        while (this.isRunning) {
            
            System.out.print("> ");
            menuOption();
            
        }
        scanner.close();
    };

    private void printMenu(){
        System.out.println("----------MENU----------");
        System.out.println("1. Show current configured paths");
        System.out.println("2. Backup the savefiles (from source to backup folder)");
        System.out.println("3. Restore the savefiles (from backup to source folder)");
        System.out.println("4. Exit");
        System.out.println("What do you want to do? (press the number and then enter)");
    }

    private void menuOption(){
        String choosenOption;
        choosenOption = this.scanner.nextLine();

        switch (choosenOption) {
            case "1":
                System.out.println("Source Path: " + Config.getOriginPath().toString());
                System.out.println("Backup Path: " + Config.getBackupPath().toString());
                break;
            case "2":
                if (SaveManager.backup()){
                    System.out.println("Backup Done!");
                } else {
                    System.err.println("Backup failed!");
                }
                
                break;
            case "3":
                if (RestoreManager.restore()){
                    System.out.println("Savefiles restored from backup!");
                } else {
                    System.err.println("Restoring failed");
                }
                break;
            case "4":
                System.out.println("Closing program");
                isRunning = false;
                break;
            default:
                System.out.println("Write a valid number");
                break;
        }
    }
}
