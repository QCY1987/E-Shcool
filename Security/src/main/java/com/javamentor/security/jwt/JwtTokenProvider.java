package com.javamentor.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expire.hour}")
    private long validityHour;

    @Value("${jwt.token.expire.days}")
    private long validityDays;

    @Value("${jwt.token.header}")
    private String headerAuthorization;

    @Value("${jwt.token.bearer}")
    private String bearer;


    Logger LOGGER = Logger.getLogger(JwtTokenProvider.class.getName());

    /**
     * Метод создания JWT токена
     *
     * @param auth       - объект Authentication
     * @param rememberMe - флаг запоминания пользователя
     * @value validityYear - время жизни JWT токена, если rememberMe = true
     * @value validityHour - время жизни JWT токена, если rememberMe = false
     * @value secret - секретное слово
     * @value roles - список ролей
     **/
    public String createToken(Authentication auth, boolean rememberMe) {

        List<String> roles = new ArrayList<>();
        auth.getAuthorities().forEach(role -> roles.add(role.getAuthority()));
        String username = ((UserDetails) auth.getPrincipal()).getUsername();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration((rememberMe) ? Date.from(ZonedDateTime.now().plusDays(validityDays).toInstant())
                                            : Date.from(ZonedDateTime.now().plus(validityHour, ChronoUnit.HOURS).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Метод аутентификации
     *
     * @param userDetails - объект UserDetails
     **/
    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Метод получения конкретного claim
     *
     * @param token - JWT токен
     * @value function - секретное слово
     **/
    public <T> T getClaimFromToken(String token, Function<Claims, T> function) {
        return function.apply(getAllClaimsFromToken(token));
    }

    /**
     * Метод получения объекта claims из JWT токена
     *
     * @param token - JWT токен
     * @value secret - секретное слово
     **/
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Метод получения username из JWT токена
     *
     * @param token - JWT токен
     **/
    public String getUserNameFromToken(String token) {//пробел
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Метод проверки времени жизни JWT токена
     *
     * @param token - JWT токен
     **/
    public boolean validateExpirationToken(String token) {
        if (getClaimFromToken(token, Claims::getExpiration).after(new Date())) {
            return true;
        }
        return false;
    }


    /**
     * Метод получения JWT токена из Header
     *
     * @param request - HTTP запрос
     * @value headerAuthorization - header "Authorization"
     * @value bearer - обязательное начало токена в header
     **/
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(headerAuthorization);
        if (bearerToken != null && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(bearer.length());
        }
        return "";
    }

    /**
     * Метод проверяющий, можно ли получить claims из JWT токена
     *
     * @param token - JWT токен
     **/
    public boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.warning("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.warning("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            LOGGER.warning("Malformed jwt");
        } catch (SignatureException sEx) {
            LOGGER.warning("Invalid signature");
        } catch (Exception e) {
            LOGGER.warning("invalid token");
        }
        return false;
    }

    public String getUserRoleFromToken(String token) {
        List<String> authorities = (ArrayList<String>) getAllClaimsFromToken(token).get("roles");
        return authorities.get(0);
    }
}
