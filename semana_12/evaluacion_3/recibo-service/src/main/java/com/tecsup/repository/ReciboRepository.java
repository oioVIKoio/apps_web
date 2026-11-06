package com.tecsup.repository;

import com.tecsup.model.EstadoPago;
import com.tecsup.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReciboRepository extends JpaRepository<Recibo, Integer> {

    List<Recibo> findByIdCliente(Integer idCliente);

    List<Recibo> findByEstadoPago(EstadoPago estadoPago);

    boolean existsByIdClienteAndIdServicioAndPeriodo(Integer idCliente, Integer idServicio, String periodo);

    boolean existsByIdClienteAndIdServicioAndPeriodoAndIdReciboNot(Integer idCliente, Integer idServicio,
                                                                  String periodo, Integer idRecibo);
}
