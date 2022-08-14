package com.fuerza.vistas.ABMEntrenador;

import com.fuerza.modelo.Entrenador;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ABMEntrenador implements Initializable, FxController {
    //Permite el uso de las propiedades del stage
    @Lazy
    @Autowired
    private StageManager stageManager;

    //Enlace de las acciones hechas por los botones
    @Autowired
    @Lazy
    private ABMEntrenadorManager abmEntrenadorManager;

    //Lista para guardar los entrenadores
    public ObservableList<Entrenador> entrenadores = FXCollections.observableArrayList();

    //Lista de errores generados
    public ArrayList<String> errores = new ArrayList<>();

    //--------------------------------------botones--------------------------------
    @FXML
    public Button BTregistrarE;
    @FXML
    public Button BTactualizarE;
    @FXML
    public Button BTeliminarE;
    @FXML
    public Button BTlimpiarE;
    @FXML
    public Button BTvolverE;

    //--------------------------------------Campos de texto--------------------------------------
    @FXML
    public TextField TFdniE;
    @FXML
    public TextField TFnombreE;
    @FXML
    public TextField TFapellidoE;

    //--------------------------------------Tabla--------------------------------------
    @FXML
    public TableView<Entrenador> TdatosE;
    //Columnas
    @FXML
    public TableColumn<Entrenador,String> TCdniE;
    @FXML
    public TableColumn<Entrenador,String> TCnombreE;
    @FXML
    public TableColumn<Entrenador,String> TCapellidoE;

    //----------------------------------------Metodo que engloba las acciones de la interfaz------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Se limpian los campos en caso de que hayan quedado con algun dato basura
        abmEntrenadorManager.funcionLimpiar();

        //Se cargan los iconos en los lugares correspondientes
        abmEntrenadorManager.funcionColocarImagen();

        //Se deshabilitan los botones de eliminar y actualizar
        BTeliminarE.setDisable(true);
        BTactualizarE.setDisable(true);

        //Se carga la tabla con los datos de la BD
        abmEntrenadorManager.funcionCargarDetallesEntrenador();

        // Se asocia los entrenadores creados a la lista para poder verlos
        TCdniE.setCellValueFactory(new PropertyValueFactory<>("dni"));
        TCnombreE.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        TCapellidoE.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
        TdatosE.setItems(entrenadores);

        //Acciones de los Botones
        //Boton Limpiar
        BTlimpiarE.setOnAction(event ->
        {
            abmEntrenadorManager.funcionLimpiar();
        });
        //Boton Registrar
        BTregistrarE.setOnAction(event ->
        {
            abmEntrenadorManager.funcionRegistrar();
            abmEntrenadorManager.funcionCargarDetallesEntrenador();
            abmEntrenadorManager.funcionLimpiar();
        });
        //Boton Actualizar
        BTactualizarE.setOnAction(event ->
        {
            abmEntrenadorManager.funcionActualizar();
            abmEntrenadorManager.funcionCargarDetallesEntrenador();
            abmEntrenadorManager.funcionLimpiar();
        });
        //Boton Eliminar
        BTeliminarE.setOnAction(event ->
        {
            abmEntrenadorManager.funcionEliminar();
            abmEntrenadorManager.funcionLimpiar();
            abmEntrenadorManager.funcionCargarDetallesEntrenador();
        });
        //Boton Volver
        BTvolverE.setOnAction(event ->
        {
            stageManager.rebuildStage(OperacionesSistema.class);
        });

        //Permite saber que se selecciono un renglon de la tabla
        TdatosE.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Entrenador>(){
                    @Override
                    public void changed(ObservableValue<? extends Entrenador> observable, Entrenador valorViejo, Entrenador valorSeleccionado) {
                        //controlamos que no se produzca un null pointer por la limpieza
                        if(valorSeleccionado != null){
                            //Obtenemos los valores de la seleccion actual
                            TFdniE.setText(valorSeleccionado.getDni());
                            TFnombreE.setText(valorSeleccionado.getNombre());
                            TFapellidoE.setText(valorSeleccionado.getApellido());
                            //Deshabilitacion de botones
                            BTregistrarE.setDisable(true);
                            BTactualizarE.setDisable(false);
                            BTeliminarE.setDisable(false);
                        }
                    }
                }
        );

    }

}
