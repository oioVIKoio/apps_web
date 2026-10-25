package com.tecsup;

import com.tecsup.entity.Role;
import com.tecsup.repository.RoleRepository;
import com.tecsup.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthJwtTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        for (String nombre : new String[]{"USER", "ADMIN", "MODERATOR"}) {
            Role role = new Role();
            role.setName(nombre);
            roleRepository.save(role);
        }
        signup("admin", "admin@test.com", "ADMIN");
        signup("user1", "user@test.com", "USER");
    }

    private void signup(String username, String email, String rol) throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"email\":\"" + email
                                + "\",\"password\":\"123456\",\"roles\":[\"" + rol + "\"]}"))
                .andExpect(status().isCreated());
    }

    private String token(String email) throws Exception {
        String json = mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode body = objectMapper.readTree(json);
        return body.get("accessToken").asString();
    }

    // SIGNUP DUPLICADO
    @Test
    void testSignupUsernameRepetido() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"email\":\"otro@test.com\",\"password\":\"123456\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Username ya existe"));
    }

    // SIGNUP CON DATOS INVALIDOS (@Valid)
    @Test
    void testSignupDatosInvalidos() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ab\",\"email\":\"no-es-correo\",\"password\":\"123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("El email no es válido"));
    }

    // LOGIN CORRECTO
    @Test
    void testSigninDevuelveToken() throws Exception {
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@test.com\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    // LOGIN CON CREDENCIALES INCORRECTAS
    @Test
    void testSigninCredencialesIncorrectas() throws Exception {
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@test.com\",\"password\":\"clave_mala\"}"))
                .andExpect(status().isUnauthorized());
    }

    // CASO 1: RUTA PUBLICA
    @Test
    void testCaso1RutaPublica() throws Exception {
        mockMvc.perform(get("/api/test/all"))
                .andExpect(status().isOk())
                .andExpect(content().string("Public Content"));
    }

    // CASO 2: SIN TOKEN
    @Test
    void testCaso2SinToken() throws Exception {
        mockMvc.perform(get("/api/test/user"))
                .andExpect(status().isUnauthorized());
    }

    // CASO 3: TOKEN INVALIDO
    @Test
    void testCaso3TokenInvalido() throws Exception {
        String jwt = token("user@test.com");
        String modificado = jwt.substring(0, jwt.length() - 1) + (jwt.endsWith("A") ? "B" : "A");

        mockMvc.perform(get("/api/test/user")
                        .header("Authorization", "Bearer " + modificado))
                .andExpect(status().isUnauthorized());
    }

    // CASO 4: TOKEN VALIDO
    @Test
    void testCaso4TokenValido() throws Exception {
        mockMvc.perform(get("/api/test/user")
                        .header("Authorization", "Bearer " + token("user@test.com")))
                .andExpect(status().isOk())
                .andExpect(content().string("User Content"));
    }

    // CASO 5: USER EN RUTA ADMIN
    @Test
    void testCaso5UserEnRutaAdmin() throws Exception {
        mockMvc.perform(get("/api/test/admin")
                        .header("Authorization", "Bearer " + token("user@test.com")))
                .andExpect(status().isForbidden());
    }

    // CASO 6: ADMIN EN RUTA ADMIN
    @Test
    void testCaso6AdminEnRutaAdmin() throws Exception {
        mockMvc.perform(get("/api/test/admin")
                        .header("Authorization", "Bearer " + token("admin@test.com")))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin Content"));
    }
}
