package com.fuerza.vistas.ABMEjercicioRealizado;

import com.fuerza.MainApp;
import com.fuerza.modelo.Cliente;
import com.fuerza.modelo.DetalleRutina;
import com.fuerza.modelo.EntrenamientoRealizado;
import com.fuerza.modelo.GrupoMuscular;
import com.fuerza.servicios.DetalleRutina.DetalleRutinaService;
import com.fuerza.vistas.ABMRendimiento.ABMRendimiento;
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
import javafx.scene.control.TableColumn;
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
public class ABMEjercicioRealizado implements Initializable, FxController {


    @Autowired
    @Lazy
    private ABMEjercicioRealizadoManager abmEjercicioRealizadoManager;

    @Autowired
    public DetalleRutinaService detalleRutinaService;

    @Autowired
    public MainApp mainApp;

    @Lazy
    @Autowired
    private StageManager stageManager;

    public ObservableList<DetalleRutina> ejercicios = FXCollections.observableArrayList();

    public ObservableList<EntrenamientoRealizado> ejerciciosRealizados = FXCollections.observableArrayList();

    public ObservableList<GrupoMuscular> grupoMusculares = FXCollections.observableArrayList();

    //Lista de errores producidos
    public ArrayList<String> errores = new ArrayList<>();

    //--------------------------------------Labels--------------------------------
    @FXML
    public ComboBox<DetalleRutina> CBEjerAsociados;

    @FXML
    public ComboBox<GrupoMuscular> CBgrupoMusculares;

    @FXML
    private Label LBLocalDate;

    @FXML
    public Label LBVolumenGrupoMuscular;

    @FXML
    public Label LBVolumenTotal;

    @FXML
    public Label LBNombreCliente;


    //--------------------------------------Tabla---------------------------------------
    @FXML
    public TableView<EntrenamientoRealizado> TVEjerciciosRealizado;

    @FXML
    public TableColumn<EntrenamientoRealizado, Integer> TCDescanso;

    @FXML
    public TableColumn<EntrenamientoRealizado, String> TCEjercicio;

    @FXML
    public TableColumn<EntrenamientoRealizado, Integer> TCPeso;

    @FXML
    public TableColumn<EntrenamientoRealizado, Integer> TCRepeticiones;

    @FXML
    public TableColumn<EntrenamientoRealizado, Integer> TCSeries;

    @FXML
    public TableColumn<EntrenamientoRealizado, Integer> TCVolumenDiario;

    //--------------------------------------Campos de texto--------------------------------
    @FXML
    public TextField TFDniCliente;

    @FXML
    public TextField TFDescanso;

    @FXML
    public TextField TFPeso;

    @FXML
    public TextField TFRepeticiones;

    @FXML
    public TextField TFSeries;

    //--------------------------------------Botones--------------------------------
    @FXML
    public Button btAgregar;

    @FXML
    public Button btEliminar;

    @FXML
    public Button btLimpiar;

    @FXML
    private Button BTBuscar;

    @FXML
    public Button btCalificar;

    @FXML
    private Button btVolver;


    //----------------------------------------Metodo que engloba las acciones de la interfaz------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        abmEjercicioRealizadoManager.LimpiarCampos();
        ejerciciosRealizados.clear();
        abmEjercicioRealizadoManager.funcioncargarGrupoMusculares();


        //disable botones, textfields y combobox
        btCalificar.setDisable(true);
        btEliminar.setDisable(true);
        btLimpiar.setDisable(true);
        btAgregar.setDisable(true);
        TFDescanso.setDisable(true);
        TFPeso.setDisable(true);
        TFRepeticiones.setDisable(true);
        TFSeries.setDisable(true);
        CBEjerAsociados.setDisable(true);
        CBgrupoMusculares.setDisable(true);
        //TVEjerciciosRealizado.setDisable(false);

        // ---------------------------------tabla--------------------------------
        TCDescanso.setCellValueFactory(new PropertyValueFactory<>("descansosEntrenamientoRealizado"));
        TCEjercicio.setCellValueFactory(new PropertyValueFactory<>("ejercicios"));
        TCPeso.setCellValueFactory(new PropertyValueFactory<>("pesoEntrenamientoRealizado"));
        TCRepeticiones.setCellValueFactory(new PropertyValueFactory<>("repeticionesEntrenamientoRealizado"));
        TCSeries.setCellValueFactory(new PropertyValueFactory<>("seriesEntrenamientoRealizado"));
        TCVolumenDiario.setCellValueFactory(new PropertyValueFactory<>("volumenDiarioEntrenamientoRealizado"));
        TVEjerciciosRealizado.setItems(ejerciciosRealizados);


    //set label LocalDate with current date
    LBLocalDate.setText(java.time.LocalDate.now().toString());
    //set the button limpiar to call the method limpiar()


    btLimpiar.setOnAction(event ->
            abmEjercicioRealizadoManager.LimpiarCampos()
    );
    //set the button buscar to call the method cargarEjerciciosCB()
    BTBuscar.setOnAction(event ->
    {

        abmEjercicioRealizadoManager.funcionBuscarDniCliente();
    });

    btCalificar.setOnAction(event ->
    {
        mainApp.S = TFDniCliente.getText();
        stageManager.rebuildStage(ABMRendimiento.class);
    });

    //set the button volver to rebuild the main stage
    btVolver.setOnAction(event ->
            stageManager.rebuildStage(OperacionesSistema.class)
    );
    //set the button actualizar to call the method actualizarEjercicio()
    btAgregar.setOnAction(event ->
    {
        abmEjercicioRealizadoManager.guardarEntrenamientoRealizado();
        abmEjercicioRealizadoManager.cargarDatosObservableListTabla();
        abmEjercicioRealizadoManager.funcionCalcularVolumenTotal();

    });


    btEliminar.setOnAction(event ->
    {
        abmEjercicioRealizadoManager.funcionEliminarEntrenamientoRealizado();
        abmEjercicioRealizadoManager.cargarDatosObservableListTabla();
        abmEjercicioRealizadoManager.LimpiarCampos();
        abmEjercicioRealizadoManager.funcionCalcularVolumenTotal();
    });

        CBgrupoMusculares.setOnAction(event ->
        {
            abmEjercicioRealizadoManager.funcionCaulcularVolumenGrupoMuscular();
        });

        TVEjerciciosRealizado.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<EntrenamientoRealizado>(){
                    @Override
                    public void changed(ObservableValue<? extends EntrenamientoRealizado> observable, EntrenamientoRealizado valorViejo, EntrenamientoRealizado valorSeleccionado) {
                        if(valorSeleccionado != null){
                            btEliminar.setDisable(false);
                        }
                    }});

    }
}

