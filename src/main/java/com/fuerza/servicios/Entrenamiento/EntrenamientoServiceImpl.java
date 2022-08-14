package com.fuerza.servicios.Entrenamiento;

import com.fuerza.modelo.Cliente;
import com.fuerza.modelo.Entrenamiento;
import com.fuerza.repositorios.EntrenamientoRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EntrenamientoServiceImpl implements EntrenamientoService, CrudService<Entrenamiento>
{

    @Autowired
    private EntrenamientoRepository entrenamientoRepository;


    @Override
    public Entrenamiento save(Entrenamiento entity)
    {
        return entrenamientoRepository.save(entity);
    }

    @Override
    public Entrenamiento update(Entrenamiento entity)
    {
        return entrenamientoRepository.save(entity);
    }

    @Override
    public void delete(Entrenamiento entity)
    {
        entrenamientoRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Entrenamiento findById(String id)
    {
        return null;
    }

    @Override
    public List<Entrenamiento> findAll()
    {
        return entrenamientoRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<Entrenamiento> users)
    {
        entrenamientoRepository.deleteAll(users);
    }



    @Override
    public Entrenamiento findById(Long id) {
        return entrenamientoRepository.findById(id).orElse(null);
    }


    @Override
    public List<Entrenamiento> findByfinalizadoAndCliente_Dni(Boolean finalizado, String dni_cliente) {
        return entrenamientoRepository.findByfinalizadoAndCliente_Dni(finalizado, dni_cliente);
    }
    @Override
    public Entrenamiento findByCliente_dni(String cliente) {
        return entrenamientoRepository.findByCliente_dni(cliente);
    }

    @Override
    @Transactional
    public int updateNota(Double nota, String dniCliente) {
        return entrenamientoRepository.updateNota(nota, dniCliente);
    }
}
