package dev.Tridip.HomeService.dto.auth;

public class LoginResDto {
    private final String name, role, username;
    private final Long id;
    public LoginResDto(Long id, String name, String role, String username) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
}
