/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms3test.csvimport.mahilum.tools;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author marrycute
 */
public class SharedValues {
    
    public static List<String> logs;
    public static Connection conn;
    public static int processCount;
    public static int successCount;
    public static int errorCount;    
}