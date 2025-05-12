package com.paccy.eucl.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ApiResponse <T>{
    private final LocalDateTime timestamp=LocalDateTime.now();

    private T data;
    private String message;
    private HttpStatus status;
    public  ApiResponse(String message, HttpStatus status, T data) {
        this.message=message;
        this.status=status;
        this.data=data;
    }

    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        assert  status!=null;
        return  ResponseEntity.status(status).body(this);
    }


}

//@JsonIgnoreProperties(ignoreUnknown = true)
//@Getter
//@Setter
//public class ApiResponse <T>{
//    private final LocalDateTime timestamp=LocalDateTime.now();
//
//    private T data;
//    private String message;
//    private HttpStatus status;
//    public  ApiResponse(String message, HttpStatus status, T data) {
//        this.message=message;
//        this.status=status;
//        this.data=data;
//    }
//
//    public static ResponseEntity<T> success( String message,HttpStatus status,T data) {
//        return  ResponseEntity.status(status).body(data);
//    }
//
//    public static ResponseEntity<T> error(String message,HttpStatus status) {
//        return ResponseEntity.status(status).body(null);
//    }
//
//
//}