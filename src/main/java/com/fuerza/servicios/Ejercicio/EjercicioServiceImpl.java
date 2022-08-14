package com.fuerza.servicios.Ejercicio;

import com.fuerza.modelo.Ejercicio;
import com.fuerza.modelo.GrupoMuscular;
import com.fuerza.repositorios.EjercicioRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjercicioServiceImpl implements EjercicioService, CrudService<Ejercicio>
{

    @Autowired
    private EjercicioRepository EjercicioRepository;


    @Override
    public Ejercicio save(Ejercicio entity)
    {
        return EjercicioRepository.save(entity);
    }

    @Override
    public Ejercicio update(Ejercicio entity)
    {
        return EjercicioRepository.save(entity);
    }

    @Override
    public void delete(Ejercicio entity)
    {
        EjercicioRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public Ejercicio findById(String id)
    {
        return EjercicioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ejercicio> findAll()
    {
        return EjercicioRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<Ejercicio> users)
    {
        EjercicioRepository.deleteAll(users);
    }

    @Override
    public Ejercicio findById(Long id) {
        return null;
    }


    @Override
    public List<Ejercicio> findBygrupoMusculares_nombreGrupoMuscular(String grupoMusculares) {
        return EjercicioRepository.findBygrupoMusculares_nombreGrupoMuscular(grupoMusculares);
    }
}
