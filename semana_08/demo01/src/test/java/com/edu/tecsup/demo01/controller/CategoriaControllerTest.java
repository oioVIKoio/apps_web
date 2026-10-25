package com.edu.tecsup.demo01.controller;

import com.edu.tecsup.demo01.dto.CategoriaDTO;
import com.edu.tecsup.demo01.model.Categoria;
import com.edu.tecsup.demo01.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriaRepository repo;

    private CategoriaDTO dto(String nombre, String descripcion) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        return dto;
    }

    // TEST POST
    @Test
    void testGuardarCategoria() throws Exception {
        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto("Tecnología", "Equipos"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Tecnología"));
    }

    // TEST VALIDACIÓN
    @Test
    void testValidacionNombreVacio() throws Exception {
        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto("", "Sin nombre"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("El nombre es obligatorio"));
    }

    // TEST GET
    @Test
    void testListarCategorias() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
    }

    // TEST GET por ID
    @Test
    void testObtenerCategoria() throws Exception {
        Categoria guardada = repo.save(new Categoria(null, "Audio", "Parlantes"));

        mockMvc.perform(get("/api/categorias/" + guardada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Audio"));
    }

    // TEST GET CUANDO NO EXISTE
    @Test
    void testObtenerCategoriaNoExiste() throws Exception {
        mockMvc.perform(get("/api/categorias/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Categoría no encontrada: 9999"));
    }

    // TEST PUT
    @Test
    void testActualizarCategoria() throws Exception {
        Categoria guardada = repo.save(new Categoria(null, "Oficina", "Útiles"));

        mockMvc.perform(put("/api/categorias/" + guardada.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto("Oficina y escolar", "Útiles y papelería"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Oficina y escolar"));
    }

    // TEST DELETE
    @Test
    void testEliminarCategoria() throws Exception {
        Categoria guardada = repo.save(new Categoria(null, "Temporal", null));

        mockMvc.perform(delete("/api/categorias/" + guardada.getId()))
                .andExpect(status().isOk());

        assertFalse(repo.existsById(guardada.getId()));
    }

    // TEST DELETE CUANDO NO EXISTE
    @Test
    void testEliminarCategoriaNoExiste() throws Exception {
        mockMvc.perform(delete("/api/categorias/9999"))
                .andExpect(status().isNotFound());
    }
}
