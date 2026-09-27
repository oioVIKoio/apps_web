package com.edu.tecsup.demo01.repository;
import com.edu.tecsup.demo01.model.AuditoriaLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<AuditoriaLog, Long> {
}
