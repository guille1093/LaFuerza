package com.fuerza.vistas.ABMCliente;

import com.fuerza.modelo.Cliente;
import com.fuerza.servicios.Cliente.ClienteServiceImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ABMClienteManager {

    //Enlace de los controles utilizados en el fxml
    @Autowired
    ABMCliente abmCliente;
    //Enlace de los servicios del entrenador
    @Autowired
    private ClienteServiceImpl clienteService;
    //Damos formato a la fecha
    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Manejo de errores
    public void validar() {
        //Evitamos que se dupliquen los errores cada vez que se producen en la misma escena
        abmCliente.errores.clear();
        //Validacion de que los campos no esten vacios para poder cargarlos
        if (abmCliente.TFdniC.getText().isEmpty()) {
            abmCliente.errores.add("El campo DNI es obligatorio");
        }
        if (abmCliente.TFnombreC.getText().isEmpty()) {
            abmCliente.errores.add("El campo Nombre es obligatorio");
        }
        if (abmCliente.TFapellidoC.getText().isEmpty()) {
            abmCliente.errores.add("El campo Apellido es obligatorio");
        }
        if (abmCliente.DPfechaingresoC.getValue() == null) {
            abmCliente.errores.add("Debe seleccionar una Fecha de Ingreso");
        }
        if (abmCliente.SeleccionGenero.getSelectedToggle() == null) {
            abmCliente.errores.add("La seleccion de Sexo es obligatorio");
        }
        if (abmCliente.TFpesoC.getText().isEmpty()) {
            abmCliente.errores.add("El campo Peso es obligatorio");
        }
        if (abmCliente.TFestaturaC.getText().isEmpty()) {
            abmCliente.errores.add("El campo Estatura es obligatorio");
        }
        //Verificamos que los campos cumplan con sus expresiones regulares
        if (!Pattern.matches(regexDNI, abmCliente.TFdniC.getText())) {
            abmCliente.errores.add("Formato invalido. El formato correcto del DNI es: 99.999.999(sin puntos)");
        }
        if (!Pattern.matches(regexNombreApellido, abmCliente.TFnombreC.getText())) {
            abmCliente.errores.add("El nombre solo acepta letras ([a-z],[A-Z]) y \n la primera letra debe estar en mayuscula");
        }
        if (!Pattern.matches(regexNombreApellido, abmCliente.TFapellidoC.getText())) {
            abmCliente.errores.add("El apellido solo acepta letras ([a-z],[A-Z]) y \n la primera letra debe estar en mayuscula");
        }
        if (!Pattern.matches(regexValorNumerico, abmCliente.TFpesoC.getText())) {
            abmCliente.errores.add("El peso solo permite numeros [0-9] y debe utilizar '.' para ingresar decimales");
        }
        if (!Pattern.matches(regexValorNumerico, abmCliente.TFestaturaC.getText())) {
            abmCliente.errores.add("La estatura solo permite numeros [0-9] y debe utilizar '.' para ingresar decimales");
        }
        //Restriccion de fecha
        try {
            //Controlamos la fecha de ingreso que no sea mayor a el dia de hoy
            LocalDate fecha1 = LocalDate.now();
            LocalDate fecha2 = abmCliente.DPfechaingresoC.getValue();
            if (fecha2.compareTo(fecha1) > 0) {
                abmCliente.errores.add("Debe seleccionar una Fecha de Ingreso que sea igual o anterior al dia de hoy" +"("+fecha1.format(formateador)+")");
            }

            //Controlamos la fecha de nacimiento que no sea mayor a el dia de hoy
            LocalDate fecha4 = abmCliente.DPfechanacC.getValue();
            if (fecha4.compareTo(fecha1) > 0) {
                abmCliente.errores.add("Debe seleccionar una Fecha de Nacimiento que sea menor al dia de hoy" +"("+fecha1.format(formateador)+")");
            }
        }catch (NullPointerException e){
            abmCliente.errores.add("Debe rellenar los campos de fechas ya que son obligatorios");
        }

    }

    //--------------Expresiones regulares a utilizar--------------

    //Solo acepta valores numericos
    String regexDNI = "\\d{8}";
    String regexValorNumerico = "\\d+(\\.\\d{1,3})?";
    //NombrePropio y Apellido
    //nombre empieza por una letra mayúscula
    //los caracteres que le siguien son minúsculas
    //letras empleadas en español
    //no son números
    //nombres que empiecen por vocal si está acentuada
    String regexNombreApellido = "^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s]*)+$";

    //--------------------------------Metodo para la Tabla de datos-------------------------
    public void funcionCargarDetallesCliente()
    {
        abmCliente.clientes.clear();
        abmCliente.clientes.addAll(clienteService.findAll());
        abmCliente.TdatosC.setItems(abmCliente.clientes);
    }

    //Metodos para los botones
    //---------------------------Funcion de Registrar------------------------------
    public void funcionRegistrarCliente()
    {
        validar();
        if(abmCliente.errores.size() > 0) {
            //Cadena para cargar los errores
            String cadenaErrores = "";
            for(int i=0; i < abmCliente.errores.size(); i++){
                //Hacemos que se agreguen los errores producidos
                cadenaErrores += "* " + abmCliente.errores.get(i) + "\n";
            }
            //Se crea la alerta
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Se ha producido uno o varios Errores");
            mensaje.setContentText(cadenaErrores);
            mensaje.show();
            return;
        }
        //Se añade los elementos al observable list creado
        clienteService.save(new Cliente(abmCliente.TFdniC.getText(), abmCliente.TFnombreC.getText(), abmCliente.TFapellidoC.getText(), abmCliente.DPfechanacC.getValue(),
                abmCliente.DPfechaingresoC.getValue(), ((RadioButton)abmCliente.SeleccionGenero.getSelectedToggle()).getText(),
                Double.parseDouble(abmCliente.TFpesoC.getText()), Double.parseDouble(abmCliente.TFestaturaC.getText()) ));
        //Limpiar campos
        funcionLimpiarCampos();
        //Se crea la alerta para avisar que se registro de manera correcta
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Aviso");
        mensaje.setHeaderText(null);
        mensaje.setContentText("Se registro de manera exitosa al cliente " + abmCliente.TFnombreC.getText() + " " + abmCliente.TFapellidoC.getText());
        mensaje.showAndWait();
    }

    //---------------------------Funcion de Limpiar------------------------------
    public void funcionLimpiarCampos()
    {
        //Limpiamos los campos
        abmCliente.TFdniC.clear();
        abmCliente.TFnombreC.clear();
        abmCliente.TFapellidoC.clear();
        abmCliente.DPfechanacC.getEditor().clear();
        abmCliente.DPfechaingresoC.getEditor().clear();
        abmCliente.SeleccionGenero.selectToggle(null);
        abmCliente.TFpesoC.clear();
        abmCliente.TFestaturaC.clear();
        //Deshabilitacion de botones
        abmCliente.BTregistrarC.setDisable(false);
        abmCliente.BTactualizarC.setDisable(true);
        abmCliente.BTeliminarC.setDisable(true);
        //Deseleccionar lo indicado en la lista
        abmCliente.TdatosC.getSelectionModel().clearSelection();
    }

    //---------------------------Funcion Eliminar-------------------
    public void funcionEliminarCliente()
    {
        //Se crea la alerta
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle("Eliminar");
        mensaje.setHeaderText("Se eliminara de la lista el elemento seleccionado");
        mensaje.setContentText("¿Esta seguro que desea eliminar el elemento?");
        Optional<ButtonType> resultado = mensaje.showAndWait();
        if (resultado.get() == ButtonType.OK) {//Se controla que opcion elije el usuario
            clienteService.delete(abmCliente.TdatosC.getSelectionModel().getSelectedItem());
        }
    }

    //---------------------------Funcion Actualizar-------------------
    public void funcionActulizarCliente(){

        clienteService.delete(abmCliente.TdatosC.getSelectionModel().getSelectedItem());
        clienteService.update(new Cliente(abmCliente.TFdniC.getText(), abmCliente.TFnombreC.getText(), abmCliente.TFapellidoC.getText(), abmCliente.DPfechanacC.getValue(),
                abmCliente.DPfechaingresoC.getValue(), ((RadioButton)abmCliente.SeleccionGenero.getSelectedToggle()).getText(),
                Double.parseDouble(abmCliente.TFpesoC.getText()), Double.parseDouble(abmCliente.TFestaturaC.getText())));

    }

    //------------------------Funcion Imagenes----------------------------
    public void funcionColocarImagen(){
        URL linkLimpiar = getClass().getResource("/images/limpiar.png");
        URL linkEliminar = getClass().getResource("/images/eliminar.png");
        URL linkModificar = getClass().getResource("/images/modificar.png");
        URL linkVolver = getClass().getResource("/images/volver.png");

        Image imagenLimpiar = new Image(linkLimpiar.toString(),30,20,false,true);
        Image imagenEliminar = new Image(linkEliminar.toString(),28,25,false,true);
        Image imagenModificar = new Image(linkModificar.toString(),22,22,false,true);
        Image imagenVolver = new Image(linkVolver.toString(),25,30,false,true);

        abmCliente.BTlimpiarC.setGraphic((new ImageView(imagenLimpiar)));
        abmCliente.BTeliminarC.setGraphic((new ImageView(imagenEliminar)));
        abmCliente.BTactualizarC.setGraphic((new ImageView(imagenModificar)));
        abmCliente.BTvolverC.setGraphic((new ImageView(imagenVolver)));
    }

}
