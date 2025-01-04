package com.ahmad.e_learning.config;

import com.ahmad.e_learning.enums.UserRoles;
import com.ahmad.e_learning.model.Role;
import com.ahmad.e_learning.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            for (UserRoles userRole : UserRoles.values()) {
                roleRepository.findByName(userRole).orElseGet(() -> {
                    Role role = new Role();
                    role.setName(userRole);
                    return roleRepository.save(role);
                });
            }
        };
    }
}
