/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author PC-JUANCHO
 */
public class CsvReader {
    private Map<String, Record> firstRoute = new HashMap<>();
    private Map<String, Record> secondRoute = new HashMap<>();
    private String file;
    int hashTableSize = 0;
    public void fileReader(LogTable hashTable){ 
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
        String line;
        while((line = bufferedReader.readLine()) != null){
            String[] parts = line.split(";");
            String action = parts[0];
            String userInfo = parts[1];
            
            //Validacion para omitir encabezados
            if(action.equals("action")){continue;}
            
            //Instancia temporal de la clase de registros
            Record actualRecord = new Record();
            actualRecord.setActions(action);
            actualRecord.setUserInfo(userInfo);
            hashTable.put(actualRecord, action);
        }
    }   catch (IOException e) {
            e.printStackTrace();
            System.out.println("Aqui esta el error >:(");
        }
    }
    public int countLines(String filePath) throws IOException {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) lines++;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return lines;
    }
    public Map<String, Record> getMapRecord(String type){
        if(type.equals("firstRoute")){
            return firstRoute;
        }
        if(type.equals("secondRoute")){
            return secondRoute;
        }
        return null;
    }
    public void setFile(String filePath){
        this.file = filePath;
    }
    public int getHashTableSize(){
        try{
            hashTableSize = countLines(file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return hashTableSize;
    }
}