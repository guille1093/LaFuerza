package com.fuerza.vistas.RutinaSemanalActiva;

import com.fuerza.servicios.Cliente.ClienteServiceImpl;
import com.fuerza.servicios.DetalleRutina.DetalleRutinaServiceImpl;
import com.fuerza.servicios.Entrenamiento.EntrenamientoServiceImpl;
import com.fuerza.servicios.EntrenamientoRealizado.EntrenamientoRealizadoServiceImpl;
import com.fuerza.vistas.ABMRutina.ABMRutina;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class RutinaSemanalActivaManager {

    @Autowired
    private RutinaSemanalActiva rutinaSemanalActiva;

    @Autowired
    private EntrenamientoRealizadoServiceImpl entrenamientoRealizadoService;

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private DetalleRutinaServiceImpl detalleRutinaService;

    @Autowired
    ABMRutina abmRutina;



    //--------------------------------Metodo para la Tabla de datos-------------------------
    public void funcionBuscarDniCliente(){

        if (clienteService.findById(rutinaSemanalActiva.tfdni.getText()) == null) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText(null);
            mensaje.setContentText("EL DNI: " + rutinaSemanalActiva.tfdni.getText() + " no se encuentra registrado");
            mensaje.show();
            rutinaSemanalActiva.LnombreRSA.setText("");

        }else {
            rutinaSemanalActiva.LnombreRSA.setText(clienteService.findById(rutinaSemanalActiva.tfdni.getText()).getNombre() + " " + clienteService.findById(rutinaSemanalActiva.tfdni.getText()).getApellido());
        }

        abmRutina.detalleRutinas.clear();
        abmRutina.detalleRutinas.addAll(detalleRutinaService.findBycliente_dni(rutinaSemanalActiva.tfdni.getText()));
        rutinaSemanalActiva.TdatosRSA.setItems(abmRutina.detalleRutinas);


    }

    //Cargar las imagenes de los iconos
    public void funcionColocarImagen(){

        URL linkVolver = getClass().getResource("/images/volver.png");
        URL linkBuscar = getClass().getResource("/images/buscar.png");


        Image imagenVolver = new Image(linkVolver.toString(),25,30,false,true);
        Image imagenBuscar = new Image(linkBuscar.toString(),15,15,false,true);


        rutinaSemanalActiva.BTvolverRSA.setGraphic((new ImageView(imagenVolver)));
        rutinaSemanalActiva.dnibt.setGraphic((new ImageView(imagenBuscar)));

    }

}
