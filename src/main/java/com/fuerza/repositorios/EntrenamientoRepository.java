package com.fuerza.repositorios;

import com.fuerza.modelo.Cliente;
import com.fuerza.modelo.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
    public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Long> {


    List<Entrenamiento> findByfinalizadoAndCliente_Dni(Boolean finalizado, String dni_cliente);

    Entrenamiento findByCliente_dni(String cliente);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.entrenamiento SET finalizado_entrenamiento = true, nota_entrenamiento= :nota " +
            "WHERE dni_cliente = :dniCliente AND finalizado_entrenamiento = false" , nativeQuery = true)
    int updateNota(@Param("nota") Double nota, @Param("dniCliente") String dniCliente);
}