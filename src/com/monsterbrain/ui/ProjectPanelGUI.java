package com.monsterbrain.ui;

import com.monsterbrain.model.ProjectModel;
import com.monsterbrain.utils.SaveFileUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectPanelGUI {
    private final Function<String, Void> callbackFn;
    private ProjectModel projectInfo;
    private JButton btnOpenProjectInAS;
    private JPanel ProjectPanel;

    public ProjectPanelGUI(ProjectModel model, Function<String, Void> callback) {
        this.callbackFn = callback;
        projectInfo = model;

        setTitle(projectInfo.getDisplayName());

        btnOpenProjectInAS.addActionListener(e -> {
            System.out.println("Open in Android Studio");
            String androidStudioPath = SaveFileUtils.instance().getProgramConfig().getAndroidStudioPath();

            if (androidStudioPath.isEmpty()) {
                String inputText = JOptionPane.showInputDialog(ProjectPanel.getRootPane(), "Enter Path");
                System.out.println("inputText = " + inputText);
                //JOptionPane.showMessageDialog(ProjectPanel.getRootPane(), "Android studio path not set.");
                return;
            }
            // String androidStudioPath = "C:\\Program Files\\Android\\Android Studio\\bin\\studio64.exe";
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "\""+androidStudioPath+"\" "+projectInfo.getLocation());
            builder.redirectErrorStream(true);
            Process p;
            try {
                p = builder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while (true) {
                    line = reader.readLine();
                    if (line == null) { break; }
                    System.out.println(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(ProjectJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void setTitle(String displayName) {
        // set project title
        if (ProjectPanel.getBorder() instanceof TitledBorder) {
            ((TitledBorder) ProjectPanel.getBorder()).setTitle(displayName);
        }
    }

    public Component getProjectPanel() {
        return ProjectPanel;
    }
}
