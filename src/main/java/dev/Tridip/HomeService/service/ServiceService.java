package dev.Tridip.HomeService.service;

import java.util.List;
import org.springframework.stereotype.Service;

import dev.Tridip.HomeService.dto.ServiceDto;
import dev.Tridip.HomeService.model.HomeService;
import dev.Tridip.HomeService.repository.ServiceRepo;

@Service
public class ServiceService {
    private final ServiceRepo serviceRepo;
    private final AdtLogService adt_log_srvc;

    public ServiceService(ServiceRepo serviceRepo, AdtLogService adt_log_srvc) {
        this.serviceRepo = serviceRepo;
        this.adt_log_srvc = adt_log_srvc;
    }

    public boolean create(ServiceDto req) {
        HomeService created = serviceRepo.createAndReturn(req);
        adt_log_srvc.createAuditLog("home_services", "INSERT", req.getProviderId(), created.getId(), null, created);
        return true;
    }

    public HomeService getById(Long id) {
        return serviceRepo.findById(id);
    }

    public List<HomeService> getAll() {
        return serviceRepo.getAll();
    }

    public HomeService update(Long id, ServiceDto req, Long currentUserId) {
        if (!serviceRepo.isOwner(id, currentUserId)) {
            throw new SecurityException("Forbidden: you are not the provider of this service");
        }
        HomeService old = serviceRepo.findById(id);
        if (old == null) return null;
        HomeService updated = serviceRepo.update(id, req);
        if (updated == null) throw new RuntimeException("Failed to update service");
        adt_log_srvc.createAuditLog("home_services", "UPDATE", currentUserId, id, old, updated);
        return updated;
    }

    public boolean delete(Long id) {
        HomeService existing = serviceRepo.findById(id);
        if (existing == null) return false;
        boolean success = serviceRepo.delete(id);
        if (success) {
            adt_log_srvc.createAuditLog("home_services", "DELETE", existing.getProviderId(), id, existing, null);
        }
        return success;
    }
}
