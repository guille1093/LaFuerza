package com.fuerza.vistas.RutinaSemanalActiva;

import com.fuerza.vistas.FxController;
import com.fuerza.vistas.RutinaActiva.RutinaActiva;
import com.fuerza.vistas.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class RutinaSemanalActiva implements Initializable, FxController {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    @Lazy
    private RutinaSemanalActivaManager rutinaSemanalActivaManager;

    @FXML
    public Button BTvolverRSA;
    @FXML
    public Button dnibt;
    @FXML
    public TextField tfdni;

    @FXML
    public Label LnombreRSA;

    @FXML
    public TableView TdatosRSA;
    @FXML
    public TableColumn TCdiaRSA;
    @FXML
    public TableColumn TCejercicioRSA;
    @FXML
    public TableColumn TCseriesRSA;
    @FXML
    public TableColumn TCrepeticionesRSA;
    @FXML
    public TableColumn TCdescansoRSA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rutinaSemanalActivaManager.funcionColocarImagen();

        // Se asocia para poder mostrar la info de la base de datos
        TCdiaRSA.setCellValueFactory(new PropertyValueFactory<>("Dia"));
        TCejercicioRSA.setCellValueFactory(new PropertyValueFactory<>("Ejercicios"));
        TCseriesRSA.setCellValueFactory(new PropertyValueFactory<>("Series"));
        TCrepeticionesRSA.setCellValueFactory(new PropertyValueFactory<>("Repeticiones"));
        TCdescansoRSA.setCellValueFactory(new PropertyValueFactory<>("Descanso"));

        //Boton volver
        BTvolverRSA.setOnAction(event ->
        {
            stageManager.rebuildStage(RutinaActiva.class);
        });
        //Buscar por dni y cargar la tabla
        dnibt.setOnAction(event ->
        {
            rutinaSemanalActivaManager.funcionBuscarDniCliente();
        });

    }
}
