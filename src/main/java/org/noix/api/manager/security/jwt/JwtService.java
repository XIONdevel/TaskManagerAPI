package org.noix.api.manager.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.tokens.Token;
import org.noix.api.manager.users.User;
import org.noix.api.manager.tokens.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenService tokenService;

    @Value("${app.security.expiration}")
    private Long expiration;
    @Value("${app.security.secret}")
    private String SECRET;


    public Token createToken(User user) {
        String jwt = generateJwt(user);
        return tokenService.saveToken(jwt, user, expiration);
    }

    private String generateJwt(User user) {
        return generateJwt(user, new HashMap<>());
    }

    private String generateJwt(User user, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public boolean isValid(String jwt, User user) {
        return !isExpired(jwt) && tokenService.isValid(jwt, user);
    }

    public boolean isExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    protected Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(jwt);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parse(jwt)
                .accept(Jws.CLAIMS)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

}
