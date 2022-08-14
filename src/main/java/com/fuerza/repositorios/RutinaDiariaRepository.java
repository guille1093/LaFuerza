package com.fuerza.repositorios;

import com.fuerza.modelo.RutinaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaDiariaRepository extends JpaRepository<RutinaDiaria, Long> {


}