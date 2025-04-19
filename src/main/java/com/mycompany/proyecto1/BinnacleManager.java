/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC-JUANCHO
 */
public class BinnacleManager {
    CsvReader csvReader = new CsvReader();
    LogTable hashTable = new LogTable();
    
    public void setDb(String filePath){
        csvReader.setFile(filePath);
        //this.firstRoute = csvReader.getMapRecord("firstRoute");
        hashTable.setTablesSize(csvReader.getHashTableSize());
        csvReader.fileReader(hashTable);
        /*try{
            for(Record actualRecord: firstRoute.values()){
                hashTable.put(actualRecord);
            }
        }catch(Exception e){
            System.out.println("Error insertando valores de firstRoute en la hashTable");
            e.printStackTrace();
        }*/
    }
    public Record searchById(String uid){
        return hashTable.getById(uid);
    }
    public List<Record> searchByUserName(String userName){
        /*if(secondRoute.containsKey(userName) && secondRoute.get(userName).exists){
            return secondRoute.get(userName);
        }
        return null;*/
        if(hashTable.getByUserName(userName) == null || hashTable.getByUserName(userName).isEmpty()){
            return null;
        }
        List<Record> trueRecords = new ArrayList<>();
        for(Record currentRecord: hashTable.getByUserName(userName)){
            if(currentRecord.getExists() && currentRecord.getUserName().equals(userName)){
                trueRecords.add(currentRecord);
            }
        }
        return trueRecords.isEmpty()? null: trueRecords;
    }
}
