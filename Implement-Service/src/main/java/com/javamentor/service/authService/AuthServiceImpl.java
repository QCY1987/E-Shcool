package com.javamentor.service.authService;

import com.javamentor.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Метод для проверки доступности роли для токена
     *
     * @param token - JWT token для проверки
     * @param role  - Роли для проверки
     * @return - true или false
     */
    @Override
    public boolean validateTokenAndRole(String token, String role) {

        if (jwtTokenProvider.validateToken(token)) {
            List<String> authorities = (List<String>) jwtTokenProvider.getClaimFromToken(token, roles -> roles.get("roles"));

            if (authorities.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
