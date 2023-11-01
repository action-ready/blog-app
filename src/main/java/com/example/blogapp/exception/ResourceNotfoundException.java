package com.example.blogapp.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotfoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Long filedValue;

    public ResourceNotfoundException(String resourceName, String fieldName, Long filedValue) {
       super(String.format("%s not found with %s : %s ", resourceName, fieldName, filedValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

}
