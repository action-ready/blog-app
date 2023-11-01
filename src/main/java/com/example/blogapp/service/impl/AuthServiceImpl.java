package com.example.blogapp.service.impl;


import com.example.blogapp.entity.Role;
import com.example.blogapp.entity.User;
import com.example.blogapp.exception.BlogAPIException;
import com.example.blogapp.payload.LoginDTO;
import com.example.blogapp.payload.RegisterDTO;
import com.example.blogapp.repository.RoleRepository;
import com.example.blogapp.repository.UserRepository;
import com.example.blogapp.security.JWTTokenProvider;
import com.example.blogapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {

        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exits !. ");
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exits !. ");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User register successfully";
    }
}
