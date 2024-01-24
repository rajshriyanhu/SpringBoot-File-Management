package com.newauth.auth.controller;

import com.newauth.auth.models.response.AuthenticationResponse;
import com.newauth.auth.service.AuthenticationService;
import com.newauth.auth.models.request.SigninRequest;
import com.newauth.auth.models.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignupRequest request){
        return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SigninRequest request){
        return ResponseEntity.ok(service.signin(request));
    }


}
