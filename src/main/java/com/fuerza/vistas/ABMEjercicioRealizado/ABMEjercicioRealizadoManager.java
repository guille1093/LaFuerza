package com.fuerza.vistas.ABMEjercicioRealizado;

import com.fuerza.modelo.EntrenamientoRealizado;
import com.fuerza.servicios.Cliente.ClienteServiceImpl;
import com.fuerza.servicios.DetalleRutina.DetalleRutinaServiceImpl;
import com.fuerza.servicios.Ejercicio.EjercicioServiceImpl;
import com.fuerza.servicios.Entrenamiento.EntrenamientoServiceImpl;
import com.fuerza.servicios.EntrenamientoRealizado.EntrenamientoRealizadoServiceImpl;
import com.fuerza.servicios.GrupoMuscular.GrupoMuscularServiceImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ABMEjercicioRealizadoManager {
    @Autowired
    ABMEjercicioRealizado abmEjercicioRealizado;

    @Autowired
    private DetalleRutinaServiceImpl detalleRutinaService;

    @Autowired
    private EjercicioServiceImpl ejercicioService;

    @Autowired
    private EntrenamientoRealizadoServiceImpl entrenamientoRealizadoService;

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private EntrenamientoServiceImpl entrenamientoService;

    @Autowired
    private GrupoMuscularServiceImpl grupoMuscularService;

    String DIA[] = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void cargarEjerciciosCB() {
        validarDNI();
        if(abmEjercicioRealizado.errores.size() > 0) {
            //Cadena para cargar los errores
            String cadenaErrores = "";
            for(int i=0; i < abmEjercicioRealizado.errores.size(); i++){
                //Hacemos que se agreguen los errores producidos
                cadenaErrores += "* " + abmEjercicioRealizado.errores.get(i) + "\n";
            }
            //Se crea la alerta
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Se ha producido uno o varios Errores");
            mensaje.setContentText(cadenaErrores);
            mensaje.show();
            return;
        }
        //Habilitar el comboBox, textField y botones
        abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: grey");
        abmEjercicioRealizado.CBEjerAsociados.setDisable(false);
        abmEjercicioRealizado.btCalificar.setDisable(false);
        abmEjercicioRealizado.btAgregar.setDisable(false);
        abmEjercicioRealizado.TFDescanso.setDisable(false);
        abmEjercicioRealizado.btLimpiar.setDisable(false);
        abmEjercicioRealizado.TFPeso.setDisable(false);
        abmEjercicioRealizado.TFRepeticiones.setDisable(false);
        abmEjercicioRealizado.TFSeries.setDisable(false);
        abmEjercicioRealizado.CBgrupoMusculares.setDisable(false);
        abmEjercicioRealizado.ejercicios.clear();
        abmEjercicioRealizado.ejercicios.addAll(detalleRutinaService.findBydiaAndCliente_dni(DIA[LocalDate.now().getDayOfWeek().getValue()],abmEjercicioRealizado.TFDniCliente.getText()));
        abmEjercicioRealizado.CBEjerAsociados.setItems(abmEjercicioRealizado.ejercicios);
       }

    public void funcionBuscarDniCliente(){


        if (clienteService.findById(abmEjercicioRealizado.TFDniCliente.getText()) == null) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText(null);
            mensaje.setContentText("EL DNI: " + abmEjercicioRealizado.TFDniCliente.getText() + " no se encuentra registrado");
            mensaje.show();
            abmEjercicioRealizado.LBNombreCliente.setText("");
            abmEjercicioRealizado.ejercicios.clear();
            abmEjercicioRealizado.CBEjerAsociados.setItems(abmEjercicioRealizado.ejercicios);
            abmEjercicioRealizado.ejerciciosRealizados.clear();
            abmEjercicioRealizado.TVEjerciciosRealizado.setItems(abmEjercicioRealizado.ejerciciosRealizados);
            abmEjercicioRealizado.LBVolumenTotal.setText("");
            abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: grey");
            abmEjercicioRealizado.CBEjerAsociados.setDisable(true);
            abmEjercicioRealizado.btCalificar.setDisable(true);
            abmEjercicioRealizado.btAgregar.setDisable(true);
            abmEjercicioRealizado.btEliminar.setDisable(true);
            abmEjercicioRealizado.TFDescanso.setDisable(true);
            abmEjercicioRealizado.btLimpiar.setDisable(true);
            abmEjercicioRealizado.TFPeso.setDisable(true);
            abmEjercicioRealizado.TFRepeticiones.setDisable(true);
            abmEjercicioRealizado.TFSeries.setDisable(true);
            abmEjercicioRealizado.CBgrupoMusculares.setDisable(true);

        }else {
            abmEjercicioRealizado.LBNombreCliente.setText(clienteService.findById(abmEjercicioRealizado.TFDniCliente.getText()).getNombre() + " " + clienteService.findById(abmEjercicioRealizado.TFDniCliente.getText()).getApellido());
            cargarEjerciciosCB();
            cargarDatosObservableListTabla();
            funcionCalcularVolumenTotal();
            funcioncargarGrupoMusculares();
        }

    }

    public void LimpiarCampos() {
        abmEjercicioRealizado.TFDescanso.setText("");
        abmEjercicioRealizado.TFPeso.setText("");
        abmEjercicioRealizado.TFRepeticiones.setText("");
        abmEjercicioRealizado.TFSeries.setText("");
        abmEjercicioRealizado.CBEjerAsociados.setValue(null);
        //set the border color to default
        abmEjercicioRealizado.TFDescanso.setStyle("-fx-border-color: grey");
        abmEjercicioRealizado.TFPeso.setStyle("-fx-border-color: grey");
        abmEjercicioRealizado.TFRepeticiones.setStyle("-fx-border-color: grey");
        abmEjercicioRealizado.TFSeries.setStyle("-fx-border-color: grey");
        abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: grey");

    }

    public void cargarDatosObservableListTabla() {
        abmEjercicioRealizado.ejerciciosRealizados.clear();
        abmEjercicioRealizado.ejerciciosRealizados.addAll(entrenamientoRealizadoService.findByfechaEntrenamientoRealizadoAndCliente_dni(LocalDate.now(), abmEjercicioRealizado.TFDniCliente.getText()));
        abmEjercicioRealizado.TVEjerciciosRealizado.setItems(abmEjercicioRealizado.ejerciciosRealizados);
    }

    public void funcioncargarGrupoMusculares(){
        abmEjercicioRealizado.grupoMusculares.clear();
        abmEjercicioRealizado.grupoMusculares.addAll(grupoMuscularService.findAll());
        abmEjercicioRealizado.CBgrupoMusculares.setItems(abmEjercicioRealizado.grupoMusculares);
    }

    public void guardarEntrenamientoRealizado() {
        validar();
        if(abmEjercicioRealizado.errores.size() > 0) {
            //Cadena para cargar los errores
            String cadenaErrores = "";
            for(int i=0; i < abmEjercicioRealizado.errores.size(); i++){
                //Hacemos que se agreguen los errores producidos
                cadenaErrores += "* " + abmEjercicioRealizado.errores.get(i) + "\n";
            }
            //Se crea la alerta
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Se ha producido uno o varios Errores");
            mensaje.setContentText(cadenaErrores);
            mensaje.show();

            return;
        }

        try {
            entrenamientoRealizadoService.save(new EntrenamientoRealizado(
                    clienteService.findById(abmEjercicioRealizado.TFDniCliente.getText()),
                    entrenamientoService.findByCliente_dni(abmEjercicioRealizado.TFDniCliente.getText()),
                    LocalDate.now(),
                    Double.parseDouble(abmEjercicioRealizado.TFPeso.getText()),
                    Integer.parseInt(abmEjercicioRealizado.TFRepeticiones.getText()),
                    Integer.parseInt(abmEjercicioRealizado.TFSeries.getText()),
                    Integer.parseInt(abmEjercicioRealizado.TFDescanso.getText()),
                    ejercicioService.findById(abmEjercicioRealizado.CBEjerAsociados.getSelectionModel().getSelectedItem().toString()),
                    funcionCaluclarVolumenDiario( Integer.parseInt(abmEjercicioRealizado.TFSeries.getText()), Integer.parseInt(abmEjercicioRealizado.TFRepeticiones.getText()), Integer.parseInt(abmEjercicioRealizado.TFPeso.getText()))));
            abmEjercicioRealizado.TVEjerciciosRealizado.setItems(abmEjercicioRealizado.ejerciciosRealizados);

            LimpiarCampos();
        }catch (NullPointerException e){

        }





    }

    public void validar(){
        //Evitamos que se dupliquen los errores cada vez que se producen en la misma escena
        abmEjercicioRealizado.errores.clear();
        //Validacion de que los campos no esten vacios para poder cargarlos en la base de datos
        if(abmEjercicioRealizado.TFDescanso.getText().isEmpty()){
            abmEjercicioRealizado.errores.add("El campo Descanso es obligatorio");
            abmEjercicioRealizado.TFDescanso.setStyle("-fx-border-color: red");
        }else{
            abmEjercicioRealizado.TFDescanso.setStyle("-fx-border-color: green");
        }
        if(abmEjercicioRealizado.TFPeso.getText().isEmpty()){
            abmEjercicioRealizado.errores.add("El campo Peso es obligatorio");
            abmEjercicioRealizado.TFPeso.setStyle("-fx-border-color: red");
        }else{
            abmEjercicioRealizado.TFPeso.setStyle("-fx-border-color: green");
        }
        if(abmEjercicioRealizado.TFRepeticiones.getText().isEmpty()){
            abmEjercicioRealizado.errores.add("El campo Repeticiones es obligatorio");
            abmEjercicioRealizado.TFRepeticiones.setStyle("-fx-border-color: red");
        }else{
            abmEjercicioRealizado.TFRepeticiones.setStyle("-fx-border-color: green");
        }
        if(abmEjercicioRealizado.TFSeries.getText().isEmpty()){
            abmEjercicioRealizado.errores.add("El campo Series es obligatorio");
            abmEjercicioRealizado.TFSeries.setStyle("-fx-border-color: red");
        }else{
            abmEjercicioRealizado.TFSeries.setStyle("-fx-border-color: green");
        }

        if (abmEjercicioRealizado.CBEjerAsociados.getSelectionModel().isEmpty()) {
            abmEjercicioRealizado.errores.add("El campo Ejercicios Asociados es obligatorio");
        }

        for (EntrenamientoRealizado entrenamientoRealizado: abmEjercicioRealizado.ejerciciosRealizados){
            String A = entrenamientoRealizado.getEjercicios().toString();
            String B = abmEjercicioRealizado.CBEjerAsociados.getSelectionModel().getSelectedItem().getEjercicios().toString();

            if (A.compareTo(B) == 0){
                abmEjercicioRealizado.errores.add("El ejercicio " + entrenamientoRealizado.getEjercicios().toString() + " ya fué cargado");

            }
        }

        //Validacion de que el campo descanso sea un numero
        if(!abmEjercicioRealizado.TFDescanso.getText().matches("[0-9]*")) {
            abmEjercicioRealizado.errores.add("El campo Descanso debe ser un numero");
            abmEjercicioRealizado.TFDescanso.setStyle("-fx-border-color: red");}
        //Validacion de que el campo peso sea un numero
        if (!abmEjercicioRealizado.TFPeso.getText().matches("[0-9]*")) {
            abmEjercicioRealizado.errores.add("El campo Peso debe ser un numero");
            abmEjercicioRealizado.TFPeso.setStyle("-fx-border-color: red");}
        //Validacion de que el campo repeticiones sea un numero
        if (!abmEjercicioRealizado.TFRepeticiones.getText().matches("[0-9]*")) {
            abmEjercicioRealizado.errores.add("El campo Repeticiones debe ser un numero");
            abmEjercicioRealizado.TFRepeticiones.setStyle("-fx-border-color: red");}
        //Validacion de que el campo series sea un numero
        if (!abmEjercicioRealizado.TFSeries.getText().matches("[0-9]*")) {
            abmEjercicioRealizado.errores.add("El campo Series debe ser un numero");
            abmEjercicioRealizado.TFSeries.setStyle("-fx-border-color: red");}
    }
    public void validarDNI() {
        //Evitamos que se dupliquen los errores cada vez que se producen en la misma escena
        abmEjercicioRealizado.errores.clear();
        //Validacion de que los campos no esten vacios para poder cargarlos en la base de datos
        if (abmEjercicioRealizado.TFDniCliente.getText().isEmpty()) {
            abmEjercicioRealizado.errores.add("El campo DNI es obligatorio");
            abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: red");
        } else {
            abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: green");
        }
        String regexDNI = "\\d{8}";
        if (!Pattern.matches(regexDNI, abmEjercicioRealizado.TFDniCliente.getText())) {
            abmEjercicioRealizado.errores.add("Formato invalido. El formato correcto del DNI es: 99.999.999(sin puntos)");
            abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: red");
        } else {
            abmEjercicioRealizado.TFDniCliente.setStyle("-fx-border-color: green");
        }
    }

    public void funcionEliminarEntrenamientoRealizado()
    {
        //Se crea la alerta
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle("Eliminar");
        mensaje.setHeaderText("Se eliminara de la lista el elemento seleccionado");
        mensaje.setContentText("¿Esta seguro que desea eliminar el elemento?");
        Optional<ButtonType> resultado = mensaje.showAndWait();
        if (resultado.get() == ButtonType.OK) {//Se controla que opcion elije el usuario
            entrenamientoRealizadoService.delete(abmEjercicioRealizado.TVEjerciciosRealizado.getSelectionModel().getSelectedItem());
            abmEjercicioRealizado.btEliminar.setDisable(true);
        }
    }

    public void funcionCalcularVolumenTotal(){

        Integer suma = 0;

        for (EntrenamientoRealizado entrenamientoRealizado : entrenamientoRealizadoService.findByfechaEntrenamientoRealizadoAndCliente_dni(LocalDate.now(), abmEjercicioRealizado.TFDniCliente.getText())){
            suma += entrenamientoRealizado.getVolumenDiarioEntrenamientoRealizado();
        }

        abmEjercicioRealizado.LBVolumenTotal.setText(suma.toString());
    }

    public int funcionCaluclarVolumenDiario(int series, int repeticiones, int peso){
        return series * repeticiones * peso;
    }

    public void funcionCaulcularVolumenGrupoMuscular(){

        Integer suma = 0;

        System.out.println();

        for (EntrenamientoRealizado entrenamientoRealizado: entrenamientoRealizadoService.
                findByClienteDniAndEjercicios_grupoMusculares(abmEjercicioRealizado.TFDniCliente.getText(),
                        abmEjercicioRealizado.CBgrupoMusculares.getSelectionModel().getSelectedItem())){
            suma+= entrenamientoRealizado.getVolumenDiarioEntrenamientoRealizado();
        }
        abmEjercicioRealizado.LBVolumenGrupoMuscular.setText((suma.toString()));
    }

}