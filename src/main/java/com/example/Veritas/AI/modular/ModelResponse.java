package com.example.Veritas.AI.modular;

import java.util.List;

public class ModelResponse {
    private String modelType;
    private String learningType;
    private int rowCount;
    private List<String> columnNames;
    private String result;
    private String sessionId;

    public ModelResponse(String modelType, String learningType, int rowCount, List<String> columnNames, List<String> predictedLabels,String sessionId) {
        this.modelType = modelType;
        this.learningType = learningType;
        this.rowCount = rowCount;
        this.columnNames = columnNames;
        this.result=result;
        this.sessionId=sessionId;

    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getLearningType() {
        return learningType;
    }

    public void setLearningType(String learningType) {
        this.learningType = learningType;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String simulatedMetric) {
        this.result = result;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ModelResponse(){

    }

    @Override
    public String toString() {
        return "ModelResponse{" +
                "modelType='" + modelType + '\'' +
                ", learningType='" + learningType + '\'' +
                ", rowCount=" + rowCount +
                ", columnNames=" + columnNames +
                ", result='" + result + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
