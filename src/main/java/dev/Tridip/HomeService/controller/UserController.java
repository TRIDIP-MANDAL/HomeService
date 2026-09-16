package dev.Tridip.HomeService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import dev.Tridip.HomeService.dto.response.ApiRespDto;
import dev.Tridip.HomeService.dto.user.UserRequestDto;
import dev.Tridip.HomeService.dto.user.UserResponseDto;
import dev.Tridip.HomeService.service.UserService;
import dev.Tridip.HomeService.utils.JwtUtils;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwt;

    public UserController(UserService userService, JwtUtils jwt) {
        this.userService = userService;
        this.jwt = jwt;
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
    public ResponseEntity<ApiRespDto<String>> updateUserProfile(@PathVariable Long id, @RequestBody UserRequestDto req, HttpServletRequest request) {
        Long currentUserId = jwt.getUserIdFromToken(jwt.getJwtTokenFromCookie(request.getCookies()));
        Boolean success = userService.updateProfile(id, req, currentUserId);
        if (success == null) {
            return new ResponseEntity<>(new ApiRespDto<>(false, "Forbidden: you can only update your own profile", null), HttpStatus.FORBIDDEN);
        }
        if (success) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "Profile updated successfully", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "User not found", null), HttpStatus.NOT_FOUND);
    }
}
