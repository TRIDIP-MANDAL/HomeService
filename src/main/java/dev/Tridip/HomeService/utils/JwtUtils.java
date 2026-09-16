package dev.Tridip.HomeService.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import jakarta.servlet.http.Cookie;
import dev.Tridip.HomeService.dto.auth.LoginResDto;



@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int expiresIn;

    @Value("${jwt.cookieName}")
    private String cookieName;

    @Value("${jwt.cookieMaxAge}")
    private int cookieMaxAge;

    private final String opeatingPathOfToken = "/";

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(LoginResDto user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())// defines to which user this token belongs to
                .claim("role", user.getRole())// defines what I want to store
                .claim("id", user.getId())
                .claim("name", user.getName())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiresIn))
                .signWith(key())
                .compact();// encodes all the data to base64 url format
    }

    public ResponseCookie assignTokenToCookie(String token) {
        return ResponseCookie.from(cookieName, token)
                .path(opeatingPathOfToken)// defines that browser will send cookie to all api endpoints
                .httpOnly(true)
                .maxAge(cookieMaxAge)
                .build(); // finalize the creation of cookie with all the given data
    }

    public String getJwtTokenFromCookie(Cookie []cookies) {
           if( cookies != null && cookies.length > 0){
            for(Cookie cookie: cookies){
                if (cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
           }
           return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder() // creates an empty config obj, where I will define how the token will be
                                     // valideted
                    .setSigningKey(key()) // here it is accepting a key, it will parse the key from token and compare
                                          // with it. if the signature part of token match with the original
                    .build() // it finalizes the configuration and then returns a JwtParser object, that will
                             // be used to read tokens
                    .parse(token); // will parse it so that the obj can read the token

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    public ResponseCookie clearCookie() {
        return ResponseCookie.from(cookieName, "")
                .path(opeatingPathOfToken)
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

}
