/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms3test.csvimport.mahilum.controllers;

import com.ms3test.csvimport.mahilum.model.Columns;
import java.sql.*;
import java.util.List;
import javax.swing.JTextArea;
/**
 *
 * @author marrycute
 */
public class SQLLiteController {
    
    public static Connection getConnection(JTextArea txtLogs) {
        String message;
        Connection conn;
        try {
            message = "Opening database";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            // System.out.println("Opened database successfully");
        } catch ( Exception ex ) {
            // System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            message = ex.getMessage();
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
            return null;
            // System.exit(0);
        }
        message = "Successfully opened database";
        System.out.println(message);
        txtLogs.setText(txtLogs.getText() + "\n" + message);
        return conn;
    }
    
    public static boolean createDefaultTable(JTextArea txtLogs)
    {
        String message;
        Connection conn = null;
        Statement stmt = null;
        try {
            message = "Check existing/creating table";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);

            // Class.forName("org.sqlite.JDBC");
            // c = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database successfully");
            conn = getConnection(txtLogs);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS COLUMNS " +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " A TEXT NOT NULL, " +
            " B TEXT NOT NULL, " +
            " C TEXT NOT NULL, " +
            " D TEXT NOT NULL, " +
            " E VARCHAR(255), " +
            " F TEXT NOT NULL, " +
            " G REAL NOT NULL, " +
            " H BOOLEAN NOT NULL, " +
            " I BOOLEAN NOT NULL, " +
            " J TEXT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch ( Exception ex ) {
            // System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            message = ex.getMessage();
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
            return false;
        }
        System.out.println("Table created successfully");
        return true;
    }
   
    public static boolean save(List<Columns> lstColumns, JTextArea txtLogs) {

        String message = "";
        Connection conn = getConnection(txtLogs);
        
        message = "Preparing to save";
        System.out.println(message);
        txtLogs.setText(txtLogs.getText() + "\n" + message);
        
        try {
            createDefaultTable(txtLogs);
            String sql = "INSERT INTO COLUMNS (A, B, C, D, E, F, G, H, I, J)" + 
                            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            // conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            PreparedStatement ps = conn.prepareStatement(sql);

            final int batchSize = 500;
            int count = 0;

            message = "Saving data. Saving 500 at a time. Do not close application";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);

            for (Columns col: lstColumns) {

                ps.setString(1, col.getA().replace("'","\'"));
                ps.setString(2, col.getB().replace("'","\'"));
                ps.setString(3, col.getC().replace("'","\'"));
                ps.setString(4, col.getD().replace("'","\'"));
                ps.setString(5, col.getE().replace("'","\'"));
                ps.setString(6, col.getF().replace("'","\'"));
                ps.setDouble(7, col.getG());
                ps.setBoolean(8, col.getH());
                ps.setBoolean(9, col.getI());
                ps.setString(10, col.getJ().replace("'","\'"));
                ps.addBatch();

                if(++count % batchSize == 0) {
                    ps.executeBatch();
                    message = "Saved " + count + " items";
                    System.out.println(message);
                    txtLogs.setText(txtLogs.getText() + "\n" + message);
                }
            }
            ps.executeBatch(); // insert remaining records
            message = "Saved " + count + " items\nDONE!";
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
            ps.close();
            conn.close();
            
        } catch (Exception ex){ 
            message = ex.getMessage();
            System.out.println(message);
            txtLogs.setText(txtLogs.getText() + "\n" + message);
            return false;
        }
        return true;
    }
    
    // this is temporarily unused method
    public static boolean save(List<Columns> lstColumns, String sf) {
        Connection conn = getConnection(null);
        Statement stmt = null;
        try {
            createDefaultTable(null);
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");

            for(Columns col: lstColumns.subList(5995, lstColumns.size() - 1)) {
                conn.setAutoCommit(false);

                stmt = conn.createStatement();
                String sql = "INSERT INTO COLUMNS (A, B, C, D, E, F, G, H, I, J)" 
                    + " VALUES ("
                    + "'" + col.getA().replace("'","''") + "', "
                    + "'" + col.getB().replace("'","''") + "', "
                    + "'" + col.getC().replace("'","''") + "', "
                    + "'" + col.getD().replace("'","''") + "', "
                    // + "'" + col.getE() + "', "
                    + "'" + "e" + "', "
                    + "'" + col.getF().replace("'","''") + "', "
                    + col.getG() + ", "
                    + col.getH() + ", "
                    + col.getI() + ", "
                    + "'" + col.getJ().replace("'","''") + "'"
                    + ");";
                System.out.println(lstColumns.indexOf(col) + ": " + sql);
                stmt.executeUpdate(sql);
                conn.commit();
            }            
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        return true;
    }
    
    public static boolean delete(long id){
        return true;
    }
}
