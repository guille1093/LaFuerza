/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuerza.springboot;

import com.fuerza.vistas.StageManager;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class AppConfig
{
    @Bean
    @Lazy(value = true) // EL Stage Manager es un bean que es creado solo luego de iniciado el Spring Context
    public StageManager stageManager(Stage stage) throws IOException
    {
        return new StageManager(stage);
    }

}
