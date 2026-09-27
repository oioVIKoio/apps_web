package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.model.AuditoriaLog;
import com.edu.tecsup.demo01.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository repo;

    public void registrar(String accion, String metodo, String detalle) {
        AuditoriaLog log = new AuditoriaLog(accion, metodo, detalle);
        repo.save(log);
    }

}

