package dev.Tridip.HomeService.dto.auth;

public final class SignUpReqDto {
    private final String email, password, name, username, phone_number, address, idempotency_key;
    public SignUpReqDto(String email, String password, String name, String username, String phone_number, String address, String idempotency_key){
        this.email = email;
        this.password = password;
        this.name = name;
        this.username = username;
        this.phone_number = phone_number;
        this.address = address;
        this.idempotency_key = idempotency_key;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    
    public String getUsername(){
        return username;
    }

    public String getPhone_number(){
        return phone_number;
    }

    public String getAddress(){
        return address;
    }
    public String getIdempotencey_kay(){
        return idempotency_key;
    }
    @Override
    public String toString() {
        return "AuthSignUpDto [email=" + email + ", password=" + password + ", name=" + name + ", username=" + username + ", phone_number=" + phone_number + ", address=" + address + "]";
    }
}
