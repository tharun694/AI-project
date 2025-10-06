package com.example.Veritas.AI.modular;

import java.util.List;
import java.util.Map;

public class SyntheticResponse {
    private String sessionId;
    private List<Map<String, String>> syntheticRows;

    public SyntheticResponse() {}

    public SyntheticResponse(String sessionId, List<Map<String, String>> syntheticRows) {
        this.sessionId = sessionId;
        this.syntheticRows = syntheticRows;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public List<Map<String, String>> getSyntheticRows() { return syntheticRows; }
    public void setSyntheticRows(List<Map<String, String>> syntheticRows) { this.syntheticRows = syntheticRows; }
}
