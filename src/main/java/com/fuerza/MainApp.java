package com.fuerza;

import com.fuerza.springboot.SpringbootJavaFxApplication;
import com.fuerza.springboot.StageReadyEvent;
import com.fuerza.vistas.StageManager;
import com.fuerza.vistas.login.LoginPane;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class MainApp implements ApplicationListener<StageReadyEvent>
{
    private static StageManager STAGE_MANAGER;


    @Autowired
    ConfigurableApplicationContext springAppContext;

    // 1. Inicia la aplicacion Java. SpringContext y JavaFx aún no se inician en esta etapa
    public static void main(String[] args)
    {
        // 2. Iniciamos JavaFX en otro hilo llamando a com.sun.javafx.application.LauncherImpl#run()
        Application.launch(SpringbootJavaFxApplication.class, args);
    }

    // 6. Metodo callback. Tomando el evento producido por el metodo SpringBootJavaFxApplication#start(), una vez que Spring y JavaFx se hayan iniciado
    @Override
    public void onApplicationEvent(StageReadyEvent event)
    {
        STAGE_MANAGER = springAppContext.getBean(StageManager.class, event.stage);
        //STAGE_MANAGER.rebuildStage(LoginPane.class);
        STAGE_MANAGER.rebuildStage(LoginPane.class);
    }

    public static StageManager getStageManager()
    {
        return STAGE_MANAGER;
    }

    public String S;
}
