package com.fuerza.vistas.MainApp;

import com.fuerza.vistas.ABMCliente.ABMCliente;
import com.fuerza.vistas.ABMEjercicio.ABMEjercicio;
import com.fuerza.vistas.ABMEjercicioRealizado.ABMEjercicioRealizado;
import com.fuerza.vistas.ABMEntrenador.ABMEntrenador;
import com.fuerza.vistas.ABMRutina.ABMRutina;
import com.fuerza.vistas.FxController;
import com.fuerza.vistas.RutinaActiva.RutinaActiva;
import com.fuerza.vistas.StageManager;
import com.fuerza.vistas.login.LoginPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class OperacionesSistema implements Initializable, FxController {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private Button botonRegistrarEntrenador;

    @FXML
    private Button botonRegistrarCliente;

    @FXML
    private Button botonCrearRutina;

    @FXML
    private Button botonCargarEntrenamientoRealizado;

    @FXML
    private Button botonRegistrarEjercicio;

    @FXML
    private Button botonRutinaActiva;

/*
    @FXML
    private Button botonRegistrarEntrenador;

    @FXML
    private Button botonRegistrarEntrenador;*/

    @FXML
    private Button botonCerrarSesion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        botonRegistrarEntrenador.setOnAction(event ->
        {
            stageManager.rebuildStage(ABMEntrenador.class);
        });

        botonRegistrarCliente.setOnAction(event ->
        {
            stageManager.rebuildStage(ABMCliente.class);
        });

        botonRegistrarEjercicio.setOnAction(event ->
        {
            stageManager.rebuildStage(ABMEjercicio.class);
        });

        botonCrearRutina.setOnAction(event ->
        {
            stageManager.rebuildStage(ABMRutina.class);
        });

        botonCargarEntrenamientoRealizado.setOnAction(event ->
        {
            stageManager.rebuildStage(ABMEjercicioRealizado.class);
        });

        botonRutinaActiva.setOnAction(event ->
        {
            stageManager.rebuildStage(RutinaActiva.class);
        });

        botonCerrarSesion.setOnAction(event ->
        {
            stageManager.rebuildStage(LoginPane.class);
        });

    }
}
