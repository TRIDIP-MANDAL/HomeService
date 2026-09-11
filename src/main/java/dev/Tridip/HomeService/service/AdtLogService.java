package dev.Tridip.HomeService.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.Tridip.HomeService.model.AuditLog;
import dev.Tridip.HomeService.repository.AuditLogRepo;

@Service
public class AdtLogService {
    private final ObjectMapper to_json;
    private final AuditLogRepo adt_repo;

    public AdtLogService(ObjectMapper to_json, AuditLogRepo adt_repo) {
        this.to_json = to_json;
        this.adt_repo = adt_repo;
    }

    // @Async what does it do ??
    public <T> boolean createAuditLog(String tableName, String operation, Integer doneBy, Integer rowId, T prevData,
            T newData) {
        String prevDataJson = null;
        String newDataJson = null;
        try {
            prevDataJson = to_json.writeValueAsString(prevData);
            newDataJson = to_json.writeValueAsString(newData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing JSON", e);
        }
        AuditLog log = new AuditLog(
                null,
                tableName,
                operation,
                doneBy,
                rowId,
                null,
                prevDataJson,
                newDataJson);

        return adt_repo.save(log);
    }
}
