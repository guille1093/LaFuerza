package com.fuerza.vistas.RutinaActiva;

import com.fuerza.modelo.DetalleRutina;
import com.fuerza.vistas.ABMEjercicio.ABMEjercicioManager;
import com.fuerza.vistas.FxController;
import com.fuerza.vistas.MainApp.OperacionesSistema;
import com.fuerza.vistas.RutinaSemanalActiva.RutinaSemanalActiva;
import com.fuerza.vistas.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class RutinaActiva implements Initializable, FxController {

    //Permite el uso de las propiedades del stage
    @Lazy
    @Autowired
    public StageManager stageManager;

    @Autowired
    @Lazy
    public RutinaActivaManager rutinaActivaManager;

    public ObservableList<DetalleRutina> detalleRutinas = FXCollections.observableArrayList();




    @FXML
    public Button BTrutinacompletaRA;

    @FXML
    public Button BTvolverRA;

    @FXML
    public Button BTbuscarRA;


    @FXML
    public Label LdiaRA;

    @FXML
    public Label LfechafinRA;

    @FXML
    public Label LfechainicioRA;

    @FXML
    public Label LnombreClienteRA;

    @FXML
    public TextField TFdniClienteRA;




    @FXML
    public TableView TCdatosRA;

    @FXML
    public TableColumn TCdescansoRA;

    @FXML
    public TableColumn TCejercicioRA;

    @FXML
    public TableColumn TCrepeticionesRA;

    @FXML
    public TableColumn TCseriesRA;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //rutinaActivaManager.funcionColocarImagen();

        rutinaActivaManager.funcionDiaActual();


        TCejercicioRA.setCellValueFactory(new PropertyValueFactory<>("ejercicios"));
        TCseriesRA.setCellValueFactory(new PropertyValueFactory<>("series"));
        TCrepeticionesRA.setCellValueFactory(new PropertyValueFactory<>("repeticiones"));
        TCdescansoRA.setCellValueFactory(new PropertyValueFactory<>("descanso"));
        TCdatosRA.setItems(detalleRutinas);




        //Boton Volver
        BTvolverRA.setOnAction(event ->
        {
            stageManager.rebuildStage(OperacionesSistema.class);
        });

        //Boton Ver rutina completa
        BTrutinacompletaRA.setOnAction(event ->
        {
            stageManager.rebuildStage(RutinaSemanalActiva.class);

        });

        BTbuscarRA.setOnAction(event ->
        {
            rutinaActivaManager.funcionBuscarDniCliente();
        });

    }
}
