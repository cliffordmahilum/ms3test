/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms3test.csvimport.mahilum;

import com.ms3test.csvimport.mahilum.ui.ImportCSVJFrame;

/**
 *
 * @author marrycute
 */
public class App {
    public static void main(String args[]) {
                /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImportCSVJFrame().setVisible(true);
            }
        });

    }
}
