package com.tecsup;

import com.tecsup.entity.Empleado;
import com.tecsup.repository.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository repo;

    private static final String JSON =
            "{\"nombres\":\"Ana\",\"apellidos\":\"Torres\",\"email\":\"ana@techsolutions.pe\",\"sueldo\":3500.50}";

    private Empleado guardar() {
        Empleado e = new Empleado();
        e.setNombres("Luis");
        e.setApellidos("Rojas");
        e.setEmail("luis@techsolutions.pe");
        e.setSueldo(new BigDecimal("2800.00"));
        return repo.save(e);
    }

    // POST
    @Test
    void testCrearEmpleado() throws Exception {
        mockMvc.perform(post("/empleados").contentType(MediaType.APPLICATION_JSON).content(JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sueldo").value(3500.50));
    }

    // POST con datos inválidos
    @Test
    void testCrearEmpleadoInvalido() throws Exception {
        mockMvc.perform(post("/empleados").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombres\":\"\",\"apellidos\":\"Torres\",\"email\":\"no-es-correo\",\"sueldo\":-5}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("El email no es válido"))
                .andExpect(jsonPath("$.sueldo").value("El sueldo debe ser mayor a 0"));
    }

    // GET
    @Test
    void testListarEmpleados() throws Exception {
        guardar();
        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // GET por ID
    @Test
    void testObtenerEmpleado() throws Exception {
        Empleado e = guardar();
        mockMvc.perform(get("/empleados/" + e.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Luis"));
    }

    // GET cuando no existe
    @Test
    void testObtenerEmpleadoNoExiste() throws Exception {
        mockMvc.perform(get("/empleados/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Empleado no encontrado: 9999"));
    }

    // PUT
    @Test
    void testActualizarEmpleado() throws Exception {
        Empleado e = guardar();
        mockMvc.perform(put("/empleados/" + e.getId()).contentType(MediaType.APPLICATION_JSON).content(JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Ana"));
    }

    // DELETE
    @Test
    void testEliminarEmpleado() throws Exception {
        Empleado e = guardar();
        mockMvc.perform(delete("/empleados/" + e.getId()))
                .andExpect(status().isOk());
        assertFalse(repo.existsById(e.getId()));
    }

    // CONFIGURACION
    @Test
    void testConfiguracion() throws Exception {
        mockMvc.perform(get("/configuracion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ambiente").value("PRUEBAS AUTOMATICAS"))
                .andExpect(jsonPath("$.perfil").value("h2"))
                .andExpect(jsonPath("$.baseDatos").exists());
    }
}
