/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms3test.csvimport.mahilum.tools;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author marrycute
 */
public class Utils<T> {
    
    private T resultValue;
    private String errMessage;
    
    public static Utils Default = new Utils();
    
    public boolean tryParseDouble(String value) {
        double result;
        try {
            result = Double.parseDouble(value);
            Default.setResultValue(result);
            return true;
        } catch (NumberFormatException ex) {
            Default.setErrorMessage(ex.getMessage());
            return false;
        }
    }
    
    private void setResultValue(T value) {
        this.resultValue = value;
    }
    
    private void setErrorMessage(String value) {
        this.errMessage = value;
    }

    public T getResult() {
        return resultValue;
    }
    
    public String getError() {
        return errMessage;
    }

    public static boolean tryParseBoolean(String value) {
        try {
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public static boolean tryParseNullableBoolean(String value) {
        try {
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public boolean trySplitString(String cvsLine, char separators) {
       
        char customQuote = '"';
        char DEFAULT_SEPARATOR = ',';
        List<String> result = new ArrayList<>();
        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            Default.setErrorMessage("Unable to split empty string");
            return false;
            // Default.setResultValue(result);
            // return true;
            // return result;
        }
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;
        char[] chars = cvsLine.toCharArray();
        for (char ch : chars) {
            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }
                }
            } else {
                if (ch == customQuote) {
                    inQuotes = true;
                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }
                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }
                } else if (ch == separators) {
                    result.add(curVal.toString());
                    curVal = new StringBuffer();
                    startCollectChar = false;
                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }
        }
        result.add(curVal.toString());
        // return result;
        Default.setResultValue(result);
        //setResultValue(result);
        return true;
    }
    
    public boolean tryParseCurrencyToDouble(String value)
    {
        // double d = 10.00;
        Locale us = new Locale("en", "US");
        NumberFormat cf = NumberFormat.getCurrencyInstance(us);
        // String s = cf.format(d);

        // System.out.println(s);

        Number number = 0;
        try
        {
           number = cf.parse(value);
        }
        catch (ParseException e)
        {
           // System.out.print(e);
            Default.setErrorMessage(e.getMessage());
            return false;
        }
        Default.setResultValue(number.doubleValue());
        return true;
    }
}
