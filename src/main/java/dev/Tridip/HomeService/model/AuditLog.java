package dev.Tridip.HomeService.model;

import java.time.LocalDateTime;

public class AuditLog {
    private Long id;
    private String tableName;
    private String operation;
    private Integer doneBy;
    private Integer rowId;
    private LocalDateTime changedAt;
    private String prevData;
    private String newData;

    public AuditLog() {
    }

    public AuditLog(Long id, String tableName, String operation, Integer doneBy, Integer rowId, LocalDateTime changedAt, String prevData, String newData) {
        this.id = id;
        this.tableName = tableName;
        this.operation = operation;
        this.doneBy = doneBy;
        this.rowId = rowId;
        this.changedAt = changedAt;
        this.prevData = prevData;
        this.newData = newData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(Integer doneBy) {
        this.doneBy = doneBy;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getPrevData() {
        return prevData;
    }

    public void setPrevData(String prevData) {
        this.prevData = prevData;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    
}
