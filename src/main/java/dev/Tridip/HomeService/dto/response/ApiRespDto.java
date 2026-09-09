package dev.Tridip.HomeService.dto.response;

public class ApiRespDto<T> {
    private Boolean success;
    private String message;
    private T data;
    public ApiRespDto(Boolean success, String message, T data){
       this.success = success;
       this.message = message;
       this.data = data;
    }
    
    public Boolean getSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
