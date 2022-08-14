package com.fuerza.vistas.ABMEntrenador;

import com.fuerza.modelo.Entrenador;
import com.fuerza.servicios.Entrenador.EntrenadorServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ABMEntrenadorManager {

    //Enlace de los controles utilizados en el fxml
    @Autowired
    ABMEntrenador abmEntrenador;
    //Enlace de los servicios del entrenador
    @Autowired
    private EntrenadorServiceImpl entrenadorService;

    //Manejo de errores
    public void validar() {
        //Evitamos que se dupliquen los errores cada vez que se producen en la misma escena
        abmEntrenador.errores.clear();
        //Validacion de que los campos no esten vacios para poder cargarlos
        if (abmEntrenador.TFdniE.getText().isEmpty()) {
            abmEntrenador.errores.add("El campo DNI es obligatorio");
        }
        if (abmEntrenador.TFnombreE.getText().isEmpty()) {
            abmEntrenador.errores.add("El campo Nombre es obligatorio");
        }
        if (abmEntrenador.TFapellidoE.getText().isEmpty()) {
            abmEntrenador.errores.add("El campo Apellido es obligatorio");
        }
        //Verificamos que los campos cumplan con sus expresiones regulares
        if (!Pattern.matches(regexDNI, abmEntrenador.TFdniE.getText())) {
            abmEntrenador.errores.add("Formato invalido. El formato correcto del DNI es: 99.999.999(sin puntos)");
        }
        if (!Pattern.matches(regexNombreApellido, abmEntrenador.TFnombreE.getText())) {
            abmEntrenador.errores.add("El nombre solo acepta letras ([a-z],[A-Z]) y \n la primera letra debe estar en mayuscula");
        }
        if (!Pattern.matches(regexNombreApellido, abmEntrenador.TFapellidoE.getText())) {
            abmEntrenador.errores.add("El apellido solo acepta letras ([a-z],[A-Z]) y \n la primera letra debe estar en mayuscula");
        }
    }
    //--------------Expresiones regulares a utilizar--------------

    //Solo acepta valores numericos
    String regexDNI = "\\d{8}";
    //NombrePropio y Apellido
    //nombre empieza por una letra mayúscula
    //los caracteres que le siguien son minúsculas
    //letras empleadas en español
    //no son números
    //nombres que empiecen por vocal si está acentuada
    String regexNombreApellido = "^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s]*)+$";

    //--------------------------------Metodo para la Tabla de datos-------------------------
    public void funcionCargarDetallesEntrenador() {
        //Limpiamos los datos existentes en la lista
        abmEntrenador.entrenadores.clear();
        //Pedimos que el observable list se cargue con los datos de la Base de datos
        abmEntrenador.entrenadores.addAll(entrenadorService.findAll());
        //Llenamos la tabla con los datos obtenidos
        abmEntrenador.TdatosE.setItems(abmEntrenador.entrenadores);
    }

    //Metodos para los botones
    //---------------------------Funcion Registrar------------------------------
    @FXML
    public void funcionRegistrar() {
        validar();
        if (abmEntrenador.errores.size() > 0) {
            //Cadena para cargar los errores
            String cadenaErrores = "";
            for (int i = 0; i < abmEntrenador.errores.size(); i++) {
                //Hacemos que se agregren los errores producidos
                cadenaErrores += "* " + abmEntrenador.errores.get(i) + "\n";
            }
            //Se crea la alerta
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Se ha producido uno o varios Errores");
            mensaje.setContentText(cadenaErrores);
            mensaje.show();
            return;
        }
        //Se registra un cliente nuevo
        //abmEntrenador.entrenadores.add(new Entrenador(abmEntrenador.TFdniE.getText(), abmEntrenador.TFnombreE.getText(), abmEntrenador.TFapellidoE.getText()));
        entrenadorService.save(new Entrenador(abmEntrenador.TFdniE.getText(), abmEntrenador.TFnombreE.getText(), abmEntrenador.TFapellidoE.getText()));

        //Se crea la alerta para avisar que se registro de manera correcta
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Aviso");
        mensaje.setHeaderText(null);
        mensaje.setContentText("Se registro de manera exitosa al entrenador " + abmEntrenador.TFnombreE.getText() + " " + abmEntrenador.TFapellidoE.getText());
        mensaje.showAndWait();
    }

    //---------------------------Funcion Limpiar------------------------------
    @FXML
    public void funcionLimpiar() {
        //Limpiamos los campos
        abmEntrenador.TFdniE.clear();
        abmEntrenador.TFnombreE.clear();
        abmEntrenador.TFapellidoE.clear();
        //Deshabilitacion de botones
        abmEntrenador.BTregistrarE.setDisable(false);
        abmEntrenador.BTactualizarE.setDisable(true);
        abmEntrenador.BTeliminarE.setDisable(true);
        //Deseleccionar lo indicado en la lista
        abmEntrenador.TdatosE.getSelectionModel().clearSelection();
    }

    //---------------------------Funcion Eliminar-------------------
    @FXML
    public void funcionEliminar() {
        //Se crea la alerta
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle("Eliminar");
        mensaje.setHeaderText("Se eliminara de la lista el elemento seleccionado");
        mensaje.setContentText("¿Esta seguro que desea eliminar el elemento?");
        Optional<ButtonType> resultado = mensaje.showAndWait();
        if (resultado.get() == ButtonType.OK) {//Se controla que opcion elije el usuario
              entrenadorService.delete(abmEntrenador.TdatosE.getSelectionModel().getSelectedItem());
        }
    }

    //---------------------------Funcion Actualizar-------------------
    @FXML
    public void funcionActualizar() {
        entrenadorService.delete(abmEntrenador.TdatosE.getSelectionModel().getSelectedItem());
        entrenadorService.update(new Entrenador(abmEntrenador.TFdniE.getText(), abmEntrenador.TFnombreE.getText(),
                    abmEntrenador.TFapellidoE.getText()));
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

        abmEntrenador.BTlimpiarE.setGraphic((new ImageView(imagenLimpiar)));
        abmEntrenador.BTeliminarE.setGraphic((new ImageView(imagenEliminar)));
        abmEntrenador.BTactualizarE.setGraphic((new ImageView(imagenModificar)));
        abmEntrenador.BTvolverE.setGraphic((new ImageView(imagenVolver)));
    }

}

