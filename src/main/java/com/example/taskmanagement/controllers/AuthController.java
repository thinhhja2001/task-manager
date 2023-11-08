package com.example.taskmanagement.controllers;

import com.example.taskmanagement.payloads.responses.AuthenticationBody;
import com.example.taskmanagement.payloads.responses.Response;
import com.example.taskmanagement.payloads.requests.AuthenticationRequest;
import com.example.taskmanagement.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response<AuthenticationBody>> register(@RequestBody AuthenticationRequest request) {
        Response<AuthenticationBody> response = authenticationService.register(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthenticationBody>> login(@RequestBody AuthenticationRequest request) {
        Response<AuthenticationBody> response = authenticationService.login(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
