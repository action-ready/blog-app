package com.example.blogapp.service;

import com.example.blogapp.payload.LoginDTO;
import com.example.blogapp.payload.RegisterDTO;

public interface AuthService {

    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
