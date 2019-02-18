
package com.ms3test.csvimport.mahilum.tools;

import com.ms3test.csvimport.mahilum.controllers.ColumnsController;
import java.awt.Component;
import javax.swing.*;
import java.util.List;

public class Loader {

    private String filename;
    private Component parent;

    public Loader(String filename, Component parent) {
        this.filename = filename;
    }

    public SwingWorker createWorker() {
        return new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Start Progress
                setProgress(0);
                
                
                return ColumnsController.importCSV(filename, null);
            }

            @Override
            protected void process(List<Integer> chunks) {
                // Get Info
                for (int number : chunks) {
                    System.out.println("Found even number: " + number);
                }
            }

            @Override
            protected void done() {
                boolean bStatus = false;
                try {
                    bStatus = get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("Finished with status " + bStatus);
                            String message = "Done import";
            message += "\n* Processed Count: " + SharedValues.processCount;
            message += "\n* Success Count: " + SharedValues.successCount;
            message += "\n* Failed Count: " + SharedValues.errorCount;
            JOptionPane.showMessageDialog(parent, message);

            }
        };
    }

}