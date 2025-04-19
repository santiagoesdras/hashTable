/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;
import spark.Request;
import spark.Response;
/**
 *
 * @author PC-JUANCHO
 */
public class Proyecto1 {
    
    public static void main(String[] args){
                ipAddress("0.0.0.0"); //Ip 0 para aceptar conexiones desde cualquier segmento de la red
        port(4567);//Puerto en el que funcionara el servidor
        staticFiles.location("public"); // ✅ sin "/"
        
        
        options("/*", (request, response) -> {
    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
    if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
    }

    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
    if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
    }

    return "OK";
});

before((request, response) -> {
    response.header("Access-Control-Allow-Origin", "*");
    response.header("Access-Control-Request-Method", "*");
    response.header("Access-Control-Allow-Headers", "*");
    response.type("application/json");
});

        
        BinnacleManager binnacle = new BinnacleManager();
        ObjectMapper mapper = new ObjectMapper();
        
        //Endpoint para setear al base de datos desde un archivo CSV
        post("/setDb", (req, res) -> {
            JsonNode jsonGet = mapper.readTree(req.body());
            if(jsonGet.has("filePath")){                
                //Inicializando timer de la busqueda
                double startTime = System.currentTimeMillis();
                
                //Llamada al metodo que procesa el archivo csv
                binnacle.setDb(jsonGet.get("filePath").asText());
                
                //Finalizando timer de la busqueda
                double stopTime = System.currentTimeMillis();
                double elapsedTime = (stopTime - startTime) / 1000;
                
                res.status(200);
                res.type("application/json");
                return "{\"response\":\"Base de datos cargada correctamente\", \"processingTime\":\"" + elapsedTime + "\"}";
            }else{
                res.status(400);
                return "Error: Parametro no encontrado";
            }
        });
        
        //Buscar por ID
        get("/buscar/id/:id", (req, res) -> {
            String id = req.params(":id");
           
            //Inicializando timer de la busqueda
            double startTime = System.currentTimeMillis();
    
            Record record = binnacle.searchById(id);
            //List<Record> records = binnacle.searchById(id);
            
            //Finalizando timer de la busqueda
            double stopTime = System.currentTimeMillis();
            double elapsedTime = (stopTime - startTime) / 1000;
            
            res.type("application/json");
            
            if (record != null){
                record.setSearchTime(elapsedTime);
                return mapper.writeValueAsString(record);
            }else{
                res.status(404);
                return "{\"error\":\"ID no encontrado\", \"processingTime\":\"" + elapsedTime + "\"}";
            }
        });
        
        //Buscar por nombre completo
        get("/buscar/nombre/:nombre", (req, res) -> {
           String nombre = req.params(":nombre").replace("+", " ");
           
           //Inicializando timer de la busqueda
           double startTime = System.currentTimeMillis();
           
           //Record record = binnacle.searchByUserName(nombre);
           List<Record> records = binnacle.searchByUserName(nombre);
           
           //Finalizando timer de la busqueda
           double stopTime = System.currentTimeMillis();
           double elapsedTime = (stopTime - startTime) / 1000;
           
           res.type("application/json");
           
           if (records != null){
               for(Record currentRecord: records){
                   currentRecord.setSearchTime(elapsedTime);
               }
               return mapper.writeValueAsString(records);
           }else{
               res.status(404);
               return "{\"error\":\"UserName no encontrado\", \"processingTime\":\"" + elapsedTime + "\"}";
           }
        });
    }
    /*public static void main(String[] args){
        List<String> uIdErrors = new ArrayList<>();
        List<Integer> hashedUserIds = new ArrayList<>();
        int userIdCollisions = 0;
        CsvReader csvReader = new CsvReader();
        csvReader.setFile("C:\\Users\\PC-JUANCHO\\Desktop\\Repositorio\\Java\\Proyecto1\\exampleProject.csv");
        csvReader.fileReader();
        Map<String, Record> records2 = csvReader.getMapRecord("firstRoute");
        LogTable logTable = new LogTable(records2.size());
        for(String uId: records2.keySet()){
            if(!hashedUserIds.contains(logTable.getHashIndex(uId))){
                hashedUserIds.add(logTable.getHashIndex(uId));
            }else{
                userIdCollisions ++;
            }
            System.out.println("Hash del id " + uId + ": " + logTable.getHashIndex(uId));
            if(logTable.getHashIndex(uId) > logTable.idTable.length){
                uIdErrors.add(uId + ", " + logTable.getHashIndex(uId));
            }
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total de userIds hasheados por id = " + records2.size());
        System.out.println("Total de eventos que quedaron fuera de los indices = " + uIdErrors.size());
        System.out.println("Colisiones totales de userIds = " + userIdCollisions);
        System.out.println("Tama;o de la tabla de userIds = " + logTable.idTable.length );
        System.out.println(uIdErrors);
    }*/
}
