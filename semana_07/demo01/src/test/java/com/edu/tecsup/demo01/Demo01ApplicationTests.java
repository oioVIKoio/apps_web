package com.edu.tecsup.demo01;

import org.junit.jupiter.api.Test;
import com.edu.tecsup.demo01.model.User;
import com.edu.tecsup.demo01.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:demo01;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.seed.user-password=test-user-password",
        "app.seed.admin-password=test-admin-password",
        "app.seed.manager-password=test-manager-password"
})
@AutoConfigureMockMvc
class Demo01ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void seedUsersHaveUpdatedEncodedPasswords() {
        assertSeedUser("user", "test-user-password", "ROLE_USER");
        assertSeedUser("admin", "test-admin-password", "ROLE_ADMIN");
        assertSeedUser("manager", "test-manager-password", "ROLE_MANAGER");
    }

    @Test
    void freeEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/api/free"))
                .andExpect(status().isOk())
                .andExpect(content().string("Endpoint público funcionando"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void managementDashboardRequiresAdmin() throws Exception {
        mockMvc.perform(get("/management/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void clientHomeAllowsUser() throws Exception {
        mockMvc.perform(get("/client/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void managerReportsRejectNonManagers() throws Exception {
        mockMvc.perform(get("/manager/reportes"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void managerReportsAllowManager() throws Exception {
        mockMvc.perform(get("/manager/reportes"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reportes para MANAGER"));
    }

    private void assertSeedUser(String username, String rawPassword, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow();
        assertThat(passwordEncoder.matches(rawPassword, user.getPassword())).isTrue();
        assertThat(user.getRoles()).extracting("name").containsExactly(roleName);
    }
}
