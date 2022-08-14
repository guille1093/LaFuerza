package com.fuerza.servicios.EntrenamientoRealizado;

import com.fuerza.modelo.Entrenamiento;
import com.fuerza.modelo.EntrenamientoRealizado;
import com.fuerza.modelo.GrupoMuscular;
import com.fuerza.repositorios.EntrenamientoRealizadoRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EntrenamientoRealizadoServiceImpl implements EntrenamientoRealizadoService, CrudService<EntrenamientoRealizado>
{

    @Autowired
    private EntrenamientoRealizadoRepository entrenamientoRealizadoRepository;


    @Override
    public EntrenamientoRealizado save(EntrenamientoRealizado entity)
    {
        return entrenamientoRealizadoRepository.save(entity);
    }

    @Override
    public EntrenamientoRealizado update(EntrenamientoRealizado entity)
    {
        return entrenamientoRealizadoRepository.save(entity);
    }

    @Override
    public void delete(EntrenamientoRealizado entity)
    {
        entrenamientoRealizadoRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public EntrenamientoRealizado findById(String id)
    {
        return entrenamientoRealizadoRepository.findById(id).orElse(null);
    }

    @Override
    public List<EntrenamientoRealizado> findAll()
    {
        return entrenamientoRealizadoRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<EntrenamientoRealizado> users)
    {
        entrenamientoRealizadoRepository.deleteAll(users);
    }

    @Override
    public EntrenamientoRealizado findById(Long id) {
        return null;
    }


    @Override
    public List<EntrenamientoRealizado> findByfechaEntrenamientoRealizadoAndCliente_dni( LocalDate fecha_actual,String cliente) {
        return entrenamientoRealizadoRepository.findByfechaEntrenamientoRealizadoAndCliente_dni(fecha_actual, cliente);
    }

    @Override
    public List<EntrenamientoRealizado> findByClienteDniAndEjercicios_grupoMusculares(String cliente, GrupoMuscular grupoMuscular) {
        return entrenamientoRealizadoRepository.findByClienteDniAndEjercicios_grupoMusculares(cliente, grupoMuscular);
    }
}
