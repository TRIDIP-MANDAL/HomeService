package dev.Tridip.HomeService.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import dev.Tridip.HomeService.model.AuditLog;
import dev.Tridip.HomeService.utils.DatabaseUtils;

@Repository
public class AuditLogRepo {
    private final DataSource dataSource;

    public AuditLogRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean save(AuditLog log) {
        String query = "INSERT INTO audit_logs (table_name, operation, done_by, row_id, changed_at, prev_data, new_data) "
                     + "VALUES (?, ?, ?, ?, ?, ?::jsonb, ?::jsonb)";
        try (Connection connection = dataSource.getConnection()) {
            Object []arr = new Object[]{
                log.getTableName(),
                log.getOperation(),
                log.getDoneBy(),
                log.getRowId(),
                log.getChangedAt() != null ? log.getChangedAt() : LocalDateTime.now(),
                log.getPrevData(),
                log.getNewData()
            };
            return DatabaseUtils.saveData(query, arr, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save audit log", e);
        }
    }
}
