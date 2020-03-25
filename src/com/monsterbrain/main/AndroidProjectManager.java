/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterbrain.main;

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
                
                mainContentJFrame.setVisible(true);
                mainContentJFrame.addFileDropListener();
            }
        });
    }
    
}
