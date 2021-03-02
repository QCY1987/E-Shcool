package com.javamentor.controller.rest.login;

import com.javamentor.dto.model.UserLogin;
import com.javamentor.security.JwtUserDetailsService;
import com.javamentor.security.jwt.JwtTokenProvider;
import com.javamentor.service.adminService.UserService;
import com.javamentor.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginRestController {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public LoginRestController(JwtUserDetailsService jwtUserDetailsService, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder, AuthService authService, UserService userService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.userService = userService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin requestUserDto) {

        try {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(requestUserDto.getUsername());
            Authentication authentication = jwtTokenProvider.getAuthentication(userDetails);

            if (passwordEncoder.matches(requestUserDto.getPassword(), userDetails.getPassword())) {
                String token = jwtTokenProvider.createToken(authentication, requestUserDto.getRememberMe());

                return ResponseEntity.ok(token);
            } else {
                return new ResponseEntity<>("Пароли не совпадают, попробуйте еще раз", HttpStatus.BAD_REQUEST);
            }
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>("Пользователя с таким логином не существует", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/check-token")
    public ResponseEntity<Boolean> checkTokenAndRoles(@RequestBody Map<String, String> tokenAndRoles) {
        if (authService.validateTokenAndRole(tokenAndRoles.get("token"), tokenAndRoles.get("roles"))) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getUserByToken")
    public ResponseEntity<?> checkUserByToken(@RequestBody String token) {
        try {
           return ResponseEntity.ok(userService.getUserFirstnameAndLastnameByUserEmail(jwtTokenProvider.getUserNameFromToken(token)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод для получения роли для токена
     *
     * @param token - JWT token для проверки
     * @author Тимур Рахматуллин
     */

    @PostMapping("/get_role_by_token")
    public ResponseEntity<String> getRoleByToken(@RequestBody String token) {
        String role = jwtTokenProvider.getUserRoleFromToken(token);
        if ((role != null) && (!role.isEmpty())  && (jwtTokenProvider.validateToken(token))) {
            return new ResponseEntity<>(role, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Токен не найден", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для получения валидации токена
     *
     * @param token - JWT token для проверки
     * @author Тимур Рахматуллин
     */

    @PostMapping("/validate_token")
    public ResponseEntity<Boolean> isTokenValid(@RequestBody String token) {

        if  ((token != null) && (!token.isEmpty()) && (jwtTokenProvider.validateToken(token))) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Контроллер для проверки существования юзера с переданным email
     * @param userEmail - Email пользователя, передается с фронта
     * @return true/false
     */
    @GetMapping("/login/{userEmail}")
    public ResponseEntity<Boolean> isExistsUser(@PathVariable(name = "userEmail") String userEmail) {
        if (userService.findByUsername(userEmail) != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Контроллер для отправки нового пароля Юзеру на почту
     * @return - Ответ с текстом body и http Статусом
     */
    @GetMapping("/login/password-recovery")
    public ResponseEntity<String> isSendPassword() {
        if (false) {
            return new ResponseEntity<>("Пароль успешно отправлен", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Пароль не отправлен", HttpStatus.BAD_REQUEST);
        }
    }

}