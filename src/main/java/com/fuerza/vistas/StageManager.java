package com.fuerza.vistas;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

public class StageManager
{
    private static final Logger LOG = getLogger(StageManager.class);

    @Autowired
    private FxWeaver fxWeaver;

    private final Stage primaryStage;

    public StageManager(Stage stage)
    {
        this.primaryStage = stage;
    }

    public void rebuildStage(Class<? extends FxController> fxControllerClass)
    {
        Scene scene = createScene(fxControllerClass);
        //scene.getStylesheets().add("/styles/Styles.css");
        showScene(fxControllerClass, scene);

    }

    private Scene createScene(Class<? extends FxController> fxControllerClass)
    {
        Parent node = fxWeaver.loadView(fxControllerClass);
        Scene scene1 = primaryStage.getScene();

        if (scene1 == null)
        {
            scene1 = new Scene(node);
        }
        scene1.setRoot(node);
        return scene1;
    }

    private void showScene(Class<? extends FxController> fxControllerClass, Scene scene)
    {
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("La Fuerza");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/icon.png"));

        try
        {
            primaryStage.show();
        } catch (Exception exception)
        {
            logAndExit("No se puede mostrar escena alto error perri", exception);
        }
    }

    private void logAndExit(String errorMsg, Exception exception)
    {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    public <C, V extends Node> V loadView(Class<C> controllerClass)
    {
        return fxWeaver.loadView(controllerClass);
    }
}
