package com.example.blogapp.controller;


import com.example.blogapp.payload.JWTAuthResponse;
import com.example.blogapp.payload.LoginDTO;
import com.example.blogapp.payload.RegisterDTO;
import com.example.blogapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService  authService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
        String token  = authService.login(loginDTO);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO  registerDTO) {
        return ResponseEntity.ok(authService.register(registerDTO));
    }
}
