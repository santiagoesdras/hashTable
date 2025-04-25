/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC-JUANCHO
 */
public class BinnacleManager {
    CsvReader csvReader = new CsvReader();//Clase CsvReader para desSerializar la informacion del archivo csv
    LogTable hashTable = new LogTable();//Clase LogTable para manejar almacenar en memoria todos los registros
    
    public void setDb(String filePath){//Metodo para setear el archivo csv de donde se leearan todos los registros
        csvReader.setFile(filePath);
        //this.firstRoute = csvReader.getMapRecord("firstRoute");
        hashTable.setTablesSize(csvReader.getHashTableSize());//Metodo para setear el tama;o de la tabla en funcion de la cantidad de registros existentes
        csvReader.fileReader(hashTable);//Metodo fileReader para procesar la informacion del archivo csv con parametro de referencia a la tabla hash
    }
    public Record searchById(String uid){
        return hashTable.getByIdNew(uid);
    }
    public List<Record> searchByUserName(String userName){
        if(hashTable.getByUserName(userName) == null || hashTable.getByUserName(userName).isEmpty()){
            return null;
        }
        List<Record> trueRecords = new ArrayList<>();
        for(Record currentRecord: hashTable.getByUserName(userName)){
            if(currentRecord.getExists() && currentRecord.getBindUserName().toUpperCase().equals(userName.toUpperCase())){
                trueRecords.add(currentRecord);
            }
        }
        return trueRecords.isEmpty()? null: trueRecords;
    }
}
