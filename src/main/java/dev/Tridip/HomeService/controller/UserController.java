package dev.Tridip.HomeService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.Tridip.HomeService.dto.response.ApiRespDto;
import dev.Tridip.HomeService.dto.user.UserRequestDto;
import dev.Tridip.HomeService.dto.user.UserResponseDto;
import dev.Tridip.HomeService.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiRespDto<UserResponseDto>> getUserProfile(@PathVariable Long id) {
        System.out.println("Controller at /v1/user/profile/{id} " + id);
        UserResponseDto user = userService.getProfile(id);
        if (user != null) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "Profile fetched successfully", user), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "User not found", null), HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiRespDto<String>> updateUserProfile(@PathVariable Long id, @RequestBody UserRequestDto req) {
        Boolean success = userService.updateProfile(id, req);
        if (success) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "Profile updated successfully", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "Failed to update profile", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
