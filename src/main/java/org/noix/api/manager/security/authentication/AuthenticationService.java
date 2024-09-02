package org.noix.api.manager.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.security.jwt.JwtService;
import org.noix.api.manager.tokens.Token;
import org.noix.api.manager.users.User;
import org.noix.api.manager.tokens.TokenService;
import org.noix.api.manager.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public void authenticate(AuthenticationRequest request, HttpServletResponse response) {
        User user = userService.loadUserByUsername(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Optional<Token> optionalToken = tokenService.getValidToken(user);
        Token token;
        if (optionalToken.isEmpty()) {
            token = jwtService.createToken(user);
        } else {
            token = optionalToken.get();
        }
        String jwt = token.getJwt();
        response.addHeader("Authorization", "Bearer " + jwt);
    }

    public void register(AuthenticationRequest request, HttpServletResponse response) throws IOException {
        Optional<User> optionalUser = userService.createUser(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        if (optionalUser.isEmpty()) {
            response.sendError(409, "Username is taken");
            return;
        }

        User user = optionalUser.get();
        Token token = jwtService.createToken(user);
        response.addHeader("Authorization", "Bearer " + token.getJwt());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String jwt = request.getHeader("Authorization").substring(7);
        tokenService.removeToken(jwt);
        response.addHeader("Authorization", "");
    }

    public void logoutEverywhere(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserFromRequest(request);
        tokenService.removeAllTokens(user);
        response.addHeader("Authorization", "");
    }
}




