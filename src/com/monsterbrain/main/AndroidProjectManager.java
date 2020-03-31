/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterbrain.main;

import com.google.gson.Gson;
import com.monsterbrain.model.ProjectModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faisal Rasak
 */
public class AndroidProjectManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final MainContentJFrame mainContentJFrame = new MainContentJFrame();

                String appDataFolder = System.getProperty("user.home") + "\\AppData\\Local\\APM";
                System.out.println("appDataFolder = " + appDataFolder);

                File saveFolder = new File(appDataFolder);
                if (!saveFolder.exists()) {
                    saveFolder.mkdir();
                    System.out.println("Save folder Created");
                } else {
                    System.out.println("Save folder exists");
                }

                // write a json file
                ProjectModel model = new ProjectModel("MyApp", "C:\\abcd\\efgh", "My Awesome Project");
                String jsonString = new Gson().toJson(model);

                File jsonFile = new File(saveFolder, "projects.json");

                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile));
                    // read line by line
                    String line;
                    String dataString = "";
                    while ((line = bufferedReader.readLine())!= null) {
                        dataString += line;
                    }
                    ProjectModel model2 = new Gson().fromJson(dataString, ProjectModel.class);
                    System.out.println("dataString = " + dataString);
                    System.out.println("model2.location = " + model2.getLocation());
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AndroidProjectManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AndroidProjectManager.class.getName()).log(Level.SEVERE, null, ex);
                }

//                try {
//                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(jsonFile));
//                    bufferedWriter.write(jsonString);
//                    bufferedWriter.close();
//                    
//                    System.out.println("Done");
//                } catch (IOException ex) {
//                    Logger.getLogger(AndroidProjectManager.class.getName()).log(Level.SEVERE, null, ex);
//                }
                mainContentJFrame.setVisible(true);
                mainContentJFrame.addFileDropListener();
            }
        });
    }

}
