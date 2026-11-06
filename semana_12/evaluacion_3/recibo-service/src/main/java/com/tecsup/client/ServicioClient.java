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
public class ServicioClient {

    private final RestClient restClient;

    public ServicioClient(@Value("${servicios.servicio.url}") String url) {
        this.restClient = RestClient.create(url);
    }

    public void validarActivo(Integer id) {
        EstadoRegistroDTO respuesta;
        try {
            respuesta = restClient.get()
                    .uri("/api/servicios/{id}", id)
                    .retrieve()
                    .body(EstadoRegistroDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Servicio no encontrado con id: " + id);
        } catch (RestClientException ex) {
            throw new ServicioNoDisponibleException("No se pudo comunicar con servicio-service");
        }
        if (!"A".equals(respuesta.getEstado())) {
            throw new BadRequestException("El servicio " + id + " está inactivo");
        }
    }
}
