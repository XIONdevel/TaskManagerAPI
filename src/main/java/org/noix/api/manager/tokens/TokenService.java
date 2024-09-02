package org.noix.api.manager.tokens;

import lombok.RequiredArgsConstructor;
import org.noix.api.manager.users.User;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Optional<Token> getValidToken(User user) {
        List<Token> tokens = tokenRepository.getAllByUser(user);
        for (Token t : tokens) {
            if (t.isExpired()) {
                tokenRepository.deleteById(t.getId());
            } else {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public Token saveToken(String jwt, User user, Long expiration) {
        Token token = Token.builder()
                .jwt(jwt)
                .user(user)
                .expiration(new Date(System.currentTimeMillis() + expiration))
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
