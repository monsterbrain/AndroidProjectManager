/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterbrain.main;

import com.monsterbrain.model.ProjectModel;
import com.monsterbrain.ui.ProjectJPanel;
import com.monsterbrain.ui.ProjectPanelGUI;
import com.monsterbrain.utils.Constants;
import com.monsterbrain.utils.FileDrop;
import com.monsterbrain.utils.SaveFileUtils;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * 
 * @author Faisal Rasak
 */
public class MainContentJFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainContentJFrame
     */
    public MainContentJFrame() {
        initComponents();

        GridLayout gridLayout = new GridLayout(4, 3, 16, 16);
        mainPanel.setLayout(gridLayout);
        
        loadProjectFromJson();
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void addFileDropListener() {
        new FileDrop(System.out, mainPanel, /*dragBorder,*/ new FileDrop.Listener() {
            @Override
            public void filesDropped(File[] files) {
                for (File file : files) {
                    try {
                        String fileType = file.isDirectory() ? "folder" : "File";
                        if (fileType.equals("folder")) {
                            String projectPath = file.getAbsolutePath();
                            String projectName = file.getName();
                            String[] gradleFileArray = file.list((File dir, String fileName) -> fileName.equals("build.gradle"));
                            boolean isAndroidProject = checkIfAndroidProject(gradleFileArray);

                            if (isAndroidProject) {
                                addAndroidProject(projectName, projectPath);
                                JOptionPane.showMessageDialog(MainContentJFrame.this, "Android project - ["+projectName+"] added");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("error = " + e.getMessage());
                    }
                }
            }

            private boolean checkIfAndroidProject(String[] gradleFileArray) {
                if (gradleFileArray == null) {
                    return false;
                }

                return (gradleFileArray.length > 0);
            }
        });
    }

    private void addAndroidProject(String projectName, String projectPath) {
        ProjectModel model = new ProjectModel(projectName, projectPath, projectName);
        SaveFileUtils.instance().addNewProjectToJson(model);
        mainPanel.add(new ProjectPanelGUI(model, actionString -> handleAction(actionString)).getProjectPanel());
    }

    private Void handleAction(String actionString) {
        switch (actionString) {
            case Constants.ACTION_OPEN_PROJECT_FOLDER:
                System.out.println("Open Project Folder");
                break;
            case Constants.ACTION_OPEN_BUILD_FOLDER:
                System.out.println("Open Build Folder");
                break;
            case Constants.ACTION_OPEN_SRC_FOLDER:
                System.out.println("Open Src Folder");
                break;
            default:
                break;
        }

        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Android Project Manager - APM 1.0");

        java.awt.GridBagLayout mainPanelLayout = new java.awt.GridBagLayout();
        mainPanelLayout.columnWidths = new int[] {0};
        mainPanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        mainPanel.setLayout(mainPanelLayout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainContentJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainContentJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainContentJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainContentJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainContentJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

    private void loadProjectFromJson() {
        List<ProjectModel> projectList = SaveFileUtils.instance().getProjectModelList();
        for (int i = 0; i < projectList.size(); i++) {
            mainPanel.add(new ProjectPanelGUI(projectList.get(i), actionString -> handleAction(actionString)).getProjectPanel());
        }
    }
}
