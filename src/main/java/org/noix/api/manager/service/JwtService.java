package org.noix.api.manager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.entity.Token;
import org.noix.api.manager.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
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


    public Cookie extractAuthCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                return cookie;
            }
        }
        return new Cookie("Authorization", null);
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
                .decryptWith(getSecretKey())
                .build()
                .parse(jwt)
                .accept(Jwe.CLAIMS)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

}
