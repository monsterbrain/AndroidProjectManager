/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterbrain.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monsterbrain.main.AndroidProjectManager;
import com.monsterbrain.model.ConfigModel;
import com.monsterbrain.model.ProjectModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Monster Brain
 */
public class SaveFileUtils {

    List<ProjectModel> projectModelList = new ArrayList<>();
    ConfigModel programConfig;

    private static SaveFileUtils instance;

    public static SaveFileUtils instance() {
        if (instance == null) {
            instance = new SaveFileUtils();
        }
        return SaveFileUtils.instance;
    }

    public ConfigModel getProgramConfig() {
        return programConfig;
    }

    public List<ProjectModel> getProjectModelList() {
        return projectModelList;
    }

    public SaveFileUtils() {
        File saveFolder = getSaveFileFolder();

        File projectsJson = new File(saveFolder, "projects.json");
        
        String jsonString = readStringFromFile(projectsJson);
        if (!jsonString.isEmpty()) {
            Type modelType = new TypeToken<List<ProjectModel>>(){}.getType();
            projectModelList = new Gson().fromJson(jsonString, modelType);
        }
        
        File configJson = new File(saveFolder, "config.json");
        if (configJson.exists()) {
            String configJsonString = readStringFromFile(configJson);
            if (!configJsonString.isEmpty()) {
                Type modelType = new TypeToken<ConfigModel>() {
                }.getType();
                programConfig = new Gson().fromJson(configJsonString, modelType);
            }
        } else {
            programConfig = new ConfigModel();
            programConfig.setAndroidStudioPath("");
            boolean isWritten = writeStringToFile(configJson, new Gson().toJson(programConfig));
            if (!isWritten) {
                System.out.println("Config json write file failed");
            }
        }
    }
    
    private String readStringFromFile(File jsonFile) {
        String dataString = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile));
            // read line by line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dataString += line;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AndroidProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AndroidProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AndroidProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataString;
    }
    
     private boolean writeStringToFile(File file, String data) {
        boolean isSuccess = false;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data);
            bufferedWriter.close();

            isSuccess = true;

            System.out.println("file updated successfully");
        } catch (IOException ex) {
            Logger.getLogger(SaveFileUtils.class.getName()).log(Level.WARNING, null, ex);
        }
        
        return isSuccess;
    }
     
    public void setNewAndroidStudioPath(String path) {
        programConfig.setAndroidStudioPath(path);

        File configJson = new File(getSaveFileFolder(), "config.json");
        boolean isWritten = writeStringToFile(configJson, new Gson().toJson(programConfig));
        if (!isWritten) {
            System.out.println("Config json write file failed");
        }
    }

    public void addNewProjectToJson(ProjectModel model) {
        File saveFolder = getSaveFileFolder();

        // add to project model list. todo Check for duplicates
        projectModelList.add(model);

        String jsonString = new Gson().toJson(projectModelList);
        File jsonFile = new File(saveFolder, "projects.json");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(jsonFile));
            bufferedWriter.write(jsonString);
            bufferedWriter.close();

            System.out.println("json updated succesfully");
        } catch (IOException ex) {
            Logger.getLogger(SaveFileUtils.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    private File getSaveFileFolder() {
        String appDataFolder = System.getProperty("user.home") + "\\AppData\\Local\\APM";

        File saveFolder = new File(appDataFolder);
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
            System.out.println("Save folder Created");
        } else {
            System.out.println("Save folder exists");
        }

        return saveFolder;
    }

}
