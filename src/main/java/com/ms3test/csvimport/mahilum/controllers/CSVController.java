package com.ms3test.csvimport.mahilum.controllers;

import com.ms3test.csvimport.mahilum.tools.SharedValues;
import java.io.FileReader;
import com.opencsv.*;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVController {
    
    public static List<String[]> readByLines(String file)
    {
        List<String[]> lstData = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                lstData.add(nextRecord);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return lstData;
    }
    
    public static List<String[]> readAllData(String file)
    {
        List<String[]> lstData = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
            .withSkipLines(1)
            .build();
            lstData = csvReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return lstData;
    }
    
    public static List<String[]> readData(String file, char separator)
    {
        List<String[]> lstData = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(file);
            CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withCSVParser(parser)
                .build();

            // Read all data at once
            lstData = csvReader.readAll();
        }
        catch (Exception e) {
            SharedValues.logs.add(e.getMessage());
            // e.printStackTrace();
        }
        
        return lstData;
    }
    
    public static boolean create(String filename, String[] header, List<String[]> lstContent, char separator) {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(".//" + filename));
            CSVWriter csvWriter = new CSVWriter(writer,
                    // CSVWriter.DEFAULT_SEPARATOR,
                    separator,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            csvWriter.writeNext(header);
            for (String[] content: lstContent) {
                csvWriter.writeNext(content);
            }       
            csvWriter.close();            
        }
        catch(IOException ex) {
            return false;
        }
        return true;
    }

}