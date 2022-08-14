package com.fuerza.servicios.RutinaDiaria;

import com.fuerza.modelo.RutinaDiaria;
import com.fuerza.repositorios.RutinaDiariaRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaDiariaServiceImpl implements RutinaDiariaService, CrudService<RutinaDiaria>{

    @Autowired
    private RutinaDiariaRepository rutinaDiariaRepository;


    @Override
    public RutinaDiaria save(RutinaDiaria entity)
    {
        return rutinaDiariaRepository.save(entity);
    }

    @Override
    public RutinaDiaria update(RutinaDiaria entity)
    {
        return rutinaDiariaRepository.save(entity);
    }

    @Override
    public void delete(RutinaDiaria entity)
    {
        rutinaDiariaRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) { }

    @Override
    public RutinaDiaria findById(Long id) { return rutinaDiariaRepository.findById(id).orElse(null); }

    @Override
    public List<RutinaDiaria> findAll()
    {
        return rutinaDiariaRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<RutinaDiaria> users)
    {
        rutinaDiariaRepository.deleteAll(users);
    }

    @Override
    public RutinaDiaria findById(String id) {return null; }



}
