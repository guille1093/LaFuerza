package com.fuerza.springboot;

import com.fuerza.MainApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * * inicializa Spring Boot dentro del metodo init()
 * * inicializa JavaFx dentro del metodo start()
 */
public class SpringbootJavaFxApplication extends Application
{

    private ConfigurableApplicationContext context;

    // 3. Hilo de JavaFx. Iniciado en MainApp
    @Override
    public void init() throws Exception
    {

        ApplicationContextInitializer<GenericApplicationContext> initializer =
                context ->
                {
                    context.registerBean(Application.class, () -> SpringbootJavaFxApplication.this); // registramos la clase como un Spring bean
                };

        // 4. Inicializamos la aplicacion Spring:
        // *  todas las clases @SpringBootApplication y @Configuration son iniciadas
        this.context = new SpringApplicationBuilder()
                .sources(MainApp.class) // clase principal de la aplicacion
                .initializers(initializer) // registramos el ApplicationContextInitializer
                .run(getParameters().getRaw().toArray(new String[0])); // iniciamos la aplicacion Spring
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // 5. Se termina la iniciacion de spring en el metodo init(). Ahora spring genera el ApplicationEvent que contiene la escena primaria.
        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception
    {
        this.context.close();
        Platform.exit();
    }
}
