package org.noix.api.manager.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.dto.request.AuthenticationRequest;
import org.noix.api.manager.entity.Token;
import org.noix.api.manager.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.security.expiration}")
    private Long expiration;


    public void authenticate(AuthenticationRequest request, HttpServletResponse response) {
        User user = userService.loadUserByUsername(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Token token = tokenService.getValidToken(user);
        String jwt;

        if (token.getJwt() != null) {
            jwt = token.getJwt();
        } else {
            token = jwtService.createToken(user);
            jwt = token.getJwt();
        }
        response.addCookie(getCookie(jwt));
    }

    public void register(AuthenticationRequest request, HttpServletResponse response) throws IOException {
        User user = userService.createUser(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        if (user.getUsername() == null) {
            response.sendError(409, "Username is taken");
            return;
        }

        Token token = jwtService.createToken(user);
        System.out.println("Token: " + token.getJwt());
        response.addCookie(getCookie(token.getJwt()));
    }

    private Cookie getCookie(String jwt) {
        Cookie cookie = new Cookie("Authorization", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (expiration / 1000));
        return cookie;
    }

}




