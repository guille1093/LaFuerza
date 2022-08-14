package com.fuerza.servicios.Ejercicio;

import com.fuerza.modelo.Ejercicio;
import com.fuerza.modelo.GrupoMuscular;

import java.util.List;


public interface EjercicioService {

    List<Ejercicio> findBygrupoMusculares_nombreGrupoMuscular(String grupoMusculares);
}
