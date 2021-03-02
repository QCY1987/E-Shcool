package com.javamentor.service.authService;

public interface AuthService {
    boolean validateTokenAndRole(String token, String url);
}
