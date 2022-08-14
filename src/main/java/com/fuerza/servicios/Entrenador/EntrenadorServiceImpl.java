package com.fuerza.servicios.Entrenador;

import com.fuerza.modelo.Entrenador;
import com.fuerza.repositorios.EntrenadorRepository;
import com.fuerza.servicios.CrudService;
import com.fuerza.servicios.Entrenador.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorServiceImpl implements EntrenadorService, CrudService<Entrenador>
{

    @Autowired
    private EntrenadorRepository EntrenadorRepository;


    @Override
    public Entrenador save(Entrenador entity) //ver si no es ncesario devolver void
    {
        return EntrenadorRepository.save(entity);
    }

    @Override
    public Entrenador update(Entrenador entity)
    {
        return EntrenadorRepository.save(entity);
    }

    @Override
    public void delete(Entrenador entity)
    {
        EntrenadorRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public Entrenador findById(String id)
    {
        return EntrenadorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Entrenador> findAll()
    {
        return EntrenadorRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<Entrenador> users)
    {
        EntrenadorRepository.deleteAll(users);
    }

    @Override
    public Entrenador findById(Long id) {
        return null;
    }

}
