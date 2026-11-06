package com.tecsup.client;

import com.tecsup.dto.EstadoRegistroDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ClienteClient {

    private final RestClient restClient;

    public ClienteClient(@Value("${servicios.cliente.url}") String url) {
        this.restClient = RestClient.create(url);
    }

    public void validarActivo(Integer id) {
        EstadoRegistroDTO respuesta;
        try {
            respuesta = restClient.get()
                    .uri("/api/clientes/{id}", id)
                    .retrieve()
                    .body(EstadoRegistroDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        } catch (RestClientException ex) {
            throw new ServicioNoDisponibleException("No se pudo comunicar con cliente-service");
        }
        if (!"A".equals(respuesta.getEstado())) {
            throw new BadRequestException("El cliente " + id + " está inactivo");
        }
    }
}
