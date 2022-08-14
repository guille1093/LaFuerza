package com.fuerza.servicios.Cliente;

import com.fuerza.modelo.Cliente;
import com.fuerza.repositorios.ClienteRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService, CrudService<Cliente>
{

    @Autowired
    private ClienteRepository ClienteRepository;


    @Override
    public Cliente save(Cliente entity)
    {
        return ClienteRepository.save(entity);
    }

    @Override
    public Cliente update(Cliente entity)
    {
        return ClienteRepository.save(entity);
    }

    @Override
    public void delete(Cliente entity)
    {
        ClienteRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public Cliente findById(String id)
    {
        return ClienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> findAll()
    {
        return ClienteRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<Cliente> users)
    {
        ClienteRepository.deleteAll(users);
    }

    @Override
    public Cliente findById(Long id) {
        return null;
    }

}
