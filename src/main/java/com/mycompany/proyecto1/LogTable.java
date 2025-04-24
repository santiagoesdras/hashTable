/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1;

import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.*;
import java.io.*;

/**
 *
 * @author PC-JUANCHO
 */
public class LogTable {
    public class RecordList{
        List<Record> records = new ArrayList<>();
        Map<String, Record> recordsNew = new HashMap<>();
    }
    RecordList[] idTable;
    RecordList[] userNameTable;
    
    int primeFactor = 7;

    LogTable(){}
    
    public void setTablesSize(int tableSize){
        idTable = new RecordList[tableSize * 2 + primeFactor];
        userNameTable = new RecordList[tableSize * 2 + primeFactor];

    }
    private int HashId(String id){
        try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(id.getBytes());
                
                BigInteger bigInt = new BigInteger(1, digest);
                return bigInt.mod(BigInteger.valueOf(idTable.length)).intValue();
            } catch (Exception e) {
                throw new RuntimeException(e);
                }
    }
    private int HashUserName(String userName){
        try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(userName.getBytes("UTF-8"));
                
                BigInteger bigInt = new BigInteger(1, digest);
                return bigInt.mod(BigInteger.valueOf(userNameTable.length)).intValue();
            } catch (Exception e) {
                throw new RuntimeException(e);
                }
    }
    public int getHashIndex(String string){
        return HashId(string.toUpperCase());
    }
    public void put(Record actualRecord, String action){
        try{
            int indexId = HashId(actualRecord.getUid().toUpperCase());
            int indexUserName = HashUserName((actualRecord.firstName + actualRecord.lastName).toUpperCase());
            if(idTable[indexId] == null || userNameTable[indexUserName] == null){
                putNewElementNew(indexId, indexUserName, actualRecord);
            }else{
                putCurrentElementNew(indexId, indexUserName, actualRecord, action);
            }
        }catch(Exception e){
            System.out.println("Error en el ingreso de usuarios");
            e.printStackTrace();
        }
//        System.out.println(idTable[HashId(actualRecord.getUid())].recordsNew.keySet());
    }
    public void putNewElementNew(int indexId, int indexUserName, Record actualRecord){
        if(idTable[indexId] == null){
            idTable[indexId] = new RecordList();
        }
        if(userNameTable[indexUserName] == null){
            userNameTable[indexUserName] = new RecordList();
        }
        idTable[indexId].recordsNew.put(actualRecord.getUid(), actualRecord);
        userNameTable[indexUserName].records.add(actualRecord); 
    }
    public void putNewElement(int indexId, int indexUserName, Record actualRecord){
            if(idTable[indexId] == null){
                idTable[indexId] = new RecordList();
            }
            if(userNameTable[indexUserName] == null){
                userNameTable[indexUserName] = new RecordList();
            }
            idTable[indexId].records.add(actualRecord);
            userNameTable[indexUserName].records.add(actualRecord); 
    }
    public void putCurrentElement(int indexId, int indexUserName, Record actualRecord, String action){
        Boolean idUpdated = false;
        Boolean userNameUpdated = false;
        for(Record currentRecord: idTable[indexId].records){
            if(actualRecord.getUid().equals(currentRecord.getUid())){
                currentRecord.setActions(action);
                idUpdated = true;
                break;
            }
        }
        for(Record currentRecord: userNameTable[indexUserName].records){
            if(actualRecord.getUid().equals(currentRecord.getUid())){
                currentRecord.setActions(action);
                userNameUpdated = true;
                break;
            }
        }
        if (!idUpdated) idTable[indexId].records.add(actualRecord);
        if (!userNameUpdated) userNameTable[indexUserName].records.add(actualRecord);
    }
    public void putCurrentElementNew(int indexId, int indexUserName, Record actualRecord, String action){
        Boolean idUpdated = false;
        Boolean userNameUpdated = false;
        if(idTable[indexId].recordsNew.containsKey(actualRecord.getUid())){
            idTable[indexId].recordsNew.get(actualRecord.getUid()).setActions(action);
            idUpdated = true;
        }
        if(userNameTable[indexUserName].recordsNew.containsKey(actualRecord.getUid())){
            userNameTable[indexUserName].recordsNew.get(actualRecord.getUid()).setActions(action);
            userNameUpdated = true;
        }
        if  (!idUpdated) idTable[indexId].recordsNew.put(actualRecord.getUid(), actualRecord);
        if (!userNameUpdated) userNameTable[indexUserName].records.add(actualRecord);
        
    }
    public Record getById(String id){
        try{
            RecordList temporalRecordList = idTable[HashId(id.toUpperCase())];
            int i = 0;
            Record temporalRecord = temporalRecordList.records.get(i);
            //return temporalRecordList.records;
            while(temporalRecord != null){
                if(temporalRecord.getUid().equals(id)){
                    break;
                }else{
                    i++;
                }
                temporalRecord = temporalRecordList.records.get(i);
            }
            return temporalRecord;   
        }catch(Exception e){
            return null;
        }
    }
    public Record getByIdNew(String id){
        try{
            RecordList temporalRecordList = idTable[HashId(id.toUpperCase())];
            if(temporalRecordList.recordsNew.containsKey(id)){
                return temporalRecordList.recordsNew.get(id);
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
    }
    public List<Record> getByUserName(String userName){
        try{
            RecordList temporalRecordList = userNameTable[HashUserName(userName.toUpperCase())];
            return temporalRecordList.records;
        }
        catch(Exception e){
            return null;
        }
    }
}
