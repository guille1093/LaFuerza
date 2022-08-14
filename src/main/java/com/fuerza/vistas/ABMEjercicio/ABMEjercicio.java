package com.fuerza.vistas.ABMEjercicio;


import com.fuerza.modelo.Ejercicio;
import com.fuerza.modelo.GrupoMuscular;
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
import com.fuerza.vistas.FxController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
@Controller
@FxmlView

public class ABMEjercicio implements Initializable, FxController {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    @Lazy
    private ABMEjercicioManager abmEjercicioManager;

    final ObservableList<Ejercicio> EjercicioList = FXCollections.observableArrayList();
    final ObservableList<GrupoMuscular> grupoMusculars = FXCollections.observableArrayList();
    public ArrayList<String> errores = new ArrayList<>();

    // --------------------------------------Botones--------------------------------------
    @FXML
    public Button BTactualizarEj;

    @FXML
    public Button BTagregarEj;

    @FXML
    public Button BTeliminarEj;

    @FXML
    public Button BTlimpiarEj;

    @FXML
    public Button BTvolverEj;

    // --------------------------------------Campos de texto--------------------------------------

    @FXML
    public TextField TFnombreEj;

    @FXML
    public TextArea TAdescripcionEj;

    // --------------------------------------Tabla--------------------------------------
    @FXML
    public TableView<Ejercicio> TdatosEj;

    @FXML
    public TableColumn<Ejercicio, String> TCejerciciosEj;
    @FXML
    public TableColumn<Ejercicio, String> TCgrupoMuscularesEj;

    @FXML
    public ComboBox<GrupoMuscular> CBgrupoMuscularEj;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        abmEjercicioManager.cargarGrupoMusculares();

        abmEjercicioManager.funcionColocarImagen();

        BTeliminarEj.setDisable(true);
        BTactualizarEj.setDisable(true);

        abmEjercicioManager.LimpiarCampos();

        TCejerciciosEj.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TCgrupoMuscularesEj.setCellValueFactory(new PropertyValueFactory<>("grupoMusculares")); //ESTE NOMBRE DEBE COINSIDIR CON EL QUE ESTÁ EN LA CLASE PRINCIPAL

        TdatosEj.setItems(EjercicioList);
        abmEjercicioManager.CargarDetallesEjercicio();


        //ACCIONES DE LOS BOTONES
        BTagregarEj.setOnAction(event ->
        {
            abmEjercicioManager.RegistrarEjercicio();
            abmEjercicioManager.CargarDetallesEjercicio();
        });


        BTactualizarEj.setOnAction(event ->
        {
            abmEjercicioManager.funcionActualizar();
            abmEjercicioManager.CargarDetallesEjercicio();
            abmEjercicioManager.LimpiarCampos();

        });

        BTeliminarEj.setOnAction(event ->
        {
            abmEjercicioManager.EliminarEjercicio();
            abmEjercicioManager.CargarDetallesEjercicio();
            abmEjercicioManager.LimpiarCampos();
        });

        BTlimpiarEj.setOnAction(event ->
        {
            abmEjercicioManager.LimpiarCampos();
        });

        //Boton Volver
        BTvolverEj.setOnAction(event ->
        {
            stageManager.rebuildStage(OperacionesSistema.class);
        });


        TdatosEj.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Ejercicio>(){

                    public void changed(ObservableValue<? extends Ejercicio> observable, Ejercicio valorViejo, Ejercicio valorSeleccionado) {
                        //controlamos que no se produzca un null pointer por la limpieza
                        if(valorSeleccionado != null){
                            //Obtenemos los valores de la seleccion actual
                            TFnombreEj.setText(valorSeleccionado.getNombre());
                            TAdescripcionEj.setText(valorSeleccionado.getDescripcion());
                            CBgrupoMuscularEj.getSelectionModel().select(valorSeleccionado.getGrupoMusculares());
                            //Deshabilitacion de botones
                            BTagregarEj.setDisable(true);
                            BTactualizarEj.setDisable(false);
                            BTeliminarEj.setDisable(false);
                        }
                    }
                }
        );
    }

}
