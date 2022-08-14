package com.fuerza.servicios.Entrenamiento;

import com.fuerza.modelo.Cliente;
import com.fuerza.modelo.Entrenamiento;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EntrenamientoService {

    List<Entrenamiento> findByfinalizadoAndCliente_Dni(Boolean finalizado, String dni_cliente);
    Entrenamiento findByCliente_dni(String cliente);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.entrenamiento SET finalizado_entrenamiento = true, nota_entrenamiento= :nota " +
            "WHERE dni_cliente = :dniCliente AND finalizado_entrenamiento = false" , nativeQuery = true)
    int updateNota(@Param("nota") Double nota, @Param("dniCliente") String dniCliente);
}
