package org.noix.api.manager.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.dto.request.AuthenticationRequest;
import org.noix.api.manager.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public void register(@RequestBody AuthenticationRequest request, HttpServletResponse response) throws IOException {
        authService.register(request, response);
    }

    @PostMapping("/authenticate")
    public void authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        authService.authenticate(request, response);
    }

    @PostMapping("/logout-here")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }

    @PostMapping("/logout-all")
    public void logoutEverywhere(HttpServletRequest request, HttpServletResponse response) {
        authService.logoutEverywhere(request, response);
    }

    //Idk if someone need an explanation... If you can access this endpoint = you authenticated ._.
    @PostMapping("/check")
    public ResponseEntity<Void> checkAuthentication() {
        return ResponseEntity.ok().build();
    }
}
