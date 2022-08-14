package com.fuerza.servicios.EntrenamientoRealizado;

import com.fuerza.modelo.Entrenamiento;
import com.fuerza.modelo.EntrenamientoRealizado;
import com.fuerza.modelo.GrupoMuscular;

import java.time.LocalDate;
import java.util.List;

public interface EntrenamientoRealizadoService {

    List<EntrenamientoRealizado> findByfechaEntrenamientoRealizadoAndCliente_dni( LocalDate fecha_actual,String cliente);

    List<EntrenamientoRealizado> findByClienteDniAndEjercicios_grupoMusculares(String cliente, GrupoMuscular grupoMuscular);

}
