package com.example.Veritas.AI.controller;

import com.example.Veritas.AI.modular.AuditResponse;
import com.example.Veritas.AI.modular.SyntheticResponse;

import com.example.Veritas.AI.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("/train")
    public String trainModel(@RequestParam MultipartFile file,
                             @RequestParam String modelType,
                             @RequestParam String learningType) {

        String sessionId = UUID.randomUUID().toString();
        int rowCount = modelService.train(file,sessionId).getRowCount();

        return " Training completed\n" +
                " Model Type: " + modelType + "\n" +
                " Learning Type: " + learningType + "\n" +
                " Rows processed: " + rowCount + "\n" +
                " Session ID: " + sessionId;
    }

    @GetMapping("/audit/{sessionId}")
    public ResponseEntity<AuditResponse> audit(@PathVariable String sessionId) {
        AuditResponse audit = modelService.auditData(sessionId);
        return ResponseEntity.ok(audit);
    }
    @GetMapping("/synthetic/{sessionId}")
    public ResponseEntity<SyntheticResponse> generateSynthetic(@PathVariable String sessionId) {
        SyntheticResponse response = modelService.generateSynthetic(sessionId);
        return ResponseEntity.ok(response);
    }


}
