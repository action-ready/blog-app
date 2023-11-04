package com.example.blogapp.payload;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {

    private String usernameOrEmail;
    private String password;





}
