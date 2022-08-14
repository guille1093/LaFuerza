package com.fuerza.vistas.ABMRutina;

import com.fuerza.modelo.Cliente;
import com.fuerza.modelo.DetalleRutina;
import com.fuerza.modelo.Entrenamiento;
import com.fuerza.modelo.RutinaDiaria;
import com.fuerza.servicios.Cliente.ClienteServiceImpl;
import com.fuerza.servicios.DetalleRutina.DetalleRutinaServiceImpl;
import com.fuerza.servicios.Ejercicio.EjercicioServiceImpl;
import com.fuerza.servicios.Entrenador.EntrenadorServiceImpl;
import com.fuerza.servicios.Entrenamiento.EntrenamientoServiceImpl;
import com.fuerza.servicios.GrupoMuscular.GrupoMuscularServiceImpl;
import com.fuerza.servicios.RutinaDiaria.RutinaDiariaServiceImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ABMRutinaManager {

    @Autowired
    ABMRutina abmRutina;

    @Autowired
    private DetalleRutinaServiceImpl detalleRutinaService;

    @Autowired
    private EjercicioServiceImpl ejercicioServicee;

    @Autowired
    private GrupoMuscularServiceImpl grupoMuscularService;

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private EntrenamientoServiceImpl entrenamientoService;

    @Autowired
    private EntrenadorServiceImpl entrenadorService;

    @Autowired
    private RutinaDiariaServiceImpl rutinaDiariaService;


    //Solo acepta valores numericos
    String regexDNI = "\\d{8}";

    String regeValorNumerico = "\\d{}";

    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public void validarRutinaDiaria(){
        abmRutina.erroresGenerarRutina.clear();

        if (abmRutina.DPfechaInicioR.getValue() == null) {
            abmRutina.erroresGenerarRutina.add("El campo Fecha de inicio es obligatorio");
        }
        if (abmRutina.CBdiaR.getSelectionModel().isEmpty()) {
            abmRutina.erroresGenerarRutina.add("El campo Día es obligatorio");
        }
        if (abmRutina.TFdniEntrenadorR.getText().isEmpty()) {
            abmRutina.erroresGenerarRutina.add("El campo DNI del Entrenador es obligatorio");
        }
        if (abmRutina.TFdniClienteR.getText().isEmpty()) {
            abmRutina.erroresGenerarRutina.add("El campo DNI del cliente es obligatorio");
        }

        if (!Pattern.matches(regexDNI, abmRutina.TFdniEntrenadorR.getText())) {
            abmRutina.erroresGenerarRutina.add("Formato invalido. El formato correcto del DNI entrenador es: 99.999.999(sin puntos)");
        }

        if (!Pattern.matches(regexDNI, abmRutina.TFdniClienteR.getText())) {
            abmRutina.erroresGenerarRutina.add("Formato invalido. El formato correcto del DNI Cliente es: 99.999.999(sin puntos)");
        }

        try {
            //Controlamos la fecha de ingreso que no sea mayor a el dia de hoy
            LocalDate fecha1 = LocalDate.now();
            LocalDate fecha2 = abmRutina.DPfechaInicioR.getValue();
            if (fecha1.compareTo(fecha2) > 0) {
                abmRutina.erroresGenerarRutina.add("Debe seleccionar una Fecha de inicio que sea igual o mayor al dia de hoy" +"("+fecha1.format(formateador)+")");
            }
        }catch (NullPointerException e){
            abmRutina.erroresGenerarRutina.add("Debe rellenar los campos de fechas ya que son obligatorios");
        }

    }

    public void validar() {

        abmRutina.errores.clear();

        if (abmRutina.CBgrupoMuscularR.getSelectionModel().isEmpty()) {
            abmRutina.errores.add("El campo Grupo muscular es obligatorio");
        }
        if (abmRutina.CBnombreEjerciciosR.getSelectionModel().isEmpty()) {
            abmRutina.errores.add("El campo Nombre del ejercicio es obligatorio");
        }
        if (abmRutina.CBseriesR.getSelectionModel().isEmpty()) {
            abmRutina.errores.add("El campo Series es obligatorio");
        }
        if (abmRutina.TFrepeticionesR.getText().isEmpty()) {
            abmRutina.errores.add("El campo Repeticiones es obligatorio");
        }
        if (abmRutina.TFdescansosR.getText().isEmpty()) {
            abmRutina.errores.add("El campo Descansos en minutos es obligatorio");
        }
        if (!abmRutina.TFrepeticionesR.getText().matches("[0-9]*")) {
            abmRutina.errores.add("Solo se admiten numeros enteros en el campo Repeticiones");
        }
        if (!abmRutina.TFdescansosR.getText().matches("[0-9]*")) {
            abmRutina.errores.add("Solo se admiten numeros enteros en el campo Descanso en minutos");
        }

    }

    public void funcionBuscarDniCliente(){



        if (clienteService.findById(abmRutina.TFdniClienteR.getText()) == null) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText(null);
            mensaje.setContentText("EL DNI: " + abmRutina.TFdniClienteR.getText() + " no se encuentra registrado");
            mensaje.show();
            abmRutina.LnombreClienteR.setText("");

        }else {
            abmRutina.LnombreClienteR.setText(clienteService.findById(abmRutina.TFdniClienteR.getText()).getNombre() + " " + clienteService.findById(abmRutina.TFdniClienteR.getText()).getApellido());
        }

        abmRutina.detalleRutinas.clear();
        abmRutina.detalleRutinas.addAll(detalleRutinaService.findBycliente_dni(abmRutina.TFdniClienteR.getText()));
        abmRutina.TdatosR.setItems(abmRutina.detalleRutinas);

        if(abmRutina.errores.size() == 0){
            abmRutina.BTgenerarRutinaR.setDisable(false);
        }


    }

    public void funcionBuscarDniEntrenador(){

        if (entrenadorService.findById(abmRutina.TFdniEntrenadorR.getText()) == null) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText(null);
            mensaje.setContentText("EL DNI: " + abmRutina.TFdniEntrenadorR.getText() + " no se encuentra registrado");
            mensaje.show();
            abmRutina.LnombreClienteR.setText("");

        }else {
            abmRutina.LnombreEntrenadorR.setText(entrenadorService.findById(abmRutina.TFdniEntrenadorR.getText()).getNombre() + " " + entrenadorService.findById(abmRutina.TFdniEntrenadorR.getText()).getApellido());
        }
    }


    public void funcionCargarDetallesRutina()
    {
        abmRutina.CBdiaR.setItems(abmRutina.dia);
        abmRutina.CBseriesR.setItems(abmRutina.series);
        abmRutina.grupoMusculares.addAll(grupoMuscularService.findAll());
        abmRutina.CBgrupoMuscularR.setItems(abmRutina.grupoMusculares);


        abmRutina.detalleRutinas.clear();
        abmRutina.detalleRutinas.addAll(detalleRutinaService.findBycliente_dni(abmRutina.TFdniClienteR.getText()));
        abmRutina.TdatosR.setItems(abmRutina.detalleRutinas);
        abmRutina.detalleRutinas.clear();


    }


    public void funcionAgregarEjercicio(){

        validar();
        if(abmRutina.errores.size() > 0) {
            //Cadena para cargar los errores
            String cadenaErrores = "";
            for(int i=0; i < abmRutina.errores.size(); i++){
                //Hacemos que se agreguen los errores producidos
                cadenaErrores += "* " + abmRutina.errores.get(i) + "\n";
            }
            //Se crea la alerta
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Se ha producido uno o varios Errores");
            mensaje.setContentText(cadenaErrores);
            mensaje.show();
            return;
        }


        detalleRutinaService.save(new DetalleRutina(
                abmRutina.CBnombreEjerciciosR.getSelectionModel().getSelectedItem(),
                clienteService.findById(abmRutina.TFdniClienteR.getText()),
                Integer.parseInt(abmRutina.TFrepeticionesR.getText()) ,
                Integer.parseInt(abmRutina.TFdescansosR.getText()) ,
                Integer.parseInt(abmRutina.CBseriesR.getSelectionModel().getSelectedItem()),
                abmRutina.CBdiaR.getSelectionModel().getSelectedItem(),
                RT
                ));


        funcionLimpiarCampos();

        //Se crea la alerta para avisar que se registro de manera correcta
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Aviso");
        mensaje.setHeaderText(null);
        mensaje.setContentText("Se registro de manera exitosa el Ejercicio ");
        mensaje.showAndWait();



    }

    public RutinaDiaria RT = new RutinaDiaria();

   public void funcionGenerarRutina(){

       validarRutinaDiaria();

       if(abmRutina.erroresGenerarRutina.size() > 0) {
           //Cadena para cargar los errores
           String cadenaErrores = "";
           for(int i=0; i < abmRutina.erroresGenerarRutina.size(); i++){
               //Hacemos que se agreguen los errores producidos
               cadenaErrores += "* " + abmRutina.erroresGenerarRutina.get(i) + "\n";
           }
           //Se crea la alerta
           Alert mensaje = new Alert(Alert.AlertType.ERROR);
           mensaje.setTitle("Error");
           mensaje.setHeaderText("Se ha producido uno o varios Errores");
           mensaje.setContentText(cadenaErrores);
           mensaje.show();
           return;
       }



       RT = rutinaDiariaService.save(new RutinaDiaria(
               entrenamientoService.save(new Entrenamiento(
                       clienteService.findById(abmRutina.TFdniClienteR.getText()),
                       entrenadorService.findById(abmRutina.TFdniEntrenadorR.getText()),
                       false,
                       abmRutina.DPfechaInicioR.getValue(),
                       0.0))
       ));

       //Se crea la alerta para avisar que se registro de manera correcta
       Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
       mensaje.setTitle("Aviso");
       mensaje.setHeaderText(null);
       mensaje.setContentText("Se creó su rutina exitosamente. Ya puede agregar los ejercicios ");
       mensaje.showAndWait();

       if(abmRutina.errores.size() == 0){
           abmRutina.BTgenerarRutinaR.setDisable(true);
           abmRutina.CBgrupoMuscularR.setDisable(false);
           abmRutina.CBnombreEjerciciosR.setDisable(false);
           abmRutina.CBseriesR.setDisable(false);
           abmRutina.BTagregarR.setDisable(false);
           abmRutina.TFrepeticionesR.setDisable(false);
           abmRutina.TFdescansosR.setDisable(false);
           abmRutina.DPfechaInicioR.setDisable(true);
           abmRutina.TFdniEntrenadorR.setDisable(true);

       }

    }


    public void funcionLimpiarCampos()
    {
        //Limpiamos los campos

        abmRutina.TFdescansosR.clear();
        abmRutina.TFrepeticionesR.clear();
        abmRutina.CBseriesR.setValue(null);
        abmRutina.CBgrupoMuscularR.getSelectionModel().clearSelection();
        abmRutina.CBnombreEjerciciosR.setValue(null);
        abmRutina.CBgrupoMuscularR.getSelectionModel().clearSelection();
        abmRutina.TdatosR.getSelectionModel().clearSelection();


        //Deshabilitacion de botones

        abmRutina.BTagregarR.setDisable(false);
        abmRutina.BTgenerarRutinaR.setDisable(true);
        abmRutina.BTelimiarR.setDisable(true);


    }
    public void funcionFiltrarCB(){
        try {
            abmRutina.ejercicios.clear();
            abmRutina.ejercicios.addAll(ejercicioServicee.findBygrupoMusculares_nombreGrupoMuscular(abmRutina.CBgrupoMuscularR.getSelectionModel().getSelectedItem().toString()));
            abmRutina.CBnombreEjerciciosR.setItems(abmRutina.ejercicios);
        }catch (NullPointerException e ){

        }
    }

    public void funcionEliminarRutina(){

        //Se crea la alerta
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle("Eliminar");
        mensaje.setHeaderText("Se eliminara de la lista el elemento seleccionado");
        mensaje.setContentText("¿Esta seguro que desea eliminar el elemento?");
        Optional<ButtonType> resultado = mensaje.showAndWait();
        if (resultado.get() == ButtonType.OK) {//Se controla que opcion elije el usuario
            detalleRutinaService.delete(abmRutina.TdatosR.getSelectionModel().getSelectedItem());
        }
    }


}
