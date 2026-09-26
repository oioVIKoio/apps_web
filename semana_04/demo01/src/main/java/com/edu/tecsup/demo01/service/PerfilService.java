package com.edu.tecsup.demo01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.edu.tecsup.demo01.models.Perfil;
import com.edu.tecsup.demo01.repository.PerfilRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepo;

    public List<Perfil> listar() {
        return perfilRepo.findAll();
    }
    public Perfil guardar(Perfil perfil){
        return perfilRepo.save(perfil);
    }

    public Perfil obtener(Long id) {
        return perfilRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado."));
    }
    public void eliminar(Long id) {
        perfilRepo.deleteById(id);
    }
}
