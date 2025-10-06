package com.example.Veritas.AI.modular;

import java.util.Map;

public class AuditResponse {
    private String sessionId;
    private int totalRows;
    private Map<String,Integer> nullCountPerColumn;
    private Map<String,Integer>uniqueCountPerColumn;
    public AuditResponse(){}

    public AuditResponse(String sessionId, int totalRows, Map<String, Integer> nullCountPerColumn, Map<String, Integer> uniqueCountPerColumn) {
        this.sessionId = sessionId;
        this.totalRows = totalRows;
        this.nullCountPerColumn = nullCountPerColumn;
        this.uniqueCountPerColumn = uniqueCountPerColumn;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public Map<String, Integer> getNullCountPerColumn() {
        return nullCountPerColumn;
    }

    public void setNullCountPerColumn(Map<String, Integer> nullCountPerColumn) {
        this.nullCountPerColumn = nullCountPerColumn;
    }

    public Map<String, Integer> getUniqueCountPerColumn() {
        return uniqueCountPerColumn;
    }

    public void setUniqueCountPerColumn(Map<String, Integer> uniqueCountPerColumn) {
        this.uniqueCountPerColumn = uniqueCountPerColumn;
    }
}
