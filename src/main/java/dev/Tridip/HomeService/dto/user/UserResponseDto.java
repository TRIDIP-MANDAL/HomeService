package dev.Tridip.HomeService.dto.user;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
    private String address;
    private LocalDateTime createdAt;
    private Boolean isActive;

    public UserResponseDto(Long id, String name, String username, String email, String phoneNumber, String role, String address, LocalDateTime createdAt, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.address = address;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
    public String getAddress() { return address; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Boolean getIsActive() { return isActive; }
}
