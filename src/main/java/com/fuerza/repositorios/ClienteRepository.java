package com.fuerza.repositorios;

import com.fuerza.modelo.Cliente;
import com.fuerza.modelo.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {


}