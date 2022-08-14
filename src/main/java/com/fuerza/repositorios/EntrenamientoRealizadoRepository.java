package com.fuerza.repositorios;

import com.fuerza.modelo.EntrenamientoRealizado;
import com.fuerza.modelo.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntrenamientoRealizadoRepository extends JpaRepository<EntrenamientoRealizado, String> {

    List<EntrenamientoRealizado> findByfechaEntrenamientoRealizadoAndCliente_dni(LocalDate fecha_actual, String cliente);

    List<EntrenamientoRealizado> findByClienteDniAndEjercicios_grupoMusculares(String cliente, GrupoMuscular grupoMuscular);
}