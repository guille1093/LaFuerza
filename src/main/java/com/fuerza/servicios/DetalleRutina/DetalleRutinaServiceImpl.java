package com.fuerza.servicios.DetalleRutina;

import com.fuerza.modelo.DetalleRutina;
import com.fuerza.repositorios.DetalleRutinaRepository;
import com.fuerza.servicios.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DetalleRutinaServiceImpl implements DetalleRutinaService, CrudService<DetalleRutina>
{

    @Autowired
    private DetalleRutinaRepository detalleRutinaRepository;


    @Override
    public DetalleRutina save(DetalleRutina entity)
    {
        return detalleRutinaRepository.save(entity);
    }

    @Override
    public DetalleRutina update(DetalleRutina entity)
    {
        return detalleRutinaRepository.save(entity);
    }

    @Override
    public void delete(DetalleRutina entity)
    {
        detalleRutinaRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public DetalleRutina findById(Long id)
    {
        return detalleRutinaRepository.findById(id).orElse(null);
    }

    @Override
    public List<DetalleRutina> findAll()
    {
        return detalleRutinaRepository.findAll();
    }



    @Override
    public void deleteInBatch(List<DetalleRutina> users)
    {
        detalleRutinaRepository.deleteAll(users);
    }



    @Override
    public DetalleRutina findById(String id) {
        return null;
    }


    @Override
    public List<DetalleRutina> findBycliente_dni(String cliente) {
        return detalleRutinaRepository.findBycliente_dni(cliente);
    }

    @Override
    public List<DetalleRutina> findBycliente_dniAndAndRutinaDiaria_UnEntrenamiento_FechaInicio(String cliente, LocalDate fechaInicio) {
        return detalleRutinaRepository.findBycliente_dniAndAndRutinaDiaria_UnEntrenamiento_FechaInicio(cliente ,fechaInicio);
    }

    @Override
    public List<DetalleRutina> findBydiaAndCliente_dni( String dia, String cliente){
        return detalleRutinaRepository.findBydiaAndCliente_dni(dia, cliente);
    }


}
