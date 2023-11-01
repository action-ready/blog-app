package com.example.blogapp.payload;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {

    private String name;
    private String username;
    private String email;
    private String password;


}
