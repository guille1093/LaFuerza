package com.fuerza.vistas.ABMCliente;

import com.fuerza.modelo.Cliente;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
@Controller
@FxmlView
public class ABMCliente implements Initializable, FxController {
    //Permite el uso de las propiedades del stage
    @Lazy
    @Autowired
    private StageManager stageManager;

    //Enlace de las acciones hechas por los botones
    @Autowired
    @Lazy
    private ABMClienteManager abmClienteManager;
    //Lista para alamcenar a los clientes
    public ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    //Lista de errores producidos
    public ArrayList<String> errores = new ArrayList<>();

    //--------------------------------------botones--------------------------------
    @FXML
    public Button BTregistrarC;
    @FXML
    public Button BTactualizarC;
    @FXML
    public Button BTeliminarC;
    @FXML
    public Button BTlimpiarC;
    @FXML
    public Button BTvolverC;

    //--------------------------------------Campos de texto--------------------------------
    @FXML
    public TextField TFdniC;
    @FXML
    public TextField TFnombreC;
    @FXML
    public TextField TFapellidoC;
    @FXML
    public TextField TFpesoC;
    @FXML
    public TextField TFestaturaC;

    //--------------------------------------Radio Button--------------------------------
    @FXML
    public ToggleGroup SeleccionGenero;
    @FXML
    public RadioButton RBTmasculinoC;
    @FXML
    public RadioButton RBTfemeninoC;

    //--------------------------------------Date Picker---------------------------------
    @FXML
    public DatePicker DPfechanacC;
    @FXML
    public DatePicker DPfechaingresoC;

    //--------------------------------------Tabla---------------------------------------
    @FXML
    public TableView<Cliente> TdatosC;
    //Columnas
    @FXML
    public TableColumn<Cliente, String> TCdniC;
    @FXML
    public TableColumn<Cliente, String> TCnombreC;
    @FXML
    public TableColumn<Cliente, String> TCapellidoC;
    @FXML
    public TableColumn<Cliente, LocalDate> TCfechanacC;
    @FXML
    public TableColumn<Cliente,LocalDate> TCfechaingresoC;
    @FXML
    public TableColumn<Cliente,String> TCsexoC;
    @FXML
    public TableColumn<Cliente,Double> TCpesoC;
    @FXML
    public TableColumn<Cliente,Double> TCestaturaC;

    //----------------------------------------Metodo que engloba las acciones de la interfaz------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Se limpian los campos en caso de que hayan quedado con algun dato basura
        abmClienteManager.funcionLimpiarCampos();

        //Se cargan los iconos en los lugares correspondientes
        abmClienteManager.funcionColocarImagen();

        //Se deshabilitan los botones de eliminar y actualizar
        BTeliminarC.setDisable(true);
        BTactualizarC.setDisable(true);

        //Se carga la tabla con los datos de la BD
        abmClienteManager.funcionCargarDetallesCliente();

        // Se asocia los clientes creados a la lista para poder verlos
        TCdniC.setCellValueFactory(new PropertyValueFactory<>("dni"));
        TCnombreC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TCapellidoC.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        TCfechaingresoC.setCellValueFactory(new PropertyValueFactory<>("FechaIngreso"));
        TCpesoC.setCellValueFactory(new PropertyValueFactory<>("Peso"));
        TCestaturaC.setCellValueFactory(new PropertyValueFactory<>("Estatura"));
        TdatosC.setItems(clientes);

        //Acciones de los Botones
        //Boton Limpiar
        BTlimpiarC.setOnAction(event ->
        {
            abmClienteManager.funcionLimpiarCampos();
        });
        //Boton Registrar
        BTregistrarC.setOnAction(event ->
        {
            abmClienteManager.funcionRegistrarCliente();
            abmClienteManager.funcionCargarDetallesCliente();
        });
        //Boton Actualizar
        BTactualizarC.setOnAction(event ->
        {
            abmClienteManager.funcionActulizarCliente();
            abmClienteManager.funcionCargarDetallesCliente();
            abmClienteManager.funcionLimpiarCampos();
        });
        //Boton Eliminar
        BTeliminarC.setOnAction(event ->
        {
            abmClienteManager.funcionEliminarCliente();
            abmClienteManager.funcionLimpiarCampos();
            abmClienteManager.funcionCargarDetallesCliente();
        });
        //Boton Volver
        BTvolverC.setOnAction(event ->
        {
            stageManager.rebuildStage(OperacionesSistema.class);
        });


        //Permite saber que se selecciono un renglon de la tabla
        TdatosC.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Cliente>(){
                    @Override
                    public void changed(ObservableValue<? extends Cliente> observable, Cliente valorViejo, Cliente valorSeleccionado) {
                        //controlamos que no se produzca un null pointer por la limpieza
                        if(valorSeleccionado != null){
                            //Obtenemos los valores de la seleccion actual
                            TFdniC.setText(valorSeleccionado.getDni());
                            TFnombreC.setText(valorSeleccionado.getNombre());
                            TFapellidoC.setText(valorSeleccionado.getApellido());
                            DPfechanacC.setValue(LocalDate.parse(valorSeleccionado.getFechaNacimiento().toString()));
                            DPfechaingresoC.setValue(LocalDate.parse(valorSeleccionado.getFechaIngreso().toString()));
                            if(valorSeleccionado.getSexo().equals("Masculino")){
                                RBTmasculinoC.setSelected(true);
                            }else{
                                RBTfemeninoC.setSelected(true);
                            }
                            TFpesoC.setText(String.valueOf(valorSeleccionado.getPeso()));
                            TFestaturaC.setText(String.valueOf(valorSeleccionado.getEstatura()));
                            //Deshabilitacion de botones
                            BTregistrarC.setDisable(true);
                            BTactualizarC.setDisable(false);
                            BTeliminarC.setDisable(false);
                        }
                    }
                }
        );
    }

}
