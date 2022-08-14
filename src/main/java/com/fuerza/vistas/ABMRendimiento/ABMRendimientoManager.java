package com.fuerza.vistas.ABMRendimiento;

import com.fuerza.MainApp;
import com.fuerza.servicios.Cliente.ClienteServiceImpl;
import com.fuerza.servicios.Entrenador.EntrenadorServiceImpl;
import com.fuerza.servicios.Entrenamiento.EntrenamientoServiceImpl;
import com.fuerza.vistas.ABMEjercicioRealizado.ABMEjercicioRealizado;
import com.fuerza.vistas.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ABMRendimientoManager {

    @Autowired
    MainApp mainApp;

    @Autowired
    @Lazy
    private EntrenamientoServiceImpl entrenamientoService;


    @Lazy
    @Autowired
    private StageManager stageManager;

    public void volver() {
        stageManager.rebuildStage (ABMEjercicioRealizado.class);
    }
    }


