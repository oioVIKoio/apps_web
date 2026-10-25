package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.model.Producto;
import com.edu.tecsup.demo01.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // TEST LISTAR
    @Test
    void testListarProductos() {
        List<Producto> lista = List.of(
                new Producto(1L, "Monitor", 500, 10, "Tecnología")
        );
        when(repo.findAll()).thenReturn(lista);

        List<Producto> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Monitor", resultado.get(0).getNombre());
        verify(repo, times(1)).findAll();
    }

    // TEST GUARDAR
    @Test
    void testGuardarProducto() {
        Producto producto = new Producto(1L, "Laptop", 3000, 5, "Tecnología");
        when(repo.save(producto)).thenReturn(producto);

        Producto resultado = service.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        verify(repo).save(producto);
    }

    // TEST OBTENER
    @Test
    void testObtenerProducto() {
        Producto producto = new Producto(1L, "Teclado", 100, 20, "Tecnología");
        when(repo.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals("Teclado", resultado.getNombre());
        verify(repo).findById(1L);
    }

    // TEST ELIMINAR
    @Test
    void testEliminarProducto() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    // TEST ACTUALIZAR (actividad)
    @Test
    void testActualizarProducto() {
        Producto existente = new Producto(1L, "Mouse", 50, 10, "Tecnología");
        Producto datos = new Producto(null, "Mouse Gamer", 120, 8, "Gaming");
        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(existente)).thenReturn(existente);

        Producto resultado = service.actualizar(1L, datos);

        assertEquals("Mouse Gamer", resultado.getNombre());
        assertEquals(120, resultado.getPrecio());
        assertEquals(8, resultado.getStock());
        assertEquals("Gaming", resultado.getCategoria());
        verify(repo).save(existente);
    }

    // TEST ACTUALIZAR CUANDO NO EXISTE (actividad)
    @Test
    void testActualizarProductoNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = service.actualizar(99L, new Producto());

        assertNull(resultado);
        verify(repo, never()).save(any());
    }

    // TEST OBTENER CUANDO NO EXISTE (actividad)
    @Test
    void testObtenerProductoNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = service.obtener(99L);

        assertNull(resultado);
        verify(repo).findById(99L);
    }
}
