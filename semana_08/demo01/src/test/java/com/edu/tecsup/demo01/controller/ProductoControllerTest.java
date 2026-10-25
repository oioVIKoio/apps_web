package com.edu.tecsup.demo01.controller;

import com.edu.tecsup.demo01.dto.ProductoDTO;
import com.edu.tecsup.demo01.model.Producto;
import com.edu.tecsup.demo01.repository.ProductoRepository;
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
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductoRepository repo;

    // TEST POST
    @Test
    void testGuardarProducto() throws Exception {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Mouse");
        dto.setPrecio(80);
        dto.setStock(20);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    // TEST GET
    @Test
    void testListarProductos() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    // TEST VALIDACIÓN
    @Test
    void testValidacionNombreVacio() throws Exception {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("");
        dto.setPrecio(10);
        dto.setStock(2);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // TEST PUT (actividad)
    @Test
    void testActualizarProducto() throws Exception {
        Producto guardado = repo.save(new Producto(null, "Teclado", 100, 10, "Tecnología"));

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Teclado Mecánico");
        dto.setPrecio(250);
        dto.setStock(5);

        mockMvc.perform(put("/api/productos/" + guardado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Teclado Mecánico"))
                .andExpect(jsonPath("$.precio").value(250.0));
    }

    // TEST PUT CUANDO NO EXISTE (actividad)
    @Test
    void testActualizarProductoNoExiste() throws Exception {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Nada");
        dto.setPrecio(10);
        dto.setStock(1);

        mockMvc.perform(put("/api/productos/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // TEST DELETE (actividad)
    @Test
    void testEliminarProducto() throws Exception {
        Producto guardado = repo.save(new Producto(null, "Parlante", 90, 3, "Audio"));

        mockMvc.perform(delete("/api/productos/" + guardado.getId()))
                .andExpect(status().isOk());

        assertFalse(repo.existsById(guardado.getId()));
    }

    // TEST DELETE CUANDO NO EXISTE (actividad)
    @Test
    void testEliminarProductoNoExiste() throws Exception {
        mockMvc.perform(delete("/api/productos/9999"))
                .andExpect(status().isNotFound());
    }
}
