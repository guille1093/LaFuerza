package com.fuerza.servicios.GrupoMuscular;

import com.fuerza.modelo.GrupoMuscular;
import com.fuerza.repositorios.GrupoMuscularRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoMuscularServiceImpl implements GrupoMuscularService, CrudService<GrupoMuscular>
{

    @Autowired
    private GrupoMuscularRepository grupoMuscularRepository;


    @Override
    public GrupoMuscular save(GrupoMuscular entity)
    {
        return grupoMuscularRepository.save(entity);
    }

    @Override
    public GrupoMuscular update(GrupoMuscular entity)
    {
        return grupoMuscularRepository.save(entity);
    }

    @Override
    public void delete(GrupoMuscular entity)
    {
        grupoMuscularRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public GrupoMuscular findById(String id)
    {
        return grupoMuscularRepository.findById(id).orElse(null);
    }

    @Override
    public List<GrupoMuscular> findAll()
    {
        return grupoMuscularRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<GrupoMuscular> users)
    {
        grupoMuscularRepository.deleteAll(users);
    }

    @Override
    public GrupoMuscular findById(Long id) {
        return null;
    }

}
