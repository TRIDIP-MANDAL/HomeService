package dev.Tridip.HomeService.service;

import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import dev.Tridip.HomeService.dto.user.UserRequestDto;
import dev.Tridip.HomeService.dto.user.UserResponseDto;
import dev.Tridip.HomeService.mapper.UserMapper;
import dev.Tridip.HomeService.model.User;
import dev.Tridip.HomeService.repository.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final AdtLogService adt_log_srvc;

    public UserService(UserRepo userRepo, AdtLogService adt_log_srvc) {
        this.userRepo = userRepo;
        this.adt_log_srvc = adt_log_srvc;
    }

    public UserResponseDto getProfile(Long id) {
        User user = userRepo.findById(id);
        if (user == null) {
            return null;
        }
        return UserMapper.mapUserToResponseDto(user);
    }

    public Boolean updateProfile(Long id, UserRequestDto req, Long currentUserId) {
        if (!userRepo.isOwner(id, currentUserId)) {
            return null;
        }
        User old_user = userRepo.findById(id);
        if (old_user == null) {
            return false;
        }
        User new_user = userRepo.update(id, req);
        if (new_user == null) throw new RuntimeException("Failed to update user profile");
        adt_log_srvc.createAuditLog("users", "UPDATE", currentUserId, id, old_user, new_user);
        return true;
    }
}
