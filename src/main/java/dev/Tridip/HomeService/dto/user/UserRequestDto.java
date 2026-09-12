package dev.Tridip.HomeService.dto.user;

public class UserRequestDto {
    private String name;
    private String username;
    private String phoneNumber;
    private String address;

    public UserRequestDto() {}

    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
}
