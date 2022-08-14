package com.fuerza.vistas.RutinaActiva;

import com.fuerza.servicios.Cliente.ClienteServiceImpl;
import com.fuerza.servicios.DetalleRutina.DetalleRutinaServiceImpl;
import com.fuerza.servicios.Entrenamiento.EntrenamientoServiceImpl;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


@Component
public class RutinaActivaManager {

    //-------------------Variables------------------------
    @Autowired
    public RutinaActiva rutinaActiva;

    @Autowired
    public ClienteServiceImpl clienteService;

    @Autowired
    public DetalleRutinaServiceImpl detalleRutinaService;

    @Autowired
    public EntrenamientoServiceImpl entrenamientoService;

    String DIA[] = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");





    //------------------------Funcion Imagenes----------------------------
    public void funcionColocarImagen(){
        URL linkVolver = getClass().getResource("/images/volver.png");
        URL linkLupa = getClass().getResource("/images/lupa.png");

        Image imagenVolver = new Image(linkVolver.toString(),25,30,false,true);
        Image imagenLupa = new Image(linkLupa.toString(),25,30,false,true);


        rutinaActiva.BTvolverRA.setGraphic((new ImageView(imagenVolver)));
        rutinaActiva.BTbuscarRA.setGraphic((new ImageView(imagenLupa)));


    }

    public void funcionDiaActual(){

        rutinaActiva.LdiaRA.setText(DIA[LocalDate.now().getDayOfWeek().getValue()]);
    }

    public void funcionBuscarDniCliente(){

        if (clienteService.findById(rutinaActiva.TFdniClienteRA.getText()) == null) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText(null);
            mensaje.setContentText("EL DNI: " + rutinaActiva.TFdniClienteRA.getText() + " no se encuentra registrado");
            mensaje.show();
            rutinaActiva.LnombreClienteRA.setText("");

        }else {
            rutinaActiva.LnombreClienteRA.setText(clienteService.findById(rutinaActiva.TFdniClienteRA.getText()).getNombre() + " " + clienteService.findById(rutinaActiva.TFdniClienteRA.getText()).getApellido());
            funcionCargarRutinaActivas();
            funcionCargarFechas();
        }

        //rutinaActiva.detalleRutinas.clear();
        //rutinaActiva.detalleRutinas.addAll(detalleRutinaService.findBycliente_dni(rutinaActiva.TFdniClienteR.getText()));
        //rutinaActiva.TdatosR.setItems(rutinaActiva.detalleRutinas);


    }

    public void funcionCargarRutinaActivas(){
        rutinaActiva.detalleRutinas.addAll(detalleRutinaService.findBydiaAndCliente_dni(DIA[LocalDate.now().getDayOfWeek().getValue()], rutinaActiva.TFdniClienteRA.getText()));
        rutinaActiva.TCdatosRA.setItems(rutinaActiva.detalleRutinas);
    }

    public void funcionCargarFechas (){

        DateTimeFormatter formateadorw = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        //lo pongo en una variable para que despues se pueda comparar
        String fechaInicio =  entrenamientoService.findByfinalizadoAndCliente_Dni(false, rutinaActiva.TFdniClienteRA.getText()).toString();
        rutinaActiva.LfechainicioRA.setText(fechaInicio);

        //ACA DEBERÍAS COMPARAR Y CALCUALAR LA FECHA FINAL. NO SE OCUPÓ FORMATEADOR PORQUE EN LA CLASE PRINCIPAL DE "Entrenamiento ya está en to string"


        //rutinaActiva.LfechafinRA.setText();

        // una vez ocupada la fecha vaciamos la variable
        fechaInicio = "";

    }
}
