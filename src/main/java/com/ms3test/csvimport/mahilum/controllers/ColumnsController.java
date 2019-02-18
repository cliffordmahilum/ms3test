package com.ms3test.csvimport.mahilum.controllers;

import com.ms3test.csvimport.mahilum.model.Columns;
import com.ms3test.csvimport.mahilum.tools.SharedValues;
import com.ms3test.csvimport.mahilum.tools.Utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTextArea;

public class ColumnsController {

    public static String[] getColumnNames() {
        String[] col = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        return col;
    }
    
    public static boolean importCSV(String filename, JTextArea txtLogs) {

        String message = "";
        int processCount = 0;
        int successCount = 0;
        int errorCount = 0;

        List<String> errList = new ArrayList<>();
        List<Columns> columnsList = new ArrayList<>();
        List<String[]> lstData = new ArrayList<>(); 
        List<String[]> lstBadData = new ArrayList<>();

        message = "Reading data from: " + filename;
        System.out.println(message);
        txtLogs.setText(message);
        lstData = CSVController.readData(filename,',');
        if(lstData.size() > 0) {
            message =  "Found " + (lstData.size() - 1) + " lines of data for processing (including header)";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
        }
        else {
            message = "No data to process";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
            return false;
        }

        for (int index = 1; index < lstData.size(); index++) {

            String[] row = lstData.get(index);
            System.out.println("Processing: " + index);
            
            if(row.length == 1) {
                // last record / empty row
                continue;
            }
            
            if(row.length != 10) {
                // errList.add("Invalid line row: " + (processCount + 1) + " + ", column: G" );
                errList.add("Invalid count" + index);
                String[] formattedRow = row.clone();
                Utils<Double> uDouble = new Utils<Double>();
                int cellIndex = 0;
                for(String cell: row) {
                    if(cell.contains(",") && !uDouble.tryParseDouble(cell)) {
                        formattedRow[cellIndex] = "\"" + cell + "\"";                        
                    }
                    else {
                        formattedRow[cellIndex] = cell;
                    }
                    cellIndex++;
                }
                lstBadData.add(formattedRow);
                errorCount++;
                processCount++;
                continue;                    
            }

            Columns columns = new Columns();
            double parsedG;
            boolean parsedH;
            boolean parsedI;

            Utils<Double> uDouble = new Utils<>();
            parsedG = uDouble.tryParseCurrencyToDouble(row[6]) ? (Double) Utils.Default.getResult() : 0;
            parsedH = Boolean.parseBoolean(row[7]);
            parsedI = Boolean.parseBoolean(row[8]);

            columns.setAll(row[0], row[1], row[2], row[3], row[4], row[5], parsedG, parsedH, parsedI, row[9] );
            columnsList.add(columns);
            processCount++;
            successCount++;
            /*
            if(uDouble.tryParseCurrencyToDouble(row[6])) {
                // errList.add("Unable to parse value from row: " + (processCount + 1) + ", column: G" );
                // errList.add(uDouble.getError() + " from row: " + (processCount + 1) + ", column: G" );
                // errorCount++;
                // processCount++;
                // continue;
                // double M = 
                parsedG = (Double) Utils.Default.getResult();
            }
            else
            {
                parsedG = 0;                    
            }
            */

            // parsedG = uDouble.getResult();

            //parsedG = Utils.parseCurrencyToDouble(data.get(6));
            // if(!Utils.tryParseDouble(data.get(6))) {
            /*
            if(!Utils.tryParseDouble(data.get(6))) {
                errList.add("Unable to parse value from row: " + (processCount + 1) + ", column: G" );
                errorCount++;
                processCount++;
                continue;
            }
            */
            // parsedG = Double.parseDouble(data.get(6));                
        }
        
        if(successCount > 0) {
            SQLLiteController.save(columnsList, txtLogs);
        }
        else {
            message = "No successful data to process";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
        }
        
        SharedValues.processCount = processCount; // exclude header from count
        SharedValues.successCount = successCount; // exclude header from count
        SharedValues.errorCount = errorCount; // exclude header from count
            
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format( new Date());
        String badDataFilename = "bad-data-" + formattedDate + ".csv";
        String[] header = getColumnNames();
        CSVController.create(badDataFilename, header, lstBadData, ',');
        
        return true;
    }
    
}
