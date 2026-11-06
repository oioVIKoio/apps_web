package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.client.ServicioClient;
import com.tecsup.dto.ReciboDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.EstadoPago;
import com.tecsup.model.Recibo;
import com.tecsup.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository repo;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ServicioClient servicioClient;

    public List<Recibo> listar(EstadoPago estadoPago) {
        if (estadoPago != null) {
            return repo.findByEstadoPago(estadoPago);
        }
        return repo.findAll();
    }

    public List<Recibo> listarPorCliente(Integer idCliente) {
        clienteClient.validarActivo(idCliente);
        return repo.findByIdCliente(idCliente);
    }

    public Recibo obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recibo no encontrado con id: " + id));
    }

    public Recibo registrar(ReciboDTO dto) {
        clienteClient.validarActivo(dto.getIdCliente());
        servicioClient.validarActivo(dto.getIdServicio());
        if (repo.existsByIdClienteAndIdServicioAndPeriodo(dto.getIdCliente(), dto.getIdServicio(), dto.getPeriodo())) {
            throw new BadRequestException("El cliente ya tiene un recibo de ese servicio en el periodo " + dto.getPeriodo());
        }
        Recibo recibo = new Recibo();
        copiar(dto, recibo);
        return repo.save(recibo);
    }

    public Recibo actualizar(Integer id, ReciboDTO dto) {
        Recibo recibo = obtener(id);
        validarPendiente(recibo);
        clienteClient.validarActivo(dto.getIdCliente());
        servicioClient.validarActivo(dto.getIdServicio());
        if (repo.existsByIdClienteAndIdServicioAndPeriodoAndIdReciboNot(
                dto.getIdCliente(), dto.getIdServicio(), dto.getPeriodo(), id)) {
            throw new BadRequestException("El cliente ya tiene un recibo de ese servicio en el periodo " + dto.getPeriodo());
        }
        copiar(dto, recibo);
        return repo.save(recibo);
    }

    public Recibo pagar(Integer id) {
        Recibo recibo = obtener(id);
        validarPendiente(recibo);
        recibo.setEstadoPago(EstadoPago.PAGADO);
        return repo.save(recibo);
    }

    public void eliminar(Integer id) {
        Recibo recibo = obtener(id);
        validarPendiente(recibo);
        repo.delete(recibo);
    }

    private void validarPendiente(Recibo recibo) {
        if (recibo.getEstadoPago() == EstadoPago.PAGADO) {
            throw new BadRequestException("El recibo " + recibo.getIdRecibo() + " ya está pagado");
        }
    }

    private void copiar(ReciboDTO dto, Recibo recibo) {
        recibo.setIdCliente(dto.getIdCliente());
        recibo.setIdServicio(dto.getIdServicio());
        recibo.setPeriodo(dto.getPeriodo());
        recibo.setMonto(dto.getMonto());
        recibo.setFechaVencimiento(dto.getFechaVencimiento());
    }
}
