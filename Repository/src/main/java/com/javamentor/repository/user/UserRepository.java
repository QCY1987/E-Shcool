package com.javamentor.repository.user;

import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT new com.javamentor.dto.model.UserDto(t.id,t.firstName,t.lastName,t.email,t.registrationDate) FROM User t WHERE " +
            "t.lastName LIKE CONCAT('%', ?1 ,'%') " +
            "OR t.firstName LIKE CONCAT('%', ?1 ,'%')")
    List<UserDto> findAllUsersDTO(String search);

    @Query("SELECT CONCAT(u.firstName, '  ', u.lastName) FROM User u WHERE u.email = ?1")
    String getUserFirstnameAndLastnameByUserEmail(String userEmail);

    boolean existsByEmail(String email);
}
