package com.fuerza.vistas.ABMEjercicio;

import com.fuerza.modelo.Ejercicio;
import com.fuerza.servicios.Ejercicio.EjercicioServiceImpl;
import com.fuerza.servicios.GrupoMuscular.GrupoMuscularServiceImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;

@Component
public class ABMEjercicioManager {

    @Autowired
    ABMEjercicio abmEjercicio;

    @Autowired
    private EjercicioServiceImpl ejercicioService;

    @Autowired
    private GrupoMuscularServiceImpl grupoMuscularService;



    public void LimpiarCampos()
    {
        abmEjercicio.TFnombreEj.clear();
        abmEjercicio.TAdescripcionEj.clear();
        abmEjercicio.CBgrupoMuscularEj.setValue(null);
        abmEjercicio.TdatosEj.getSelectionModel().clearSelection();
        abmEjercicio.BTagregarEj.setDisable(false);
        abmEjercicio.BTactualizarEj.setDisable(true);
        abmEjercicio.BTeliminarEj.setDisable(true);
    }

    public void cargarGrupoMusculares(){
        abmEjercicio.grupoMusculars.addAll(grupoMuscularService.findAll());
        abmEjercicio.CBgrupoMuscularEj.setItems(abmEjercicio.grupoMusculars);
    }

    public void CargarDetallesEjercicio()
    {
        abmEjercicio.EjercicioList.clear();
        abmEjercicio.EjercicioList.addAll(ejercicioService.findAll());
        abmEjercicio.TdatosEj.setItems(abmEjercicio.EjercicioList);
    }

    public void RegistrarEjercicio()
    {
        validar();
        if (abmEjercicio.errores.size() > 0) {
            //Cadena para cargar los errores
            String cadenaErrores = "";
            for (int i = 0; i < abmEjercicio.errores.size(); i++) {
                //Hacemos que se agregren los errores producidos
                cadenaErrores += "* " + abmEjercicio.errores.get(i) + "\n";
            }
            //Se crea la alerta
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Se ha producido uno o varios Errores");
            mensaje.setContentText(cadenaErrores);
            mensaje.show();
            return;
        }

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(abmEjercicio.TFnombreEj.getText());
        ejercicio.setDescripcion(abmEjercicio.TAdescripcionEj.getText());;
        ejercicio.setGrupoMusculares(abmEjercicio.CBgrupoMuscularEj.getSelectionModel().getSelectedItem());
        Ejercicio newEjercicio = ejercicioService.save(ejercicio);

        //limpiar campos
        LimpiarCampos();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Aviso");
        mensaje.setHeaderText(null);
        mensaje.setContentText("Se registro de manera exitosa el ejercicio " + abmEjercicio.TFnombreEj.getText());
        mensaje.showAndWait();
    }


    public void EliminarEjercicio()
    {
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle("Eliminar");
        mensaje.setHeaderText("Se eliminara de la lista el elemento seleccionado");
        mensaje.setContentText("¿Esta seguro que desea eliminar el ejercicio " + abmEjercicio.TdatosEj.getSelectionModel().getSelectedItem().getNombre() +"?");
        Optional<ButtonType> resultado = mensaje.showAndWait();
        if (resultado.get() == ButtonType.OK) {//Se controla que opcion elije el usuario
            ejercicioService.delete(abmEjercicio.TdatosEj.getSelectionModel().getSelectedItem());
        }
    }

    public void validar(){
        //Evitamos que se dupliquen los errores cada vez que se producen en la misma escena
        abmEjercicio.errores.clear();
        //Validacion de que los campos no esten vacios para poder cargarlos
        if (abmEjercicio.TFnombreEj.getText().isEmpty()) {
            abmEjercicio.errores.add("El campo Nombre es obligatorio");
        }
        if (abmEjercicio.TAdescripcionEj.getText().isEmpty()) {
            abmEjercicio.errores.add("El campo Descripción es obligatorio");
        }

        if (abmEjercicio.CBgrupoMuscularEj.getSelectionModel().isEmpty()) {
            abmEjercicio.errores.add("El campo Grupo muscular es obligatorio");
        }

    }

    public void funcionActualizar() {

        ejercicioService.delete(abmEjercicio.TdatosEj.getSelectionModel().getSelectedItem());
        ejercicioService.update(new Ejercicio(abmEjercicio.TFnombreEj.getText(), abmEjercicio.TAdescripcionEj.getText(), abmEjercicio.CBgrupoMuscularEj.getSelectionModel().getSelectedItem()));
    }

    //------------------------Funcion Imagenes----------------------------
    public void funcionColocarImagen(){
        URL linkLimpiar = getClass().getResource("/images/limpiar.png");
        URL linkEliminar = getClass().getResource("/images/eliminar.png");
        URL linkModificar = getClass().getResource("/images/modificar.png");

        Image imagenLimpiar = new Image(linkLimpiar.toString(),30,20,false,true);
        Image imagenEliminar = new Image(linkEliminar.toString(),28,25,false,true);
        Image imagenModificar = new Image(linkModificar.toString(),22,22,false,true);

        abmEjercicio.BTlimpiarEj.setGraphic((new ImageView(imagenLimpiar)));
        abmEjercicio.BTeliminarEj.setGraphic((new ImageView(imagenEliminar)));
        abmEjercicio.BTactualizarEj.setGraphic((new ImageView(imagenModificar)));
    }


}