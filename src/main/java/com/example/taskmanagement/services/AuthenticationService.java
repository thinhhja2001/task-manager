package com.example.taskmanagement.services;

import com.example.taskmanagement.models.User;
import com.example.taskmanagement.payloads.requests.AuthenticationRequest;
import com.example.taskmanagement.payloads.responses.AuthenticationBody;
import com.example.taskmanagement.payloads.responses.Response;
import com.example.taskmanagement.providers.JwtTokenProvider;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.utils.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Response<AuthenticationBody> login(AuthenticationRequest request) {
        try {
            var user = userRepository.findByUsername(request.getUsername());
            if (user == null) {
                return Response.<AuthenticationBody>builder()
                        .status(HttpStatus.userNotFound.statusCode)
                        .msg(HttpStatus.userNotFound.message)
                        .build();
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            var jwtToken = jwtTokenProvider.generateToken(user);
            return Response.<AuthenticationBody>builder()
                    .status(HttpStatus.success.statusCode)
                    .msg(HttpStatus.success.message)
                    .data(AuthenticationBody.builder()
                            .token(jwtToken)
                            .build())
                    .build();
        } catch (AuthenticationException e) {
            return Response.<AuthenticationBody>builder()
                    .status(HttpStatus.incorrectCredential.statusCode)
                    .msg(HttpStatus.incorrectCredential.message)
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.<AuthenticationBody>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }

    }

    public Response<AuthenticationBody> register(AuthenticationRequest request) {
        try {
            var user = userRepository.findByUsername(request.getUsername());
            if (user != null) {
                return Response.<AuthenticationBody>builder()
                        .status(HttpStatus.userExisted.statusCode)
                        .msg(HttpStatus.userExisted.message)
                        .build();
            }
            user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            var userSaved = userRepository.save(user);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            var jwtToken = jwtTokenProvider.generateToken(userSaved);
            return Response.<AuthenticationBody>builder()
                    .status(HttpStatus.success.statusCode)
                    .msg(HttpStatus.success.message)
                    .data(AuthenticationBody.builder()
                            .token(jwtToken)
                            .build()).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.<AuthenticationBody>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }

    }
}
