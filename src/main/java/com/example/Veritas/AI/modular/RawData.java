package com.example.Veritas.AI.modular;

import jakarta.persistence.*;

@Entity
public class RawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String sessionId;
    @Column(columnDefinition = "CLOB")
    private String rowDataJson;
    private int rowNumber;
    public RawData(){}

    public RawData( String sessionId, String rowDataJson, int rowNumber) {
        this.sessionId = sessionId;
        this.rowDataJson = rowDataJson;
        this.rowNumber = rowNumber;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRowDataJson() {
        return rowDataJson;
    }

    public void setRowDataJson(String rowDataJson) {
        this.rowDataJson = rowDataJson;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
