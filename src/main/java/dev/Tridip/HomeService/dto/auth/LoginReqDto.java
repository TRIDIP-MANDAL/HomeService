package dev.Tridip.HomeService.dto.auth;

public class LoginReqDto {
    private final String email, password;
    public LoginReqDto(String email, String password){
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
