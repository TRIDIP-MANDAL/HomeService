package dev.Tridip.HomeService.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import dev.Tridip.HomeService.dto.ServiceDto;
import dev.Tridip.HomeService.dto.response.ApiRespDto;
import dev.Tridip.HomeService.model.HomeService;
import dev.Tridip.HomeService.service.ServiceService;
import dev.Tridip.HomeService.utils.JwtUtils;

@RestController
@RequestMapping("/api/v1/service")
public class ServiceController {
    private final ServiceService serviceService;
    private final JwtUtils jwt;

    public ServiceController(ServiceService serviceService, JwtUtils jwt) {
        this.serviceService = serviceService;
        this.jwt = jwt;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiRespDto<String>> createService(@RequestBody ServiceDto req) {
        boolean success = serviceService.create(req);
        if (success) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "Service created successfully", null), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "Failed to create service", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiRespDto<HomeService>> getServiceById(@PathVariable Long id) {
        HomeService service = serviceService.getById(id);
        if (service != null) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "Service fetched successfully", service), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "Service not found", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiRespDto<List<HomeService>>> getAllServices() {
        List<HomeService> services = serviceService.getAll();
        return new ResponseEntity<>(new ApiRespDto<>(true, "Services fetched successfully", services), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiRespDto<HomeService>> updateService(@PathVariable Long id, @RequestBody ServiceDto req, HttpServletRequest request) {
        Long currentUserId = jwt.getUserIdFromToken(jwt.getJwtTokenFromCookie(request.getCookies()));
        try {
            HomeService updated = serviceService.update(id, req, currentUserId);
            if (updated != null) {
                return new ResponseEntity<>(new ApiRespDto<>(true, "Service updated successfully", updated), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiRespDto<>(false, "Service not found", null), HttpStatus.NOT_FOUND);
        } catch (SecurityException e) {
            return new ResponseEntity<>(new ApiRespDto<>(false, "Forbidden: you are not the provider of this service", null), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiRespDto<String>> deleteService(@PathVariable Long id) {
        boolean success = serviceService.delete(id);
        if (success) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "Service deleted successfully", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "Service not found", null), HttpStatus.NOT_FOUND);
    }
}
