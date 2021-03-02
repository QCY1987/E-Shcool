package com.javamentor.service.adminService;

import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.User;
import com.javamentor.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository adminRepository) {
        this.userRepository = adminRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getAllUsersDTO(String search) {
        return userRepository.findAllUsersDTO(search);
    }

    @Override
    public UserDto getUserDTOById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        return new UserDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existUserById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public String getUserFirstnameAndLastnameByUserEmail(String username) {
        return userRepository.getUserFirstnameAndLastnameByUserEmail(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
