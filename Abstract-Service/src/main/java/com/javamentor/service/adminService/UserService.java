package com.javamentor.service.adminService;

import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    List<UserDto> getAllUsersDTO(String search);

    User getUserById(Long id);

    UserDto getUserDTOById(Long id);

    boolean existUserById(Long id);

    User findByUsername(String username);

    String getUserFirstnameAndLastnameByUserEmail(String name);

    boolean existsByEmail(String email);

    User getUserByEmail(String email);
}
