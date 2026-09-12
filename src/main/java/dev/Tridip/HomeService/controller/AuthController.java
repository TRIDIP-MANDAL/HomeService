package dev.Tridip.HomeService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.Tridip.HomeService.dto.auth.LoginReqDto;
import dev.Tridip.HomeService.dto.auth.LoginResDto;
import dev.Tridip.HomeService.dto.auth.SignUpReqDto;
import dev.Tridip.HomeService.dto.response.ApiRespDto;
import dev.Tridip.HomeService.service.AuthService;
import dev.Tridip.HomeService.utils.JwtUtils;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwt;

    public AuthController(AuthService authService, JwtUtils jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiRespDto<String>> signUp(@RequestBody SignUpReqDto user) {
        Boolean success = authService.signUp(user);
        if (success) {
            return new ResponseEntity<>(new ApiRespDto<>(true, "User created successfully", user.getUsername()),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "User already exists", null), HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRespDto<LoginResDto>> signIn(@RequestBody LoginReqDto user) {

        LoginResDto res = authService.logIn(user);
        if (res != null) {
            String token = jwt.generateToken(res);
            ResponseCookie cookie = jwt.assignTokenToCookie(token);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new ApiRespDto<>(true, "User logged in successfully", res));
        }
        return new ResponseEntity<>(new ApiRespDto<>(false, "Invalid credentials", null), HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/logout")
    public ResponseEntity<ApiRespDto<String>> logout() {
        ResponseCookie cookie = jwt.clearCookie();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiRespDto<>(true, "User logged out successfully", null));
    }
}
