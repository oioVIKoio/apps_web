package com.tecsup.services;

import com.tecsup.dto.ClienteDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Cliente;
import com.tecsup.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listar() {
        return repo.findByEstado("A");
    }

    public Cliente obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    public Cliente registrar(ClienteDTO dto) {
        if (repo.existsByDni(dto.getDni())) {
            throw new BadRequestException("Ya existe un cliente con el DNI " + dto.getDni());
        }
        Cliente cliente = new Cliente();
        copiar(dto, cliente);
        return repo.save(cliente);
    }

    public Cliente actualizar(Integer id, ClienteDTO dto) {
        Cliente cliente = obtener(id);
        if (repo.existsByDniAndIdClienteNot(dto.getDni(), id)) {
            throw new BadRequestException("Ya existe un cliente con el DNI " + dto.getDni());
        }
        copiar(dto, cliente);
        return repo.save(cliente);
    }

    public void eliminar(Integer id) {
        Cliente cliente = obtener(id);
        cliente.setEstado("I");
        repo.save(cliente);
    }

    private void copiar(ClienteDTO dto, Cliente cliente) {
        cliente.setDni(dto.getDni());
        cliente.setNombres(dto.getNombres());
        cliente.setApellidos(dto.getApellidos());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
    }
}
