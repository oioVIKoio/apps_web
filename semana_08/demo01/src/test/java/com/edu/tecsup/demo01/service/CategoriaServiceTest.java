package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.exception.RecursoNoEncontradoException;
import com.edu.tecsup.demo01.model.Categoria;
import com.edu.tecsup.demo01.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repo;

    @InjectMocks
    private CategoriaService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // TEST LISTAR
    @Test
    void testListarCategorias() {
        when(repo.findAll()).thenReturn(List.of(new Categoria(1L, "Tecnología", "Equipos")));

        List<Categoria> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Tecnología", resultado.get(0).getNombre());
        verify(repo).findAll();
    }

    // TEST GUARDAR
    @Test
    void testGuardarCategoria() {
        Categoria categoria = new Categoria(1L, "Audio", "Parlantes y audífonos");
        when(repo.save(categoria)).thenReturn(categoria);

        Categoria resultado = service.guardar(categoria);

        assertEquals("Audio", resultado.getNombre());
        verify(repo).save(categoria);
    }

    // TEST OBTENER
    @Test
    void testObtenerCategoria() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Categoria(1L, "Oficina", "Útiles")));

        Categoria resultado = service.obtener(1L);

        assertEquals("Oficina", resultado.getNombre());
    }

    // TEST OBTENER CUANDO NO EXISTE
    @Test
    void testObtenerCategoriaNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtener(99L));
    }

    // TEST ACTUALIZAR
    @Test
    void testActualizarCategoria() {
        Categoria existente = new Categoria(1L, "Gaming", "Juegos");
        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(existente)).thenReturn(existente);

        Categoria resultado = service.actualizar(1L, new Categoria(null, "Gamer", "Periféricos"));

        assertEquals("Gamer", resultado.getNombre());
        assertEquals("Periféricos", resultado.getDescripcion());
    }

    // TEST ELIMINAR
    @Test
    void testEliminarCategoria() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Categoria(1L, "Audio", null)));

        service.eliminar(1L);

        verify(repo).deleteById(1L);
    }

    // TEST ELIMINAR CUANDO NO EXISTE
    @Test
    void testEliminarCategoriaNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.eliminar(99L));
        verify(repo, never()).deleteById(any());
    }
}
