package com.javamentor.controller.rest.admin.user;

import com.javamentor.service.adminService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService adminService) {
        this.userService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserList(@RequestHeader("search") String search) {
        return ResponseEntity.ok(userService.getAllUsersDTO(search));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "userId") Long userId) {
        if (userService.existUserById(userId)) {
            return ResponseEntity.ok(userService.getUserById(userId));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
