package com.fuerza.repositorios;

import com.fuerza.modelo.Ejercicio;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EntityScan


public interface EjercicioRepository extends JpaRepository<Ejercicio, String> {


    List<Ejercicio> findBygrupoMusculares_nombreGrupoMuscular(String grupoMusculares);

}