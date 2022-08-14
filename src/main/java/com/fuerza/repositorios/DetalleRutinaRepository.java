package com.fuerza.repositorios;

import com.fuerza.modelo.DetalleRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleRutinaRepository extends JpaRepository<DetalleRutina, Long> {


    List<DetalleRutina> findBycliente_dni(String cliente);

    List<DetalleRutina> findBycliente_dniAndAndRutinaDiaria_UnEntrenamiento_FechaInicio(String cliente, LocalDate fechaInicio);

    List<DetalleRutina> findBydiaAndCliente_dni( String dia, String cliente);

}