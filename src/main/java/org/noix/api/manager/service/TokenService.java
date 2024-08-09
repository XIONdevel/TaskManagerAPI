package org.noix.api.manager.service;

import lombok.RequiredArgsConstructor;
import org.noix.api.manager.entity.Token;
import org.noix.api.manager.entity.User;
import org.noix.api.manager.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;


    public boolean isValid(String jwt, User user) {
        Optional<Token> optionalToken = tokenRepository.getTokenByJwt(jwt);
        if (optionalToken.isEmpty()) {
            return false;
        }
        Token token = optionalToken.get();
        return token.getUser().equals(user) && !token.isExpired();
    }

    public Token getValidToken(User user) {
        List<Token> tokens = tokenRepository.getAllByUser(user);
        for (Token t : tokens) {
            if (!t.isExpired()) {
                return t;
            }
        }
        return Token.builder().jwt(null).build();
    }

    public Token saveToken(String jwt, User user, Long expiration) {
        Token token = Token.builder()
                .jwt(jwt)
                .user(user)
                .expiration(new java.sql.Date(System.currentTimeMillis() + expiration))
                .build();
        return tokenRepository.save(token);
    }

    public void removeAllTokens(User user) {
        tokenRepository.deleteAllByUser(user);
    }

    public void removeToken(String jwt) {
        tokenRepository.deleteByJwt(jwt);
    }
}
