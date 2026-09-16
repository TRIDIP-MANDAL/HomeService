package dev.Tridip.HomeService.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import dev.Tridip.HomeService.dto.ServiceDto;
import dev.Tridip.HomeService.mapper.ServiceMapper;
import dev.Tridip.HomeService.model.HomeService;
import dev.Tridip.HomeService.utils.DatabaseUtils;

@Repository
public class ServiceRepo {
    private final DataSource dataSource;

    public ServiceRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean create(ServiceDto req) {
        String query = "INSERT INTO home_services (name, description, price, provider_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            return DatabaseUtils.saveData(query, ServiceMapper.createMapper(req), connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create service", e);
        }
    }

    public HomeService createAndReturn(ServiceDto req) {
        String query = "INSERT INTO home_services (name, description, price, provider_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            Long newId = DatabaseUtils.saveAndReturnKey(query, ServiceMapper.createMapper(req), connection);
            return this.findById(newId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create service", e);
        }
    }

    public HomeService findById(Long id) {
        String query = "SELECT * FROM home_services WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = DatabaseUtils.getData(query, new Object[] { id }, connection);
            List<HomeService> list = ServiceMapper.getMappedData(rs);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to find service by id", e);
        }
    }

    public List<HomeService> getAll() {
        String query = "SELECT * FROM home_services";
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = DatabaseUtils.getData(query, new Object[] {}, connection);
            return ServiceMapper.getMappedData(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public HomeService update(Long id, ServiceDto req) {
        String query = "UPDATE home_services SET name=?, description=?, price=? WHERE id=?";
        try (Connection connection = dataSource.getConnection()) {
            Boolean success = DatabaseUtils.saveData(query, ServiceMapper.updateMapper(req, id), connection);
            if (success) {
                return this.findById(id);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update service", e);
        }
    }


    public boolean isOwner(Long serviceId, Long currentUserId) {
        String query = "SELECT id FROM home_services WHERE id = ? AND provider_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = DatabaseUtils.getData(query, new Object[] { serviceId, currentUserId }, connection);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to check service ownership", e);
        }
    }

    public boolean delete(Long id) {
        String query = "DELETE FROM home_services WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            return DatabaseUtils.saveData(query, new Object[] { id }, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete service", e);
        }
    }
}
