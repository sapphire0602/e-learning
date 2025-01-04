package com.ahmad.e_learning.repository;

import com.ahmad.e_learning.enums.UserRoles;
import com.ahmad.e_learning.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoles name);
}
