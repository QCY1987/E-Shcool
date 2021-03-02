package com.javamentor.repository.user;

import com.javamentor.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
