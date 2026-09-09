package dev.Tridip.HomeService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.Tridip.HomeService.dto.response.ApiRespDto;

@ControllerAdvice // to receive all the exception
public class GlobalExceptionhandler {
    
    // @ExceptionHandler(Exception.class)
    @ExceptionHandler
    public ResponseEntity<ApiRespDto<String>> handleException(Exception ex){
        System.out.println(ex.toString());
        return new ResponseEntity<>(new ApiRespDto<String>(false, "INternal Server Error", null),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
