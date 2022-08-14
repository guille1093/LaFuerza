package com.fuerza.vistas.ABMRutina;

import com.fuerza.modelo.*;
import com.fuerza.vistas.FxController;
import com.fuerza.vistas.MainApp.OperacionesSistema;
import com.fuerza.vistas.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ABMRutina implements Initializable, FxController {


    @Autowired
    @Lazy
    private ABMRutinaManager abmRutinaManager;

    @Lazy
    @Autowired
    private StageManager stageManager;


    public ObservableList<DetalleRutina> detalleRutinas = FXCollections.observableArrayList();

    public ObservableList<String> dia = FXCollections.observableArrayList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado");
    public ObservableList<GrupoMuscular> grupoMusculares = FXCollections.observableArrayList();

    public ObservableList<Ejercicio> ejercicios = FXCollections.observableArrayList();
    public ObservableList<String> series = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");





    //Lista de errores generados
    public ArrayList<String> erroresGenerarRutina = new ArrayList<>();
    public ArrayList<String> errores = new ArrayList<>();

    //--------------------------------------botones--------------------------------
    @FXML
    public Button BTagregarR;
    @FXML
    public Button BTelimiarR;
    @FXML
    public Button BTlimpiarR;

    @FXML
    public Button BTBuscarDniClienteR;

    @FXML
    public Button BTBuscarDniEntrenadorR;
    @FXML
    public Button BTgenerarRutinaR;

    @FXML
    public Button BTvolverR;

    //--------------------------------------Campos de texto--------------------------------------
    @FXML
    public TextField TFdniClienteR;

    @FXML
    public TextField TFdniEntrenadorR;
    @FXML
    public TextField TFrepeticionesR;
    @FXML
    public TextField TFdescansosR;

    @FXML
    public Label LnombreClienteR;

    @FXML
    public Label LnombreEntrenadorR;

    //---------------------------------------Combo Box---------------------------------------------
    @FXML
    public ComboBox<String> CBdiaR;
    @FXML
    public ComboBox<GrupoMuscular> CBgrupoMuscularR;
    @FXML
    public ComboBox<Ejercicio> CBnombreEjerciciosR;
    @FXML
    public ComboBox<String> CBseriesR;


    //--------------------------------------Tabla--------------------------------------
    @FXML
    public TableView<DetalleRutina> TdatosR;
    //Columnas
    @FXML
    public TableColumn<DetalleRutina,String> TCdiaR;
    @FXML
    public TableColumn<DetalleRutina,String> TCnombreR;
    @FXML
    public TableColumn<DetalleRutina,Integer> TCseriesR;
    @FXML
    public TableColumn<DetalleRutina,Integer> TCrepeticionesR;

    //------------------------------------Fecha------------------------------------------
    @FXML
    public DatePicker DPfechaInicioR;



    //----------------------------------------Metodo que engloba las acciones de la interfaz------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Se limpian los campos en caso de que hayan quedado con algun dato basura
        abmRutinaManager.funcionLimpiarCampos();
        BTgenerarRutinaR.setDisable(false);

        abmRutinaManager.funcionCargarDetallesRutina();

        BTelimiarR.setDisable(true);
        CBgrupoMuscularR.setDisable(true);
        CBnombreEjerciciosR.setDisable(true);
        CBseriesR.setDisable(true);
        BTagregarR.setDisable(true);
        TFrepeticionesR.setDisable(true);
        TFdescansosR.setDisable(true);


        TCdiaR.setCellValueFactory(new PropertyValueFactory<>("dia"));
        TCnombreR.setCellValueFactory(new PropertyValueFactory<>("ejercicios"));
        TCseriesR.setCellValueFactory(new PropertyValueFactory<>("series"));
        TCrepeticionesR.setCellValueFactory(new PropertyValueFactory<>("repeticiones"));
        TdatosR.setItems(detalleRutinas);


        // ++++++++++++++++++++++++ACCIONES DE LOS BOTONES+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        CBgrupoMuscularR.setOnAction(event ->
        {
            abmRutinaManager.funcionFiltrarCB();
        });


        BTBuscarDniClienteR.setOnAction(event ->
        {
            abmRutinaManager.funcionBuscarDniCliente();

        });

        BTBuscarDniEntrenadorR.setOnAction(event ->
        {
            abmRutinaManager.funcionBuscarDniEntrenador();
        });


        BTagregarR.setOnAction(event ->
        {
            abmRutinaManager.funcionAgregarEjercicio();
            abmRutinaManager.funcionBuscarDniCliente();
            BTgenerarRutinaR.setDisable(true);
        });


        BTlimpiarR.setOnAction(event ->
        {
            abmRutinaManager.funcionLimpiarCampos();
        });


        BTelimiarR.setOnAction(event ->
        {
            abmRutinaManager.funcionEliminarRutina();
            abmRutinaManager.funcionBuscarDniCliente();

            abmRutinaManager.funcionLimpiarCampos();
        });

        BTgenerarRutinaR.setOnAction(event ->
        {
            abmRutinaManager.funcionGenerarRutina();
        });


        BTvolverR.setOnAction(event ->
        {
            stageManager.rebuildStage(OperacionesSistema.class);
        });

        TdatosR.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<DetalleRutina>(){
                    @Override
                    public void changed(ObservableValue<? extends DetalleRutina> observable, DetalleRutina valorViejo, DetalleRutina valorSeleccionado) {
                        //controlamos que no se produzca un null pointer por la limpieza
                        if(valorSeleccionado != null){
                            //Obtenemos los valores de la seleccion actual
                            TFdescansosR.setText(String.valueOf(valorSeleccionado.getDescanso()));
                            TFdniClienteR.setText(String.valueOf(valorSeleccionado.getCliente()));
                            TFdniEntrenadorR.setText(String.valueOf(valorSeleccionado.getRutinaDiaria().getUnEntrenamiento().getCreador()));
                            TFrepeticionesR.setText(String.valueOf(valorSeleccionado.getRepeticiones()));
                            CBdiaR.getSelectionModel().select(valorSeleccionado.getDia());
                            DPfechaInicioR.setValue(valorSeleccionado.getRutinaDiaria().getUnEntrenamiento().getFechaInicio());
                            //Deshabilitacion de botones
                            BTelimiarR.setDisable(false);
                            abmRutinaManager.funcionBuscarDniEntrenador();

                            DPfechaInicioR.setDisable(true);
                            TFdniEntrenadorR.setDisable(true);

                        }
                    }
                }
        );



    }

}
