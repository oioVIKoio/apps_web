package com.edu.tecsup.demo01.util;

import com.edu.tecsup.demo01.model.Role;
import com.edu.tecsup.demo01.model.User;
import com.edu.tecsup.demo01.repository.RoleRepository;
import com.edu.tecsup.demo01.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepo,
                               RoleRepository roleRepo,
                               PasswordEncoder encoder,
                               @Value("${app.seed.user-password}") String userPassword,
                               @Value("${app.seed.admin-password}") String adminPassword,
                               @Value("${app.seed.manager-password}") String managerPassword) {
        return args -> {
            Role roleUser = findOrCreateRole(roleRepo, "ROLE_USER");
            Role roleAdmin = findOrCreateRole(roleRepo, "ROLE_ADMIN");
            Role roleManager = findOrCreateRole(roleRepo, "ROLE_MANAGER");

            provisionUser(userRepo, encoder, "user", userPassword, roleUser);
            provisionUser(userRepo, encoder, "admin", adminPassword, roleAdmin);
            provisionUser(userRepo, encoder, "manager", managerPassword, roleManager);
        };
    }

    private Role findOrCreateRole(RoleRepository roleRepo, String roleName) {
        return roleRepo.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            return roleRepo.save(role);
        });
    }

    private void provisionUser(UserRepository userRepo,
                               PasswordEncoder encoder,
                               String username,
                               String password,
                               Role role) {
        User user = userRepo.findByUsername(username).orElseGet(User::new);
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRoles(Set.of(role));
        userRepo.save(user);
    }
}
