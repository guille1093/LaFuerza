package com.fuerza.servicios.DetalleRutina;

import com.fuerza.modelo.DetalleRutina;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DetalleRutinaService {
    List<DetalleRutina> findBycliente_dni( String cliente);
    List<DetalleRutina> findBycliente_dniAndAndRutinaDiaria_UnEntrenamiento_FechaInicio(String cliente, LocalDate fechaInicio);

    List<DetalleRutina> findBydiaAndCliente_dni( String dia, String cliente);

}
