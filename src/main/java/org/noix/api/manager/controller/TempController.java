package org.noix.api.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.dto.request.AuthenticationRequest;
import org.noix.api.manager.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TempController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public void register(@RequestBody AuthenticationRequest request, HttpServletResponse response) throws IOException {
        authService.register(request, response);
    }

    @PostMapping("/authenticate")
    public void authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        authService.authenticate(request, response);
    }


}
