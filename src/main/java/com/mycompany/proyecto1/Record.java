/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;
import java.io.*;
/**
 *
 * @author PC-JUANCHO
 */
public class Record {
    public  Boolean exists;
    private int inserts = 0;
    private int deletes = 0;
    String firstName;
    String lastName;
    String uId;
    double searchTime = 0;
    
    Record(){
    }
    public void setUserInfo(String userInfo){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(userInfo);
            firstName = node.get("firstName").asText();
            lastName = node.get("lastName").asText();
            uId = node.get("uid").asText();
        } catch (JsonProcessingException e) {
            System.out.print("Error al parsear el json: ");
            System.out.println(userInfo);
            e.printStackTrace();
        }
    }
    public void setActions(String action){
        if("insert".equals(action.toLowerCase())){
            inserts++;
        }else if("delete".equals(action.toLowerCase())){
            deletes++;
        }
        updateExists();
    }
    public void setSearchTime(double searchTime){
        this.searchTime = searchTime;
    }
    public String getUserName(){
        return firstName + lastName;
    }
    public String getUid(){
        return uId;
    }
    public Boolean getExists(){
        return exists;
    }
    public double getSearchTime(){
        return searchTime;
    }
    private void updateExists(){
        exists = (inserts > deletes);
    }
    @Override
    public String toString(){
        return firstName + " " + lastName + " " + uId + " " + "exists: " + exists;
    }
}
