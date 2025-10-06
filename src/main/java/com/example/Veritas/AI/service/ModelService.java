package com.example.Veritas.AI.service;

import com.example.Veritas.AI.Repository.RawDataRepository;
import com.example.Veritas.AI.modular.AuditResponse;
import com.example.Veritas.AI.modular.ModelResponse;
import com.example.Veritas.AI.modular.RawData;
import com.example.Veritas.AI.modular.SyntheticResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Key;
import java.util.*;

@Service
public class ModelService {
    //    @Autowired
    @Autowired
    private RawDataRepository rawDataRepository;

    public ModelResponse train(MultipartFile file, String sessionId) {
ModelResponse response=new ModelResponse();

//        String sessionId = UUID.randomUUID().toString();
        int rowcount = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())
        ) {
            List<String> headers = new ArrayList<>(csvParser.getHeaderMap().keySet());
            ObjectMapper objectMapper = new ObjectMapper();
            for (CSVRecord record : csvParser) {
                Map<String, String> rowMap = new HashMap<>();
                for (String header : headers) {
                    rowMap.put(header, record.get(header));
                }
                String json = objectMapper.writeValueAsString(rowMap);
                RawData data = new RawData(sessionId, json,rowcount++);
                rawDataRepository.save(data);

              }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("CSV process failed");
        }
//        response.setModelType(modelType);
//        response.setLearningType(learningType);
        response.setRowCount(rowcount);
        response.setResult("Training completed: "+rowcount+" records processed.");
        return response;
    }

    public AuditResponse auditData(String sessionId) {
        List<RawData>rows=rawDataRepository.findBySessionId(sessionId);
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Integer>nullCount=new HashMap<>();
        Map<String,Set<String>>uniqueValues=new HashMap<>();
        int total=0;
        for (RawData row:rows){
            try{
                Map<String,String>data=objectMapper.readValue(row.getRowDataJson(),Map.class);
                total++;
                for (Map.Entry<String,String> entry:data.entrySet()){
                    String key=entry.getKey();
                    String value=entry.getValue();
                    if(value==null|| value.trim().isEmpty()){
    nullCount.put(key,nullCount.getOrDefault(key,0)+1);
                        }
                    uniqueValues.computeIfAbsent(key,K->new HashSet<>()).add(value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String,Integer>uniqueCount=new HashMap<>();
        for (String Key:uniqueValues.keySet()){
            uniqueCount.put(Key,uniqueCount.put(Key,uniqueValues.get(Key).size()));
        }
        return new AuditResponse(sessionId,total,nullCount,uniqueCount);
    }
    public SyntheticResponse generateSynthetic(String sessionId) {
        List<RawData> rows = rawDataRepository.findBySessionId(sessionId);
        ObjectMapper objectMapper = new ObjectMapper();

        if (rows.isEmpty()) {
            return new SyntheticResponse(sessionId, List.of());
        }


        Map<String, String> original = null;
        try {
            original = objectMapper.readValue(rows.get(0).getRowDataJson(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<String, String>> syntheticList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Map<String, String> fakeRow = new HashMap<>();
            for (String key : original.keySet()) {
                fakeRow.put(key, "SYN_" + key + "_" + (i + 1));
            }
            syntheticList.add(fakeRow);
        }

        return new SyntheticResponse(sessionId, syntheticList);
    }

}
