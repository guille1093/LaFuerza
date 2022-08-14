package com.fuerza.vistas.ABMRendimiento;

import com.fuerza.MainApp;
import com.fuerza.servicios.Entrenamiento.EntrenamientoServiceImpl;
import com.fuerza.vistas.FxController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView

public class ABMRendimiento implements Initializable, FxController {
    @Lazy
    @Autowired
    private ABMRendimientoManager abmrendimientoManager;

    @Autowired
    private EntrenamientoServiceImpl entrenamientoService;

    @Autowired
    MainApp mainApp;

    @FXML
    public Button BT_Rating;

    @FXML
    public org.controlsfx.control.Rating Rating;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        BT_Rating.setOnAction(event -> {
            entrenamientoService.updateNota(Double.parseDouble(Rating.ratingProperty().getValue().toString()), mainApp.S);
            abmrendimientoManager.volver();
        });
    }
}