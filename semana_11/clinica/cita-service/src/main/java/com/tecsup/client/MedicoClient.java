package com.tecsup.client;

import com.tecsup.dto.EstadoPersonaDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class MedicoClient {

    private final RestClient restClient;

    public MedicoClient(@Value("${servicios.medico.url}") String url) {
        this.restClient = RestClient.create(url);
    }

    public void validarActivo(Integer id) {
        EstadoPersonaDTO respuesta;
        try {
            respuesta = restClient.get()
                    .uri("/api/medicos/{id}", id)
                    .retrieve()
                    .body(EstadoPersonaDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Médico no encontrado con id: " + id);
        } catch (RestClientException ex) {
            throw new ServicioNoDisponibleException("No se pudo comunicar con medico-service");
        }
        if (!"A".equals(respuesta.getEstado())) {
            throw new BadRequestException("El médico " + id + " está inactivo");
        }
    }
}
