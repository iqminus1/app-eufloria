package uz.pdp.appeufloria.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${app.token.expireDays}")
    private Integer expireDays;

    @Value("${app.token.secretKey}")
    private String secretKeyString;

    @Cacheable(value = "tokenGenerate",key = "#username")
    public String generateToken(String username) {
        Date expire = new Date(System.currentTimeMillis() + expireDays * 24 * 60 * 60 * 1000);
        return Jwts.builder()
                .subject(username)
                .expiration(expire)
                .issuedAt(new Date())
                .signWith(getKey())
                .compact();
    }

    @Cacheable(value = "tokenSubject",key = "#token")
    public String getSubject(String token) {
        return ((Claims) Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parse(token)
                .getPayload()
        ).getSubject();
    }


    private SecretKey getKey() {
        byte[] decode = Base64.getDecoder().decode(secretKeyString);
        return Keys.hmacShaKeyFor(decode);
    }
}
